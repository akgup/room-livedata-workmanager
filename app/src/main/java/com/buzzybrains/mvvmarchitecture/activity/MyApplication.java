package com.buzzybrains.mvvmarchitecture.activity;

import android.app.Application;

import com.buzzybrains.mvvmarchitecture.util.ReleaseTree;

import timber.log.BuildConfig;
import timber.log.Timber;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }
}
