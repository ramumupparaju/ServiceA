package com.incon.service.ui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.IStatusClickCallback;
import com.incon.service.databinding.StatusViewBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by PC on 10/2/2017.
 */

public abstract class BaseRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<FetchNewRequestResponse> filteredList = new ArrayList<>();
    public List<FetchNewRequestResponse> fetchNewRequestResponseList = new ArrayList<>();
    public IStatusClickCallback clickCallback;

    public Comparator comparator = new Comparator<FetchNewRequestResponse>() {
        @Override
        public int compare(FetchNewRequestResponse o1, FetchNewRequestResponse o2) {
            try {
                Date a = null;
                Date b = null;

                return (b.compareTo(a));
            } catch (Exception e) {

            }

            return -1;

        }
    };

    public StatusViewBinding getStatusView(Context context) {
        return DataBindingUtil.inflate(
                LayoutInflater.from(context), R.layout.status_view, null, false);
    }
    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public FetchNewRequestResponse getItemFromPosition(int position) {
        return filteredList.get(position);
    }

    public void setData(List<FetchNewRequestResponse> fetchNewRequestResponseList) {
        this.fetchNewRequestResponseList = fetchNewRequestResponseList;
        filteredList.clear();
        filteredList.addAll(fetchNewRequestResponseList);
        Collections.sort(filteredList, comparator);
        notifyDataSetChanged();
    }


    public void setClickCallback(IStatusClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

/*    public void searchData(String searchableString, String searchType) {
        filteredList.clear();
        if (searchType.equalsIgnoreCase(AppConstants.FilterConstants.NAME)) {
            for (Product returnHistoryResponse
                    : allDataResponseList) {
                if (returnHistoryResponse.getProductName() != null
                        && returnHistoryResponse.getProductName().toLowerCase().startsWith(
                        searchableString.toLowerCase())) {
                    filteredList.add(returnHistoryResponse);
                }
            }
        } else if (searchType.equalsIgnoreCase(AppConstants.FilterConstants.BRAND)) {
            for (Product returnHistoryResponse
                    : allDataResponseList) {
                if (returnHistoryResponse.getBrandName() != null && returnHistoryResponse
                        .getBrandName().toLowerCase().startsWith(
                                searchableString.toLowerCase())) {
                    filteredList.add(returnHistoryResponse);
                }
            }
        } else {
            filteredList.addAll(allDataResponseList);
        }
        notifyDataSetChanged();
    }*/

    public void clearSelection() {
        for (FetchNewRequestResponse fetchNewRequestResponse : filteredList) {
            fetchNewRequestResponse.setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void clearData() {
        fetchNewRequestResponseList.clear();
        notifyDataSetChanged();
    }

}
