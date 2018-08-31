package com.example.phone.phonecallmanager.call_managment;

import android.database.Cursor;
import android.provider.ContactsContract;

import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.model.CallType;
import com.example.phone.phonecallmanager.model.Phone;
import com.example.phone.phonecallmanager.model.PhoneCall;
import com.example.phone.phonecallmanager.model.Settings;
import com.example.phone.phonecallmanager.utils.CallUtils;
import com.example.phone.phonecallmanager.utils.RealmUtils;

import java.util.ArrayList;

import io.realm.Realm;

public class CallModelInteractor implements ICallMarshall.Repository{

    private static final String TAG = CallModelInteractor.class.getSimpleName();


    private ICallMarshall.Main iCallMarhsall;
    private CallType callType;

    CallModelInteractor(ICallMarshall.Main iCallMarhall) {
        this.iCallMarhsall = iCallMarhall;
    }

    public void processCall() {
        if (iCallMarhsall.isReadContactsPermissionEnabled() && isBlockAllButContacts() && !isInContacts()) {
            iCallMarhsall.endCall();
        }
        if (callType.getType().equals(Constants.BLOCKED)) {
            iCallMarhsall.endCall();
        }
        iCallMarhsall.openApp();
    }



    public void logCall(String phoneNumber) {
        Realm realm = Realm.getDefaultInstance();
        final PhoneCall phoneCall = new PhoneCall();
        phoneCall.setId(RealmUtils.getAutoincrementId(PhoneCall.class));
        Phone phone = getPhone(phoneNumber);
        if (phone == null) {
            phone = new Phone(RealmUtils.getAutoincrementId(Phone.class), phoneNumber, "", callType);
        }
        final Phone finalPhone = phone;
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                phoneCall.setPhone(finalPhone);
                phoneCall.setTimestamp(System.currentTimeMillis());
                realm.copyToRealmOrUpdate(phoneCall);
            }
        });
        realm.close();
    }

    public Phone getPhone(String phoneNumber) {
        Realm realm = Realm.getDefaultInstance();
        Phone phone = realm.where(Phone.class).equalTo("number", phoneNumber).findFirst();
        realm.close();
        return phone;
    }


    @Override
    public void setCallType(String phoneNumber) {
        this.callType = CallUtils.setCallType(phoneNumber);
    }

    @Override
    public CallType getCallType() {
        return callType;
    }

    @Override
    public ArrayList<String> getPhoneNumbersFromContacts() {
        ArrayList<String> array = new ArrayList<>();

        Cursor c = iCallMarhsall.getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (c.moveToNext())
        {
            String phoneNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            array.add(CallUtils.formatPhoneNumber(phoneNumber));
        }
        c.close();

        return array;
    }

    @Override
    public boolean isBlockAllButContacts() {
        Realm realm = Realm.getDefaultInstance();
        boolean isAllButContacts = realm.where(Settings.class).findFirst().isBlockAllExceptContacts();
        realm.close();
        return isAllButContacts;
    }

    @Override
    public boolean isInContacts() {
        for (String phoneNumber : getPhoneNumbersFromContacts()) {
            if (iCallMarhsall.getPhoneNumber().equals(CallUtils.formatPhoneNumber(phoneNumber))) {
                return true;
            }
        }
        return false;
    }
}
