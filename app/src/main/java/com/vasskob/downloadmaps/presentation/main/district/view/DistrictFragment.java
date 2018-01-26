package com.vasskob.downloadmaps.presentation.main.district.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
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
import com.vasskob.downloadmaps.domain.model.District;
import com.vasskob.downloadmaps.domain.model.Download;
import com.vasskob.downloadmaps.presentation.main.ActivityCallback;
import com.vasskob.downloadmaps.presentation.main.district.presenter.DistrictPresenter;
import com.vasskob.downloadmaps.presentation.main.district.view.adapter.DistrictAdapter;
import com.vasskob.downloadmaps.utils.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import timber.log.Timber;

import static com.vasskob.downloadmaps.presentation.main.view.MainActivity.MESSAGE_PROGRESS;

public class DistrictFragment extends MvpAppCompatFragment implements DistrictView {

    public static final String REGION_KEY = "REGION_KEY";

    private Unbinder mUnBinder;
    private DistrictAdapter mAdapter;
    private List<District> mDistrictList;
    private ActivityCallback mCallback;
    private LocalBroadcastManager bManager;

    @BindView(R.id.fl_label)
    FrameLayout flRegionLabel;

    @BindView(R.id.rl_progress_container)
    RelativeLayout rlProgressContainer;

    @BindView(R.id.pb_free_memory)
    ProgressBar pbFreeMemory;

    @BindView(R.id.tv_memory_label)
    TextView tvLoadingProgress;

    @BindView(R.id.tv_memory_size)
    TextView tvMemorySize;

    @BindView(R.id.rv_word_regions)
    RecyclerView rvRegions;

    @Inject
    Provider<DistrictPresenter> mPresenterProvider;

    @InjectPresenter(type = PresenterType.LOCAL)
    DistrictPresenter mPresenter;

    @ProvidePresenter(type = PresenterType.LOCAL)
    DistrictPresenter providePresenter() {
        return mPresenterProvider.get();
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MESSAGE_PROGRESS)) {
                Download download = intent.getParcelableExtra("download");
                pbFreeMemory.setProgress(download.getProgress());
                if (download.getProgress() == 100) {
                    tvLoadingProgress.setText("File Download Complete");
                } else {
                    tvLoadingProgress.setText(String.format("Downloaded (%d/%d) MB", download.getCurrentFileSize(), download.getTotalFileSize()));
                }
            }
        }
    };

    private DistrictAdapter.OnDistrictClickListener mListener = new DistrictAdapter.OnDistrictClickListener() {

        private District district;
        private String downloadURL;

        @Override
        public void onDistrictClick(int position) {
            Timber.d("onContinentClick: " + mDistrictList.get(position));
            //      mCallback.onDis(mDistrictList.get(position));
        }

        @Override
        public void onDownloadClick(int position) {
            district = mDistrictList.get(position);
            downloadURL = StringUtils.getDownloadURL(district.getCountryParent()+ "_" + district.getName()+"_"+district.getContinentParent());
            Timber.d("onDownloadClick: URL downloadFile = "
                    + downloadURL);
            rlProgressContainer.setVisibility(View.VISIBLE);
            pbFreeMemory.setVisibility(View.VISIBLE);
             //   mPresenter.loadFile(downloadURL, "MyFile_");
            mCallback.onRegionDownload(downloadURL);
        }
    };

    public static Fragment newInstance(List<District> districts) {
        Timber.d("newInstance: Regions!!! = " + districts);
        Bundle args = new Bundle();
        args.putParcelableArrayList(REGION_KEY, (ArrayList<? extends Parcelable>) districts);
        DistrictFragment fragment = new DistrictFragment();
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
        mDistrictList = getArguments().getParcelableArrayList(REGION_KEY);
        Timber.d("onCreate: Regions!!! = " + mDistrictList);
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
        mAdapter = new DistrictAdapter(mListener);
        rvRegions.setAdapter(mAdapter);
        showDistricts(mDistrictList);
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("onResume: ");
        registerReceiver();
    }

    private void registerReceiver() {
        bManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MESSAGE_PROGRESS);
        bManager.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.d("onPause: ");
        bManager.unregisterReceiver(broadcastReceiver);
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
    public void showDistricts(List<District> districts) {
        pbFreeMemory.setVisibility(View.GONE);
        Collections.sort(districts);
        mAdapter.updateDistricts(districts);
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
