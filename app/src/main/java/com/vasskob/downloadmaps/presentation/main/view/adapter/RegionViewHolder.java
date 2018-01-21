package com.vasskob.downloadmaps.presentation.main.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Region;
import com.vasskob.downloadmaps.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

class RegionViewHolder extends RecyclerView.ViewHolder {

    private RegionAdapter.OnRegionClickListener mListener;

    @BindView(R.id.tv_region_name)
    TextView tvRegionName;

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    @BindView(R.id.iv_download)
    ImageView ivDownload;

    @BindView(R.id.iv_globe)
    ImageView ivRegionIcon;

    RegionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void populate(Region region, RegionAdapter.OnRegionClickListener listener) {
        Timber.d("populate: Region = " + region.getName() + " isMap = " + region.getMap());
        mListener = listener;
        tvRegionName.setText(StringUtils.getCapitalName(region.getName()));

        if (region.isContinent()) ivRegionIcon.setImageResource(R.drawable.ic_world_globe_dark_gr);
        else ivRegionIcon.setImageResource(R.drawable.ic_map_gr);

        if (region.hasMap()) ivDownload.setVisibility(View.VISIBLE);
        else ivDownload.setVisibility(View.INVISIBLE);
    }


    @OnClick(R.id.rl_item_container)
    void onItemClick() {
        mListener.onRegionClick(getAdapterPosition());
    }

    private boolean isClicked;

    @OnClick(R.id.iv_download)
    void onDownloadClick() {
        mListener.onDownloadClick(getAdapterPosition());
        if (isClicked) {
            initLoadingBar(true);
            isClicked = false;
        } else {
            initLoadingBar(false);
            isClicked = false;
        }
    }

    private void initLoadingBar(boolean isVisible) {
        if (isVisible) pbLoading.setVisibility(View.VISIBLE);
        else pbLoading.setVisibility(View.INVISIBLE);
        pbLoading.setMax(100);
        pbLoading.setProgress(45);
        ivDownload.setSelected(isVisible);
    }
}