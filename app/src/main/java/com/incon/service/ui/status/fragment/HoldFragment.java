package com.incon.service.ui.status.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.ui.BaseNCRPOptionFragment;
import com.incon.service.ui.status.adapter.HoldAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 3/12/2018.
 */

public class HoldFragment extends BaseNCRPOptionFragment implements ServiceCenterContract.View {
    private View rootView;

    @Override
    protected void initializePresenter() {
        holdPresenter = new ServiceCenterPresenter();
        holdPresenter.setView(this);
        setBasePresenter(holdPresenter);
    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            holdBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_hold,
                    container, false);
            rootView = holdBinding.getRoot();
            shimmerFrameLayout = rootView.findViewById(R.id
                    .effect_shimmer);
            loadBottomSheet();
            initViews();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        serviceRequest = new ServiceRequest();
        serviceRequest.setStatus(AppUtils.ServiceRequestTypes.HOLD.name());
        holdAdapter = new HoldAdapter();
        holdAdapter.setClickCallback(iClickCallback);
        holdBinding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        holdBinding.holdRecyclerview.setAdapter(holdAdapter);
        holdBinding.holdRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void dismissSwipeRefresh() {
        super.dismissSwipeRefresh();
        if (holdBinding.swiperefresh.isRefreshing()) {
            holdBinding.swiperefresh.setRefreshing(false);
        }
    }

    @Override
    public void loadBottomSheet() {
        super.loadBottomSheet();
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                holdAdapter.clearSelection();
            }
        });
    }


    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            holdAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = holdAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            holdAdapter.notifyDataSetChanged();
            productSelectedPosition = position;
            createBottomSheetFirstRow();
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetFirstRow() {
        ArrayList<Integer> drawablesArray = new ArrayList<>();
        ArrayList<String> textArray = new ArrayList<>();
        ArrayList<Integer> tagsArray = new ArrayList<>();

        tagsArray.add(R.id.CUSTOMER);
        textArray.add(getString(R.string.bottom_option_customer));
        drawablesArray.add(R.drawable.ic_option_customer);

        bottomSheetPurchasedBinding.firstRow.setVisibility(View.VISIBLE);
        bottomSheetPurchasedBinding.secondRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.firstRow.removeAllViews();
        bottomSheetPurchasedBinding.firstRow.setWeightSum(tagsArray.size());
        setBottomViewOptions(bottomSheetPurchasedBinding.firstRow, textArray, drawablesArray, tagsArray, bottomSheetFirstRowClickListener);

    }

    private View.OnClickListener bottomSheetFirstRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();

            ArrayList<Integer> drawablesArray = new ArrayList<>();
            ArrayList<String> textArray = new ArrayList<>();
            ArrayList<Integer> tagsArray = new ArrayList<>();


            changeSelectedViews(bottomSheetPurchasedBinding.firstRow, tag);

            bottomSheetPurchasedBinding.secondRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.secondRow.removeAllViews();
            bottomSheetPurchasedBinding.secondRow.setWeightSum(tagsArray.size());
            // setBottomViewOptions(bottomSheetPurchasedBinding.secondRow, textArray, drawablesArray, tagsArray, bottomSheetSecondRowClickListener);
        }
    };


    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersList) {

    }

    @Override
    public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {
        doRefresh(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        holdPresenter.disposeAll();
    }
}
