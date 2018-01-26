package com.vasskob.downloadmaps.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class District implements Comparable<District>, Parcelable {

    private String name;
    private String type;
    private String map;
    private String srtm;
    private String hillshade;
    private String countryParent;
    private String continentParent;
    private List<Region> regionList = new ArrayList<>();

    public List<Region> getRegionList() {
        return regionList;
    }

    public void addRegion(Region region) {
        regionList.add(region);
    }

    private volatile DownloadState downloadState = DownloadState.NOT_STARTED;

    public DownloadState getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(DownloadState downloadState) {
        this.downloadState = downloadState;
    }

    public District() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getSrtm() {
        return srtm;
    }

    public void setSrtm(String srtm) {
        this.srtm = srtm;
    }

    public String getHillshade() {
        return hillshade;
    }

    public void setHillshade(String hillshade) {
        this.hillshade = hillshade;
    }

    public String getCountryParent() {
        return countryParent;
    }

    public void setCountryParent(String countryParent) {
        this.countryParent = countryParent;
    }

    public String getContinentParent() {
        return continentParent;
    }

    public void setContinentParent(String continentParent) {
        this.continentParent = continentParent;
    }

    public void validate() {
        if (type == null) { //1. by default map=yes, srtm=yes, hillshade=yes (in case 'type' not specified)
            if (map == null) map = "yes";
            if (srtm == null) srtm = "yes";
            if (hillshade == null) hillshade = "yes";
            type = "map";
        } else { //in case type specified it takes precedence and sets all flags = no
            map = "no";
            srtm = "no";
            hillshade = "no";
            switch (type) {
                case "srtm":
                    srtm = "yes";
                    break;
                case "hillshade":
                    hillshade = "yes";
                    break;
                case "continent":
                    break;
            }
        }
    }

    @Override
    public int compareTo(@NonNull District country) {
        try {
            return name.compareTo(country.name);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.map);
        dest.writeString(this.srtm);
        dest.writeString(this.hillshade);
        dest.writeString(this.countryParent);
        dest.writeString(this.continentParent);
        dest.writeInt(this.downloadState == null ? -1 : this.downloadState.ordinal());
    }

    protected District(Parcel in) {
        this.name = in.readString();
        this.type = in.readString();
        this.map = in.readString();
        this.srtm = in.readString();
        this.hillshade = in.readString();
        this.countryParent = in.readString();
        this.continentParent = in.readString();
        int tmpDownloadState = in.readInt();
        this.downloadState = tmpDownloadState == -1 ? null : DownloadState.values()[tmpDownloadState];
    }

    public static final Parcelable.Creator<District> CREATOR = new Parcelable.Creator<District>() {
        @Override
        public District createFromParcel(Parcel source) {
            return new District(source);
        }

        @Override
        public District[] newArray(int size) {
            return new District[size];
        }
    };

    public boolean hasMap() {
        return TextUtils.equals(map, "yes");
    }

    @Override
    public String toString() {
        return "District{" +
                "name='" + name + '\'' +
//                ", type='" + type + '\'' +
//                ", map='" + map + '\'' +
//                ", srtm='" + srtm + '\'' +
//                ", hillshade='" + hillshade + '\'' +
                ", regionList='" + regionList + '\'' +
                ", countryParent='" + countryParent + '\'' +
                ", continentParent='" + continentParent + '\'' +
//                ", downloadState=" + downloadState +
                '}';
    }
}
