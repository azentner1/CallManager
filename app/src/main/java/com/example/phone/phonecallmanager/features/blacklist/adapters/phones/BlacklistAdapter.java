package com.example.phone.phonecallmanager.features.blacklist.adapters.phones;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.constants.Constants;
import com.example.phone.phonecallmanager.enums.EmptyViewMessage;
import com.example.phone.phonecallmanager.features.blacklist.adapters.base.BlacklistAdapterViewHolder;
import com.example.phone.phonecallmanager.features.blacklist.presenter.BlacklistPresenter;
import com.example.phone.phonecallmanager.utils.RecyclerViewUtils;

import butterknife.ButterKnife;

public class BlacklistAdapter extends RecyclerView.Adapter<BlacklistAdapterViewHolder> {

    private static final String TAG = BlacklistAdapter.class.getSimpleName();

    private BlacklistPresenter blacklistPresenter;

    public BlacklistAdapter(BlacklistPresenter blacklistPresenter) {
        this.blacklistPresenter = blacklistPresenter;
    }

    @Override
    public BlacklistAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == Constants.BLACKLIST_EMPTY) {
            view = RecyclerViewUtils.getEmptyView(parent.getContext(), parent, EmptyViewMessage.LIST);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blacklist_item, parent, false);
        }
        ButterKnife.bind(this, view);
        return new BlacklistAdapterViewHolder(blacklistPresenter, view);
    }

    @Override
    public void onBindViewHolder(BlacklistAdapterViewHolder holder, int position) {
        blacklistPresenter.onBindRowPosition(position, holder);
    }

    @Override
    public int getItemCount() {
        return blacklistPresenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return blacklistPresenter.getItemViewType(position);
    }
}
