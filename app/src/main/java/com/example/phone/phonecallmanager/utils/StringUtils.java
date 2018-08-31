package com.example.phone.phonecallmanager.utils;

public class StringUtils {

    public static String getJustNumbers(String phoneNumber) {
        return phoneNumber.replaceAll("\\D", "");
    }
}
