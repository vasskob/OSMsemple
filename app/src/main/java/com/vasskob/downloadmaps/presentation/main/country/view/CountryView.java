package com.vasskob.downloadmaps.presentation.main.country.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.vasskob.downloadmaps.domain.model.Country;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface CountryView extends MvpView {
    void showLoadingDialog();
    void showLoadingProgress();
    void showCountries(List<Country> countries);
}


