package com.example.phone.phonecallmanager.features.main.contract;

public interface MainContract {

    interface Presenter {
        void setPhoneNumber(String phoneNumber);
    }

    interface View {
        void openCallFragment();
        void closeCallFragment();
    }

}
