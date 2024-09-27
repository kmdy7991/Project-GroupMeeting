package com.groupmeeting.unit.meet;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
