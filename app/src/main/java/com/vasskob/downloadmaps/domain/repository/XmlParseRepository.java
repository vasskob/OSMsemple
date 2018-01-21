package com.vasskob.downloadmaps.domain.repository;

import com.vasskob.downloadmaps.domain.model.Region;

import java.io.InputStream;

import io.reactivex.Single;

public interface XmlParseRepository {
    Single<Region> parse(InputStream is);
}
