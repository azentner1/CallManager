package com.example.phone.phonecallmanager;

import android.app.Application;

import com.example.phone.phonecallmanager.utils.RealmUtils;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        //initializing test data as per assignment instructions (predefined phone numbers, types)
        if (!RealmUtils.isInitialRealmDataSet()) {
            RealmUtils.initTestRealmData();
        }
    }
}
