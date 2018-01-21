package com.vasskob.downloadmaps.presentation.main.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.vasskob.downloadmaps.domain.model.Region;
import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;
import com.vasskob.downloadmaps.presentation.main.view.MainView;
import com.vasskob.downloadmaps.utils.MemoryUtils;
import com.vasskob.downloadmaps.utils.XmlParser;

import java.io.InputStream;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private FileDownloadRepository mRepository;

    public MainPresenter(FileDownloadRepository mRepository) {
        this.mRepository = mRepository;
    }

    public void getFreeMemory() {
        long freeMemory = MemoryUtils.getFreeMemory();
        long totalMemory = MemoryUtils.getTotalMemory();
        getViewState().showFreeMemory(freeMemory, totalMemory);
    }

    public void getContinents(InputStream inputStream) {
        XmlParser parser = new XmlParser();
        Region root = parser.parse(inputStream);
        if (root.getRegions() != null) {
            getViewState().showRegions(root.getRegions());
        }
    }

    public void loadFile(String url) {
        mRepository.downloadFile(url);
    }
}
