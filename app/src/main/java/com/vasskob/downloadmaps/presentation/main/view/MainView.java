package com.vasskob.downloadmaps.presentation.main.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.vasskob.downloadmaps.domain.model.Region;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {
    void showFreeMemory(long freeMemory, long totalMemory);
    void showRegions(List<Region> regionList);
}
