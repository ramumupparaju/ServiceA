package com.incon.service.ui.status.adapter;

import android.databinding.DataBindingUtil;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.incon.service.AppConstants;
import com.incon.service.AppUtils;
import com.incon.service.BR;
import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.databinding.ItemPaymentFragmentBinding;
import com.incon.service.ui.BaseRecyclerViewAdapter;
import com.incon.service.utils.AddressFromLatLngAddress;

/**
 * Created by PC on 12/6/2017.
 */

public class PaymentAdapter extends BaseRecyclerViewAdapter {
    private AddressFromLatLngAddress addressFromLatLngAddress;
    private ConnectApplication context;

    public PaymentAdapter() {
        addressFromLatLngAddress = new AddressFromLatLngAddress();
        context = ConnectApplication.getAppContext();
    }


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
            String[] location = fetchNewRequestResponse.getCustomer().getLocation().split(",");
            loadLocationDetailsFromGeocoder(new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1])), fetchNewRequestResponse);


            if (fetchNewRequestResponse.isSelected()) {
                binding.viewsLayout.setVisibility(View.VISIBLE);
            } else {
                binding.viewsLayout.setVisibility(View.GONE);
            }
            binding.executePendingBindings();

            }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }

    private void loadLocationDetailsFromGeocoder(LatLng latLng, FetchNewRequestResponse fetchNewRequestResponse) {

        addressFromLatLngAddress.getAddressFromLocation(context,
                latLng, AppConstants.RequestCodes.LOCATION_ADDRESS_FROM_LATLNG, new LocationHandler(fetchNewRequestResponse));
    }

    private class LocationHandler extends Handler {
        private final FetchNewRequestResponse fetchNewRequestResponse;

        public LocationHandler(FetchNewRequestResponse fetchNewRequestResponse) {
            this.fetchNewRequestResponse = fetchNewRequestResponse;
        }

        @Override
        public void handleMessage(Message message) {
            Bundle bundle = message.getData();
            Address locationAddress = bundle.getParcelable(AppConstants.BundleConstants
                    .LOCATION_ADDRESS);
            if (locationAddress != null) {
                switch (message.what) {
                    case AppConstants.RequestCodes.LOCATION_ADDRESS_FROM_LATLNG:
                        fetchNewRequestResponse.getServiceCenter().setAddress(locationAddress.getAddressLine(0));
                        break;
                    default:
                        //do nothing
                }
            }
        }
    }
}
