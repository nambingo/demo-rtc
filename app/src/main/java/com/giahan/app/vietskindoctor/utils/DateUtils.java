package com.giahan.app.vietskindoctor.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by pham.duc.nam on 18/06/2018.
 */
public class DateUtils {
    static String formatDate = "yyyy-MM-dd HH:mm:ss";
    static String format = "dd-MM-yyyy";
    static String formatList = "EEE, dd/MM/yy      HH:mm";

    public static Date getDate(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.US);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCurrentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.US);
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    public static String convertToString(Date source) {
        if (source == null) {
            return null;
        }

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US);
        return df.format(source);
    }

    public static String convertDate(String date) {
        String format = "dd-MM-yyyy  HH:mm";
        return convertDate(date, format);
    }

    public static String convertDay(String date){
        String format = "dd-MM-yyyy";
        return convertDate(date, format);
    }

    public static String convertHours(String date){
        String format = "HH:mm";
        return convertDate(date, format);
    }

    private static String convertDate(String date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.US);
        DateFormat df = new SimpleDateFormat(format, Locale.US);
        try {
            return df.format(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String convertDateString(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.US);
        try {
            Date mDate = simpleDateFormat.parse(date);
            simpleDateFormat.getCalendar().setTime(mDate);
            DateFormat df = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                df = new SimpleDateFormat("EEE, dd/MM/yy      HH:mm", Locale.forLanguageTag("vn-VN"));
            }else {
                df = new SimpleDateFormat("EEE, dd/MM/yy      HH:mm", Locale.US);
            }
            return df.format(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateRemain(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.US);
        try {
            Date mDate = simpleDateFormat.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            calendar.add(Calendar.DAY_OF_YEAR, +15);
            Date newDate = calendar.getTime();

            Date today = new Date();
            long diff =  newDate.getTime() - today.getTime();
            int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
            return String.valueOf(numOfDays);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Long getTimestamp(String date){
        DateFormat dateFormat = new SimpleDateFormat(formatDate, Locale.US);
        try {
            Date date1 = (Date) dateFormat.parse(date);
            return date1.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
