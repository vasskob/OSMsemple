package com.vasskob.downloadmaps.data.repository;

import com.vasskob.downloadmaps.data.api.ApiInterface;
import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;

public class FileDownloadRepositoryImpl implements FileDownloadRepository {

    private ApiInterface mApi;

    public FileDownloadRepositoryImpl(ApiInterface mApi) {
        this.mApi = mApi;
    }

    @Override
    public void downloadFile(String url) {
        mApi.downloadFile(url);
    }
}
