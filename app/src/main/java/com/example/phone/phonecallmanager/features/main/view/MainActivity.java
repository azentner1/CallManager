package com.example.phone.phonecallmanager.features.main.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.phone.phonecallmanager.R;
import com.example.phone.phonecallmanager.features.main.ViewPagerAdapter;
import com.example.phone.phonecallmanager.features.main.contract.MainContract;
import com.example.phone.phonecallmanager.features.main.presenter.MainPresenter;
import com.example.phone.phonecallmanager.features.phone_call.view.CallFragment;
import com.example.phone.phonecallmanager.utils.KeyboardUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_CODE = 2000;

    @BindView(R.id.tabLayoutMain)
    public TabLayout tabLayoutMain;
    @BindView(R.id.viewPagerMain)
    public ViewPager viewPagerMain;
    @BindView(R.id.toolbarMain)
    public Toolbar toolbarMain;

    private ViewPagerAdapter viewPagerAdapter;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainPresenter = new MainPresenter(this);
        if (!arePermissionsGranted()) {
            invokePermissions();
        }
        initUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mainPresenter.handleIntent(intent);
    }

    private void initUi() {
        setSupportActionBar(toolbarMain);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        viewPagerMain.setAdapter(viewPagerAdapter);
        tabLayoutMain.setupWithViewPager(viewPagerMain);
        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                KeyboardUtils.hideKeyboard(MainActivity.this, viewPagerMain);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(findViewById(R.id.root), R.string.permissions_reject_warning, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    public boolean arePermissionsGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;
    }

    public void invokePermissions() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS, Manifest.permission.READ_CONTACTS};
            requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void openCallFragment() {
        getSupportFragmentManager().beginTransaction().add(android.R.id.content, CallFragment.newInstance(mainPresenter.getPhoneNumber()), "").
                addToBackStack(null).commit();
    }

    @Override
    public void closeCallFragment() {
        getSupportFragmentManager().popBackStack();
    }

}
