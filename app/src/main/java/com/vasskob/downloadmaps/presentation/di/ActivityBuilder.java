package com.vasskob.downloadmaps.presentation.di;

import com.vasskob.downloadmaps.presentation.main.di.MainActivityModule;
import com.vasskob.downloadmaps.presentation.main.di.MainActivityScope;
import com.vasskob.downloadmaps.presentation.main.view.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @MainActivityScope
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivity();
}
