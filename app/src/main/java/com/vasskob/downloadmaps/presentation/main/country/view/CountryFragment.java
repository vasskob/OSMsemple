package com.vasskob.downloadmaps.presentation.main.country.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.PresenterType;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Region;
import com.vasskob.downloadmaps.presentation.main.ActivityCallback;
import com.vasskob.downloadmaps.presentation.main.country.presenter.CountryPresenter;
import com.vasskob.downloadmaps.presentation.main.view.adapter.RegionAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

public class CountryFragment extends MvpAppCompatFragment implements CountryView {

    public static final String REGION_KEY = "REGION_KEY";

    private Unbinder mUnBinder;
    private RegionAdapter mAdapter;
    private List<Region> mRegionList;
    private ActivityCallback mCallback;

    @BindView(R.id.fl_label)
    FrameLayout flRegionLabel;

    @BindView(R.id.rl_progress_container)
    RelativeLayout rlProgressContainer;

    @BindView(R.id.pb_free_memory)
    ProgressBar pbFreeMemory;

    @BindView(R.id.tv_memory_size)
    TextView tvMemorySize;

    @BindView(R.id.rv_word_regions)
    RecyclerView rvRegions;

    @Inject
    Provider<CountryPresenter> mPresenterProvider;

    @InjectPresenter(type = PresenterType.LOCAL)
    CountryPresenter mPresenter;

    @ProvidePresenter(type = PresenterType.LOCAL)
    CountryPresenter providePresenter() {
        return mPresenterProvider.get();
    }

    private RegionAdapter.OnRegionClickListener mListener = new RegionAdapter.OnRegionClickListener() {
        @Override
        public void onRegionClick(int position) {
            Timber.d("onRegionClick: " + mRegionList.get(position));
            mCallback.onRegionClick(mRegionList.get(position));
        }
    };

    public static Fragment newInstance(List<Region> regions) {
        Timber.d("newInstance: Regions!!! = " + regions);
        Bundle args = new Bundle();
        args.putParcelableArrayList(REGION_KEY, (ArrayList<? extends Parcelable>) regions);
        CountryFragment fragment = new CountryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        try {
            mCallback = (ActivityCallback) context;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegionList = getArguments().getParcelableArrayList(REGION_KEY);
        Timber.d("onCreate: Regions!!! = " + mRegionList);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnBinder = ButterKnife.bind(this, view);
        Timber.d("onViewCreated: ");
        initContinentRView();
        initUI();
    }

    private void initUI() {
        flRegionLabel.setVisibility(View.GONE);
        rlProgressContainer.setVisibility(View.GONE);
    }

    private void initContinentRView() {
        rvRegions.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvRegions.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new RegionAdapter(mListener);
        rvRegions.setAdapter(mAdapter);
        showRegions(mRegionList);
    }

    @Override
    public void showLoadingDialog() {
        // TODO: 21.01.18 implement
    }

    @Override
    public void showLoadingProgress() {
        rlProgressContainer.setVisibility(View.VISIBLE);
        // TODO: 21.01.18 implement
    }

    @Override
    public void showRegions(List<Region> regionList) {
        pbFreeMemory.setVisibility(View.GONE);
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
