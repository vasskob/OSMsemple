package com.vasskob.downloadmaps.presentation.main.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Region;
import com.vasskob.downloadmaps.presentation.main.ActivityCallback;
import com.vasskob.downloadmaps.presentation.main.continent.view.ContinentFragment;
import com.vasskob.downloadmaps.presentation.main.country.view.CountryFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements ActivityCallback, HasSupportFragmentInjector, MainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(ContinentFragment.newInstance(), false);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,
                        fragment,
                        fragment.getClass().getSimpleName());
        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    @Override
    public void onRegionClick(Region region) {
        Timber.d("onRegionClick: Region = " + region.getRegions());
        if (region.getRegions().size() == 0 || region.getRegions().isEmpty()) return;
        initActionBar(region.getName(), true);
        replaceFragment(CountryFragment.newInstance(region.getRegions()), true);
    }

    @Override
    public void initActionBar(String title, boolean isEnabled) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(isEnabled);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
