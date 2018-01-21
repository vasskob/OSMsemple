package com.vasskob.downloadmaps.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class Region implements Comparable<Region>, Parcelable {

    private String name;
    private String type;
    private String downloadSuffix;
    private String innerDownloadSuffix;
    private String downloadPrefix;
    private String innerDownloadPrefix;
    private String map;
    private String srtm;
    private String hillshade;
    private String wiki;
    private String roads;
    private String translate;
    private String joinMapFiles;
    private String boundary;

    // tree info
    private Region parent;
    private List<Region> regions = new ArrayList<>(); // Child regions collection

    // download info
    private volatile DownloadState downloadState = DownloadState.NOT_STARTED;
    public int downloadProgress;
    public int fileSize;

    public DownloadState getDownloadState() {
        return downloadState;
    }

    public void setDownloadState(DownloadState downloadState) {
        this.downloadState = downloadState;
    }

    public Region() {
    }

    public Region(String name) {
        this.name = name;
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

    public String getDownloadSuffix() {
        return downloadSuffix;
    }

    public void setDownloadSuffix(String downloadSuffix) {
        this.downloadSuffix = downloadSuffix;
    }

    public String getInnerDownloadSuffix() {
        return innerDownloadSuffix;
    }

    public void setInnerDownloadSuffix(String innerDownloadSuffix) {
        this.innerDownloadSuffix = innerDownloadSuffix;
    }

    public String getDownloadPrefix() {
        return downloadPrefix;
    }

    public void setDownloadPrefix(String downloadPrefix) {
        this.downloadPrefix = downloadPrefix;
    }

    public String getInnerDownloadPrefix() {
        return innerDownloadPrefix;
    }

    public void setInnerDownloadPrefix(String innerDownloadPrefix) {
        this.innerDownloadPrefix = innerDownloadPrefix;
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

    public String getWiki() {
        return wiki;
    }

    public void setWiki(String wiki) {
        this.wiki = wiki;
    }

    public String getRoads() {
        return roads;
    }

    public void setRoads(String roads) {
        this.roads = roads;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getJoinMapFiles() {
        return joinMapFiles;
    }

    public void setJoinMapFiles(String joinMapFiles) {
        this.joinMapFiles = joinMapFiles;
    }

    public String getBoundary() {
        return boundary;
    }

    public void setBoundary(String boundary) {
        this.boundary = boundary;
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
        if (wiki == null) wiki = map;
        if (roads == null) roads = map;
    }

    public void addRegion(Region region) {
        regions.add(region);
        region.parent = this;
    }

    public Region getParent() {
        return parent;
    }

    public List<Region> getRegions() {
        return regions;
    }

    @Override
    public int compareTo(@NonNull Region region) {
        try {
            return name.compareTo(region.name);
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean hasMap() {
        return TextUtils.equals(map, "yes");
    }

    public boolean isContinent() {
        return parent.getParent() == null;
    }

    public enum DownloadState {
        NOT_STARTED,
        QUEUED,
        DOWNLOADING,
        COMPLETE
    }

    @Override
    public String toString() {
        return "Region{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", map='" + map + '\'' +
                ", translate='" + translate + '\'' +
                ", parent=" + parent +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.type);
        dest.writeString(this.downloadSuffix);
        dest.writeString(this.innerDownloadSuffix);
        dest.writeString(this.downloadPrefix);
        dest.writeString(this.innerDownloadPrefix);
        dest.writeString(this.map);
        dest.writeString(this.srtm);
        dest.writeString(this.hillshade);
        dest.writeString(this.wiki);
        dest.writeString(this.roads);
        dest.writeString(this.translate);
        dest.writeString(this.joinMapFiles);
        dest.writeString(this.boundary);
        dest.writeParcelable(this.parent, flags);
        dest.writeList(this.regions);
        dest.writeInt(this.downloadState == null ? -1 : this.downloadState.ordinal());
        dest.writeInt(this.downloadProgress);
        dest.writeInt(this.fileSize);
    }

    protected Region(Parcel in) {
        this.name = in.readString();
        this.type = in.readString();
        this.downloadSuffix = in.readString();
        this.innerDownloadSuffix = in.readString();
        this.downloadPrefix = in.readString();
        this.innerDownloadPrefix = in.readString();
        this.map = in.readString();
        this.srtm = in.readString();
        this.hillshade = in.readString();
        this.wiki = in.readString();
        this.roads = in.readString();
        this.translate = in.readString();
        this.joinMapFiles = in.readString();
        this.boundary = in.readString();
        this.parent = in.readParcelable(Region.class.getClassLoader());
        this.regions = new ArrayList<>();
        in.readList(this.regions, Region.class.getClassLoader());
        int tmpDownloadState = in.readInt();
        this.downloadState = tmpDownloadState == -1 ? null : DownloadState.values()[tmpDownloadState];
        this.downloadProgress = in.readInt();
        this.fileSize = in.readInt();
    }

    public static final Parcelable.Creator<Region> CREATOR = new Parcelable.Creator<Region>() {
        @Override
        public Region createFromParcel(Parcel source) {
            return new Region(source);
        }

        @Override
        public Region[] newArray(int size) {
            return new Region[size];
        }
    };
}
