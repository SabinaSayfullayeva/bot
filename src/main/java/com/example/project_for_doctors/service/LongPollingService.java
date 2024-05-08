package com.example.project_for_doctors.service;

import com.example.project_for_doctors.constants.RestConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
@Service
@RequiredArgsConstructor
public class LongPollingService extends TelegramLongPollingBot {
    private final WebhookService webhookService;
    @Override
    public String getBotUsername() {
       return RestConstants.BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return RestConstants.BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
 webhookService.getUpdate(update);
    }
}
