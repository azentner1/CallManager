package com.example.phone.phonecallmanager.features.blacklist.features.add.view;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.enums.SelectedListType;
import com.example.phone.phonecallmanager.features.blacklist.contract.BlacklistContract;
import com.example.phone.phonecallmanager.features.blacklist.features.add.contract.AddNumberContract;
import com.example.phone.phonecallmanager.features.blacklist.features.add.presenter.AddNumberPresenter;
import com.example.phone.phonecallmanager.features.blacklist.presenter.BlacklistPresenter;
import com.example.phone.phonecallmanager.model.CallType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddNumberFragment extends Fragment implements AddNumberContract.View {

    private static final String TAG = AddNumberFragment.class.getSimpleName();

    @BindView(R.id.toolbarAddNumber)
    public Toolbar toolbarAddNumber;
    @BindView(R.id.etAddNumberName)
    public TextInputEditText etAddNumberName;
    @BindView(R.id.etAddNumberNumber)
    public TextInputEditText etAddNumberNumber;
    @BindView(R.id.tilAddNumberNumber)
    public TextInputLayout tilAddNumberNumber;
    @BindView(R.id.ivAddNumberTypeTrusted)
    public ImageView ivAddNumberTypeTrusted;
    @BindView(R.id.ivAddNumberTypeSuspicious)
    public ImageView ivAddNumberTypeSuspicious;
    @BindView(R.id.ivAddNumberTypeBlocked)
    public ImageView ivAddNumberTypeBlocked;
    @BindView(R.id.fabBlacklistAdd)
    public FloatingActionButton fabBlacklistAdd;
    @BindView(R.id.ivAddNumberBack)
    public ImageView ivAddNumberBack;
    @BindView(R.id.txtAddNumberTypeLabel)
    public TextView txtAddNumberTypeLabel;
    @BindView(R.id.txtAddNumberTypeError)
    public TextView txtAddNumberTypeError;

    private AddNumberPresenter addNumberPresenter;
    private BlacklistContract.Presenter blacklistPresenter;


    public static AddNumberFragment newInstance(String phoneNumber) {
        Bundle args = new Bundle();
        args.putString(Constants.PHONE_NUMBER, phoneNumber);
        AddNumberFragment fragment = new AddNumberFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setPresenter(BlacklistContract.Presenter blacklistPresenter) {
        this.blacklistPresenter = blacklistPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addNumberPresenter = new AddNumberPresenter(this);
        if (getArguments() != null) {
            addNumberPresenter.setEditPhoneNumber(getArguments().getString(Constants.PHONE_NUMBER, ""));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_number, container, false);
        ButterKnife.bind(this, view);
        if (addNumberPresenter.isEditPhoneNumber()) {
            addNumberPresenter.initData();
        }
        return view;
    }


    @OnClick(R.id.ivAddNumberTypeTrusted)
    public void onTrustedClick(View view) {
        addNumberPresenter.handleIconTap(SelectedListType.TRUSTED);
    }

    @OnClick(R.id.ivAddNumberTypeSuspicious)
    public void onSuspiciousClick(View view) {
        addNumberPresenter.handleIconTap(SelectedListType.SUSPICIOUS);
    }

    @OnClick(R.id.ivAddNumberTypeBlocked)
    public void onBlockedClick(View view) {
        addNumberPresenter.handleIconTap(SelectedListType.BLOCKED);
    }

    @OnClick(R.id.fabBlacklistAdd)
    public void onAddClick(View view) {
        addNumberPresenter.savePhone();
    }

    @OnClick(R.id.ivAddNumberBack)
    public void onBackClick() {
        addNumberPresenter.closeKeyboard(getActivity().getApplicationContext(), getView().getRootView());
        blacklistPresenter.onNumberFragmentClosed();
    }

    @Override
    public void setIconTint(CallType callType) {
        if (callType.getType().equals(Constants.TRUSTED)) {
            ivAddNumberTypeTrusted.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_trusted_circle_color));
            ivAddNumberTypeSuspicious.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_suspicious_circle));
            ivAddNumberTypeBlocked.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_blocked_circle));
        } else if (callType.getType().equals(Constants.SUSPICIOUS)) {
            ivAddNumberTypeTrusted.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_trusted_circle));
            ivAddNumberTypeSuspicious.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_suspicious_circle_color));
            ivAddNumberTypeBlocked.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_blocked_circle));
        } else {
            ivAddNumberTypeTrusted.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_trusted_circle));
            ivAddNumberTypeSuspicious.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_suspicious_circle));
            ivAddNumberTypeBlocked.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_blocked_circle_color));
        }
    }

    @Override
    public void closeDialogOk() {
        addNumberPresenter.closeKeyboard(getActivity().getApplicationContext(), getView().getRootView());
        blacklistPresenter.onNumberAdded();
        Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNameText(String name) {
        etAddNumberName.setText(name);
    }

    @Override
    public void setNumberText(String number) {
        etAddNumberNumber.setText(number);
    }

    @Override
    public String getNameText() {
        return etAddNumberName.getText().toString();
    }

    @Override
    public String getNumberText() {
        return etAddNumberNumber.getText().toString();
    }

    @Override
    public void setNumberTextError() {
        tilAddNumberNumber.setError(getString(R.string.number_field_error));
    }

    @Override
    public void setIconError() {
        txtAddNumberTypeError.setText(getString(R.string.call_type_field_error));
        txtAddNumberTypeError.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeNumberError() {
        tilAddNumberNumber.setError(null);
    }

    @Override
    public void removeIconError() {
        txtAddNumberTypeError.setVisibility(View.INVISIBLE);
    }
}
