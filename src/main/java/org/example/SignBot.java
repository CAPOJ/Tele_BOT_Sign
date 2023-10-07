package org.example;

import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class SignBot extends TelegramLongPollingBot {
    protected static final String TOKEN = Components.TOKEN_MY;
    private static int fieldOf = 111;
    public static void main(String[] args){
        int index = 0;
        Month[] mon = Month.values();
        for (int i=0; i< mon.length/2;i++){
            System.out.println(getRusMonth(mon[i]) + " " + getRusMonth(mon[i+6]));
        }
//        for (Month month : mon){
//        }
    }
    private static String getRusMonth(Month month){
        String rusVers = month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.forLanguageTag("ru"));
        return StringUtils.capitalize(rusVers);
    }

    public SignBot(String tokenMy) {
        super(tokenMy);
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        if (update.hasMessage()){
            Message message = update.getMessage();
            if(message.hasText()){
                String text = message.getText();
                if (text.equals("/start")){
                    SendMessage sendMessage = SendMessage.builder()
                            .text("Введите дату своего рождения в формате (DD.MM.YYYY)")
                            .chatId(message.getChatId())
                            .replyMarkup(createMonthKeyboard())
                            .build();
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        throw new RuntimeException(e);
                    }
                    return;
                }else if (text.matches("^\\d+(\\.\\d+)(\\.\\d+)?$")){
                    int[] date = new int[] {Integer.parseInt(text.substring(0,text.indexOf("."))),
                            Integer.parseInt(text.substring(text.indexOf(".")+1,text.lastIndexOf("."))),
                            Integer.parseInt(text.substring(text.lastIndexOf(".")+1))};

                    if (date[0]>31 || date[1]>12){
                        String er="";
                        if (date[0]>31){
                            er = "день месяца";
                        } else{
                            er = "месяц";
                        }
                        String answer = "Ваш " + er + " неверный, пожалуйста, введите данные снова";
                        sendingMessage(message, answer);
                    } else{
                        sendingMessage(message,"Знак зодиака: " +   ZodiakUtilits.getSignName(date[0],date[1]));
                    }
                } else {
                    inCorrectData(message);
                    return;
                }
            }
        } else {
            return;
        }
    }

    private static ReplyKeyboard createMonthKeyboard() {
        List<List<InlineKeyboardButton>> keyboardRows = new ArrayList<>(6);

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .build();
    }

    private void inCorrectData(Message message){
        SendMessage sendMessage = SendMessage.builder()
                .text("Введите данные в необходимом формате (DD.MM.YYYY)")
                .chatId(message.getChatId())
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
    private void sendingMessage(Message message ,String text){
        SendMessage sendMessage = SendMessage.builder()
                .text(text)
                .chatId(message.getChatId())
                .build();
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private int recognizeSign(int[] arr) {
        int day = arr[0];
        int month = arr[1];
        int year = arr[2];
        Calendar calendar = new GregorianCalendar(year, month, day);
        return 0;
    }

    @Override
    public String getBotUsername() {
        return "Zodiak8_BOT";
    }
    @Override
    public String getBotToken() {
        return TOKEN;
    }


}
