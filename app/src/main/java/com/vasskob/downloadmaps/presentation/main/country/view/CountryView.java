package com.vasskob.downloadmaps.presentation.main.country.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.vasskob.downloadmaps.domain.model.Region;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CountryView extends MvpView {
    void showLoadingDialog();
    void showLoadingProgress();
    void showRegions(List<Region> regionList);
}


