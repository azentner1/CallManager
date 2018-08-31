package com.example.phone.phonecallmanager.features.main;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.phone.phonecallmanager.features.blacklist.view.BlacklistFragment;
import com.example.phone.phonecallmanager.features.phone_log.view.PhoneCallLogFragment;
import com.example.phone.phonecallmanager.features.settings.view.SettingsFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 3;
    private String[] tabTitles;
    private Context context;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        setUpTabTitles();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return PhoneCallLogFragment.newInstance();
            case 1:
                return BlacklistFragment.newInstance();
            case 2:
                return SettingsFragment.newInstance();
            default:
                return PhoneCallLogFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }


    private void setUpTabTitles() {
        tabTitles = new String[]{"Log", "Phones", "Settings"};
    }
}
