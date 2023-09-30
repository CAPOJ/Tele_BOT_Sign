package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZodiakUtilitsTest {

    @MethodSource("getSignNameData")
    @ParameterizedTest
    void getSignName(String signName, LocalDate beginDate, LocalDate endDate) {
        for (LocalDate date = beginDate; !date.isAfter(endDate); date = date.plusDays(1)){
            System.out.println(signName + " " + beginDate + " " + endDate + " ? " + date);
            Assertions.assertEquals(signName, ZodiakUtilits.getSignName(date.getDayOfMonth(), date.getMonthValue()));
        }
    }

    private static Object[][] getSignNameData(){
        return new Object[][]{
                {"Овен", LocalDate.parse("2024-03-21"),LocalDate.parse("2024-04-19")},
                {"Телец", LocalDate.parse("2024-04-20"),LocalDate.parse("2024-05-20")},
                {"Близнецы", LocalDate.parse("2024-05-21"),LocalDate.parse("2024-06-20")},
                {"Рак", LocalDate.parse("2024-06-21"),LocalDate.parse("2024-07-22")},
                {"Лев", LocalDate.parse("2024-07-23"),LocalDate.parse("2024-08-22")},
                {"Дева", LocalDate.parse("2024-08-23"),LocalDate.parse("2024-09-22")},
                {"Весы", LocalDate.parse("2024-09-23"),LocalDate.parse("2024-10-22")},
                {"Скорпион", LocalDate.parse("2024-10-23"),LocalDate.parse("2024-11-21")},
                {"Стрелец", LocalDate.parse("2024-11-22"),LocalDate.parse("2024-12-21")},
                {"Козерог", LocalDate.parse("2024-12-22"),LocalDate.parse("2025-01-19")},
                {"Водолей", LocalDate.parse("2025-01-20"),LocalDate.parse("2025-02-18")},
                {"Рыбы", LocalDate.parse("2025-02-19"),LocalDate.parse("2025-03-20")},

        };
    }
    public static void main(String[] args) throws Exception{
        List<String> lines = Files.readAllLines(Paths.get("Signs.txt"));
        String testCodeTemplate = Files.readString(Paths.get("templates.test.txt"));
        YearMonth yearMonth = YearMonth.of(2024, Month.MARCH);
        for (String line:lines){
            String signName = line.substring(0, line.indexOf(":"));
            String[] lineItems = line.split(" ");
            LocalDate beginDate = LocalDate.parse(yearMonth + "-"+lineItems[2]);
            yearMonth = yearMonth.plusMonths(1);
            LocalDate endDate = LocalDate.parse(yearMonth + "-"+lineItems[5]);
            String testCode =testCodeTemplate.replace("{bd}",beginDate.toString())
                    .replace("{ed}",endDate.toString())
                    .replace("{sn}",signName);
            System.out.println(testCode);
        }
    }
}