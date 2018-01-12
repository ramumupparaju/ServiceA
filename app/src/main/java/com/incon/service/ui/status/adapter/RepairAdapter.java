package com.incon.service.ui.status.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.AppUtils;
import com.incon.service.BR;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.databinding.ItemRepairFragmentBinding;
import com.incon.service.ui.BaseRecyclerViewAdapter;

/**
 * Created by PC on 12/6/2017.
 */

public class RepairAdapter extends BaseRecyclerViewAdapter {

    @Override
    public RepairAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemRepairFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_repair_fragment, parent, false);
        return new RepairAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FetchNewRequestResponse fetchNewRequestResponse = filteredList.get(position);
        ((RepairAdapter.ViewHolder) holder).bind(fetchNewRequestResponse);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemRepairFragmentBinding binding;

        public ViewHolder(ItemRepairFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(FetchNewRequestResponse fetchNewRequestResponse) {
            binding.setVariable(BR.fetchNewRequestResponse, fetchNewRequestResponse);
            View root = binding.getRoot();
            AppUtils.loadImageFromApi(binding.brandImageview, fetchNewRequestResponse
                    .getProductLogoUrl());
            AppUtils.loadImageFromApi(binding.productImageview, fetchNewRequestResponse
                    .getProductImageUrl());
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }

}
