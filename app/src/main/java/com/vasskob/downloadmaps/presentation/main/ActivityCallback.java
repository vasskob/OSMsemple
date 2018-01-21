package com.vasskob.downloadmaps.presentation.main;

import com.vasskob.downloadmaps.domain.model.Region;

public interface ActivityCallback {
    void onRegionClick(Region region);
    void initActionBar(String string, boolean isBackBtnEnabled);
}
