package com.example.phone.phonecallmanager.call_managment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.phone.phonecallmanager.utils.CallUtils;

public class CallReceiver extends BroadcastReceiver {

    private static final String TAG = CallReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }
        if (!intent.getAction().equals("android.intent.action.PHONE_STATE")) {
            return;
        }
        String phoneNumber = CallUtils.formatPhoneNumber(intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER));
        new CallMarshall(context, phoneNumber).processCall(intent.getStringExtra(TelephonyManager.EXTRA_STATE));
    }
}
