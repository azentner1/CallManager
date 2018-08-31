package com.example.phone.phonecallmanager.utils;

import android.util.Log;

import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.model.CallType;
import com.example.phone.phonecallmanager.model.Phone;
import com.example.phone.phonecallmanager.model.Settings;

import io.realm.Realm;
import io.realm.RealmModel;

public class RealmUtils {

    private static final String TAG = RealmUtils.class.getSimpleName();

    public static void initTestRealmData() {
        setCallTypes();
        setDefaultNumbers();
        initSettings();
    }

    public static boolean isInitialRealmDataSet() {
        Realm realm = Realm.getDefaultInstance();
        Phone phone = realm.where(Phone.class).equalTo("number", "4259501212").findFirst();
        realm.close();
        return phone != null;
    }

    public static void setDefaultNumbers() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Phone phone1 = new Phone(1, "4259501212", "Unknown", getBlockedCallType());
                Phone phone2 = new Phone(2, "2539501212", "Unknown", getSuspiciousCallType());
                realm.copyToRealmOrUpdate(phone1);
                realm.copyToRealmOrUpdate(phone2);
            }
        });
        realm.close();
    }

    public static void initSettings() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Settings settings = new Settings();
                settings.setId(1);
                realm.copyToRealmOrUpdate(settings);
            }
        });
        realm.close();
    }

    public static void setCallTypes() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                CallType callTypeSusp = new CallType(1, Constants.BLOCKED);
                CallType callTypeScam = new CallType(2, Constants.SUSPICIOUS);
                CallType callTypeNormal = new CallType(3, Constants.TRUSTED);
                realm.copyToRealmOrUpdate(callTypeSusp);
                realm.copyToRealmOrUpdate(callTypeScam);
                realm.copyToRealmOrUpdate(callTypeNormal);
            }
        });
        realm.close();

    }

    public static CallType getBlockedCallType() {
        Realm realm = Realm.getDefaultInstance();
        CallType callType = realm.copyFromRealm(realm.where(CallType.class).equalTo("type", Constants.BLOCKED).findFirst());
        realm.close();
        return callType;
    }

    public static CallType getSuspiciousCallType() {
        Realm realm = Realm.getDefaultInstance();
        CallType callType = realm.copyFromRealm(realm.where(CallType.class).equalTo("type", Constants.SUSPICIOUS).findFirst());
        realm.close();
        return callType;
    }

    public static CallType getTrustedCallType() {
        Realm realm = Realm.getDefaultInstance();
        CallType callType = realm.copyFromRealm(realm.where(CallType.class).equalTo("type", Constants.TRUSTED).findFirst());
        realm.close();
        return callType;
    }

    public static <T extends RealmModel> Long getAutoincrementId(Class<T> clazz) {
        long id;
        Realm realm = Realm.getDefaultInstance();
        id = realm.where(clazz).count() + 1;
        return id;
    }

    public static CallType getCallType() {
        return null;
    }
}
