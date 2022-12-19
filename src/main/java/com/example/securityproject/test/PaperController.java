package com.example.securityproject.test;

import com.example.securityproject.user.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {

    private final PaperService paperService;
    private final MessageService messageService;

    public PaperController(PaperService paperService, MessageService messageService) {
        this.paperService = paperService;
        this.messageService = messageService;
    }

    @GetMapping("/greeting/{name}")
    public String greeting(@PathVariable String name) {
        return "hello " + messageService.message(name);
    }

    @GetMapping("/mypapers")
    public List<Paper> papers(@AuthenticationPrincipal User user) {
        return paperService.getMyPapers(user.getUsername());
    }

    @GetMapping("/get/{paperId}")
    public Paper getPaper(@AuthenticationPrincipal User user, @PathVariable Long paperId) {
        return paperService.getPaper(paperId);
    }
}
