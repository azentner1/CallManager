package com.example.phone.phonecallmanager.sms_managment;

import android.content.Context;

import java.util.List;

public interface SmsConctract {

    interface Main {
        void abortMessage();
    }

    interface Model {
        void logMessage();
        void deleteSms();
        List<String> getBannedWordsList();
    }

    interface Presenter {
        void processMessage();
        String getPhoneNumber();
        void openApp();
        Context getContext();
    }
}
