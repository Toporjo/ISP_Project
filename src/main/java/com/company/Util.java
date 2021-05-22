package com.company;

import com.company.db.constant.Language;
import com.company.db.entity.Tariff;
import com.company.jstl.PrintService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Util {

    private Util(){}

    public static Date getEndOfDay(Date day, Calendar cal) {
        if (day == null) day = new Date();
        cal.setTime(day);
        cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND,      cal.getMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
        return cal.getTime();
    }


    public static double add(double first,double second){
        BigDecimal firstBig = BigDecimal.valueOf(first);
        BigDecimal secondBig = BigDecimal.valueOf(second);

        return firstBig.add(secondBig).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue();

    }

    public static double subtract(double minuend, double subtrahend){
        BigDecimal firstBig = BigDecimal.valueOf(minuend);
        BigDecimal secondBig = BigDecimal.valueOf(subtrahend);

        return firstBig.subtract(secondBig).setScale(2,BigDecimal.ROUND_HALF_DOWN).doubleValue();

    }

    public static double calculateDiscount(double price,int discount){
        BigDecimal bigDecimal = new BigDecimal(price*discount);
        bigDecimal = bigDecimal.setScale(1,BigDecimal.ROUND_HALF_UP);
        bigDecimal = bigDecimal.divide(BigDecimal.valueOf(100),BigDecimal.ROUND_HALF_UP);
        bigDecimal = new BigDecimal(price).subtract(bigDecimal);


        return bigDecimal.doubleValue();
    }

    public static Language determineLocale(HttpSession session, HttpServletRequest request){
        Optional<Language> language = Optional.ofNullable((Language) session.getAttribute("locale"));
        return language.orElse(Language.getLanguageByCode(request.getLocale().toString()));
    }

    public static List<String> asList(String str) {
        List<String> list = new ArrayList();
        StringTokenizer st = new StringTokenizer(str);
        while (st.hasMoreTokens()) list.add(st.nextToken());
        return list;
    }

    public static String readProperty(String key,Language language) throws IOException {
        Locale locale = new Locale(language.getIsoCode());
        ResourceBundle bundle = ResourceBundle.getBundle("resources", locale);

        return bundle.getString(key);
    }

    public static String formatTariffs(List<Tariff> tariffs){
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDate.now().toString()).append(System.lineSeparator());
        for (Tariff t:tariffs){
            sb.append("~~~~~~").append(tariffs.indexOf(t)+1).append("~~~~~~").append(System.lineSeparator());
            sb.append(t.getTariffName()).append(System.lineSeparator());
            sb.append(t.getDescription()).append(System.lineSeparator());
            sb.append(t.getPrice()).append(System.lineSeparator());
            sb.append(t.getDiscount()).append(System.lineSeparator());
        }
        return sb.toString();
    }

}
