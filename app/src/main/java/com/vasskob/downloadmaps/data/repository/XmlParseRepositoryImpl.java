package com.vasskob.downloadmaps.data.repository;

import com.vasskob.downloadmaps.domain.model.Continent;
import com.vasskob.downloadmaps.domain.model.Country;
import com.vasskob.downloadmaps.domain.model.District;
import com.vasskob.downloadmaps.domain.model.Region;
import com.vasskob.downloadmaps.domain.repository.XmlParseRepository;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import timber.log.Timber;

public class XmlParseRepositoryImpl implements XmlParseRepository {

    private static final String ATR_NAME = "name";
    private static final String ATR_TYPE = "type";
    private static final String ATR_MAP = "map";
    private static final String ATR_SRTM = "srtm";
    private static final String ATR_HILLSHADE = "hillshade";

    private static final String KEY_REGIONS_LIST = "regions_list";
    private static final String KEY_REGION = "region";

    public Single<List<Continent>> parse(InputStream is) {
        XmlPullParserFactory factory;
        XmlPullParser parser;
        int currentLevel = 0;
        List<Continent> continentList = new ArrayList<>();
        Continent continent = new Continent();
        Country country = new Country();
        District district = new District();

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
                            switch (currentLevel) {
                                case 0:
                                    Continent newContinent = new Continent();
                                    continentList.add(newContinent);
                                    getAttributesForContinent(newContinent, parser);
                                    continent = newContinent;
                                    currentLevel++;
                                    break;
                                case 1:
                                    Country newCountry = new Country();
                                    continent.addCountry(newCountry);
                                    getAttributesForCountry(newCountry, parser);
                                    newCountry.setContinentParent(continent.getName());
                                    country = newCountry;
                                    currentLevel++;
                                    break;
                                case 2:
                                    District newDistrict = new District();
                                    country.addDistrict(newDistrict);
                                    getAttributesForDistrict(newDistrict, parser);
                                    newDistrict.setContinentParent(continent.getName());
                                    newDistrict.setCountryParent(country.getName());
                                    district = newDistrict;
                                    currentLevel++;
                                    break;
                                case 3:
                                    Region newRegion = new Region();
                                    district.addRegion(newRegion);
                                    getAttributesForRegion(newRegion, parser);
                                    newRegion.setContinentParent(continent.getName());
                                    newRegion.setCountryParent(country.getName());
                                    newRegion.setDistrictParent(country.getName());
                                    currentLevel++;
                                    break;
                                case 4:
                                    currentLevel++;
                                    break;
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(KEY_REGIONS_LIST)) {
                            return Single.just(continentList);
                        } else if (tagName.equalsIgnoreCase(KEY_REGION)) {
                            currentLevel--;
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

    private void getAttributesForContinent(Continent continent, XmlPullParser parser) {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeName = parser.getAttributeName(i);
            String attributeValue = parser.getAttributeValue(i);
            switch (attributeName) {
                case ATR_NAME:
                    continent.setName(attributeValue);
                    break;
                default:
                    Timber.d("getAttributes: Can`t parse attribute:" + attributeName + "=" + attributeValue);
            }
        }
    }

    private void getAttributesForCountry(Country country, XmlPullParser parser) {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeName = parser.getAttributeName(i);
            String attributeValue = parser.getAttributeValue(i);
            switch (attributeName) {
                case ATR_NAME:
                    country.setName(attributeValue);
                    break;
                case ATR_TYPE:
                    country.setType(attributeValue);
                    break;
                case ATR_MAP:
                    country.setMap(attributeValue);
                    break;
                case ATR_SRTM:
                    country.setSrtm(attributeValue);
                    break;
                case ATR_HILLSHADE:
                    country.setHillshade(attributeValue);
                    break;
                default:
                    Timber.d("getAttributes: Can`t parse attribute:" + attributeName + "=" + attributeValue);
            }
        }
        country.validate();
    }

    private void getAttributesForDistrict(District district, XmlPullParser parser) {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeName = parser.getAttributeName(i);
            String attributeValue = parser.getAttributeValue(i);
            switch (attributeName) {
                case ATR_NAME:
                    district.setName(attributeValue);
                    break;
                case ATR_TYPE:
                    district.setType(attributeValue);
                    break;
                case ATR_MAP:
                    district.setMap(attributeValue);
                    break;
                case ATR_SRTM:
                    district.setSrtm(attributeValue);
                    break;
                case ATR_HILLSHADE:
                    district.setHillshade(attributeValue);
                    break;
                default:
                    Timber.d("getAttributes: Can`t parse attribute:" + attributeName + "=" + attributeValue);
            }
        }
        district.validate();
    }

    private void getAttributesForRegion(Region region, XmlPullParser parser) {
        for (int i = 0; i < parser.getAttributeCount(); i++) {
            String attributeName = parser.getAttributeName(i);
            String attributeValue = parser.getAttributeValue(i);
            switch (attributeName) {
                case ATR_NAME:
                    region.setName(attributeValue);
                    break;
                case ATR_TYPE:
                    region.setType(attributeValue);
                    break;
                case ATR_MAP:
                    region.setMap(attributeValue);
                    break;
                case ATR_SRTM:
                    region.setSrtm(attributeValue);
                    break;
                case ATR_HILLSHADE:
                    region.setHillshade(attributeValue);
                    break;
                default:
                    Timber.d("getAttributes: Can`t parse attribute:" + attributeName + "=" + attributeValue);
            }
        }
        region.validate();
    }
}
