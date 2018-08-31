package com.example.phone.phonecallmanager.features.blacklist.contract;

import com.example.phone.phonecallmanager.enums.SelectedListType;
import com.example.phone.phonecallmanager.model.Phone;

import java.util.ArrayList;

public interface BlacklistContract {

    interface Presenter {
        void setAllPhoneList();
        void setTrustedPhoneList();
        void setSuspiciousPhoneList();
        void setBlockedPhoneList();
        void onNumberAdded();
        void onNumberFragmentClosed();
        void onEditPhone(int position);
        String getEditPhoneNumber();
        boolean shouldShowEditHint();
    }

    interface View {
        void refreshData();
        void closeEditFragment();
        void openEditFragment();
    }

    interface Model {
        ArrayList<Phone> getBlockedPhones();
        ArrayList<Phone> getSuspiciousPhones();
        ArrayList<Phone> getTrustedPhones();
        ArrayList<Phone> getAllPhones();
        boolean getShouldShowEditHint();
    }
}
