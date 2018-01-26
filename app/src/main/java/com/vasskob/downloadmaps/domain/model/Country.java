package com.vasskob.downloadmaps.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Country implements Comparable<Country>, Parcelable {

    private String name;
    private String type;
    private String map;
    private String srtm;
    private String hillshade;

    private List<District> districtList = new ArrayList<>();
    private String continentParent;

    private volatile DownloadState downloadState = DownloadState.NOT_STARTED;

    public DownloadState getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(DownloadState downloadState) {
        this.downloadState = downloadState;
    }

    public void addDistrict(District district) {
        districtList.add(district);
    }

    public Country() {
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

    public List<District> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<District> districtList) {
        this.districtList = districtList;
    }

    public String getContinentParent() {
        return continentParent;
    }

    public void setContinentParent(String continentParent) {
        this.continentParent = continentParent;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
//                ", type='" + type + '\'' +
//                ", map='" + map + '\'' +
//                ", srtm='" + srtm + '\'' +
//                ", hillshade='" + hillshade + '\'' +
                ", districtList=" + districtList +
                ", continentParent='" + continentParent + '\'' +
//                ", downloadState=" + downloadState +
                '}';
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
    public int compareTo(@NonNull Country country) {
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
        dest.writeList(this.districtList);
        dest.writeString(this.continentParent);
        dest.writeInt(this.downloadState == null ? -1 : this.downloadState.ordinal());
    }

    protected Country(Parcel in) {
        this.name = in.readString();
        this.type = in.readString();
        this.map = in.readString();
        this.srtm = in.readString();
        this.hillshade = in.readString();
        this.districtList = new ArrayList<District>();
        in.readList(this.districtList, District.class.getClassLoader());
        this.continentParent = in.readString();
        int tmpDownloadState = in.readInt();
        this.downloadState = tmpDownloadState == -1 ? null : DownloadState.values()[tmpDownloadState];
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    public boolean hasMap() {
        return TextUtils.equals(map, "yes");
    }
}
