package com.example.phone.phonecallmanager.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    public static String formatDateDisplayValue(long timestamp) {
        try {
            Date d = new Date(timestamp);
            return new SimpleDateFormat("MM/dd/yy HH:ss").format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
