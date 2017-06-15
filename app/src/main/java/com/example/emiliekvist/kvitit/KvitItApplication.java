package com.example.emiliekvist.kvitit;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by EmilieKvist on 15-06-2017.
 */

public class KvitItApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
