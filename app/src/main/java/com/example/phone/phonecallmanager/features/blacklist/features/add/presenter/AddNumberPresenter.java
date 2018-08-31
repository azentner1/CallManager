package com.example.phone.phonecallmanager.features.blacklist.features.add.presenter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.example.phone.phonecallmanager.enums.SelectedListType;
import com.example.phone.phonecallmanager.features.blacklist.features.add.contract.AddNumberContract;
import com.example.phone.phonecallmanager.features.blacklist.features.add.model.AddNumberModelInteractor;
import com.example.phone.phonecallmanager.model.CallType;
import com.example.phone.phonecallmanager.model.Phone;
import com.example.phone.phonecallmanager.utils.CallUtils;
import com.example.phone.phonecallmanager.utils.KeyboardUtils;

public class AddNumberPresenter implements AddNumberContract.Presenter  {

    private final AddNumberContract.View view;
    private Phone phone;
    private CallType callType;
    private AddNumberModelInteractor addNumberModelInteractor;
    private SelectedListType selectedListType;
    private String phoneNumber;

    public AddNumberPresenter(AddNumberContract.View view) {
        this.view = view;
        this.addNumberModelInteractor = new AddNumberModelInteractor(this);
    }

    @Override
    public void handleIconTap(SelectedListType selectedListType) {
        this.selectedListType = selectedListType;
        this.callType = addNumberModelInteractor.getCallType();
        view.setIconTint(callType);
    }

    @Override
    public boolean validateData() {
        boolean numberValid = false;
        boolean typeValid = false;
        if (TextUtils.isEmpty(view.getNumberText())) {
            view.setNumberTextError();
            numberValid = false;
        } else {
            view.removeNumberError();
            numberValid = true;
        }
        if (callType == null) {
            view.setIconError();
            typeValid = false;
        } else {
            view.removeIconError();
            typeValid = true;
        }
        return numberValid && typeValid;
    }

    @Override
    public void savePhone() {
        if (!validateData()) {
            return;
        }
        if (!isEditPhoneNumber()) {
            phone = new Phone();
            phone = addNumberModelInteractor.getPhone();
        }
        phone.setName(view.getNameText());
        if (!phoneNumber.startsWith("00")) {
            phoneNumber = "00" + phoneNumber;
        }
        phone.setNumber(CallUtils.formatPhoneNumber(view.getNumberText()));
        phone.setCallType(callType);
        addNumberModelInteractor.savePhone();
    }

    @Override
    public SelectedListType getSelectedListType() {
        return selectedListType;
    }

    @Override
    public Phone getPhone() {
        return phone;
    }

    @Override
    public void phoneSaved() {
        view.closeDialogOk();
    }

    @Override
    public void closeKeyboard(Context context, View view) {
        KeyboardUtils.hideKeyboard(context, view);
    }

    @Override
    public void setEditPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean isEditPhoneNumber() {
        return !TextUtils.isEmpty(phoneNumber);
    }

    @Override
    public void initData() {
        phone = addNumberModelInteractor.getEditPhone(phoneNumber);
        callType = phone.getCallType();
        view.setNameText(phone.getName());
        view.setNumberText(CallUtils.formatPhoneNumber(phone.getNumber()));
        view.setIconTint(phone.getCallType());
        addNumberModelInteractor.stopShowEditHint();
    }
}
