package com.incon.service.ui.status.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.BR;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.databinding.ItemApprovalFragmentBinding;
import com.incon.service.ui.BaseRecyclerViewAdapter;

/**
 * Created by PC on 12/6/2017.
 */

public class ApprovalAdapter extends BaseRecyclerViewAdapter {
    @Override
    public ApprovalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemApprovalFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_approval_fragment, parent, false);
        return new ApprovalAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FetchNewRequestResponse fetchNewRequestResponse = filteredList.get(position);
        ((ApprovalAdapter.ViewHolder) holder).bind(fetchNewRequestResponse);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemApprovalFragmentBinding binding;

        public ViewHolder(ItemApprovalFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(FetchNewRequestResponse fetchNewRequestResponse) {
            binding.setVariable(BR.fetchNewRequestResponse, fetchNewRequestResponse);
            View root = binding.getRoot();
            binding.executePendingBindings();

        }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }

}
