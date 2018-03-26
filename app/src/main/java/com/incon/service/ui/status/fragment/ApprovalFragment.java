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
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.IStatusClickCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppEditTextDialog;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.ui.BaseNCRPOptionFragment;
import com.incon.service.ui.status.adapter.ApprovalAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */

public class ApprovalFragment extends BaseNCRPOptionFragment implements ServiceCenterContract.View {
    private View rootView;
    public AppEditTextDialog manualApproachDialog;

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

    private IStatusClickCallback iClickCallback = new IStatusClickCallback() {
        @Override
        public void onClickStatusButton(int statusType) {

        }

        @Override
        public void onClickStatus(int productPosition, int statusPosition) {

        }

        @Override
        public void onClickPosition(int position) {
            approvalAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = approvalAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            approvalAdapter.notifyDataSetChanged();
            productSelectedPosition = position;
            createBottomSheetFirstRow();
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetFirstRow() {

        ArrayList<Integer> drawablesArray = new ArrayList<>();
        ArrayList<String> textArray = new ArrayList<>();
        ArrayList<Integer> tagsArray = new ArrayList<>();


        tagsArray.add(R.id.HOLD);
        textArray.add(getString(R.string.bottom_option_hold));
        drawablesArray.add(R.drawable.ic_option_hold);

        tagsArray.add(R.id.REJECT);
        textArray.add(getString(R.string.bottom_option_reject));
        drawablesArray.add(R.drawable.ic_option_accept_request);

        tagsArray.add(R.id.MOVE_TO);
        textArray.add(getString(R.string.bottom_option_move_to));
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

            changeSelectedViews(bottomSheetPurchasedBinding.firstRow, tag);

            if (tag == R.id.HOLD) {
                showUpdateStatusDialog(R.id.HOLD);
            } else if (tag == R.id.REJECT) {
                showUpdateStatusDialog(R.id.REJECT);
            } else if (tag == R.id.MOVE_TO) {
                showMoveToDialog();
            } else if (tag == R.id.MANUAL_APPROACH) {
                showManualApproachDialog();
            }
            bottomSheetPurchasedBinding.secondRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.secondRowLine.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.secondRow.removeAllViews();
            bottomSheetPurchasedBinding.secondRow.setWeightSum(tagsArray.size());
            //setBottomViewOptions(bottomSheetPurchasedBinding.secondRow, textArray, drawablesArray, tagsArray, bottomSheetSecondRowClickListener);
        }
    };

    private void showManualApproachDialog() {
        manualApproachDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String commentString) {
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                break;
                            case AlertDialogCallback.CANCEL:
                                manualApproachDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_manual_approach))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        manualApproachDialog.showDialog();
        manualApproachDialog.setCancelable(true);


    }


    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO have to do filter list
    }


    @Override
    public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {
        dismissDialog(updateStatusDialog);
        dismissDialog(moveToOptionDialog);
        dismissDialog(bottomSheetDialog);
        try {
            doRefresh(true);
        } catch (Exception e) {
            //TODO have to handle
        }
    }

    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersList) {
//do nothing
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceCenterPresenter.disposeAll();
    }
}
