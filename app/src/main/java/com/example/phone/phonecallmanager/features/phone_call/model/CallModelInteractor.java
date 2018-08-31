package com.example.phone.phonecallmanager.features.phone_call.model;

import com.example.phone.phonecallmanager.features.phone_call.contract.CallContract;
import com.example.phone.phonecallmanager.features.phone_call.presenter.CallPresenter;
import com.example.phone.phonecallmanager.model.Phone;

import io.realm.Realm;

public class CallModelInteractor implements CallContract.Model {

    private static final String TAG = CallModelInteractor.class.getSimpleName();


    private CallContract.Presenter presenter;

    public CallModelInteractor(CallContract.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public Phone getPhone() {
        Realm realm = Realm.getDefaultInstance();
        Phone phone = realm.copyFromRealm(realm.where(Phone.class).equalTo("number", presenter.getPhoneNumber()).findFirst());
        realm.close();
        return phone;
    }
}
