package com.vasskob.downloadmaps.presentation.main.view.adapter;

import android.support.annotation.MainThread;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Region;

import java.util.ArrayList;
import java.util.List;

public class RegionAdapter extends RecyclerView.Adapter<RegionViewHolder> {

    private final OnRegionClickListener mListener;
    private List<Region> mRegionList = new ArrayList<>();

    public RegionAdapter(OnRegionClickListener listener) {
        mListener = listener;
    }

    @MainThread
    public void updateRegions(List<Region> newVisitorList) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new RegionDiffCallback(mRegionList, newVisitorList), false);
        mRegionList.clear();
        mRegionList.addAll(newVisitorList);
        result.dispatchUpdatesTo(RegionAdapter.this);
    }

    @Override
    public RegionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_region, parent, false);
        return new RegionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RegionViewHolder holder, int position) {
        holder.populate(mRegionList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mRegionList != null ? mRegionList.size() : 0;
    }

    public interface OnRegionClickListener {
        void onRegionClick(int position);
        void onDownloadClick(int position);
    }
}
