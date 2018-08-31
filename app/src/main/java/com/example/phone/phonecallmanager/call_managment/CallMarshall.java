package com.example.phone.phonecallmanager.call_managment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;

import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.features.main.view.MainActivity;

import java.lang.reflect.Method;

public class CallMarshall implements ICallMarshall.Main {

    private static final String TAG = CallMarshall.class.getSimpleName();

    private Context context;
    private String phoneNumber;
    private CallModelInteractor callModelInteractor;
    private String stringExtra;

    public CallMarshall(Context context, String phoneNumber) {
        this.context = context;
        this.phoneNumber = phoneNumber;
        this.callModelInteractor = new CallModelInteractor(this);
    }

    public void processCall(String stringExtra) {
        this.stringExtra = stringExtra;
        if (stringExtra.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            return;
        }
        if (stringExtra.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)) {
            openApp();
            return;
        }
        callModelInteractor.setCallType(phoneNumber);
        callModelInteractor.logCall(phoneNumber);
        callModelInteractor.processCall();
    }

    @Override
    public void endCall() {
        com.android.internal.telephony.ITelephony telephonyService;
        TelephonyManager telephony = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            Class c = Class.forName(telephony.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (com.android.internal.telephony.ITelephony) m.invoke(telephony);
            telephonyService.silenceRinger();
            telephonyService.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void openApp() {
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.putExtra(Constants.CALL_STATE, stringExtra);
        intent.putExtra(Constants.PHONE_NUMBER, phoneNumber);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public boolean isReadContactsPermissionEnabled() {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }
}
