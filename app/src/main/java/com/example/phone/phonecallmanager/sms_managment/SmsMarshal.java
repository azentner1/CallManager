package com.example.phone.phonecallmanager.sms_managment;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.features.main.view.MainActivity;
import com.example.phone.phonecallmanager.model.Phone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmsMarshal implements SmsConctract.Presenter {

    private Context context;
    private String phoneNumber;
    private String messageBody;
    private SmsConctract.Main main;
    private SmsModelInteractor modelInteractor;
    private Phone phone;

    public SmsMarshal(Context context, String phoneNumber, String messageBody, SmsConctract.Main main) {
        this.context = context;
        this.phoneNumber = phoneNumber;
        this.messageBody = messageBody;
        this.main = main;
        this.modelInteractor = new SmsModelInteractor(this);
    }

    @Override
    public void processMessage() {
        phone = modelInteractor.getPhone();
        if ((phone != null && phone.getCallType().getType().equals(Constants.BLOCKED)) || hasBlockedWords()) {
            modelInteractor.logMessage();
            modelInteractor.deleteSms();
            main.abortMessage();
            openApp();
        }
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean hasBlockedWords() {
        if (TextUtils.isEmpty(messageBody)) {
            return false;
        }
        List<String> banList = modelInteractor.getBannedWordsList();
        if (banList == null) {
            return false;
        }
        for (String item : banList) {
            if (messageBody.toLowerCase().contains(item.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void openApp() {
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.putExtra(Constants.CALL_STATE_MESSAGE, "");
        intent.putExtra(Constants.PHONE_NUMBER, phoneNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    public Context getContext() {
        return context;
    }
}
