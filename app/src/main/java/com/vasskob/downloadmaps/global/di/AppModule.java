package com.vasskob.downloadmaps.global.di;

import android.content.Context;

import com.vasskob.downloadmaps.DownloadMapsApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(DownloadMapsApp application) {
        return application.getApplicationContext();
    }
}
