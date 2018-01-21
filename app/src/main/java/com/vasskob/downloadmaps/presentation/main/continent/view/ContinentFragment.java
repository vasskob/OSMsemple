package com.vasskob.downloadmaps.presentation.main.continent.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Region;
import com.vasskob.downloadmaps.presentation.main.OnRegionClickListener;
import com.vasskob.downloadmaps.presentation.main.continent.presenter.ContinentPresenter;
import com.vasskob.downloadmaps.presentation.main.view.adapter.RegionAdapter;
import com.vasskob.downloadmaps.utils.MemoryUtils;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class ContinentFragment extends MvpAppCompatFragment implements ContinentView {

    private static final String REGIONS_XML = "regions.xml";

    private Unbinder mUnBinder;
    private RegionAdapter mAdapter;
    private List<Region> mRegionList;
    private OnRegionClickListener mCallback;

    @BindView(R.id.pb_free_memory)
    ProgressBar pbFreeMemory;

    @BindView(R.id.tv_memory_size)
    TextView tvMemorySize;

    @BindView(R.id.rv_word_regions)
    RecyclerView rvRegions;

    @Inject
    Provider<ContinentPresenter> mPresenterProvider;

    @InjectPresenter(type = PresenterType.LOCAL)
    ContinentPresenter mPresenter;

    @ProvidePresenter(type = PresenterType.LOCAL)
    ContinentPresenter providePresenter() {
        return mPresenterProvider.get();
    }

    private RegionAdapter.OnRegionClickListener mListener = new RegionAdapter.OnRegionClickListener() {
        @Override
        public void onRegionClick(int position) {
            Timber.d("onRegionClick: " + mRegionList.get(position));
            mCallback.onContinentClick(mRegionList.get(position));
        }
    };

    public static Fragment newInstance() {
        Bundle args = new Bundle();
        ContinentFragment fragment = new ContinentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        try {
            mCallback = (OnRegionClickListener) context;
        } catch (ClassCastException classCastE) {
            throw new ClassCastException(context.toString() + " must implement OnContinentClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_region, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        Timber.d("onViewCreated: ");
        initContinentRView();
        mPresenter.getFreeMemory();
        getContinents();
        initActionBar();
    }

    private void initActionBar() {
        mCallback.initActionBar(getString(R.string.app_name),false);
    }

    private void getContinents() {
        try {
            mPresenter.getContinents(getActivity().getAssets().open(REGIONS_XML));
        } catch (IOException e) {
            Timber.e("onCreate: getContinents Error " + e.getMessage());
        }
    }

    private void initContinentRView() {
        rvRegions.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRegions.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RegionAdapter(mListener);
        rvRegions.setAdapter(mAdapter);
    }

    @Override
    public void showFreeMemory(long freeMemory, long totalMemory) {
        String memory = getString(R.string.label_free_memory, MemoryUtils.formatSize(getActivity(), freeMemory));
        CharSequence styledMemory = Html.fromHtml(memory);
        tvMemorySize.setText(styledMemory);
        pbFreeMemory.setMax(100);
        long divisor = totalMemory / 100;
        pbFreeMemory.setProgress(100 - (int) (freeMemory / divisor));
    }

    @Override
    public void showRegions(List<Region> regionList) {
        mRegionList = regionList;
        mAdapter.updateRegions(regionList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mListener = null;
    }

}
