package com.groupmeeting.meet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/link")
public class ShareController {

    @GetMapping("/meet/id")
    public String meet(InviteDto inviteDto, Model model) {

        var invate  = new InviteDto(inviteDto.id(), inviteDto.name(), inviteDto.imageUrl());
        model.addAttribute("invite", invate);
        return "inviteKakao";
    }

    @GetMapping("/test")
    public String test() {
        return "deepLink";
    }

}
