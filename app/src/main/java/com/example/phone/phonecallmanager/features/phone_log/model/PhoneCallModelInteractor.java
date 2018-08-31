package com.example.phone.phonecallmanager.features.phone_log.model;

import com.example.phone.phonecallmanager.features.phone_log.contract.PhoneCallLogContract;
import com.example.phone.phonecallmanager.features.phone_log.presenter.PhoneCallLogPresenter;
import com.example.phone.phonecallmanager.model.PhoneCall;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class PhoneCallModelInteractor implements PhoneCallLogContract.Model {

    private static final String TAG = PhoneCallModelInteractor.class.getSimpleName();


    @Override
    public ArrayList<PhoneCall> getPhoneCallList() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<PhoneCall> phoneCallRealmResults = realm.where(PhoneCall.class).sort("id", Sort.DESCENDING).findAll();
        ArrayList<PhoneCall> phoneCallArrayList = new ArrayList<>(realm.copyFromRealm(phoneCallRealmResults));
        realm.close();
        return phoneCallArrayList;
    }

}
