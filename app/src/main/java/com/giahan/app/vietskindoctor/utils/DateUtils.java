package com.giahan.app.vietskindoctor.utils;

import android.text.TextUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
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
    static String formatAge = "yyyy-MM-dd";

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

    public static String convertDayRequest(String date){
        String format = "dd-MM-yyyy";
        return convertDateRequest(date, format);
    }

    public static String convertHours(String date){
        String format = "HH:mm";
        return convertDate(date, format);
    }

    private static String convertDate(String date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Locale.US);
        DateFormat df = new SimpleDateFormat(format, Locale.US);
        try {
            String f = df.format(simpleDateFormat.parse(date));
            return f;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String convertDateRequest(String date, String format){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Constant.LOCALE_VN);
        DateFormat df = new SimpleDateFormat(format, Locale.US);
        try {
            String f = df.format(simpleDateFormat.parse(date));
            return f;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String convertDateString(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate, Constant.LOCALE_VN);
        try {
            Date mDate = simpleDateFormat.parse(date);
            simpleDateFormat.getCalendar().setTime(mDate);
            DateFormat df = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                df = new SimpleDateFormat("EEE, dd/MM/yy      HH:mm ", Locale.forLanguageTag("vn-VN"));
            }else {
                df = new SimpleDateFormat("EEE, dd/MM/yy      HH:mm ", Constant.LOCALE_VN);
            }
            return df.format(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDateRemain(String date){
        if (TextUtils.isEmpty(date)) return null;
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

    public static String getAge(String date) {
        DateFormat dateFormat = new SimpleDateFormat(formatAge, Locale.US);
        try {
            Date mDate = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate);
            return String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - calendar.get(Calendar.YEAR));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertCurrentTime() {
        return new SimpleDateFormat(formatDate).format(new Date());
    }

    public static boolean compareDates(String startDate, String endDate) {

        SimpleDateFormat dfDate = new SimpleDateFormat(formatDate, Locale.US);

        boolean b = false;

        try {
            if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                b = true;  // If start date is before end date.
            } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                b = true;  // If two dates are equal.
            } else {
                b = false; // If start date is after the end date.
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return b;
    }
}
