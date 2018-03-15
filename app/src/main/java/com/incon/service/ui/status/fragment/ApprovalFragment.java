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
import com.incon.service.ui.status.adapter.ApprovalAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 12/5/2017.
 */

public class ApprovalFragment extends BaseNCRPOptionFragment implements ServiceCenterContract.View {
    private View rootView;

    @Override
    protected void initializePresenter() {
        serviceCenterPresenter = new ServiceCenterPresenter();
        serviceCenterPresenter.setView(this);
        setBasePresenter(serviceCenterPresenter);

    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            approvalBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_approval,
                    container, false);
            rootView = approvalBinding.getRoot();
            shimmerFrameLayout = rootView.findViewById(R.id
                    .effect_shimmer);
            initViews();
            loadBottomSheet();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        serviceRequest = new ServiceRequest();
        serviceRequest.setStatus(AppUtils.ServiceRequestTypes.APPROVAL.name());
        approvalAdapter = new ApprovalAdapter();
        approvalAdapter.setClickCallback(iClickCallback);
        approvalBinding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        approvalBinding.apprvalRecyclerview.setAdapter(approvalAdapter);
        approvalBinding.apprvalRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void loadBottomSheet() {
        super.loadBottomSheet();
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                approvalAdapter.clearSelection();

            }
        });

    }

    @Override
    public void dismissSwipeRefresh() {
        super.dismissSwipeRefresh();
        if (approvalBinding.swiperefresh.isRefreshing()) {
            approvalBinding.swiperefresh.setRefreshing(false);
        }
    }

    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            approvalAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = approvalAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            productSelectedPosition = position;
            approvalAdapter.notifyDataSetChanged();
            createBottomSheetFirstRow();
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetFirstRow() {

        ArrayList<Integer> drawablesArray = new ArrayList<>();
        ArrayList<String> textArray = new ArrayList<>();
        ArrayList<Integer> tagsArray = new ArrayList<>();

        textArray.add(getString(R.string.bottom_option_hold));
        tagsArray.add(R.id.STATUS_UPDATE_HOLD);
        drawablesArray.add(R.drawable.ic_option_hold);

        textArray.add(getString(R.string.bottom_option_reject));
        tagsArray.add(R.id.STATUS_UPDATE_REJECT);
        drawablesArray.add(R.drawable.ic_option_accept_request);

        textArray.add(getString(R.string.bottom_option_move_to));
        tagsArray.add(R.id.STATUS_UPDATE_MOVE_TO);
        drawablesArray.add(R.drawable.ic_option_hold);

        tagsArray.add(R.id.MANUAL_APPROACH);
        textArray.add(getString(R.string.bottom_option_manual_approach));
        drawablesArray.add(R.drawable.ic_option_customer);

        bottomSheetPurchasedBinding.firstRow.setVisibility(View.VISIBLE);
        bottomSheetPurchasedBinding.secondRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.firstRow.removeAllViews();
        bottomSheetPurchasedBinding.firstRow.setWeightSum(tagsArray.size());
        setBottomViewOptions(bottomSheetPurchasedBinding.firstRow, textArray, drawablesArray, tagsArray, bottomSheetFirstRowClickListener);

    }

    // bottom sheet click event
    private View.OnClickListener bottomSheetFirstRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Integer tag = (Integer) view.getTag();

            ArrayList<Integer> drawablesArray = new ArrayList<>();
            ArrayList<String> textArray = new ArrayList<>();
            ArrayList<Integer> tagsArray = new ArrayList<>();

            FetchNewRequestResponse itemFromPosition = approvalAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.firstRow, tag);


            bottomSheetPurchasedBinding.secondRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.secondRow.removeAllViews();
            bottomSheetPurchasedBinding.secondRow.setWeightSum(tagsArray.size());
            setBottomViewOptions(bottomSheetPurchasedBinding.secondRow, textArray, drawablesArray, tagsArray, bottomSheetSecondRowClickListener);
        }
    };

    private View.OnClickListener bottomSheetSecondRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();
            FetchNewRequestResponse itemFromPosition = approvalAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.secondRow, tag);
            ArrayList<Integer> drawablesArray = new ArrayList<>();
            ArrayList<String> textArray = new ArrayList<>();
            ArrayList<Integer> tagsArray = new ArrayList<>();


            if (tag == R.id.STATUS_UPDATE_HOLD) {
                showUpdateStatusDialog(R.id.STATUS_UPDATE_HOLD);
            }
            else if (tag == R.id.STATUS_UPDATE_REJECT) {

                showUpdateStatusDialog(R.id.STATUS_UPDATE_REJECT);
            }
            else if (tag == R.id.STATUS_UPDATE_MOVE_TO) {
                showMoveToDialog();

            }

        }
    };


    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO have to do filter list
    }


    @Override
    public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {
//do nothing
    }

    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersList) {
//do nothing
    }
}
