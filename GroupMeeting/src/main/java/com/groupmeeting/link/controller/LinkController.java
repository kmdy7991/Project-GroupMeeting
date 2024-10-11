package com.groupmeeting.link.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("dp")
public class DeeplinkController {
    private final MeetingService meetingService;


    public DeeplinkController(MeetingService meetingService) {this.meetingService = meetingService;}

    @GetMapping("m/{id}")
    public String getMeetingView(@PathVariable UUID id, Model model) throws ResourceNotFoundException {
        var meeting = meetingService.findByInviteId(id);
        model.addAttribute("meeting", meeting);
        return "meeting";
    }
}
