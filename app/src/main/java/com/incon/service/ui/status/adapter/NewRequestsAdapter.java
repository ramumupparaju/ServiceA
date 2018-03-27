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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;
import com.incon.service.AppConstants;
import com.incon.service.AppUtils;
import com.incon.service.BR;
import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.status.StatusList;
import com.incon.service.databinding.ItemNewRequestFragmentBinding;
import com.incon.service.databinding.StatusViewBinding;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.ui.BaseRecyclerViewAdapter;
import com.incon.service.utils.AddressFromLatLngAddress;
import com.incon.service.utils.DeviceUtils;

import java.util.List;

import static com.incon.service.AppUtils.getStatusName;

/**
 * Created by PC on 12/6/2017.
 */

public class NewRequestsAdapter extends BaseRecyclerViewAdapter {
    private AddressFromLatLngAddress addressFromLatLngAddress;
    private ConnectApplication context;

    public NewRequestsAdapter() {
        addressFromLatLngAddress = new AddressFromLatLngAddress();
        context = ConnectApplication.getAppContext();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemNewRequestFragmentBinding binding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_new_request_fragment, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FetchNewRequestResponse fetchNewRequestResponse = filteredList.get(position);
        ((NewRequestsAdapter.ViewHolder) holder).bind(fetchNewRequestResponse, position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ItemNewRequestFragmentBinding binding;


        public ViewHolder(ItemNewRequestFragmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(FetchNewRequestResponse fetchNewRequestResponse, int position) {
            binding.setVariable(BR.fetchNewRequestResponse, fetchNewRequestResponse);
            // todo have to know
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) binding.cardView.getLayoutParams();
            int leftRightMargin = (int) DeviceUtils.convertPxToDp(8);
            if (position == 0) {
                int topMargin = (int) DeviceUtils.convertPxToDp(6);
                int bottomMargin = (int) DeviceUtils.convertPxToDp(3);
                layoutParams.setMargins(leftRightMargin, topMargin, leftRightMargin, bottomMargin);
            } else if (position == filteredList.size()) {
                int topMargin = (int) DeviceUtils.convertPxToDp(3);
                int bottomMargin = (int) DeviceUtils.convertPxToDp(6);
                layoutParams.setMargins(leftRightMargin, topMargin, leftRightMargin, bottomMargin);
            } else {
                int topBottomMargin = (int) DeviceUtils.convertPxToDp(3);
                layoutParams.setMargins(leftRightMargin, topBottomMargin, leftRightMargin, topBottomMargin);
            }

            if (fetchNewRequestResponse.getAssignedUser() != null) {
                binding.textAssigned.setText(fetchNewRequestResponse.getAssignedUser().getName());
            } else {
                binding.textAssigned.setVisibility(View.GONE);
            }
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

            binding.statusScrollview.post(new Runnable() {
                public void run() {
                    binding.statusScrollview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                }
            });

            List<StatusList> statusList = fetchNewRequestResponse.getStatusList();
            createStatusView(binding, statusList, position);

            binding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            clickCallback.onClickPosition(getAdapterPosition());
        }

    }

    private void createStatusView(ItemNewRequestFragmentBinding binding, List<StatusList> statusList, int position) {

        int size = statusList.size();
        if (statusList == null || size == 0) {
            binding.statusLayout.setVisibility(View.GONE);
        } else {
            binding.statusLayout.setVisibility(View.VISIBLE);
            binding.statusLayout.removeAllViews();
            for (int i = 0; i < size; i++) {
                StatusList statusData = statusList.get(i);
                ServiceRequest serviceRequest = statusData.getRequest();
                LinearLayout linearLayout = new LinearLayout(context);
                StatusViewBinding statusView = getStatusView(context);
                statusView.viewTv.setText(getStatusName(Integer.parseInt(serviceRequest.getStatus())));
                statusView.viewLeftLine.setVisibility(i == 0 ? View.INVISIBLE : View.VISIBLE);
                statusView.viewRightLine.setVisibility(i == size - 1 ? View.INVISIBLE : View.VISIBLE);
                View statusRootView = statusView.getRoot();
                statusRootView.setOnClickListener(onClickListener);
                statusRootView.setTag(String.format("%1$s;%2$s", position, i));
                linearLayout.addView(statusRootView);
                binding.statusLayout.addView(linearLayout);
            }
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Object tag = view.getTag();
            if (tag != null) {
                String[] positionsArray = ((String) tag).split(";");
                clickCallback.onClickStatus(Integer.parseInt(positionsArray[0]), Integer.parseInt(positionsArray[1]));
            }

        }
    };



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
