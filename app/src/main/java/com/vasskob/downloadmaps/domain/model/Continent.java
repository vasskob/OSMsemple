package com.vasskob.downloadmaps.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Continent implements Comparable<Continent>, Parcelable {

    private String name;
    private List<Country> countryList = new ArrayList<>();

    public Continent() {
    }

    public void addCountry(Country country) {
        countryList.add(country);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "name='" + name + '\'' +
                ", countryList=" + countryList +
                '}';
    }

    @Override
    public int compareTo(@NonNull Continent continent) {
        try {
            return name.compareTo(continent.name);
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
        dest.writeTypedList(this.countryList);
    }

    protected Continent(Parcel in) {
        this.name = in.readString();
        this.countryList = in.createTypedArrayList(Country.CREATOR);
    }

    public static final Parcelable.Creator<Continent> CREATOR = new Parcelable.Creator<Continent>() {
        @Override
        public Continent createFromParcel(Parcel source) {
            return new Continent(source);
        }

        @Override
        public Continent[] newArray(int size) {
            return new Continent[size];
        }
    };
}
