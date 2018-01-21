package com.vasskob.downloadmaps.presentation.main.continent.di;

import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;
import com.vasskob.downloadmaps.domain.repository.XmlParseRepository;
import com.vasskob.downloadmaps.presentation.main.continent.presenter.ContinentPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ContinentModule {

    @ContinentScope
    @Provides
    ContinentPresenter provideContinentPresenter(FileDownloadRepository repository, XmlParseRepository parser) {
        return new ContinentPresenter(repository, parser);
    }
}
