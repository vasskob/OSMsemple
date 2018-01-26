package com.vasskob.downloadmaps.presentation.main.district.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.vasskob.downloadmaps.domain.model.District;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface DistrictView extends MvpView {
    void showLoadingDialog();
    void showLoadingProgress();
    void showDistricts(List<District> countries);
}


