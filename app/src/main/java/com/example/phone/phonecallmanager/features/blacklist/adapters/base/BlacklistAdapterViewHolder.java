package com.example.phone.phonecallmanager.features.blacklist.adapters.base;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.features.blacklist.interfaces.IBlacklistDataAdapter;
import com.example.phone.phonecallmanager.features.blacklist.presenter.BlacklistPresenter;
import com.example.phone.phonecallmanager.features.phone_log.interfaces.ICallLogDataAdapter;
import com.example.phone.phonecallmanager.model.Phone;
import com.example.phone.phonecallmanager.model.PhoneCall;
import com.example.phone.phonecallmanager.utils.CallUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemLongClick;
import butterknife.OnLongClick;
import butterknife.Optional;

public class BlacklistAdapterViewHolder extends RecyclerView.ViewHolder implements IBlacklistDataAdapter {

    @Nullable
    @BindView(R.id.rlBlacklistRoot)
    public RelativeLayout rlBlacklistRoot;
    @Nullable
    @BindView(R.id.ivBlacklistImage)
    public ImageView ivBlacklistImage;
    @Nullable
    @BindView(R.id.txtBlacklistName)
    public TextView txtBlacklistName;
    @Nullable
    @BindView(R.id.txtBlacklistPhoneNumber)
    public TextView txtBlacklistPhoneNumber;
    @Nullable
    @BindView(R.id.txtBlacklistPhoneEditHint)
    public TextView txtBlacklistPhoneEditHint;

    private BlacklistPresenter blacklistPresenter;


    public BlacklistAdapterViewHolder(BlacklistPresenter blacklistPresenter, View view) {
        super(view);
        ButterKnife.bind(this, view);
        this.blacklistPresenter = blacklistPresenter;
    }

    @Override
    public void setData(Phone phone) {
        txtBlacklistName.setText(CallUtils.formatName(phone.getName()));
        txtBlacklistPhoneNumber.setText(PhoneNumberUtils.formatNumber(CallUtils.formatPhoneNumberForUi(phone.getNumber()), Locale.getDefault().getCountry()));
        if (blacklistPresenter.shouldShowEditHint()) {
            txtBlacklistPhoneEditHint.setVisibility(View.VISIBLE);
        } else {
            txtBlacklistPhoneEditHint.setVisibility(View.GONE);
        }
    }

    @Optional
    @OnLongClick(R.id.rlBlacklistRoot)
    public boolean onLongItemClick() {
        blacklistPresenter.onEditPhone(getAdapterPosition());
        return true;
    }
}
