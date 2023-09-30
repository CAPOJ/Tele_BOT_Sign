package org.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.List;

public class ZodiakUtilits {
    public static String getSignName(int day, int month){
        if ((month == 3 && day >= 21) || (month == 4 && day <= 19)){
            return "Овен";
        }

        if ((month == 4 && day >= 20) || (month == 5 && day <= 20)){
            return "Телец";
        }

        if ((month == 5 && day >= 21) || (month == 6 && day <= 20)){
            return "Близнецы";
        }

        if ((month == 6 && day >= 21) || (month == 7 && day <= 22)){
            return "Рак";
        }

        if ((month == 7 && day >= 23) || (month == 8 && day <= 22)){
            return "Лев";
        }

        if ((month == 8 && day >= 23) || (month == 9 && day <= 22)){
            return "Дева";
        }

        if ((month == 9 && day >= 23) || (month == 10 && day <= 22)){
            return "Весы";
        }

        if ((month == 10 && day >= 23) || (month == 11 && day <= 21)){
            return "Скорпион";
        }

        if ((month == 11 && day >= 22) || (month == 12 && day <= 21)){
            return "Стрелец";
        }

        if ((month == 12 && day >= 22) || (month == 1 && day <= 19)){
            return "Козерог";
        }

        if ((month == 1 && day >= 20) || (month == 2 && day <= 18)){
            return "Водолей";
        }

        if ((month == 2 && day >= 19) || (month == 3 && day <= 20)){
            return "Рыбы";
        }
        throw new IllegalArgumentException("Months: " + month + " day: " + day);
    }
    public static void main(String args[]) throws Exception{
        List<String> lines = Files.readAllLines(Paths.get("Signs.txt"));
        String testCodeTemplate = Files.readString(Paths.get("templates.codes.txt"));
        YearMonth yearMonth = YearMonth.of(2024, Month.MARCH);
        for (String line:lines){
            String signName = line.substring(0, line.indexOf(":"));
            String[] lineItems = line.split(" ");
            LocalDate beginDate = LocalDate.parse(yearMonth + "-"+lineItems[2]);
            yearMonth = yearMonth.plusMonths(1);
            LocalDate endDate = LocalDate.parse(yearMonth + "-"+lineItems[5]);
            String testCode = testCodeTemplate
                    .replace("{bd}",String.valueOf(beginDate.getDayOfMonth()))
                        .replace("{bm}",String.valueOf(beginDate.getMonthValue()))
                            .replace("{ed}",String.valueOf(endDate.getDayOfMonth()))
                                    .replace("{em}",String.valueOf(endDate.getMonthValue()))
                                        .replace("{sn}",signName);
            System.out.println(testCode);
            System.out.println();
        }
    }
}
