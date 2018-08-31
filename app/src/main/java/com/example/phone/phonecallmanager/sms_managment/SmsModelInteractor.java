package com.example.phone.phonecallmanager.sms_managment;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.phone.phonecallmanager.model.Message;
import com.example.phone.phonecallmanager.model.Phone;
import com.example.phone.phonecallmanager.model.PhoneCall;
import com.example.phone.phonecallmanager.model.Settings;
import com.example.phone.phonecallmanager.utils.RealmUtils;

import java.util.List;

import io.realm.Realm;

public class SmsModelInteractor implements SmsConctract.Model {

    private SmsConctract.Presenter presenter;

    public SmsModelInteractor(SmsConctract.Presenter presenter) {
        this.presenter = presenter;
    }

    public Phone getPhone() {
        Realm realm = Realm.getDefaultInstance();
        Phone phone = realm.copyFromRealm(realm.where(Phone.class).equalTo("number", presenter.getPhoneNumber()).findFirst());
        realm.close();
        return phone;
    }


    @Override
    public List<String> getBannedWordsList() {
        Realm realm = Realm.getDefaultInstance();
        Settings settings = realm.copyFromRealm(realm.where(Settings.class).findFirst());
        realm.close();
        return settings.getBannedWordsList();
    }

    @Override
    public void logMessage() {
        Realm realm = Realm.getDefaultInstance();
        final Message message = new Message();
        message.setId(RealmUtils.getAutoincrementId(Message.class));
        message.setPhoneNumber(presenter.getPhoneNumber());
        final PhoneCall phoneCall = new PhoneCall();
        phoneCall.setId(RealmUtils.getAutoincrementId(PhoneCall.class));
        phoneCall.setMessage(message);
        phoneCall.setTimestamp(System.currentTimeMillis());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(phoneCall);
            }
        });
        realm.close();
    }


    //Doesn't work on API > KitKat - we have to be default messaging app in order to mess with messages
    @Override
    public void deleteSms() {
        Uri uri = Uri.parse("content://sms/");
        Cursor cursor = presenter.getContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            String messageId = cursor.getString(0);
            presenter.getContext().getContentResolver().delete(Uri.parse("content://sms/" + messageId), null, null);
        }
    }
}
