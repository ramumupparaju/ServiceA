package com.incon.service.ui.status.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseNCRPOptionFragment;

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
            loadBottomSheet();
            initViews();
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

        tagsArray.add(R.id.STATUS_UPDATE);
        textArray.add(getString(R.string.bottom_option_status_update));
        drawablesArray.add(R.drawable.ic_option_service_support);

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


            FetchNewRequestResponse itemFromPosition = newRequestsAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.secondRow, tag);

            if (tag == R.id.CUSTOMER) {

                tagsArray.add(R.id.CUSTOMER_CALL_CUSTOMER_CARE);
                textArray.add(getString(R.string.bottom_option_call_customer_care));
                drawablesArray.add(R.drawable.ic_option_call);

                tagsArray.add(R.id.CUSTOMER_LOCATION);
                textArray.add(getString(R.string.bottom_option_location));
                drawablesArray.add(R.drawable.ic_option_location);


            }  else if (tag == R.id.STATUS_UPDATE) {
                textArray.add(getString(R.string.bottom_option_assign));
                tagsArray.add(R.id.STATUS_UPDATE_ASSIGN);
                drawablesArray.add(R.drawable.ic_option_accept_request);

                textArray.add(getString(R.string.bottom_option_terminate));
                tagsArray.add(R.id.STATUS_UPDATE_TERMINATE);
                drawablesArray.add(R.drawable.ic_option_hold);


                textArray.add(getString(R.string.bottom_option_move_to));
                tagsArray.add(R.id.STATUS_UPDATE_MOVE_TO);
                drawablesArray.add(R.drawable.ic_option_hold);

            }


            changeSelectedViews(bottomSheetPurchasedBinding.firstRow, tag);

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

            FetchNewRequestResponse itemFromPosition = newRequestsAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.secondRow, tag);


            ArrayList<Integer> drawablesArray = new ArrayList<>();
            ArrayList<String> textArray = new ArrayList<>();
            ArrayList<Integer> tagsArray = new ArrayList<>();


            if (tag == R.id.CUSTOMER_CALL_CUSTOMER_CARE) {
                callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                return;
            } else if (tag == R.id.CUSTOMER_LOCATION) {
                showLocationDialog();
                return;

            } else if (tag == R.id.STATUS_UPDATE_TERMINATE) {
                showUpdateStatusDialog(R.id.STATUS_UPDATE_TERMINATE);

            } else if (tag == R.id.STATUS_UPDATE_MOVE_TO) {
                showMoveToDialog();

            } else if (tag == R.id.STATUS_UPDATE_ASSIGN) {
                fetchAssignDialogData();
            }

            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRowLine.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.thirdRow.removeAllViews();
            bottomSheetPurchasedBinding.thirdRow.setWeightSum(tagsArray.size());
        }
    };


    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersList) {

    }

    @Override
    public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {
        doRefresh(true);
    }

}
