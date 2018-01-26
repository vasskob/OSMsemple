package com.vasskob.downloadmaps.domain.repository;

import io.reactivex.Completable;

public interface FileDownloadRepository {
    Completable downloadFile(String url, String filename);
}
