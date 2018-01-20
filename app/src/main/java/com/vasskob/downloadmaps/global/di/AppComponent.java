package com.vasskob.downloadmaps.global.di;

import com.vasskob.downloadmaps.DownloadMapsApp;
import com.vasskob.downloadmaps.data.di.DataComponent;
import com.vasskob.downloadmaps.data.di.DataScope;
import com.vasskob.downloadmaps.presentation.di.ActivityBuilder;

import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;


@DataScope
@Component(
        dependencies = DataComponent.class,

        modules = {
                AppModule.class,
                AndroidSupportInjectionModule.class,

                ActivityBuilder.class
        })

public interface AppComponent {
    void inject(DownloadMapsApp application);
}
