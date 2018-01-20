package com.vasskob.downloadmaps.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.NonNull;

public class MemoryUtils {

    public static long getTotalMemory() {
        String externalStorage = Environment.getExternalStorageDirectory().getPath();
        StatFs statFs = new StatFs(externalStorage);
        return statFs.getBlockSizeLong() * statFs.getBlockCountLong();
    }

    public static long getFreeMemory() {
        String externalStorage = Environment.getExternalStorageDirectory().getPath();
        StatFs statFs = new StatFs(externalStorage);
        return statFs.getBlockSizeLong() * statFs.getAvailableBlocksLong();
    }

    public static String formatSize(@NonNull Context context, long bytes) {
        return android.text.format.Formatter.formatFileSize(context, bytes);
    }
}
