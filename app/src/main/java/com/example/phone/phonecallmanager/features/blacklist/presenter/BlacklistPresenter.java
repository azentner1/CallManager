package com.example.phone.phonecallmanager.features.blacklist.presenter;

import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.enums.SelectedListType;
import com.example.phone.phonecallmanager.features.blacklist.contract.BlacklistContract;
import com.example.phone.phonecallmanager.features.blacklist.interfaces.IBlacklistDataAdapter;
import com.example.phone.phonecallmanager.features.blacklist.model.BlacklistModelInteractor;
import com.example.phone.phonecallmanager.model.Phone;

import java.util.ArrayList;

public class BlacklistPresenter implements BlacklistContract.Presenter {

    private BlacklistContract.View view;
    private ArrayList<Phone> blockedPhones;
    private ArrayList<Phone> suspiciousPhones;
    private ArrayList<Phone> trustedPhones;
    private ArrayList<Phone> allPhones;
    private BlacklistModelInteractor blacklistModelInteractor;
    private SelectedListType selectedListType;
    private String phoneNumber = "";

    public BlacklistPresenter(BlacklistContract.View view) {
        this.view = view;
        this.selectedListType = SelectedListType.ALL;
        blacklistModelInteractor = new BlacklistModelInteractor();
    }

    public void onBindRowPosition(int position, IBlacklistDataAdapter blacklistDataAdapter) {
        if (shouldAbort()) {
            return;
        }
        Phone phone;
        if (selectedListType == null) {
            selectedListType = SelectedListType.ALL;
        }

        if (selectedListType.equals(SelectedListType.TRUSTED)) {
            phone = trustedPhones.get(position);
        } else if (selectedListType.equals(SelectedListType.SUSPICIOUS)) {
            phone = suspiciousPhones.get(position);
        } else if (selectedListType.equals(SelectedListType.BLOCKED)) {
            phone = blockedPhones.get(position);
        } else {
            phone = allPhones.get(position);
        }
        blacklistDataAdapter.setData(phone);
    }


    public int getItemCount() {
        if (selectedListType.equals(SelectedListType.TRUSTED)) {
            if (trustedPhones == null || trustedPhones.size() == 0) {
                return 1;
            }
            return trustedPhones.size();
        } else if (selectedListType.equals(SelectedListType.SUSPICIOUS)) {
            if (suspiciousPhones == null || suspiciousPhones.size() == 0) {
                return 1;
            }
            return suspiciousPhones.size();
        } else if (selectedListType.equals(SelectedListType.BLOCKED)) {
            if (blockedPhones == null || blockedPhones.size() == 0) {
                return 1;
            }
            return blockedPhones.size();
        } else {
            if (allPhones == null || allPhones.size() == 0) {
                return 1;
            }
            return allPhones.size();
        }
    }

    @Override
    public void setAllPhoneList() {
        this.allPhones = blacklistModelInteractor.getAllPhones();
        selectedListType = SelectedListType.ALL;
        view.refreshData();
    }

    @Override
    public void setBlockedPhoneList() {
        this.blockedPhones = blacklistModelInteractor.getBlockedPhones();
        selectedListType = SelectedListType.BLOCKED;
        view.refreshData();
    }

    @Override
    public void setTrustedPhoneList() {
        this.trustedPhones = blacklistModelInteractor.getTrustedPhones();
        selectedListType = SelectedListType.TRUSTED;
        view.refreshData();
    }

    @Override
    public void setSuspiciousPhoneList() {
        this.suspiciousPhones = blacklistModelInteractor.getSuspiciousPhones();
        selectedListType = SelectedListType.SUSPICIOUS;
        view.refreshData();
    }

    public int getItemViewType(int position) {
        if (selectedListType.equals(SelectedListType.TRUSTED)) {
            if (trustedPhones == null || trustedPhones.size() == 0) {
                return Constants.BLACKLIST_EMPTY;
            }
            return Constants.BLACKLIST_ITEM;
        } else if (selectedListType.equals(SelectedListType.SUSPICIOUS)) {
            if (suspiciousPhones == null || suspiciousPhones.size() == 0) {
                return Constants.BLACKLIST_EMPTY;
            }
            return Constants.BLACKLIST_ITEM;
        } else if (selectedListType.equals(SelectedListType.BLOCKED)) {
            if (blockedPhones == null || blockedPhones.size() == 0) {
                return Constants.BLACKLIST_EMPTY;
            }
            return Constants.BLACKLIST_ITEM;
        } else {
            if (allPhones == null || allPhones.size() == 0) {
                return Constants.BLACKLIST_EMPTY;
            }
            return Constants.BLACKLIST_ITEM;
        }
    }

    private boolean shouldAbort() {
        if (selectedListType.equals(SelectedListType.TRUSTED)) {
            return  trustedPhones == null || trustedPhones.size() == 0;
        } else if (selectedListType.equals(SelectedListType.SUSPICIOUS)) {
            return suspiciousPhones == null || suspiciousPhones.size() == 0;
        } else if (selectedListType.equals(SelectedListType.BLOCKED)) {
            return blockedPhones == null || blockedPhones.size() == 0;
        } else {
            return allPhones == null || allPhones.size() == 0;
        }
    }

    private void updateListData() {
        if (selectedListType.equals(SelectedListType.ALL)) {
            setAllPhoneList();
        } else if (selectedListType.equals(SelectedListType.TRUSTED)) {
            setTrustedPhoneList();
        } else if (selectedListType.equals(SelectedListType.SUSPICIOUS)) {
            setSuspiciousPhoneList();
        } else {
            setBlockedPhoneList();
        }
    }

    @Override
    public void onNumberFragmentClosed() {
        this.phoneNumber = "";
        view.closeEditFragment();
    }

    @Override
    public void onNumberAdded() {
        this.phoneNumber = "";
        view.closeEditFragment();
        updateListData();
    }

    @Override
    public void onEditPhone(int position) {
        Phone phone;
        if (selectedListType.equals(SelectedListType.ALL)) {
            phone = allPhones.get(position);
        } else if (selectedListType.equals(SelectedListType.TRUSTED)) {
            phone = trustedPhones.get(position);
        } else if (selectedListType.equals(SelectedListType.SUSPICIOUS)) {
            phone = suspiciousPhones.get(position);
        } else {
            phone = blockedPhones.get(position);
        }
        this.phoneNumber = phone.getNumber();
        view.openEditFragment();
    }

    @Override
    public String getEditPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean shouldShowEditHint() {
        return blacklistModelInteractor.getShouldShowEditHint();
    }
}
