package com.vasskob.downloadmaps.presentation.main;

import com.vasskob.downloadmaps.domain.model.Continent;
import com.vasskob.downloadmaps.domain.model.Country;

public interface ActivityCallback {
    void onContinentClick(Continent continent);
    void onCountryClick(Country country);
    void onRegionDownload(String fileUrl);

    void initActionBar(String string, boolean isBackBtnEnabled);
}
