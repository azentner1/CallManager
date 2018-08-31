package com.example.phone.phonecallmanager.features.phone_log.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.custom_ui.recylcer_view_ui.DividerItemDecoration;
import com.example.phone.phonecallmanager.features.phone_log.adapters.phone_call_log.PhoneCallLogAdapter;
import com.example.phone.phonecallmanager.features.phone_log.contract.PhoneCallLogContract;
import com.example.phone.phonecallmanager.features.phone_log.presenter.PhoneCallLogPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneCallLogFragment extends Fragment implements PhoneCallLogContract.View {


    private static final String TAG = PhoneCallLogFragment.class.getSimpleName();

    @BindView(R.id.rvPhoneCallLog)
    public RecyclerView rvPhoneCallLog;
    private PhoneCallLogAdapter phoneCallLogAdapter;
    private PhoneCallLogPresenter phoneCallLogPresenter;
    private DividerItemDecoration itemDecoration;

    public static PhoneCallLogFragment newInstance() {
        Bundle args = new Bundle();
        PhoneCallLogFragment fragment = new PhoneCallLogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneCallLogPresenter = new PhoneCallLogPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone_call_log, container, false);
        ButterKnife.bind(this, view);
        initUi();
        initAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        phoneCallLogPresenter.setPhoneCallData();
    }

    private void initUi() {
        rvPhoneCallLog.setHasFixedSize(true);
        rvPhoneCallLog.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemDecoration = new DividerItemDecoration(getResources(), getResources().getDimensionPixelOffset(R.dimen.item_separator_padding_left),
                getResources().getDimensionPixelOffset(R.dimen.item_separator_padding_right));
        rvPhoneCallLog.addItemDecoration(itemDecoration);
    }

    private void initAdapter() {
        phoneCallLogAdapter = new PhoneCallLogAdapter(getContext(), phoneCallLogPresenter);
        rvPhoneCallLog.setAdapter(phoneCallLogAdapter);
    }

    @Override
    public void refreshData() {
        phoneCallLogAdapter.notifyDataSetChanged();
    }
}
