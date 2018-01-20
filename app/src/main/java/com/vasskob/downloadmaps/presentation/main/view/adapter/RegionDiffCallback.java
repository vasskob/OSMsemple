package com.vasskob.downloadmaps.presentation.main.view.adapter;

import android.support.v7.util.DiffUtil;

import com.vasskob.downloadmaps.domain.model.Region;

import java.util.List;

public class RegionDiffCallback extends DiffUtil.Callback {

    private List<Region> oldList, newList;

    RegionDiffCallback(List<Region> oldList, List<Region> newList) {
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
            Region oldVisitor = oldList.get(oldItemPosition);
            Region newVisitor = newList.get(newItemPosition);
            return oldVisitor.equals(newVisitor);
        } else return oldList.size() == 0 && newList.size() == 0;
    }
}
