package com.example.phone.phonecallmanager.features.phone_log.presenter;

import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.features.phone_log.contract.PhoneCallLogContract;
import com.example.phone.phonecallmanager.features.phone_log.interfaces.ICallLogDataAdapter;
import com.example.phone.phonecallmanager.features.phone_log.model.PhoneCallModelInteractor;
import com.example.phone.phonecallmanager.model.PhoneCall;

import java.util.ArrayList;

public class PhoneCallLogPresenter implements PhoneCallLogContract.Presenter {

    private PhoneCallLogContract.View view;
    private ArrayList<PhoneCall> phoneCalls;
    private PhoneCallModelInteractor phoneCallModelInteractor;

    public PhoneCallLogPresenter(PhoneCallLogContract.View view) {
        this.view = view;
        this.phoneCallModelInteractor = new PhoneCallModelInteractor();
    }

    public void onBindRowPosition(int position, ICallLogDataAdapter iCallLogDataAdapter) {
        if (phoneCalls == null || phoneCalls.size() == 0) {
            return;
        }
        PhoneCall phoneCall = phoneCalls.get(position);
        iCallLogDataAdapter.setData(phoneCall);
    }

    public int getItemCount() {
        if (phoneCalls == null || phoneCalls.size() == 0) {
            return 1;
        }
        return phoneCalls.size();
    }

    public int getItemViewType(int position) {
        if (phoneCalls == null || phoneCalls.size() == 0) {
            return Constants.PHONE_LOG_EMPTY;
        }
        return Constants.PHONE_LOG_ITEM;
    }

    @Override
    public void setPhoneCallData() {
        this.phoneCalls = phoneCallModelInteractor.getPhoneCallList();
        view.refreshData();
    }
}
