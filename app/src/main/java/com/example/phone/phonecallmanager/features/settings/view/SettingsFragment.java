package com.example.phone.phonecallmanager.features.settings.view;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.features.settings.SettingsContract;
import com.example.phone.phonecallmanager.features.settings.presenter.SettingsPresenter;
import com.example.phone.phonecallmanager.utils.KeyboardUtils;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;


public class SettingsFragment extends Fragment implements SettingsContract.View {

    private static final int CALL_PERMISSIONS_REQUEST_CODE = 2001;
    private static final int SMS_PERMISSIONS_REQUEST_CODE = 2002;
    private static final int CONTACT_PERMISSIONS_REQUEST_CODE = 2003;

    private SettingsPresenter settingsPresenter;

    @BindView(R.id.swSettingsBlockContact)
    public Switch swSettingsBlockContact;
    @BindView(R.id.rlSettingsBlockContactRoot)
    public RelativeLayout rlSettingsBlockContactRoot;
    @BindView(R.id.ntvSettings)
    public NachoTextView ntvSettings;
    @BindView(R.id.fabSettingsSave)
    public FloatingActionButton fabSettingsSave;
    @BindView(R.id.rlSettingsPermissionsCallRoot)
    public RelativeLayout rlSettingsPermissionsCallRoot;
    @BindView(R.id.ivSettingsPermissionCall)
    public ImageView ivSettingsPermissionCall;
    @BindView(R.id.rlSettingsPermissionsSmsRoot)
    public RelativeLayout rlSettingsPermissionsSmsRoot;
    @BindView(R.id.ivSettingsPermissionSms)
    public ImageView ivSettingsPermissionSms;
    @BindView(R.id.rlSettingsPermissionsContactRoot)
    public RelativeLayout rlSettingsPermissionsContactRoot;
    @BindView(R.id.ivSettingsPermissionContact)
    public ImageView ivSettingsPermissionContact;

    public static SettingsFragment newInstance() {
        Bundle args = new Bundle();
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsPresenter = new SettingsPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        settingsPresenter.initSettings();
        initUi();
        setData();
        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == CALL_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ivSettingsPermissionCall.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_granted));
            } else {
                ivSettingsPermissionCall.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_denied));
            }
        } else if (requestCode == SMS_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ivSettingsPermissionSms.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_granted));
            } else {
                ivSettingsPermissionSms.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_denied));
            }
        } else if (requestCode == CONTACT_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ivSettingsPermissionContact.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_granted));
            } else {
                ivSettingsPermissionContact.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_denied));
            }
        }
    }

    public void initUi() {
        ntvSettings.addChipTerminator(',', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
        ntvSettings.enableEditChipOnTouch(false, true);

    }

    public void setData() {
        ntvSettings.setText(settingsPresenter.getBannedWordsList());
        if (areCallPermissionsGranted()) {
            ivSettingsPermissionCall.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_granted));
        } else {
            ivSettingsPermissionCall.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_denied));
        }
        if (areSmsPermissionsGranted()) {
            ivSettingsPermissionSms.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_granted));
        } else {
            ivSettingsPermissionSms.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_denied));
        }
        if (areContactPermissionsGranted()) {
            ivSettingsPermissionContact.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_granted));
        } else {
            ivSettingsPermissionContact.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_permission_denied));
        }
    }


    @Override
    public void setBlockAllExceptContactsValue(boolean value) {
        swSettingsBlockContact.setChecked(value);
    }


    @OnClick(R.id.rlSettingsBlockContactRoot)
    public void onBlockAllCallExceptFromContactsRoot() {
        swSettingsBlockContact.setChecked(!swSettingsBlockContact.isChecked());
    }

    @OnCheckedChanged(R.id.swSettingsBlockContact)
    public void onBlockAllCallExceptFromContacts() {
        settingsPresenter.setBlockAllExceptContacts(swSettingsBlockContact.isChecked());
    }


    @OnClick(R.id.fabSettingsSave)
    public void onSettingsSaveClick() {
        settingsPresenter.saveSettings();
        KeyboardUtils.hideKeyboard(getContext(), ntvSettings);
        Toast.makeText(getContext(), R.string.saved, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.rlSettingsPermissionsCallRoot)
    public void onSettingsPermissionsCallRoot() {
        if (!areCallPermissionsGranted()) {
            askCallPermission();
        }
    }

    @OnClick(R.id.rlSettingsPermissionsSmsRoot)
    public void onSettingsPermissionsSmsRoot() {
        if (!areSmsPermissionsGranted()) {
            askSmsPermission();
        }
    }

    @OnClick(R.id.rlSettingsPermissionsContactRoot)
    public void onSettingsPermissionsContactRoot() {
        if (!areContactPermissionsGranted()) {
            askContactPermission();
        }
    }

    @Override
    public List<String> getBannedWordsList() {
        return ntvSettings.getChipValues();
    }


    public void askCallPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE};
            requestPermissions(permissions, CALL_PERMISSIONS_REQUEST_CODE);
        }
    }

    public void askSmsPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS};
            requestPermissions(permissions, SMS_PERMISSIONS_REQUEST_CODE);
        }
    }

    public void askContactPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.READ_CONTACTS};
            requestPermissions(permissions, CONTACT_PERMISSIONS_REQUEST_CODE);
        }
    }

    public boolean areCallPermissionsGranted() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean areSmsPermissionsGranted() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean areContactPermissionsGranted() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }
}
