package com.incon.service.ui.status.fragment;

import android.app.DatePickerDialog;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.EstimationDialogCallback;
import com.incon.service.custom.view.EstimationDialog;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.BaseNCRPOptionFragment;
import com.incon.service.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */
public class CheckUpFragment extends BaseNCRPOptionFragment implements ServiceCenterContract.View {
    private View rootView;
    private EstimationDialog estimationDialog;

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

                tagsArray.add(R.id.STATUS_UPDATE_ESTIMATION);
                textArray.add(getString(R.string.bottom_option_estimation));
                drawablesArray.add(R.drawable.ic_option_accept_request);

                tagsArray.add(R.id.STATUS_UPDATE_HOLD);
                textArray.add(getString(R.string.bottom_option_hold));
                drawablesArray.add(R.drawable.ic_option_accept_request);

                tagsArray.add(R.id.STATUS_UPDATE_TERMINATE);
                textArray.add(getString(R.string.bottom_option_terminate));
                drawablesArray.add(R.drawable.ic_option_hold);

                tagsArray.add(R.id.STATUS_UPDATE_MOVE_TO);
                textArray.add(getString(R.string.bottom_option_move_to));
                drawablesArray.add(R.drawable.ic_option_hold);

                tagsArray.add(R.id.STATUS_UPDATE_ASSIGN);
                textArray.add(getString(R.string.bottom_option_assign));
                drawablesArray.add(R.drawable.ic_option_hold);

            }

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
                // TODO have to get details from back end

            } else if (tag == R.id.PRODUCT_PAST_HISTORY) {
                showPastHisoryDialog();
                return;

            } else if (tag == R.id.SERVICE_CENTER_CALL) {
                callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                return;

            } else if (tag == R.id.STATUS_UPDATE_ESTIMATION) {
                showEstimationDialog();

            } else if (tag == R.id.STATUS_UPDATE_HOLD) {
                showUpdateStatusDialog(R.id.STATUS_UPDATE_HOLD);

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
            setBottomViewOptions(bottomSheetPurchasedBinding.thirdRow, textArray, drawablesArray, tagsArray, bottomSheetThirdRowClickListener);
        }
    };

    private void showEstimationDialog() {

        estimationDialog = new EstimationDialog.AlertDialogBuilder(getContext(), new EstimationDialogCallback() {

            @Override
            public void dateClicked(String date) {
                showDatePickerToEstimate(date);
            }

            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {
                FetchNewRequestResponse requestResponse = newRequestsAdapter.getItemFromPosition(productSelectedPosition);
                Request request = requestResponse.getRequest();
                upDateStatus.setRequestid(request.getId());
                serviceCenterPresenter.upDateStatus(userId, upDateStatus);
            }

            @Override
            public void alertDialogCallback(byte dialogStatus) {
                switch (dialogStatus) {
                    case AlertDialogCallback.OK:
                        break;
                    case AlertDialogCallback.CANCEL:
                        estimationDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }
        }).build();
        estimationDialog.showDialog();
    }

    private void showDatePickerToEstimate(String date) {
        AppUtils.hideSoftKeyboard(getContext(), getView());
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        String selectedDate = date;
        if (!TextUtils.isEmpty(selectedDate)) {
            cal.setTimeInMillis(DateUtils.convertStringFormatToMillis(
                    selectedDate, DateFormatterConstants.DD_MM_YYYY));
        }

        int customStyle = android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                ? R.style.DatePickerDialogTheme : android.R.style.Theme_DeviceDefault_Light_Dialog;
        DatePickerDialog datePicker = new DatePickerDialog(getContext(),
                customStyle,
                estimationDatePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.show();
    }

    private DatePickerDialog.OnDateSetListener estimationDatePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                // when dialog box is closed, below method will be called.
                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    Calendar selectedDateTime = Calendar.getInstance();
                    selectedDateTime.set(selectedYear, selectedMonth, selectedDay);

                    String dobInDD_MM_YYYY = DateUtils.convertDateToOtherFormat(
                            selectedDateTime.getTime(), DateFormatterConstants.DD_MM_YYYY);
                    estimationDialog.setDateFromPicker(dobInDD_MM_YYYY);

                }
            };


    private View.OnClickListener bottomSheetThirdRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();
            changeSelectedViews(bottomSheetPurchasedBinding.thirdRow, tag);
        }

    };

    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO have to implement search
    }


    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersList) {
        if (usersList == null) {
            usersList = new ArrayList<>();
        }

        //this.usersList = usersList;
        showAssignDialog(usersList);

    }

    @Override
    public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {
        dismissDialog(estimationDialog);
        dismissDialog(updateStatusDialog);
        dismissDialog(moveToOptionDialog);
        dismissDialog(assignDialog);
        dismissDialog(bottomSheetDialog);
        try {
            doRefresh(true);
        } catch (Exception e) {
            //TODO have to handle
        }
    }

}
