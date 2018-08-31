package com.example.phone.phonecallmanager.sms_managment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.phone.phonecallmanager.utils.CallUtils;

public class SmsReceiver extends BroadcastReceiver implements SmsConctract.Main {

    private static final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (!intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            return;
        }

        Bundle bundle = intent.getExtras();
        Object messages[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[messages.length];

        for (int i = 0; i < messages.length; i++) {
            smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
        }

        final String phoneNumber = smsMessage[0].getOriginatingAddress();
        final String messageBody = smsMessage[0].getMessageBody();
        new SmsMarshal(context, CallUtils.formatPhoneNumber(phoneNumber), messageBody, this).processMessage();
    }

    //Doesn't work on API > KitKat - we have to be default messaging app in order to mess with messages
    @Override
    public void abortMessage() {
        abortBroadcast();
    }
}
