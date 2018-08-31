package com.example.phone.phonecallmanager.call_managment;

import android.content.Context;

import com.example.phone.phonecallmanager.model.CallType;

import java.util.ArrayList;

public interface ICallMarshall {

    interface Repository {
        void setCallType(String phoneNumber);
        CallType getCallType();
        ArrayList<String> getPhoneNumbersFromContacts();
        boolean isBlockAllButContacts();
        boolean isInContacts();
    }

    interface Main {
        void endCall();
        void openApp();
        String getPhoneNumber();
        Context getContext();
        boolean isReadContactsPermissionEnabled();
    }

}
