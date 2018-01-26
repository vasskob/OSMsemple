package com.vasskob.downloadmaps.data.repository;

import com.vasskob.downloadmaps.data.api.ApiInterface;
import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;

import io.reactivex.Completable;
import timber.log.Timber;

public class FileDownloadRepositoryImpl implements FileDownloadRepository {

    private ApiInterface mApi;

    public FileDownloadRepositoryImpl(ApiInterface mApi) {
        this.mApi = mApi;
    }

    @Override
    public Completable downloadFile(String fileUrl, String filename) {
        Timber.d("downloadFile: URL = " + fileUrl + " FileName = " + filename);
       // return mApi.downloadFile(fileUrl);
        return null;
    }
}
