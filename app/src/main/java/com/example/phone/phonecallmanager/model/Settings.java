package com.example.phone.phonecallmanager.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Settings extends RealmObject {

    @PrimaryKey
    private long id;
    private boolean blockAllExceptContacts;
    private boolean shouldShowEditHint;
    private RealmList<String> bannedWordsList;

    public Settings() {
        this.shouldShowEditHint = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isBlockAllExceptContacts() {
        return blockAllExceptContacts;
    }

    public void setBlockAllExceptContacts(boolean blockAllExceptContacts) {
        this.blockAllExceptContacts = blockAllExceptContacts;
    }

    public boolean isShouldShowEditHint() {
        return shouldShowEditHint;
    }

    public void setShouldShowEditHint(boolean shouldShowEditHint) {
        this.shouldShowEditHint = shouldShowEditHint;
    }

    public RealmList<String> getBannedWordsList() {
        return bannedWordsList;
    }

    public void setBannedWordsList(RealmList<String> bannedWordsList) {
        this.bannedWordsList = bannedWordsList;
    }
}
