package com.vasskob.downloadmaps.presentation.main.continent.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.vasskob.downloadmaps.domain.model.Continent;
import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;
import com.vasskob.downloadmaps.domain.repository.XmlParseRepository;
import com.vasskob.downloadmaps.presentation.main.continent.view.ContinentView;
import com.vasskob.downloadmaps.utils.MemoryUtils;

import java.io.InputStream;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

@InjectViewState
public class ContinentPresenter extends MvpPresenter<ContinentView> {

    private final XmlParseRepository mParser;
    private FileDownloadRepository mRepository;
    private CompositeDisposable mCompositeDisposable;

    public ContinentPresenter(FileDownloadRepository repository, XmlParseRepository parser) {
        mRepository = repository;
        mParser = parser;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void getFreeMemory() {
        long freeMemory = MemoryUtils.getFreeMemory();
        long totalMemory = MemoryUtils.getTotalMemory();
        getViewState().showFreeMemory(freeMemory, totalMemory);
    }

    public void getContinents(InputStream inputStream) {
        mParser.parse(inputStream)
                .doOnSubscribe(this::addDisposable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(continentList -> {
                            getViewState().showContinents(continentList);
                            LogContinents(continentList);
                        },
                        throwable -> Timber.d("getContinents: error" + throwable.getMessage()));
    }

    private void LogContinents(List<Continent> continentList) {
        for (Continent continent : continentList) {
            Timber.d("getContinents: CoNtList = " + continent + " \n");
        }
    }

    private void addDisposable(Disposable disposable) {
        Timber.d("addDisposable: " + disposable);
        mCompositeDisposable.add(disposable);
    }

    public void loadFile(String url, String filename) {
        Timber.d("loadFile: downloadFile");
//        mRepository.downloadFile(url, filename)
//                .doOnSubscribe(this::addDisposable)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(() -> Timber.d("loadFile: ONSUCCEESS "),
//                        throwable -> Timber.d("loadFile: ONERROOR"));
    }

    @Override
    public void onDestroy() {
        Timber.d("onDestroy: ");
        if (!mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.clear();
        }
        super.onDestroy();
    }
}
