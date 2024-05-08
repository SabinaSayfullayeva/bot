package com.example.project_for_doctors.controller;

import com.example.project_for_doctors.service.WebhookService;
import com.itextpdf.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/telegram")
public class BotController {

    private final WebhookService webhookService;


    @PostMapping
    public void getPost(@RequestBody Update update) throws IOException {
        System.out.println(update);
        webhookService.getUpdate(update);
    }

}
