package com.groupmeeting;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class UseTest {

    public void useAsync() throws Exception {

        List<String> a = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            a.add(i+"");
        }

        var k = a.stream().map(i -> {
            try {
                return call(i);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toList();

        for(var kk : k){
            System.out.println(kk.get());
        }

//        k.forEach(System.out::println);
    }

    @Async
    public CompletableFuture<String> call(String i) throws Exception {
//        Thread.sleep(500);
        log.info("thread = {}", Thread.currentThread().getName());
        CompletableFuture<String> f = new CompletableFuture<>();
        log.info("동작하는지?");
        log.info("call = {}", i );
        return CompletableFuture.supplyAsync(() -> i);
    }

    @Async
    public CompletableFuture<String> call2(String i) throws Exception {
        log.info("asyncthread = {}", Thread.currentThread().getName());
//        Thread.sleep(500);
        return callsync(i);
    }


    public CompletableFuture<String> callsync(String i) {
        log.info("syncthread = {}", Thread.currentThread().getName());
        return CompletableFuture.completedFuture(i);
    }

}
