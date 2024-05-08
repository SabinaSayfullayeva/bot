package com.example.project_for_doctors;

import com.example.project_for_doctors.repository.UserRepository;
import com.example.project_for_doctors.service.LongPollingService;
import com.example.project_for_doctors.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
@RequiredArgsConstructor

@SpringBootApplication
public class ProjectForDoctorsApplication {





    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(ProjectForDoctorsApplication.class, args);


    }
    @Bean
    public TelegramBotsApi telegramBotsApi(LongPollingService longPollingService) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi=new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(longPollingService);
        return telegramBotsApi;
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



}
