package com.incon.service.ui.status.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.custom.view.AssignDialog;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.BaseNCRPOptionFragment;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.PaymentAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppConstants.StatusConstants.ASSIGNED;
import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */

public class PaymentFragment extends BaseNCRPOptionFragment implements ServiceCenterContract.View {

    private View rootView;
    private AssignDialog assignDialog;


    @Override
    protected void initializePresenter() {
        paymentPresenter = new ServiceCenterPresenter();
        paymentPresenter.setView(this);
        setBasePresenter(paymentPresenter);

    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            paymentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment,
                    container, false);
            rootView = paymentBinding.getRoot();
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
        serviceRequest.setStatus(AppUtils.ServiceRequestTypes.PAYMENT.name());

        paymentAdapter = new PaymentAdapter();
        paymentAdapter.setClickCallback(iClickCallback);
        paymentBinding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        paymentBinding.paymentRecyclerview.setAdapter(paymentAdapter);
        paymentBinding.paymentRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void dismissSwipeRefresh() {
        super.dismissSwipeRefresh();
        if (paymentBinding.swiperefresh.isRefreshing()) {
            paymentBinding.swiperefresh.setRefreshing(false);
        }
    }

    @Override
    public void loadBottomSheet() {
        super.loadBottomSheet();
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                paymentAdapter.clearSelection();

            }
        });

    }


    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            paymentAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = paymentAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            paymentAdapter.notifyDataSetChanged();
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

        tagsArray.add(R.id.PRODUCT);
        textArray.add(getString(R.string.bottom_option_product));
        drawablesArray.add(R.drawable.ic_option_product);

        tagsArray.add(R.id.SERVICE_CENTER);
        textArray.add(getString(R.string.bottom_option_service_center));
        drawablesArray.add(R.drawable.ic_option_find_service_center);

        tagsArray.add(R.id.STATUS_UPDATE);
        textArray.add(getString(R.string.bottom_option_status_update));
        drawablesArray.add(R.drawable.ic_option_service_support);

        bottomSheetPurchasedBinding.firstRow.setVisibility(View.VISIBLE);
        bottomSheetPurchasedBinding.secondRowLine.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.secondRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
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

            if (tag == R.id.CUSTOMER) {

                tagsArray.add(R.id.CUSTOMER_CALL_CUSTOMER_CARE);
                textArray.add(getString(R.string.bottom_option_call_customer_care));
                drawablesArray.add(R.drawable.ic_option_call);


            } else if (tag == R.id.PRODUCT) {

                tagsArray.add(R.id.PRODUCT_WARRANTY_DETAILS);
                textArray.add(getString(R.string.bottom_option_warranty_details));
                drawablesArray.add(R.drawable.ic_option_warranty);

                tagsArray.add(R.id.PRODUCT_PAST_HISTORY);
                textArray.add(getString(R.string.bottom_option_past_history));
                drawablesArray.add(R.drawable.ic_option_pasthistory);

            } else if (tag == R.id.SERVICE_CENTER) {

                tagsArray.add(R.id.SERVICE_CENTER_CALL);
                textArray.add(getString(R.string.bottom_option_Call));
                drawablesArray.add(R.drawable.ic_option_call);

            } else if (tag == R.id.STATUS_UPDATE) {

                tagsArray.add(R.id.STATUS_UPDATE_PAID);
                textArray.add(getString(R.string.bottom_option_paid));
                drawablesArray.add(R.drawable.ic_option_accept_request);

                tagsArray.add(R.id.STATUS_UPDATE_TERMINATE);
                textArray.add(getString(R.string.bottom_option_terminate));
                drawablesArray.add(R.drawable.ic_option_accept_request);

            }

            bottomSheetPurchasedBinding.secondRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.secondRow.removeAllViews();
            bottomSheetPurchasedBinding.secondRow.setWeightSum(tagsArray.size());
            setBottomViewOptions(bottomSheetPurchasedBinding.secondRow, textArray, drawablesArray, tagsArray, bottomSheetSecondRowClickListener);
        }
    };


    // bottom sheet top view click event
    private View.OnClickListener bottomSheetSecondRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();

            ArrayList<Integer> drawablesArray = new ArrayList<>();
            ArrayList<String> textArray = new ArrayList<>();
            ArrayList<Integer> tagsArray = new ArrayList<>();

            FetchNewRequestResponse itemFromPosition = paymentAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.secondRow, tag);


            if (tag == R.id.CUSTOMER_CALL_CUSTOMER_CARE) {
                callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                return;
            } else if (tag == R.id.PRODUCT_WARRANTY_DETAILS) {
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));


            } else if (tag == R.id.PRODUCT_PAST_HISTORY) {
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));

            } else if (tag == R.id.SERVICE_CENTER_CALL) {
                callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                return;
            } else if (tag == R.id.STATUS_UPDATE_PAID) {

                tagsArray.add(R.id.STATUS_UPDATE_PAID_CASH);
                textArray.add(getString(R.string.bottom_option_cash));
                drawablesArray.add(R.drawable.ic_options_features);

                tagsArray.add(R.id.STATUS_UPDATE_PAID_ONLINE);
                textArray.add(getString(R.string.bottom_option_online));
                drawablesArray.add(R.drawable.ic_option_pasthistory);

                tagsArray.add(R.id.STATUS_UPDATE_PAID_CARD);
                textArray.add(getString(R.string.bottom_option_card));
                drawablesArray.add(R.drawable.ic_option_pasthistory);


            } else if (tag == R.id.STATUS_UPDATE_TERMINATE) {
                showUpdateStatusDialog(R.id.STATUS_UPDATE_TERMINATE);

            } else if (tag == R.id.STATUS_UPDATE_ASSIGN) {
                 fetchAssignDialogData();

            }

            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRow.removeAllViews();
            bottomSheetPurchasedBinding.thirdRow.setWeightSum(tagsArray.size());
            setBottomViewOptions(bottomSheetPurchasedBinding.thirdRow, textArray, drawablesArray, tagsArray, bottomSheetThirdRowClickListener);
        }
    };





    private View.OnClickListener bottomSheetThirdRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();


            FetchNewRequestResponse itemFromPosition = paymentAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.thirdRow, tag);

            if (tag == R.id.STATUS_UPDATE_PAID_CASH) {
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));

            } else if (tag == R.id.STATUS_UPDATE_PAID_ONLINE) {
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));

            } else if (tag == R.id.STATUS_UPDATE_PAID_CARD) {
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));

            }


        }

    };


    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO search click listener
    }


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
        paymentPresenter.disposeAll();
    }
}
