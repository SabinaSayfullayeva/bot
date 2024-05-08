package com.example.project_for_doctors.constants;

public interface RestConstants {
    String BOT_TOKEN = "6993818329:AAGa3rN4Exz5n6OZxDd5ZeDdRHrr782p6GU";
    String BOT_USERNAME = "t.me/doctors_adding_bot";
    String URL = "https://api.telegram.org/bot";
    String FOR_MESSAGE = URL + BOT_TOKEN + "/sendMessage";
    String EDIT_MESSAGE = URL + BOT_TOKEN + "/editMessageText";
    String FORWARD = URL + BOT_TOKEN + "/forwardMessage";
    String FOR_DELETE_MESSAGE = URL + BOT_TOKEN + "/deleteMessage";
    String FOR_DOCUMENT = URL + BOT_TOKEN + "/sendDocument";
}
