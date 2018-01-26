package com.vasskob.downloadmaps.presentation.main.country.view.adapter;

import android.support.annotation.MainThread;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vasskob.downloadmaps.R;
import com.vasskob.downloadmaps.domain.model.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryViewHolder> {

    private final OnCountryClickListener mListener;
    private List<Country> mCountryList = new ArrayList<>();

    public CountryAdapter(OnCountryClickListener listener) {
        mListener = listener;
    }

    @MainThread
    public void updateCountries(List<Country> newCountryList) {
        final DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new CountryDiffCallback(mCountryList, newCountryList), false);
        mCountryList.clear();
        mCountryList.addAll(newCountryList);
        result.dispatchUpdatesTo(CountryAdapter.this);
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_district, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        holder.populate(mCountryList.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mCountryList != null ? mCountryList.size() : 0;
    }

    public interface OnCountryClickListener {
        void onCountryClick(int position);
        void onDownloadClick(int position);
    }
}
