package com.groupmeeting.global.client;

import com.groupmeeting.auth.key.OidcPublicKeyList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "apple-auth",  url = "${oauth.apple.public-key-url}")
public interface AppleAuthClient {
    @GetMapping
    OidcPublicKeyList getPublicKeys();
}
