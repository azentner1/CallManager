package com.example.phone.phonecallmanager.features.phone_call.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneNumberUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.features.phone_call.contract.CallContract;
import com.example.phone.phonecallmanager.features.phone_call.presenter.CallPresenter;
import com.example.phone.phonecallmanager.utils.CallUtils;
import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CallFragment extends Fragment implements CallContract.View {

    private static final String TAG = CallFragment.class.getSimpleName();

    @BindView(R.id.rplCall)
    public RipplePulseLayout rplCall;
    @BindView(R.id.txtCallNumber)
    public TextView txtCallNumber;
    @BindView(R.id.txtCallNumberInfo)
    public TextView txtCallNumberInfo;
    @BindView(R.id.ivCallTrusted)
    public ImageView ivCallTrusted;
    @BindView(R.id.ivCallSuspicious)
    public ImageView ivCallSuspicious;
    @BindView(R.id.ivCallBlocked)
    public ImageView ivCallBlocked;
    @BindView(R.id.txtCallNumberScam)
    public TextView txtCallNumberScam;

    private CallPresenter callPresenter;

    public static CallFragment newInstance(String phoneNumber) {
        Bundle args = new Bundle();
        args.putString(Constants.PHONE_NUMBER, phoneNumber);
        CallFragment fragment = new CallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callPresenter = new CallPresenter(this);
        if (getArguments() != null) {
            callPresenter.setPhoneNumber(getArguments().getString(Constants.PHONE_NUMBER, ""));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call, container, false);
        ButterKnife.bind(this, view);
        rplCall.startRippleAnimation();
        callPresenter.init();
        return view;
    }

    @Override
    public void setNumberText() {
        txtCallNumber.setText(PhoneNumberUtils.formatNumber(CallUtils.formatPhoneNumberForUi(callPresenter.getPhoneNumber()), Locale.getDefault().getCountry()));
    }

    @Override
    public void setIconTint() {
        if (callPresenter.getCallType().getType().equals(Constants.TRUSTED)) {
            ivCallTrusted.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_trusted_circle_color));
            ivCallSuspicious.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_suspicious_circle));
            ivCallBlocked.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_blocked_circle));
        } else if (callPresenter.getCallType().getType().equals(Constants.SUSPICIOUS)) {
            ivCallTrusted.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_trusted_circle));
            ivCallSuspicious.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_suspicious_circle_color));
            ivCallBlocked.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_blocked_circle));
        } else {
            ivCallTrusted.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_trusted_circle));
            ivCallSuspicious.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_suspicious_circle));
            ivCallBlocked.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_blocked_circle_color));
        }
    }

    @Override
    public void setMessage() {
        txtCallNumberInfo.setText(String.format(getString(R.string.call_number_info_text), CallUtils.getCallTypeDisplayString(callPresenter.getCallType())));
        if (callPresenter.getCallType().getType().equals(Constants.TRUSTED)) {
            txtCallNumberInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
        } else if (callPresenter.getCallType().getType().equals(Constants.SUSPICIOUS)) {
            txtCallNumberInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.orange));
        } else {
            txtCallNumberInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
        }
    }

    @Override
    public void setScamMessage() {
        txtCallNumberScam.setVisibility(View.VISIBLE);
    }
}
