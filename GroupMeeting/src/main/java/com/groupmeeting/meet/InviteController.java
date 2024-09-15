package com.groupmeeting.meet;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/api/invite")
//@Service
@RequiredArgsConstructor
public class InviteController {

    @Value("${apple.private-key-path}")
    private String url;

    @GetMapping("/create")
    public String test() {
        System.out.println(url);
//        System.out.println(environment.getProperty("apple.private-key-path"));
        return "inviteKakao";
    }
}
