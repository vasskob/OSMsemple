package com.vasskob.downloadmaps.presentation.main.continent.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Continent;
import com.vasskob.downloadmaps.utils.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

class ContinentViewHolder extends RecyclerView.ViewHolder {

    private ContinentAdapter.OnContinentClickListener mListener;

    @BindView(R.id.tv_region_name)
    TextView tvRegionName;

    @BindView(R.id.pb_loading)
    ProgressBar pbLoading;

    @BindView(R.id.iv_globe)
    ImageView ivRegionIcon;

    ContinentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void populate(Continent continent, ContinentAdapter.OnContinentClickListener listener) {
        Timber.d("populate: Region = " + continent.getName() + " isMap = ");
        mListener = listener;
        tvRegionName.setText(StringUtils.getCapitalName(continent.getName()));

        ivRegionIcon.setImageResource(R.drawable.ic_world_globe_dark_gr);

       // if (continent.hasMap()) ivDownload.setVisibility(View.VISIBLE);
       // else ivDownload.setVisibility(View.INVISIBLE);
    }


    @OnClick(R.id.rl_item_container)
    void onItemClick() {
        mListener.onContinentClick(getAdapterPosition());
    }

    private boolean isClicked;

    private void initLoadingBar(boolean isVisible) {
        if (isVisible) pbLoading.setVisibility(View.VISIBLE);
        else pbLoading.setVisibility(View.INVISIBLE);
        pbLoading.setMax(100);
        pbLoading.setProgress(45);
    }
}