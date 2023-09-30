package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SignBot extends TelegramLongPollingBot {
    protected static final String TOKEN = Components.TOKEN_MY;
    private static int fieldOf = 111;

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
                            .text("Enter your birth date (DD.MM.YYYY)")
                            .chatId(message.getChatId())
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
                            er = "day of month";
                        } else{
                            er = "month";
                        }
                        String answer = "Your " + er + " is wrong, please enter your data again";
                        sendingMessage(message, answer);
                    } else{
//                        System.out.println(getSigns(currDate).toString());
                        sendingMessage(message,"Working");
                        sendingMessage(message,"Done");
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
    private void inCorrectData(Message message){
        SendMessage sendMessage = SendMessage.builder()
                .text("Enter your birth date correct way (DD.MM.YYYY)")
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

//    public String getSigns(Calendar calendar) {
//        HashMap<String, Calendar[]> data = new HashMap<>();
//        String[] curr = new String[2];
//        String[] months = new String[2];
//        for (String in : Components.SIGNS) {
//            curr = in.split(":");
//            System.out.println(Arrays.toString(curr));
//            months = curr[1].split("-");
//            System.out.println(Arrays.toString(months));
//            if (
//                    (Calendar.MONTH == Integer.parseInt(months[0].split(" ")[0]) ||
//                            Calendar.MONTH == Integer.parseInt(months[1].split(" ")[0])) &&
//                            (Calendar.DAY_OF_MONTH >= Integer.parseInt(months[0].split(" ")[1]) &&
//                                    Calendar.DAY_OF_MONTH <= Integer.parseInt(months[1].split(" ")[1])
//                            )) {
//                return curr[0];
//            } else {
//                return "!!!@#@#@#";
//            }
////            Calendar frst = new GregorianCalendar(
////                    calendar.get(Calendar.YEAR),
////                    Integer.parseInt(months[0].split(" ")[0]),
////                    Integer.parseInt(months[0].split(" ")[1])
////            );
////            Calendar sec = new GregorianCalendar(
////                    calendar.get(Calendar.YEAR),
////                    Integer.parseInt(months[1].split(" ")[0]),
////                    Integer.parseInt(months[1].split(" ")[1])
////            );
////            data.put(curr[0],
////                    new Calendar[]{
////                            new GregorianCalendar(
////                                    calendar.get(Calendar.YEAR),
////                                    Integer.parseInt(months[0].split(" ")[0]),
////                                    Integer.parseInt(months[0].split(" ")[1])
////                            ),
////                            new GregorianCalendar(
////                                    calendar.get(Calendar.YEAR),
////                                    Integer.parseInt(months[1].split(" ")[0]),
////                                    Integer.parseInt(months[1].split(" ")[1])
////                            )
////                    }
////                    );
////            System.out.println("!!!!");
////        }
////        for (String keys: data.keySet()){
////            System.out.println(keys);
////        }
////        return "";
//        }
//        return "LOLOMFG";
//    }

    @Override
    public String getBotUsername() {
        return "Zodiak8_BOT";
    }
//    private static int getMonth(String str){
//        int answ = switch (str) {
//            case "March" -> Calendar.MARCH;
//            case "April" -> Calendar.APRIL;
//            case "May" -> Calendar.MAY;
//            case "June" -> Calendar.JUNE;
//            case "July" -> Calendar.JULY;
//            case "August" -> Calendar.AUGUST;
//            case "September" -> Calendar.SEPTEMBER;
//            case "October" -> Calendar.OCTOBER;
//            case "November" -> Calendar.NOVEMBER;
//            case "December" -> Calendar.DECEMBER;
//            case "January" -> Calendar.JANUARY;
//            case "February" -> Calendar.FEBRUARY;
//            default -> answ = 0;
//        };
//        System.out.println(answ);
//        return answ;
//    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }


}
