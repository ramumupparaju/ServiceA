package com.incon.service.ui.status.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.databinding.library.baseAdapters.BR;
import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.productinforesponse.ProductInfoResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.ItemNewRequestFragmentBinding;
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
        ProductInfoResponse returnHistoryResponse = filteredList.get(position);
        ((PaymentAdapter.ViewHolder) holder).bind(returnHistoryResponse);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemPaymentFragmentBinding binding;

        public ViewHolder(ItemPaymentFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(ProductInfoResponse returnHistoryResponse) {
            }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }

}
