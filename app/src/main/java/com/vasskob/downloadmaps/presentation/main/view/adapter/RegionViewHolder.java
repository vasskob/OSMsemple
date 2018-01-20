package com.vasskob.downloadmaps.presentation.main.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Region;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

class RegionViewHolder extends RecyclerView.ViewHolder {

    private RegionAdapter.OnRegionClickListener mListener;

    @BindView(R.id.tv_region_name)
    TextView tvRegionName;

    RegionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void populate(Region region, RegionAdapter.OnRegionClickListener listener) {
        Timber.d("populate: Region = " + region);
        mListener = listener;
        tvRegionName.setText(region.getName());
    }

    @OnClick(R.id.rl_item_container)
    void onItemClick() {
        mListener.onRegionClick(getAdapterPosition());
    }
}
