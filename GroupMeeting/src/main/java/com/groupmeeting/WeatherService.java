package com.groupmeeting;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class OpenWeatherService {
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String APP_ID;

    public OpenWeatherService(
            OkHttpClient httpClient,
            ObjectMapper objectMapper,
            @Value("${openweather.app-id}") String appId
    ) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.APP_ID = appId;
    }

    @Async
    public CompletableFuture<WeatherInfo> getClosestWeatherInfoFromDateTime(
            GeoLocation geoLocation,
            LocalDateTime dateTime
    ) {
        return getWeatherInfoByLocation(geoLocation).thenApply(response -> {
            var weatherList = response.getList();
            Float temperature = null;
            Integer weatherId = null;
            String weatherIcon = null;
            for (var i = 0; i < weatherList.size() - 1; i++) {
                ZoneId zoneId = ZoneId.systemDefault();
                var before = weatherList.get(i);
                var after = weatherList.get(i + 1);
                var startAtTimestamp = dateTime.atZone(zoneId).toEpochSecond();
                if (!(startAtTimestamp <= after.getDt() && startAtTimestamp >= before.getDt())) continue;

                OpenWeatherResponse.WeatherList target;
                if (Math.abs(startAtTimestamp - after.getDt()) > Math.abs(startAtTimestamp - before.getDt())) {
                    target = before;
                } else {
                    target = after;
                }

                temperature = target.getMain().getTemp();
                weatherId = target.getWeather().get(0).getId();
                weatherIcon = target.getWeather().get(0).getIcon();
            }

            var info = new WeatherInfo(
                    temperature,
                    weatherId,
                    weatherIcon
            );
            return weatherId == null ? null : info;
        });
    }

    public CompletableFuture<OpenWeatherResponse> getWeatherInfoByLocation(GeoLocation geoLocation) {
        CompletableFuture<OpenWeatherResponse> future = new CompletableFuture<>();


        String API_URL = "https://api.openweathermap.org/data/2.5/forecast";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(API_URL).newBuilder();
        urlBuilder.addQueryParameter("lat", geoLocation.latitude().toString());
        urlBuilder.addQueryParameter("lon", geoLocation.longitude().toString());
        urlBuilder.addQueryParameter("appid", APP_ID);
        String UNITS = "metric";
        urlBuilder.addQueryParameter("units", UNITS);
        String CNT = "40";
        urlBuilder.addQueryParameter("cnt", CNT);

        String url = urlBuilder.build().toString();

        Request httpRequest = new Request.Builder()
                .url(url)
                .build();

        httpClient.newCall(httpRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error(e.getMessage());
                future.completeExceptionally(e);
                call.cancel();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    future.completeExceptionally(new IOException("Unexpected code " + response));
                }
                var resBody = response.body();
                if (resBody == null) throw new IOException("No response body found");
                future.complete(objectMapper.readValue(resBody.string(), OpenWeatherResponse.class));

                response.close();
            }
        });
        return future;
    }
}
