package com.vasskob.downloadmaps.presentation.main.country.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;
import com.vasskob.downloadmaps.presentation.main.country.view.CountryView;

import java.io.InputStream;

@InjectViewState
public class CountryPresenter extends MvpPresenter<CountryView> {

    private FileDownloadRepository mRepository;

    public CountryPresenter(FileDownloadRepository mRepository) {
        this.mRepository = mRepository;
    }

    public void getContinents(InputStream inputStream) {

    }

    public void loadFile(String url) {
        mRepository.downloadFile(url);
    }
}
