package com.vasskob.downloadmaps;

import android.app.Activity;
import android.app.Application;

import com.vasskob.downloadmaps.data.di.DaggerDataComponent;
import com.vasskob.downloadmaps.data.di.DataComponent;
import com.vasskob.downloadmaps.global.di.AppComponent;
import com.vasskob.downloadmaps.global.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import timber.log.Timber;

public class DownloadMapsApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;
    private DataComponent mDataComponent;
    private static DownloadMapsApp mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        initTimber();
        initDagger();
        mApplication = this;
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initDagger() {
        mDataComponent = DaggerDataComponent.builder()
                .application(this)
                .build();

        AppComponent mAppComponent = DaggerAppComponent.builder()
                .dataComponent(mDataComponent)
                .build();

        mAppComponent.inject(this);
    }

    public static DownloadMapsApp getInstance() {
        return mApplication;
    }

    public DataComponent getDataComponent() {
        return mDataComponent;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }
}
