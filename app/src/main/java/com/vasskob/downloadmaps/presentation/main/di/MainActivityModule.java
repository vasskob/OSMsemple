package com.vasskob.downloadmaps.presentation.main.di;

import com.vasskob.downloadmaps.presentation.main.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @MainActivityScope
    @Provides
    MainPresenter provideMainPresenter() {
        return new MainPresenter();
    }
}
