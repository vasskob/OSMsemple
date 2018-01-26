package com.vasskob.downloadmaps.presentation.main.country.view.adapter;

import android.support.v7.util.DiffUtil;

import com.vasskob.downloadmaps.domain.model.Country;

import java.util.List;

public class CountryDiffCallback extends DiffUtil.Callback {

    private List<Country> oldList, newList;

    CountryDiffCallback(List<Country> oldList, List<Country> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if (oldList.size() != 0 && newList.size() != 0) {
            Country oldVisitor = oldList.get(oldItemPosition);
            Country newVisitor = newList.get(newItemPosition);
            return oldVisitor.equals(newVisitor);
        } else return oldList.size() == 0 && newList.size() == 0;
    }
}
