package com.example.phone.phonecallmanager.features.phone_call.presenter;

import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.features.phone_call.contract.CallContract;
import com.example.phone.phonecallmanager.features.phone_call.model.CallModelInteractor;
import com.example.phone.phonecallmanager.model.CallType;
import com.example.phone.phonecallmanager.model.Phone;


public class CallPresenter implements CallContract.Presenter {

    private static final String TAG = CallPresenter.class.getSimpleName();


    private final CallContract.View view;
    private String phoneNumber;
    private CallModelInteractor callModelInteractor;
    private Phone phone;

    public CallPresenter(CallContract.View view) {
        this.view = view;
        this.callModelInteractor = new CallModelInteractor(this);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public CallType getCallType() {
        return phone.getCallType();
    }

    @Override
    public void init() {
        phone = callModelInteractor.getPhone();
        view.setNumberText();
        view.setIconTint();
        view.setMessage();
        if (phone.getCallType().getType().equals(Constants.SUSPICIOUS)) {
            view.setScamMessage();
        }
    }
}
