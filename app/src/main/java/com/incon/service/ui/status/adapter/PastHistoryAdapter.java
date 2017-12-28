package com.incon.service.ui.status.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.BR;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.databinding.ItemPastHistoryBinding;
import com.incon.service.ui.BaseRecyclerViewAdapter;

/**
 * Created by MY HOME on 28-Dec-17.
 */

public class PastHistoryAdapter extends BaseRecyclerViewAdapter {

    @Override
    public PastHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPastHistoryBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_past_history, parent, false);
        return new PastHistoryAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FetchNewRequestResponse fetchNewRequestResponse = filteredList.get(position);
        ((PastHistoryAdapter.ViewHolder) holder).bind(fetchNewRequestResponse, position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private final ItemPastHistoryBinding binding;


        public ViewHolder(ItemPastHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(FetchNewRequestResponse fetchNewRequestResponse, int position) {
            binding.setVariable(BR.fetchNewRequestResponse, fetchNewRequestResponse);
            View root = binding.getRoot();
            binding.executePendingBindings();
        }
    }

}
