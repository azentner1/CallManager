package com.example.phone.phonecallmanager.features.blacklist.features.add.model;

import com.example.phone.phonecallmanager.enums.SelectedListType;
import com.example.phone.phonecallmanager.features.blacklist.features.add.contract.AddNumberContract;
import com.example.phone.phonecallmanager.model.CallType;
import com.example.phone.phonecallmanager.model.Phone;
import com.example.phone.phonecallmanager.model.Settings;
import com.example.phone.phonecallmanager.utils.RealmUtils;

import io.realm.Realm;

public class AddNumberModelInteractor implements AddNumberContract.Model {


    private AddNumberContract.Presenter presenter;

    public AddNumberModelInteractor(AddNumberContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Phone createPhone() {
        Phone phone = new Phone();
        phone.setId(RealmUtils.getAutoincrementId(Phone.class));
        return phone;
    }

    @Override
    public CallType getCallType() {
        CallType callType;
        if (presenter.getSelectedListType().equals(SelectedListType.TRUSTED)) {
            callType = RealmUtils.getTrustedCallType();
        } else if (presenter.getSelectedListType().equals(SelectedListType.SUSPICIOUS)) {
            callType = RealmUtils.getSuspiciousCallType();
        } else {
            callType = RealmUtils.getBlockedCallType();
        }
        return callType;
    }

    @Override
    public void setCallType(CallType callType) {

    }

    @Override
    public Phone getPhone() {
        return createPhone();
    }

    @Override
    public Phone getEditPhone(String phoneNumber) {
        Realm realm = Realm.getDefaultInstance();
        Phone phone = realm.copyFromRealm(realm.where(Phone.class).equalTo("number", phoneNumber).findFirst());
        realm.close();
        return phone;
    }

    @Override
    public void savePhone() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(presenter.getPhone());
            }
        });
        realm.close();
        presenter.phoneSaved();
    }

    @Override
    public void stopShowEditHint() {
        Realm realm = Realm.getDefaultInstance();
        final Settings settings =realm.where(Settings.class).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                settings.setShouldShowEditHint(false);
                realm.copyToRealmOrUpdate(settings);
            }
        });
        realm.close();
    }
}
