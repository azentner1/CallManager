package com.example.phone.phonecallmanager.features.phone_log.adapters.base;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.features.phone_log.interfaces.ICallLogDataAdapter;
import com.example.phone.phonecallmanager.model.CallType;
import com.example.phone.phonecallmanager.model.PhoneCall;
import com.example.phone.phonecallmanager.utils.CallUtils;
import com.example.phone.phonecallmanager.utils.DateTimeUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneCallLogAdapterViewHolder extends RecyclerView.ViewHolder implements ICallLogDataAdapter {

    @Nullable
    @BindView(R.id.ivPhoneCallLogImage)
    public ImageView ivPhoneCallLogImage;
    @Nullable
    @BindView(R.id.txtPhoneCallLogCallerName)
    public TextView txtPhoneCallLogCallerName;
    @Nullable
    @BindView(R.id.txtPhoneCallLogCallDate)
    public TextView txtPhoneCallLogCallDate;
    @Nullable
    @BindView(R.id.ivPhoneCallLogStatus)
    public ImageView ivPhoneCallLogStatus;


    private Context context;

    public PhoneCallLogAdapterViewHolder(Context context, View view) {
        super(view);
        this.context = context;
        ButterKnife.bind(this, view);
    }

    @Override
    public void setData(PhoneCall phoneCall) {
        if (phoneCall.getPhone() != null) {
            txtPhoneCallLogCallerName.setText(PhoneNumberUtils.formatNumber(CallUtils.formatPhoneNumberForUi(phoneCall.getPhone().getNumber()), Locale.getDefault().getCountry()));
            txtPhoneCallLogCallDate.setText(DateTimeUtils.formatDateDisplayValue(phoneCall.getTimestamp()));
            ivPhoneCallLogImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_phone));
            if (phoneCall.getPhone().getCallType().getType().equals(Constants.TRUSTED)) {
                ivPhoneCallLogImage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green)));
            } else if (phoneCall.getPhone().getCallType().getType().equals(Constants.SUSPICIOUS)) {
                ivPhoneCallLogImage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.orange)));
            } else {
                ivPhoneCallLogImage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)));
            }
        } else {
            txtPhoneCallLogCallerName.setText(PhoneNumberUtils.formatNumber(CallUtils.formatPhoneNumberForUi(phoneCall.getMessage().getPhoneNumber()), Locale.getDefault().getCountry()));
            txtPhoneCallLogCallDate.setText(DateTimeUtils.formatDateDisplayValue(phoneCall.getTimestamp()));
            ivPhoneCallLogImage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_message));
            ivPhoneCallLogImage.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red)));
        }
    }



}
