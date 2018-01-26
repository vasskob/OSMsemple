package com.vasskob.downloadmaps.domain.repository;

import com.vasskob.downloadmaps.domain.model.Continent;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Single;

public interface XmlParseRepository {
    Single<List<Continent>> parse(InputStream is);
}
