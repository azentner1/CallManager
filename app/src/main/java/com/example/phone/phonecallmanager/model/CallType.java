package com.example.phone.phonecallmanager.model;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CallType extends RealmObject {

    @PrimaryKey
    private long id;
    private String type;

    public CallType() {

    }

    public CallType(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
