package com.vasskob.downloadmaps.data.di;

import com.vasskob.downloadmaps.DownloadMapsApp;
import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;
import com.vasskob.downloadmaps.domain.repository.XmlParseRepository;
import com.vasskob.downloadmaps.global.di.AppModule;
import com.vasskob.downloadmaps.global.DownloadService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,

        NetworkModule.class,
        DataModule.class
})
public interface DataComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(DownloadMapsApp application);

        DataComponent build();
    }

    void inject(DownloadService downloadService);

    FileDownloadRepository getFileDownloadRepository();

    XmlParseRepository getXmlParseRepository();

}
