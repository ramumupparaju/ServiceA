package com.incon.service.ui.feedback.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.R;
import com.incon.service.apimodel.components.productinforesponse.ProductInfoResponse;
import com.incon.service.databinding.ItemCheckupFragmentBinding;
import com.incon.service.databinding.ItemFeedbackFragmentBinding;
import com.incon.service.ui.BaseRecyclerViewAdapter;
import com.incon.service.ui.status.adapter.CheckUpAdapter;

/**
 * Created by shiva on 12/8/2017.
 */

public class FeedBackAdapter extends BaseRecyclerViewAdapter {

    @Override
    public FeedBackAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemFeedbackFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_feedback_fragment, parent, false);
        return new FeedBackAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ProductInfoResponse returnHistoryResponse = filteredList.get(position);
        ((FeedBackAdapter.ViewHolder) holder).bind(returnHistoryResponse);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemFeedbackFragmentBinding binding;

        public ViewHolder(ItemFeedbackFragmentBinding binding) {
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
