package com.vasskob.downloadmaps.presentation.main.continent.view.adapter;

import android.support.v7.util.DiffUtil;

import com.vasskob.downloadmaps.domain.model.Continent;

import java.util.List;

public class ContinentDiffCallback extends DiffUtil.Callback {

    private List<Continent> oldList, newList;

    ContinentDiffCallback(List<Continent> oldList, List<Continent> newList) {
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
            Continent oldVisitor = oldList.get(oldItemPosition);
            Continent newVisitor = newList.get(newItemPosition);
            return oldVisitor.equals(newVisitor);
        } else return oldList.size() == 0 && newList.size() == 0;
    }
}
