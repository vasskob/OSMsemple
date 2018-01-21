package com.vasskob.downloadmaps.data.repository;

import com.vasskob.downloadmaps.domain.model.Region;
import com.vasskob.downloadmaps.domain.repository.XmlParseRepository;
import com.vasskob.downloadmaps.utils.StringUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

import io.reactivex.Single;
import timber.log.Timber;

public class XmlParseRepositoryImpl implements XmlParseRepository {

    private static final String ATR_NAME = "name";
    private static final String ATR_TYPE = "type";
    private static final String ATR_INNER_DOWNLOAD_SUFFIX = "inner_download_suffix";
    private static final String ATR_INNER_DOWNLOAD_PREFIX = "inner_download_prefix";
    private static final String ATR_DOWNLOAD_SUFFIX = "download_suffix";
    private static final String ATR_DOWNLOAD_PREFIX = "download_prefix";
    private static final String ATR_MAP = "map";
    private static final String ATR_TRANSLATE = "translate";
    private static final String ATR_SRTM = "srtm";
    private static final String ATR_HILLSHADE = "hillshade";
    private static final String ATR_WIKI = "wiki";
    private static final String ATR_ROADS = "roads";
    private static final String ATR_BOUNDARY = "boundary";
    private static final String ATR_JOIN_MAP_FILES = "join_map_files";
    private static final String KEY_REGIONS_LIST = "regions_list";
    private static final String KEY_REGION = "region";

    public Single<Region> parse(InputStream is) {
        XmlPullParserFactory factory;
        XmlPullParser parser;
        int currentLevel = 0;
        Region root = new Region();
        Region parent = root;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();
            parser.setInput(is, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                String tagName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase(KEY_REGIONS_LIST)) {
                            currentLevel = 0;
                        } else if (tagName.equalsIgnoreCase(KEY_REGION)) {

                            Region newRegion = new Region();
                            parent.addRegion(newRegion);
                            getAttributes(newRegion, parser);
                            currentLevel = currentLevel + 1;
                            parent = newRegion;
                        }
                        break;


                    case XmlPullParser.TEXT:
                        //  GETTING TEXT ENCLOSED BY ANY TAG
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(KEY_REGIONS_LIST)) {
                            Collections.sort(root.getRegions());
                            return Single.just(root);
                        } else if (tagName.equalsIgnoreCase(KEY_REGION)) {
                            currentLevel = currentLevel - 1;
                            Collections.sort(parent.getRegions());
                            parent = parent.getParent();
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getAttributes(Region region, XmlPullParser parser) {
        int maxI = parser.getAttributeCount();
        for (int i = 0; i < maxI; i++) {
            String attributeName = parser.getAttributeName(i);
            String attributeValue = parser.getAttributeValue(i);
            switch (attributeName) {
                case ATR_NAME:
                    region.setName(StringUtils.getCapitalName(attributeValue));
                    break;
                case ATR_TYPE:
                    region.setType(attributeValue);
                    break;
                case ATR_INNER_DOWNLOAD_SUFFIX:
                    region.setInnerDownloadSuffix(attributeValue);
                    break;
                case ATR_INNER_DOWNLOAD_PREFIX:
                    region.setInnerDownloadPrefix(attributeValue);
                    break;
                case ATR_DOWNLOAD_SUFFIX:
                    region.setDownloadSuffix(attributeValue);
                    break;
                case ATR_DOWNLOAD_PREFIX:
                    region.setDownloadPrefix(attributeValue);
                    break;
                case ATR_MAP:
                    region.setMap(attributeValue);
                    break;
                case ATR_TRANSLATE:
                    region.setTranslate(attributeValue);
                    break;
                case ATR_SRTM:
                    region.setSrtm(attributeValue);
                    break;
                case ATR_HILLSHADE:
                    region.setHillshade(attributeValue);
                    break;
                case ATR_WIKI:
                    region.setWiki(attributeValue);
                    break;
                case ATR_ROADS:
                    region.setRoads(attributeValue);
                    break;
                case ATR_BOUNDARY:
                    region.setBoundary(attributeValue);
                    break;
                case ATR_JOIN_MAP_FILES:
                    region.setJoinMapFiles(attributeValue);
                    break;
                default:
                    Timber.d("getAttributes: Can`t parse attribute:" + attributeName + "=" + attributeValue);
            }
        }
        region.validate();
    }
}
