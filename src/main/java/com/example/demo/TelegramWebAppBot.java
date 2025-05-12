package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component  //  Делаем TelegramWebAppBot компонентом Spring
public class TelegramWebAppBot extends TelegramLongPollingBot {

    private static final Logger LOGGER = Logger.getLogger(TelegramWebAppBot.class.getName());

    // Замени YOUR_BOT_TOKEN на токен своего бота
    private static final String BOT_TOKEN = "7865867313:AAFYKyoYh-6TuGDdF-qV0RyoI3U0ytDs4E0";

    // Замени YOUR_BOT_USERNAME на имя своего бота
    private static final String BOT_USERNAME = "@ppportfolio_bbbot";

    // Замени YOUR_WEB_APP_URL на URL твоего сайта
    private static final String WEB_APP_URL = "https://danthes1303.github.io/";

    @PostConstruct  // Этот метод будет вызван после создания бина
    public void init() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            LOGGER.info("Telegram bot started.");
        } catch (TelegramApiException e) {
            LOGGER.log(Level.SEVERE, "Error starting Telegram bot: " + e.getMessage(), e);
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText) {
                case "/start":
                    startCommandReceived(chatId);
                    break;
                default:
                    sendMessage(chatId, "Я не понимаю эту команду.");
            }
        } else if (update.hasCallbackQuery()) {
            // Обработка данных, возвращенных из Web App (необязательно на первом этапе).
            String callbackData = update.getCallbackQuery().getData();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            // Здесь можно обработать данные из Web App
            sendMessage(chatId, "Получены данные из Web App: " + callbackData);

        }
    }

    private void startCommandReceived(long chatId) {
        String answer = "Нажмите кнопку, чтобы открыть Web App:";
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(answer);

        // Создаем inline-кнопку для запуска Web App
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        InlineKeyboardButton webAppButton = new InlineKeyboardButton();
        webAppButton.setText("Открыть Web App");

        // Создаем WebAppInfo объект
        WebAppInfo webAppInfo = new WebAppInfo(WEB_APP_URL);

        webAppButton.setWebApp(webAppInfo); // Устанавливаем WebAppInfo
        rowInline.add(webAppButton);
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
            LOGGER.log(Level.SEVERE, "Error sending message: " + e.getMessage(), e);
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            LOGGER.log(Level.SEVERE, "Error sending message: " + e.getMessage(), e);
        }
    }
}
