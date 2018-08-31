package com.example.phone.phonecallmanager.features.phone_log.adapters.phone_call_log;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.enums.EmptyViewMessage;
import com.example.phone.phonecallmanager.features.phone_log.adapters.base.PhoneCallLogAdapterViewHolder;
import com.example.phone.phonecallmanager.features.phone_log.presenter.PhoneCallLogPresenter;
import com.example.phone.phonecallmanager.utils.RecyclerViewUtils;

import butterknife.ButterKnife;

public class PhoneCallLogAdapter extends RecyclerView.Adapter<PhoneCallLogAdapterViewHolder> {

    private static final String TAG = PhoneCallLogAdapter.class.getSimpleName();

    private Context context;
    private PhoneCallLogPresenter phoneCallLogPresenter;

    public PhoneCallLogAdapter(Context context, PhoneCallLogPresenter phoneCallLogPresenter) {
        this.context = context;
        this.phoneCallLogPresenter = phoneCallLogPresenter;
    }

    @Override
    public PhoneCallLogAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == Constants.PHONE_LOG_EMPTY) {
            view = RecyclerViewUtils.getEmptyView(parent.getContext(), parent, EmptyViewMessage.LOG);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_call_log_item, parent, false);
        }
        ButterKnife.bind(this, view);
        return new PhoneCallLogAdapterViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(PhoneCallLogAdapterViewHolder holder, int position) {
        phoneCallLogPresenter.onBindRowPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return phoneCallLogPresenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return phoneCallLogPresenter.getItemViewType(position);
    }
}
