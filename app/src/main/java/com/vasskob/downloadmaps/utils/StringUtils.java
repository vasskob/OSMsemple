package com.vasskob.downloadmaps.utils;

import android.text.TextUtils;

import com.vasskob.downloadmaps.BuildConfig;
import com.vasskob.downloadmaps.domain.model.Region;

public class StringUtils {

    public static String getCapitalName(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public static String getDownloadURL(Region region) {
        if (TextUtils.isEmpty(region.getParent().getName())) {
            return BuildConfig.BASE_URL + "download.php?standard=yes&file="
                    + getCapitalName(region.getName())
                    + "_2.obf.zip";
        } else if (TextUtils.isEmpty(region.getParent().getParent().getName())) {
            return BuildConfig.BASE_URL + "download.php?standard=yes&file="
                    + getCapitalName(region.getName())
                    + "_"
                    + region.getParent().getName()
                    + "_2.obf.zip";
        } else return BuildConfig.BASE_URL + "download.php?standard=yes&file="
                + getCapitalName(region.getParent().getName())
                + "_"
                + region.getName()
                + "_"
                + region.getParent().getParent().getName()
                + "_2.obf.zip";
    }
}
/*
http://download.osmand.net/download.php?standard=yes&file=Berlin_germany_europe_2.obf.zip
http://download.osmand.net/download.php?standard=yes&file=Germany_berlin_europe_2.obf.zip TRUE*/
