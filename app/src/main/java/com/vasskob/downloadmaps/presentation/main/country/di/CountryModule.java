package com.vasskob.downloadmaps.presentation.main.country.di;

import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;
import com.vasskob.downloadmaps.presentation.main.country.presenter.CountryPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class CountryModule {

    @CountryScope
    @Provides
    CountryPresenter provideCountryPresenter(FileDownloadRepository fileDownloadRepository) {
        return new CountryPresenter(fileDownloadRepository);
    }
}
