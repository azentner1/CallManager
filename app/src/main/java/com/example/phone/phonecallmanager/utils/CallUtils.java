package com.example.phone.phonecallmanager.utils;

import android.text.TextUtils;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.model.CallType;
import com.example.phone.phonecallmanager.model.Phone;

import io.realm.Realm;

public class CallUtils {

    public static CallType setCallType(String phoneNumber) {
        Realm realm = Realm.getDefaultInstance();
        Phone phone = realm.where(Phone.class).equalTo("number", phoneNumber).findFirst();
        if (phone == null) {
            return RealmUtils.getTrustedCallType();
        }
        if (phone.getCallType().getType().equals(Constants.TRUSTED)) {
            return RealmUtils.getTrustedCallType();
        }
        if (phone.getCallType().getType().equals(Constants.SUSPICIOUS)) {
            return RealmUtils.getSuspiciousCallType();
        }
        if (phone.getCallType().getType().equals(Constants.BLOCKED)) {
            return RealmUtils.getBlockedCallType();
        }
        realm.close();
        return RealmUtils.getTrustedCallType();
    }

    public static String getCallTypeDisplayString(CallType callType) {
        if (callType.getType().equals(Constants.TRUSTED)) {
            return Constants.TRUSTED;
        } else if (callType.getType().equals(Constants.SUSPICIOUS)) {
            return Constants.SCAM;
        } else {
            return Constants.BLOCKED;
        }
    }

    public static String formatPhoneNumber(String phoneNumber) {
        String number = StringUtils.getJustNumbers(phoneNumber.replace("+", "00"));
        if (!number.startsWith("00")) {
            number = "00" + number;
        }
        return number;
    }


    public static String formatPhoneNumberForUi(String phoneNumber) {
        return StringUtils.getJustNumbers(phoneNumber.replace("00", "+"));
    }

    public static String formatName(String name) {
        if (TextUtils.isEmpty(name)) {
            return Constants.UNKNOWN;
        }
        return name;
    }
}
