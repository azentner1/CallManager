package com.example.phone.phonecallmanager.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PhoneCall extends RealmObject {

    @PrimaryKey
    private long id;
    private Phone phone;
    private Message message;
    private long timestamp;


    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
