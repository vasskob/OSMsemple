package com.vasskob.downloadmaps.global;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;

import com.vasskob.downloadmaps.DownloadMapsApp;
import com.vasskob.downloadmaps.data.api.ApiInterface;
import com.vasskob.downloadmaps.domain.model.Download;
import com.vasskob.downloadmaps.presentation.main.view.MainActivity;
import com.vasskob.downloadmaps.utils.StringUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import timber.log.Timber;

public class DownloadService extends IntentService {

    public static final String FILE_URL = "FILE_URL";
    private String fileUrl;

    @Inject
    ApiInterface mApi;

    private final Download mDownloadResult = new Download();

    public DownloadService() {
        super(DownloadService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.d("onCreate: ");
        DownloadMapsApp.getInstance().getDataComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        fileUrl = intent.getStringExtra(FILE_URL);
        initDownload(fileUrl);
    }

    private void initDownload(String fileUrl) {
        Call<ResponseBody> request = mApi.downloadFile(fileUrl);
        try {
            startDownload(request.execute().body());
        } catch (IOException e) {
            Timber.e("initDownload: " + e.getMessage());
        }
    }

    private void startDownload(ResponseBody body) throws IOException {
        Timber.d("startDownload: ");
        if (body == null) {
            return;
        }

        int count;
        byte data[] = new byte[1024 * 4];
        long fileSize = body.contentLength();
        InputStream bis = new BufferedInputStream(body.byteStream(), 1024 * 8);
        File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), StringUtils.getFileName(fileUrl));
        OutputStream output = new FileOutputStream(outputFile);
        long total = 0;
        long startTime = System.currentTimeMillis();
        int timeCount = 1;

        int totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));

        mDownloadResult.setTotalFileSize(totalFileSize);

        while ((count = bis.read(data)) != -1) {

            total += count;

            double current = Math.round(total / (Math.pow(1024, 2)));

            int progress = (int) ((total * 100) / fileSize);

            long currentTime = System.currentTimeMillis() - startTime;

            if (currentTime > 100 * timeCount) {
                mDownloadResult.setCurrentFileSize((int) current);
                mDownloadResult.setProgress(progress);
                sendBroadcast(mDownloadResult);
                timeCount++;
            }

            output.write(data, 0, count);
        }
        onDownloadComplete();
        output.flush();
        output.close();
        bis.close();
    }

    private void onDownloadComplete() {
        Download download = new Download();
        download.setProgress(100);
        sendBroadcast(download);
    }

    private void sendBroadcast(Download download) {
        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
        intent.putExtra(Download.DOWNLOAD_EXTRA, download);
        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }
}
