package com.example.phone.phonecallmanager.features.blacklist.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.custom_ui.recylcer_view_ui.DividerItemDecoration;
import com.example.phone.phonecallmanager.features.blacklist.adapters.phones.BlacklistAdapter;
import com.example.phone.phonecallmanager.features.blacklist.contract.BlacklistContract;
import com.example.phone.phonecallmanager.features.blacklist.features.add.view.AddNumberFragment;
import com.example.phone.phonecallmanager.features.blacklist.presenter.BlacklistPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BlacklistFragment extends Fragment implements BlacklistContract.View {

    private static final String TAG = BlacklistFragment.class.getSimpleName();

    @BindView(R.id.rvBlacklist)
    public RecyclerView rvBlacklist;
    @BindView(R.id.fabBlacklistAdd)
    public FloatingActionButton fabBlacklistAdd;

    private BlacklistAdapter blacklistAdapter;
    private DividerItemDecoration itemDecoration;
    private BlacklistPresenter blacklistPresenter;

    public static BlacklistFragment newInstance() {
        Bundle args = new Bundle();
        BlacklistFragment fragment = new BlacklistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        blacklistPresenter = new BlacklistPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blacklist, container, false);
        ButterKnife.bind(this, view);
        initUi();
        initAdapter();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        blacklistPresenter.setAllPhoneList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.blacklist_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_blacklist_all:
                blacklistPresenter.setAllPhoneList();
                break;

            case R.id.menu_blacklist_trusted:
                blacklistPresenter.setTrustedPhoneList();
                break;

            case R.id.menu_blacklist_suspicious:
                blacklistPresenter.setSuspiciousPhoneList();
                break;

            case R.id.menu_blacklist_blocked:
                blacklistPresenter.setBlockedPhoneList();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUi() {
        rvBlacklist.setHasFixedSize(true);
        rvBlacklist.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemDecoration = new DividerItemDecoration(getResources(), getResources().getDimensionPixelOffset(R.dimen.item_separator_padding_left),
                getResources().getDimensionPixelOffset(R.dimen.item_separator_padding_right));
        rvBlacklist.addItemDecoration(itemDecoration);
        rvBlacklist.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fabBlacklistAdd.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if ((dy > 0 || dy < 0) && fabBlacklistAdd.isShown()) {
                    fabBlacklistAdd.hide();
                }
            }
        });
    }

    private void initAdapter() {
        blacklistAdapter = new BlacklistAdapter(blacklistPresenter);
        rvBlacklist.setAdapter(blacklistAdapter);
    }

    @Override
    public void refreshData() {
        blacklistAdapter.notifyDataSetChanged();
    }



    @OnClick(R.id.fabBlacklistAdd)
    public void addToBlacklist() {
        openEditFragment();
    }

    @Override
    public void openEditFragment() {
        AddNumberFragment addNumberFragment = AddNumberFragment.newInstance(blacklistPresenter.getEditPhoneNumber());
        addNumberFragment.setPresenter(blacklistPresenter);
        getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, addNumberFragment, "").addToBackStack(null).commit();
    }

    @Override
    public void closeEditFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }


}
