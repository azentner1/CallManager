package com.example.phone.phonecallmanager.features.phone_call.contract;

import com.example.phone.phonecallmanager.model.CallType;
import com.example.phone.phonecallmanager.model.Phone;

public interface CallContract {

    interface Presenter {
        void init();
        String getPhoneNumber();
        CallType getCallType();
    }

    interface View {
        void setNumberText();
        void setIconTint();
        void setMessage();
        void setScamMessage();
    }

    interface Model {
        Phone getPhone();
    }
}
