package com.vasskob.downloadmaps.presentation.main.di;

import com.vasskob.downloadmaps.data.api.ApiInterface;
import com.vasskob.downloadmaps.presentation.main.presenter.MainPresenter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class MainActivityModule {

    @MainActivityScope
    @Provides
    MainPresenter provideMainPresenter() {
        return new MainPresenter();    }


    @MainActivityScope
    @Provides
    ApiInterface provideApi(Retrofit retrofit) {
        return retrofit.create(ApiInterface.class);
    }

}
