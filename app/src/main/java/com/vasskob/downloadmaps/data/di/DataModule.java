package com.vasskob.downloadmaps.data.di;

import com.vasskob.downloadmaps.data.api.ApiInterface;
import com.vasskob.downloadmaps.data.repository.FileDownloadRepositoryImpl;
import com.vasskob.downloadmaps.data.repository.XmlParseRepositoryImpl;
import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;
import com.vasskob.downloadmaps.domain.repository.XmlParseRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @Singleton
    FileDownloadRepository provideFileDownloadRepository(ApiInterface api) {
        return new FileDownloadRepositoryImpl(api);
    }

    @Provides
    @Singleton
    XmlParseRepository provideXmlParser() {
        return new XmlParseRepositoryImpl();
    }

}