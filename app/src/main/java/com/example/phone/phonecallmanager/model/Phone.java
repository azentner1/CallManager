package com.example.phone.phonecallmanager.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Phone extends RealmObject {

    @PrimaryKey
    private long id;
    private String number;
    private String name;
    private CallType callType;

    public Phone() {
    }

    public Phone(long id, String number, String name, CallType callType) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.callType = callType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CallType getCallType() {
        return callType;
    }

    public void setCallType(CallType callType) {
        this.callType = callType;
    }
}
