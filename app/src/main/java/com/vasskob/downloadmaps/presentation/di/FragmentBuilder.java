package com.vasskob.downloadmaps.presentation.di;

import com.vasskob.downloadmaps.presentation.main.continent.di.ContinentModule;
import com.vasskob.downloadmaps.presentation.main.continent.di.ContinentScope;
import com.vasskob.downloadmaps.presentation.main.continent.view.ContinentFragment;
import com.vasskob.downloadmaps.presentation.main.country.di.CountryModule;
import com.vasskob.downloadmaps.presentation.main.country.di.CountryScope;
import com.vasskob.downloadmaps.presentation.main.country.view.CountryFragment;
import com.vasskob.downloadmaps.presentation.main.district.di.DistrictModule;
import com.vasskob.downloadmaps.presentation.main.district.di.DistrictScope;
import com.vasskob.downloadmaps.presentation.main.district.view.DistrictFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {

    @ContinentScope
    @ContributesAndroidInjector(modules = ContinentModule.class)
    abstract ContinentFragment provideContinentFragment();

    @CountryScope
    @ContributesAndroidInjector(modules = CountryModule.class)
    abstract CountryFragment provideCountryFragment();

    @DistrictScope
    @ContributesAndroidInjector(modules = DistrictModule.class)
    abstract DistrictFragment provideDistrictFragment();

}

