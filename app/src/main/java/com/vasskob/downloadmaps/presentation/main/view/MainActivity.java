package com.vasskob.downloadmaps.presentation.main.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Continent;
import com.vasskob.downloadmaps.domain.model.Country;
import com.vasskob.downloadmaps.global.DownloadService;
import com.vasskob.downloadmaps.presentation.main.ActivityCallback;
import com.vasskob.downloadmaps.presentation.main.continent.view.ContinentFragment;
import com.vasskob.downloadmaps.presentation.main.country.view.CountryFragment;
import com.vasskob.downloadmaps.presentation.main.district.view.DistrictFragment;
import com.vasskob.downloadmaps.utils.StringUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements ActivityCallback, HasSupportFragmentInjector, MainView {

    public static final String MESSAGE_PROGRESS = "message_progress";
    private static final int PERMISSION_REQUEST_CODE = 1;
    private String fileUrl;

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
    public void onContinentClick(Continent continent) {
        Timber.d("onContinentClick: Region = " + continent.getName());
        if (continent.getCountryList() == null) return;
        initActionBar(StringUtils.getCapitalName(continent.getName()), true);
        replaceFragment(CountryFragment.newInstance(continent.getCountryList()), true);
    }

    @Override
    public void onCountryClick(Country country) {
        Timber.d("onContinentClick: Region = " + country.getName());
        if (country.getDistrictList() == null) return;
        initActionBar(StringUtils.getCapitalName(country.getName()), true);
        replaceFragment(DistrictFragment.newInstance(country.getDistrictList()), true);
    }

    @Override
    public void onRegionDownload(String url) {
        fileUrl = url;
        if (checkPermission()) {
            startDownload();
        } else {
            requestPermission();
        }
    }

    private void startDownload() {
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra(DownloadService.FILE_URL, fileUrl);
        startService(intent);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload();
                } else {
                    Toast.makeText(this, "Permission Denied, Please allow to proceed !", Toast.LENGTH_LONG).show();
                }
                break;
        }
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
