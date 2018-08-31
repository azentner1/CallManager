package com.example.phone.phonecallmanager.features.settings.model;

import com.example.phone.phonecallmanager.features.settings.SettingsContract;
import com.example.phone.phonecallmanager.model.Settings;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

public class SettingsModelInteractor implements SettingsContract.Model {

    public boolean getBlockAllExceptContacts() {
        Realm realm = Realm.getDefaultInstance();
        Settings settings = realm.where(Settings.class).findFirst();
        boolean value = settings.isBlockAllExceptContacts();
        realm.close();
        return value;
    }


    public void setBlockAllExceptContacts(final boolean value) {
        final Settings settings = getSettings();
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                settings.setBlockAllExceptContacts(value);
                realm.copyToRealmOrUpdate(settings);
            }
        });
        realm.close();
    }


    public Settings getSettings() {
        Realm realm = Realm.getDefaultInstance();
        Settings settings = realm.copyFromRealm(realm.where(Settings.class).findFirst());
        realm.close();
        return settings;
    }

    @Override
    public void saveBannedWords(final RealmList<String> realmList) {
        Realm realm = Realm.getDefaultInstance();
        final Settings settings = realm.where(Settings.class).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                settings.setBannedWordsList(realmList);
            }
        });
        realm.close();
    }

    @Override
    public List<String> loadBannedWordsList() {
        Realm realm = Realm.getDefaultInstance();
        Settings settings = realm.where(Settings.class).findFirst();
        List<String> list = settings.getBannedWordsList();
        realm.close();
        return list;
    }
}
