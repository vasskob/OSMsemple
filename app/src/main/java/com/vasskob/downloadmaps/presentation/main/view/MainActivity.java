package com.vasskob.downloadmaps.presentation.main.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Region;
import com.vasskob.downloadmaps.presentation.main.presenter.MainPresenter;
import com.vasskob.downloadmaps.presentation.main.view.adapter.RegionAdapter;
import com.vasskob.downloadmaps.utils.MemoryUtils;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import timber.log.Timber;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private static final String REGIONS_XML = "regions.xml";

    @BindView(R.id.pb_free_memory)
    ProgressBar pbFreeMemory;

    @BindView(R.id.tv_memory_size)
    TextView tvMemorySize;

    @BindView(R.id.rv_word_regions)
    RecyclerView rvRegions;

    @Inject
    Provider<MainPresenter> mPresenterProvider;

    @InjectPresenter(type = PresenterType.LOCAL)
    MainPresenter mPresenter;
    private RegionAdapter mAdapter;

    @ProvidePresenter(type = PresenterType.LOCAL)
    MainPresenter providePresenter() {
        return mPresenterProvider.get();
    }

    private RegionAdapter.OnRegionClickListener mListener = new RegionAdapter.OnRegionClickListener() {
        @Override
        public void onRegionClick(int position) {
            // TODO: 20.01.18 implement routing
            Timber.d("onRegionClick: " + position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initContinentRView();
        mPresenter.getFreeMemory();
        getContinents();
    }

    private void getContinents() {
        try {
            mPresenter.getContinents(getAssets().open(REGIONS_XML));
        } catch (IOException e) {
            Timber.e("onCreate: getContinents Error " + e.getMessage());
        }
    }

    private void initContinentRView() {
        rvRegions.setLayoutManager(new LinearLayoutManager(this));
        rvRegions.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RegionAdapter(mListener);
        rvRegions.setAdapter(mAdapter);
    }

    @Override
    public void showFreeMemory(long freeMemory, long totalMemory) {
        String memory = getString(R.string.label_free_memory, MemoryUtils.formatSize(this, freeMemory));
        CharSequence styledMemory = Html.fromHtml(memory);
        tvMemorySize.setText(styledMemory);
        pbFreeMemory.setMax(100);
        long divisor = totalMemory / 100;
        pbFreeMemory.setProgress(100 - (int) (freeMemory / divisor));
    }

    @Override
    public void showRegions(List<Region> regionList) {
        mAdapter.updateRegions(regionList);
    }
}
