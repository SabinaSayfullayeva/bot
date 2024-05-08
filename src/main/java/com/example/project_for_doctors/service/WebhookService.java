package com.example.project_for_doctors.service;

import com.example.project_for_doctors.constants.RestConstants;
import com.example.project_for_doctors.payload.BotState;
import com.example.project_for_doctors.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    private final Map<Long, BotState> userStates = new HashMap<>();

    public void setUserState(Long chatId, BotState state) {
        userStates.put(chatId, state);
    }

    public BotState getUserState(Long chatId) {
        return userStates.getOrDefault(chatId, BotState.START);
    }

    public static String getChatId(Update update) {
        return UserService.getChatId(update);
    }

    public static Long longChatId(Update update) {
        return UserService.longChatId(update);
    }

    public void whenStart(Update update) {
        SendMessage sendMessage = new SendMessage(getChatId(update),
                "Assalomu alaykum hurmatli " + update.getMessage().getFrom().getFirstName() +
                " bizning botimizga xush kelibsiz \n" +
                        "siz bizning botimizda o'z  sahifangizni yaratishingiz mumkin \n"
        +"bizning botimizdan foydalanish pullik \n"+
                "sahifangizni yaratishingiz uchun ro'yxatdan o'ting");
        sendMessage.setReplyMarkup(forStart());
        restTemplate.postForObject(RestConstants.FOR_MESSAGE, sendMessage, Object.class);
        setUserState(longChatId(update),BotState.REGISTRATION);
    }

private InlineKeyboardMarkup forStart(){
    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

    InlineKeyboardButton button1 = new InlineKeyboardButton();
    button1.setText("Ro'yxatdan o'tish");

    button1.setCallbackData("Royxatdan_otish");

    List<InlineKeyboardButton> row1 = new ArrayList<>();
    row1.add(button1);
    rowsInline.add(row1);
    markupInline.setKeyboard(rowsInline);
    return markupInline;
}

public void getUpdate(Update update){
    if (update.hasMessage() && update.getMessage().hasText()) {
        String message = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();

        if (message.equals("/start")) whenStart(update);}
}

}
