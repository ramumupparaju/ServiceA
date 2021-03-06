package com.incon.service.ui.status.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.custom.view.AssignDialog;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseNCRPOptionFragment;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */

public class PaymentFragment extends BaseNCRPOptionFragment implements ServiceCenterContract.View {

    private View rootView;
    private AssignDialog assignDialog;


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
            newRequestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_newrequest,
                    container, false);
            rootView = newRequestBinding.getRoot();
            shimmerFrameLayout = rootView.findViewById(R.id
                    .effect_shimmer);

            initViews();
            loadBottomSheet();

        }
        setTitle();
        return rootView;
    }

    @Override
    public void createBottomSheetFirstRow() {

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

               /* tagsArray.add(R.id.STATUS_UPDATE_TERMINATE);
                textArray.add(getString(R.string.bottom_option_terminate));
                drawablesArray.add(R.drawable.ic_option_accept_request);*/

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

            FetchNewRequestResponse itemFromPosition = newRequestsAdapter.getItemFromPosition(
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
            }

            // todo have  to know and remove
          /*  else if (tag == R.id.STATUS_UPDATE_ASSIGN) {
                 fetchAssignDialogData();

            }*/

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
        dismissDialog(updateStatusDialog);
        dismissDialog(assignDialog);
        try {
            doRefresh(true);
        } catch (Exception e) {
            //TODO have to handle
        }
    }
}
