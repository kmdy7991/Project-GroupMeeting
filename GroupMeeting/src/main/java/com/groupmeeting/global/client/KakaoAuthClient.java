package com.groupmeeting.global.client;

import com.groupmeeting.auth.OidcPublicKeyList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "kakao-auth", url = "${oauth.kakao.public-key-info}")
public interface KakaoAuthClient {
    @GetMapping
    OidcPublicKeyList getPublicKeys();
}
