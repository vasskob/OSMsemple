package com.vasskob.downloadmaps.presentation.main.continent.view.adapter;

import android.support.annotation.MainThread;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Continent;

import java.util.ArrayList;
import java.util.List;

public class ContinentAdapter extends RecyclerView.Adapter<ContinentViewHolder> {

    private final OnContinentClickListener mListener;
    private List<Continent> mContinentList = new ArrayList<>();

    public ContinentAdapter(OnContinentClickListener listener) {
        mListener = listener;
    }

    @MainThread
    public void updateContinents(List<Continent> continentList) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new ContinentDiffCallback(mContinentList, continentList), false);
        mContinentList.clear();
        mContinentList.addAll(continentList);
        result.dispatchUpdatesTo(ContinentAdapter.this);
    }

    @Override
    public ContinentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_district, parent, false);
        return new ContinentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContinentViewHolder holder, int position) {
        holder.populate(mContinentList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mContinentList != null ? mContinentList.size() : 0;
    }

    public interface OnContinentClickListener {
        void onContinentClick(int position);
    }
}

