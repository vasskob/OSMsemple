package com.vasskob.downloadmaps.domain.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Region implements Comparable<Region> {

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
    private List<Region> regions=new ArrayList<>(); // Child regions collection

    // download info
    public volatile DownloadState downloadState = DownloadState.NOT_STARTED;
    public int downloadProgress;
    public int fileSize;

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
        if (type==null) { //1. by default map=yes, srtm=yes, hillshade=yes (in case 'type' not specified)
            if (map==null) map="yes";
            if (srtm==null)srtm="yes";
            if (hillshade==null) hillshade="yes";
            type="map";
        } else { //in case type specified it takes precedence and sets all flags = no
            map="no";
            srtm="no";
            hillshade="no";
            switch (type) {
                case "srtm": srtm="yes"; break;
                case "hillshade": hillshade="yes"; break;
                case "continent": break;
            }
        }
        if (wiki==null) wiki=map;
        if (roads==null) roads=map;
    }

    public void addRegion(Region region){
        regions.add(region);
        region.parent=this;
    }

    public Region getParent() {
        return parent;
    }

    public List<Region> getRegions() {
        return regions;
    }

    @Override
    public int compareTo(@NonNull Region region) {
        try{
            return name.compareTo(region.name);
        } catch (Exception e)  {
            return 0;
        }
    }

    public enum DownloadState {
        NOT_STARTED,
        QUEUED,
        DOWNLOADING,
        COMPLETE
    }
}
