package com.vasskob.downloadmaps.presentation.main.continent.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.vasskob.downloadmaps.domain.model.Region;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ContinentView extends MvpView {
    void showFreeMemory(long freeMemory, long totalMemory);
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showRegions(List<Region> regionList);
}


