package com.example.phone.phonecallmanager.features.phone_log.contract;

import com.example.phone.phonecallmanager.model.PhoneCall;

import java.util.ArrayList;

public interface PhoneCallLogContract {

    interface Presenter {
        void setPhoneCallData();
    }

    interface View {
        void refreshData();
    }

    interface Model {
        ArrayList<PhoneCall> getPhoneCallList();
    }

}
