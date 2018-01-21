package com.vasskob.downloadmaps.presentation.di;

import com.vasskob.downloadmaps.presentation.main.continent.di.ContinentModule;
import com.vasskob.downloadmaps.presentation.main.continent.di.ContinentScope;
import com.vasskob.downloadmaps.presentation.main.continent.view.ContinentFragment;
import com.vasskob.downloadmaps.presentation.main.country.di.CountryModule;
import com.vasskob.downloadmaps.presentation.main.country.di.CountryScope;
import com.vasskob.downloadmaps.presentation.main.country.view.CountryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {

    @ContinentScope
    @ContributesAndroidInjector(modules = ContinentModule.class)
    abstract ContinentFragment provideContinentFragmentFactory();

    @CountryScope
    @ContributesAndroidInjector(modules = CountryModule.class)
    abstract CountryFragment provideCountryFragmentFactory();

}

