package com.vasskob.downloadmaps.presentation.main.district.di;

import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;
import com.vasskob.downloadmaps.presentation.main.district.presenter.DistrictPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class DistrictModule {

    @DistrictScope
    @Provides
    DistrictPresenter provideDistrictPresenter(FileDownloadRepository fileDownloadRepository) {
        return new DistrictPresenter(fileDownloadRepository);
    }
}
