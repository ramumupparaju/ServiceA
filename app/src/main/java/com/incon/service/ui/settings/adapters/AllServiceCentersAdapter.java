package com.incon.service.ui.settings.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.BR;
import com.incon.service.R;
import com.incon.service.callbacks.IEditClickCallback;
import com.incon.service.databinding.ItemAllServiceCentersBinding;
import com.incon.service.dto.addservicecenter.AddServiceCenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 09 Aug 2017 7:11 PM.
 */
public class AllServiceCentersAdapter extends RecyclerView.Adapter<AllServiceCentersAdapter.ViewHolder> {

    private List<AddServiceCenter> serviceCenterResponses = new ArrayList<>();
    private IEditClickCallback clickCallback;

    @Override
    public AllServiceCentersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAllServiceCentersBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_all_service_centers, parent, false);
        return new ViewHolder(binding);
    }

    public void setServiceCenterResponses(List<AddServiceCenter> serviceCenterResponses) {
        this.serviceCenterResponses = serviceCenterResponses;
        notifyDataSetChanged();
    }

    public void setClickCallback(IEditClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    public void onBindViewHolder(AllServiceCentersAdapter.ViewHolder holder, int position) {
        AddServiceCenter serviceCenterResponse = serviceCenterResponses.get(position);
        holder.bind(serviceCenterResponse, position);

    }

    @Override
    public int getItemCount() {
        return serviceCenterResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemAllServiceCentersBinding binding;

        public ViewHolder(ItemAllServiceCentersBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            View root = binding.getRoot();
            (root.findViewById(R.id.edit_imageview)).setOnClickListener(this);
            root.setOnClickListener(this);
        }

        public void bind(AddServiceCenter serviceCenterResponse, int position) {
            binding.setVariable(BR.modelResponse, serviceCenterResponse);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.edit_imageview) {
                clickCallback.onEditClickPosition(getAdapterPosition());
                return;
            }
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }
}
