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
import com.incon.service.databinding.ItemPaymentFragmentBinding;
import com.incon.service.ui.BaseRecyclerViewAdapter;

/**
 * Created by PC on 12/6/2017.
 */

public class PaymentAdapter extends BaseRecyclerViewAdapter {

    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPaymentFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_payment_fragment, parent, false);
        return new PaymentAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FetchNewRequestResponse fetchNewRequestResponse = filteredList.get(position);
        ((PaymentAdapter.ViewHolder) holder).bind(fetchNewRequestResponse);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemPaymentFragmentBinding binding;

        public ViewHolder(ItemPaymentFragmentBinding binding) {
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
