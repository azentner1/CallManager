package com.example.phone.phonecallmanager.features.settings;

import java.util.List;

import io.realm.RealmList;

public interface SettingsContract {

    interface Presenter {
        void setBlockAllExceptContacts(boolean value);
        void saveSettings();
    }

    interface View {
        void setBlockAllExceptContactsValue(boolean value);
        List<String> getBannedWordsList();
    }

    interface Model {
        boolean getBlockAllExceptContacts();
        void setBlockAllExceptContacts(boolean value);
        void saveBannedWords(RealmList<String> realmList);
        List<String> loadBannedWordsList();
    }

}
