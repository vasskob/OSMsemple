package com.vasskob.downloadmaps.presentation.main.country.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.vasskob.downloadmaps.domain.repository.FileDownloadRepository;
import com.vasskob.downloadmaps.presentation.main.country.view.CountryView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

@InjectViewState
public class CountryPresenter extends MvpPresenter<CountryView> {

    private FileDownloadRepository mRepository;
    private CompositeDisposable mCompositeDisposable;

    public CountryPresenter(FileDownloadRepository mRepository) {
        this.mRepository = mRepository;
        mCompositeDisposable = new CompositeDisposable();
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

    private void addDisposable(Disposable disposable) {
        Timber.d("addDisposable: " + disposable);
        mCompositeDisposable.add(disposable);
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
