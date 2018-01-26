package com.vasskob.downloadmaps.utils;

import com.vasskob.downloadmaps.BuildConfig;

public class StringUtils {

    private static final String QUERY = "download.php?standard=yes&file=";
    private static String FILE_URL_PREFIX = BuildConfig.BASE_URL + QUERY;
    private static final String FILE_URL_SUFFIX = "_2.obf.zip";

    public static String getDownloadURL(String regionName) {
        return FILE_URL_PREFIX + getCapitalName(regionName) + FILE_URL_SUFFIX;
    }

    public static String getCapitalName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String getFileName(String fileUrl) {
        return fileUrl.substring(FILE_URL_PREFIX.length(), fileUrl.length());
    }
}
/*
http://download.osmand.net/download.php?standard=yes&file=Berlin_germany_europe_2.obf.zip Bad
http://download.osmand.net/download.php?standard=yes&file=Germany_berlin_europe_2.obf.zip Good */
