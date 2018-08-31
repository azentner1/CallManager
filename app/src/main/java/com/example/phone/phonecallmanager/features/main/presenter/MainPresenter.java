package com.example.phone.phonecallmanager.features.main.presenter;

import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.features.main.contract.MainContract;

public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private MainContract.View view;
    private String phoneNumber;
    private String callState;

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }


    @Override
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void handleIntent(Intent intent) {
        if (intent.hasExtra(Constants.CALL_STATE)) {
            this.callState = intent.getStringExtra(Constants.CALL_STATE);
            this.phoneNumber = intent.getStringExtra(Constants.PHONE_NUMBER);
            if (callState.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)) {
                view.openCallFragment();
            } else {
                view.closeCallFragment();
            }
        }

        if (intent.hasExtra(Constants.CALL_STATE_MESSAGE)) {
            this.phoneNumber = intent.getStringExtra(Constants.PHONE_NUMBER);
        }

    }
}
