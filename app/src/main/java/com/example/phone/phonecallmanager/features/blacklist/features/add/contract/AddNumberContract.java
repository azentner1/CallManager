package com.example.phone.phonecallmanager.features.blacklist.features.add.contract;

import android.content.Context;
import android.view.View;

import com.example.phone.phonecallmanager.enums.SelectedListType;
import com.example.phone.phonecallmanager.features.blacklist.contract.BlacklistContract;
import com.example.phone.phonecallmanager.features.blacklist.presenter.BlacklistPresenter;
import com.example.phone.phonecallmanager.model.CallType;
import com.example.phone.phonecallmanager.model.Phone;

public interface AddNumberContract {

    interface Presenter {
        void savePhone();
        void handleIconTap(SelectedListType selectedListType);
        SelectedListType getSelectedListType();
        Phone getPhone();
        boolean validateData();
        void phoneSaved();
        void closeKeyboard(Context context, android.view.View view);
        void setEditPhoneNumber(String phoneNumber);
        boolean isEditPhoneNumber();
        void initData();
    }

    interface View {
        void setPresenter(BlacklistContract.Presenter presenter);
        void setIconTint(CallType callType);
        void closeDialogOk();
        String getNameText();
        String getNumberText();
        void setNameText(String name);
        void setNumberText(String number);
        void setNumberTextError();
        void setIconError();
        void removeNumberError();
        void removeIconError();
    }

    interface Model {
        Phone createPhone();
        CallType getCallType();
        void setCallType(CallType callType);
        Phone getPhone();
        void savePhone();
        Phone getEditPhone(String phoneNumber);
        void stopShowEditHint();
    }

}
