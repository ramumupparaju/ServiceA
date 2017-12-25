package com.incon.service.ui.settings.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.BR;
import com.incon.service.R;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.ItemAllServiceCentersBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 09 Aug 2017 7:11 PM.
 */
public class AllServiceCentersAdapter extends RecyclerView.Adapter<AllServiceCentersAdapter.ViewHolder> {

    private List<ServiceCenterResponse> serviceCenterResponses = new ArrayList<>();
    private IClickCallback clickCallback;

    @Override
    public AllServiceCentersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemAllServiceCentersBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_all_service_centers, parent, false);
        return new ViewHolder(binding);
    }

    public void setServiceCenterResponses(List<ServiceCenterResponse> serviceCenterResponses) {
        this.serviceCenterResponses = serviceCenterResponses;
        notifyDataSetChanged();
    }

    public void setClickCallback(IClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    @Override
    public void onBindViewHolder(AllServiceCentersAdapter.ViewHolder holder, int position) {
        ServiceCenterResponse serviceCenterResponse = serviceCenterResponses.get(position);
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
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(ServiceCenterResponse serviceCenterResponse, int position) {
            binding.setVariable(BR.modelResponse, serviceCenterResponse);
            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }
}
