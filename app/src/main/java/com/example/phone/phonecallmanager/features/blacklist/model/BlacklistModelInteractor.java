package com.example.phone.phonecallmanager.features.blacklist.model;

import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.features.blacklist.contract.BlacklistContract;
import com.example.phone.phonecallmanager.features.blacklist.presenter.BlacklistPresenter;
import com.example.phone.phonecallmanager.model.Phone;
import com.example.phone.phonecallmanager.model.Settings;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class BlacklistModelInteractor implements BlacklistContract.Model {


    @Override
    public ArrayList<Phone> getBlockedPhones() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Phone> realmResults = realm.where(Phone.class).equalTo("callType.type", Constants.BLOCKED).sort("id", Sort.DESCENDING).findAll();
        ArrayList<Phone> arrayList = new ArrayList<>(realm.copyFromRealm(realmResults));
        realm.close();
        return arrayList;
    }

    @Override
    public ArrayList<Phone> getSuspiciousPhones() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Phone> realmResults = realm.where(Phone.class).equalTo("callType.type", Constants.SUSPICIOUS).sort("id", Sort.DESCENDING).findAll();
        ArrayList<Phone> arrayList = new ArrayList<>(realm.copyFromRealm(realmResults));
        realm.close();
        return arrayList;
    }

    @Override
    public ArrayList<Phone> getTrustedPhones() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Phone> realmResults = realm.where(Phone.class).equalTo("callType.type", Constants.TRUSTED).sort("id", Sort.DESCENDING).findAll();
        ArrayList<Phone> arrayList = new ArrayList<>(realm.copyFromRealm(realmResults));
        realm.close();
        return arrayList;
    }

    @Override
    public ArrayList<Phone> getAllPhones() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Phone> realmResults = realm.where(Phone.class).findAll().sort("id", Sort.DESCENDING);
        ArrayList<Phone> arrayList = new ArrayList<>(realm.copyFromRealm(realmResults));
        realm.close();
        return arrayList;
    }

    @Override
    public boolean getShouldShowEditHint() {
        Realm realm = Realm.getDefaultInstance();
        Settings settings = realm.copyFromRealm(realm.where(Settings.class).findFirst());
        realm.close();
        return settings.isShouldShowEditHint();
    }
}
