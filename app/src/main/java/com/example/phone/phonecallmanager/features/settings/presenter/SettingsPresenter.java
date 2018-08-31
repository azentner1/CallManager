package com.example.phone.phonecallmanager.features.settings.presenter;

import com.example.phone.phonecallmanager.features.settings.SettingsContract;
import com.example.phone.phonecallmanager.features.settings.model.SettingsModelInteractor;

import java.util.List;

import io.realm.RealmList;

public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View view;
    private SettingsModelInteractor settingsModelInteractor;

    public SettingsPresenter(SettingsContract.View view) {
        this.view = view;
        settingsModelInteractor = new SettingsModelInteractor();
    }

    @Override
    public void setBlockAllExceptContacts(boolean value) {
        settingsModelInteractor.setBlockAllExceptContacts(value);
    }

    @Override
    public void saveSettings() {
        RealmList<String> realmList = null;
        if (view.getBannedWordsList() != null && view.getBannedWordsList().size() > 0) {
            realmList = new RealmList<>();
            realmList.addAll(view.getBannedWordsList());
        }
        settingsModelInteractor.saveBannedWords(realmList);
    }

    public void initSettings() {
        view.setBlockAllExceptContactsValue(settingsModelInteractor.getBlockAllExceptContacts());
    }

    public List<String> getBannedWordsList() {
        return settingsModelInteractor.getSettings().getBannedWordsList();
    }
}
