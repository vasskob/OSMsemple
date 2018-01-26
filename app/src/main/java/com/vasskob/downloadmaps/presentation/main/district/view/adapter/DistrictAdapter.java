package com.vasskob.downloadmaps.presentation.main.district.view.adapter;

import android.support.annotation.MainThread;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.District;

import java.util.ArrayList;
import java.util.List;

public class DistrictAdapter extends RecyclerView.Adapter<DistrictViewHolder> {

    private final OnDistrictClickListener mListener;
    private List<District> mDistrictList = new ArrayList<>();

    public DistrictAdapter(OnDistrictClickListener listener) {
        mListener = listener;
    }

    @MainThread
    public void updateDistricts(List<District> newDistrictList) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new DistrictDiffCallback(mDistrictList, newDistrictList), false);
        mDistrictList.clear();
        mDistrictList.addAll(newDistrictList);
        result.dispatchUpdatesTo(DistrictAdapter.this);
    }

    @Override
    public DistrictViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_district, parent, false);
        return new DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DistrictViewHolder holder, int position) {
        holder.populate(mDistrictList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mDistrictList != null ? mDistrictList.size() : 0;
    }

    public interface OnDistrictClickListener {
        void onDistrictClick(int position);
        void onDownloadClick(int position);
    }
}
