package com.vasskob.downloadmaps.presentation.main.presenter;

import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;

public class MainPresenter {

    private FileDownloadRepository mRepository;

    public MainPresenter(FileDownloadRepository mRepository) {
        this.mRepository = mRepository;
    }

    public void loadFile(String url) {
        mRepository.downloadFile(url);
    }
}
