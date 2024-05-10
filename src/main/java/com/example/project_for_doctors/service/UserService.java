package com.example.project_for_doctors.service;

import com.example.project_for_doctors.constants.RestConstants;
import com.example.project_for_doctors.entity.User;
import com.example.project_for_doctors.payload.BotState;
import com.example.project_for_doctors.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final RestTemplate restTemplate;
    private final Map<Long, BotState> userStates = new HashMap<>();


// USERGA BOSHLANG'ICH STATENI TAYINLASH UCHUN

    public void setUserState(Long chatId, BotState state) {
        userStates.put(chatId, state);
    }

    public BotState getUserState(Long chatId) {
        return userStates.getOrDefault(chatId, BotState.START);
    }

    //STRING CHATID NI OLIB OLISH UCHUN
    public static String getChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId().toString();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getFrom().getId().toString();
        }
        return "";
    }

    // LONG CHATID NI OLISH UCHUN
    public static Long longChatId(Update update) {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }
        return null;
    }

    // USERNI SAQLASH UCHUN

    public void saveUser(Update update) {
        String chatId = getChatId(update);
        if (userRepository.existsByChatId(chatId)) {
            User user = userRepository.findByChatId(chatId);
            if (user.getFistName() != null && user.getLastName()!=null&& user.getFatherName()!=null && user.getExperience() != null &&
                    user.getPhoneNumber() != null && user.getSkills() != null
                    && user.getLocation() != null) {
                setUserState(longChatId(update), BotState.ALREADY_REGISTRATED);
                me(update);
            } else {
                setUserState(longChatId(update), BotState.FIRST_NAME);
                userFirstName(update);
            }
        } else {
            User user = User.builder().chatId(chatId).build();
            userRepository.save(user);
            setUserState(longChatId(update), BotState.FIRST_NAME);
            userFirstName(update);
        }
    }
    //MALUMOTLARRNI BOTDA KO'RSATADI AGR USER AVVAL ROYXATDAN O'TGAN BOLSA

    private void me(Update update) {
        String chatId = getChatId(update);
        User user = userRepository.findByChatId(chatId);
        String text = "*Sizning malumotlaringiz \n*"
                + "Firstname : " + user.getFistName() +
                 "\nLastname : " + user.getFistName() +
                 "\nFather name : " + user.getFatherName() +
                "\nPhone number : " + user.getPhoneNumber() +
                "\nSkills : " + user.getSkills() +
                "\nExperience : " + user.getExperience() + " years" +
                "\nLocation : " + user.getLocation();

        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId);
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        editMessageText.setReplyMarkup(forMe());
        editMessageText.enableMarkdown(true);
        editMessageText.setText(text);

        restTemplate.postForObject(RestConstants.EDIT_MESSAGE, editMessageText, Object.class);
        setUserState(longChatId(update), BotState.ALREADY_REGISTRATED);
    }


    //QAYTA RO'YXATDAN O'TISH UCHUN

    private InlineKeyboardMarkup forMe() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        InlineKeyboardButton button2 = new InlineKeyboardButton();

        button1.setText("♻\uFE0FQayta ro'yxatdan o'tish");
        button2.setText("To'lov qilish");

        button1.setCallbackData("userReRegister");
        button2.setCallbackData("to'lov");

        List<InlineKeyboardButton> row1 = new ArrayList<>();
        List<InlineKeyboardButton> row2 = new ArrayList<>();

        row1.add(button1);
        row2.add(button2);

        rowsInline.add(row1);
        rowsInline.add(row2);

        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }


    //QAYTA RO'YXATDAN O'TISH UCHUN
    public void userReRegister(Update update) {
        setUserState(longChatId(update), BotState.FIRST_NAME);
        userFirstName(update);
    }


//USER FIO UCHUN

    public void userFirstName(Update update) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setText("*Ro'yxatdan O'tishni boshladingiz *\n" +
                "Ismingizni kiriting ");
        editMessageText.setChatId(getChatId(update));
        editMessageText.enableMarkdown(true);
        editMessageText.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
        restTemplate.postForObject(RestConstants.EDIT_MESSAGE, editMessageText, Object.class);
        setUserState(longChatId(update), BotState.LAST_NAME);

    }

    //LASTNAME UCHUN

    public void userlastName(Update update) {
        User user = userRepository.findByChatId(getChatId(update));
        String firstName=update.getMessage().getText();
        user.setFistName(firstName);
        userRepository.save(user);
        SendMessage sendMessage = new SendMessage(getChatId(update), "Familiyangizni kiriting : ");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        restTemplate.postForObject(RestConstants.FOR_MESSAGE, sendMessage, Object.class);
        setUserState(longChatId(update), BotState.FATHER_NAME);}


    //FATHER NAME UCHUN

    public void userFatherName(Update update) {
        User user = userRepository.findByChatId(getChatId(update));
        String lastName=update.getMessage().getText();
        user.setLastName(lastName);
        userRepository.save(user);
        SendMessage sendMessage = new SendMessage(getChatId(update), "Sharifingizni kiriting : ");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        restTemplate.postForObject(RestConstants.FOR_MESSAGE, sendMessage, Object.class);
        setUserState(longChatId(update), BotState.SKILLS);}


//SKILL UCHUN

    public void skills(Update update) {
        User user = userRepository.findByChatId(getChatId(update));
        String fatherName=update.getMessage().getText();
        user.setFatherName(fatherName);
        userRepository.save(user);
        SendMessage sendMessage = new SendMessage(getChatId(update), "Kasbingizni kiriting  \n Namuna : lor , xirurg , ...");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        restTemplate.postForObject(RestConstants.FOR_MESSAGE, sendMessage, Object.class);
        setUserState(longChatId(update), BotState.EXPERIENCE);


    }

//EXPERIENCE UCHUN
    public void experience(Update update) {
        User user = userRepository.findByChatId(getChatId(update));
        String skills = update.getMessage().getText();
        user.setSkills(skills);
        userRepository.save(user);
        SendMessage sendMessage = new SendMessage(getChatId(update), "Necha yil malakaga egasiz : ");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        restTemplate.postForObject(RestConstants.FOR_MESSAGE, sendMessage, Object.class);
        setUserState(longChatId(update), BotState.LOCATION);


    }

//LOCATION UCHUN
    public void location(Update update) {
        User user = userRepository.findByChatId(getChatId(update));
        String experience = update.getMessage().getText();
        user.setExperience(experience);
        userRepository.save(user);
        SendMessage sendMessage = new SendMessage(getChatId(update), "Manzilingizni kiriting : ");
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        restTemplate.postForObject(RestConstants.FOR_MESSAGE, sendMessage, Object.class);
        setUserState(longChatId(update), BotState.DOCTOR_PHONE);

    }





// TELEFON RAQAM UCHUN

    public void doctorPhone(Update update) {
        User user = userRepository.findByChatId(getChatId(update));
        String location=update.getMessage().getText();
        user.setLocation(location);
        userRepository.save(user);
        SendMessage sendMessage = new SendMessage(getChatId(update),
                "\uD83D\uDCF11-Telefon raqamingizni kiriting:\n" +
                        "Namuna: +998XXYYYYYYY");
       // sendMessage.setReplyMarkup(generateMarkup());
        restTemplate.postForObject(RestConstants.FOR_MESSAGE, sendMessage, Object.class);
        setUserState(longChatId(update), BotState.REGISTRATION_FINISHED);


    }

     public static ReplyKeyboardMarkup generateMarkup() {
         ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
         List<KeyboardRow> rowList = new ArrayList<>();
         KeyboardRow row1 = new KeyboardRow();
         KeyboardButton button1 = new KeyboardButton();
         button1.setText("\uD83D\uDCF1Mening raqamim");
         button1.setRequestContact(true);
         row1.add(button1);
         rowList.add(row1);
         markup.setKeyboard(rowList);
         markup.setSelective(true);
         markup.setResizeKeyboard(true);
         return markup;
     }
//TELEFON RAQAM XATO KIRITILGANDA
    public void incorrectPhone(Update update) {
        registerDone(update);
    }

    public void registerDone(Update update) {
        User user = userRepository.findByChatId(getChatId(update));
        if (update.getMessage().hasText()) {
            if (isValidPhoneNumber(update.getMessage().getText())) {
                String phoneNumber = update.getMessage().getText();
                user.setPhoneNumber(phoneNumber);
                executeRegisterDone(update, user);
            } else {
                SendMessage sendMessage = new SendMessage(getChatId(update),
                        "Telefon raqam formati xato. Qayta kiriting:");
                // sendMessage.setReplyMarkup(generateMarkup());
                restTemplate.postForObject(RestConstants.FOR_MESSAGE, sendMessage, Object.class);
                setUserState(longChatId(update), BotState.INCORRECT_PHONE);

            }
        } else if (update.getMessage().hasText()) {
            String phoneNumber = update.getMessage().getText();
            phoneNumber = phoneNumber.startsWith("+") ? phoneNumber : "+" + phoneNumber;
            user.setPhoneNumber(phoneNumber);
            executeRegisterDone(update, user);
        }

    }


    public void executeRegisterDone(Update update,User user) {
        userRepository.save(user);
        SendMessage sendMessage1 = new SendMessage(getChatId(update),
                "*Xurmatli shifokor , siz muvaffaqiyatli ro'yxatdan o'tdingiz✅*");
        SendMessage sendMessage2 = new SendMessage(getChatId(update),
                "*\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47Sizning ma'lumotlaringiz\uD83D\uDC47\uD83D\uDC47\uD83D\uDC47 \n\n*" +
                        "*\uD83D\uDC6BIsm:  *" + user.getFistName() + "\n" +
                        "*\uD83D\uDCF1Telefon raqam:   *" + user.getPhoneNumber());
        sendMessage1.enableMarkdown(true);
        sendMessage1.setReplyMarkup(new ReplyKeyboardRemove(true));
        sendMessage2.enableMarkdown(true);
        sendMessage2.setReplyMarkup(forRegistraterDone());
        try {
            restTemplate.postForObject(RestConstants.FOR_MESSAGE, sendMessage1, Object.class);
            restTemplate.postForObject(RestConstants.FOR_MESSAGE, sendMessage2, Object.class);
            setUserState(longChatId(update), BotState.CONFIRM_PAYMENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private InlineKeyboardMarkup forRegistraterDone() {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        InlineKeyboardButton button1 = new InlineKeyboardButton();

        button1.setText("To'lov qilish");
        button1.setCallbackData("to'lov");



        List<InlineKeyboardButton> row1 = new ArrayList<>();

        row1.add(button1);

        rowsInline.add(row1);

        markupInline.setKeyboard(rowsInline);

        return markupInline;

    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "\\+998[1-9]\\d{8}";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }


}
