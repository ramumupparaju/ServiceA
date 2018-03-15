package com.incon.service.ui.status.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.updatestatus.Status;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.EditTimeCallback;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.TimeSlotAlertDialogCallback;
import com.incon.service.custom.view.EditTimeDialog;
import com.incon.service.custom.view.TimeSlotAlertDialog;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.BaseNCRPOptionFragment;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.ui.status.adapter.NewRequestsAdapter;
import com.incon.service.utils.DateUtils;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.incon.service.AppConstants.StatusConstants.ACCEPT;
import static com.incon.service.AppConstants.StatusConstants.ATTENDING;
import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */
public class NewRequestsFragment extends BaseNCRPOptionFragment implements ServiceCenterContract.View {
    private View rootView;

    private EditTimeDialog editTimeDialog;
    private TimeSlotAlertDialog timeSlotAlertDialog;


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

    private void initViews() {
        serviceRequest = new ServiceRequest();
        serviceRequest.setStatus(AppUtils.ServiceRequestTypes.NEW.name());
        newRequestsAdapter = new NewRequestsAdapter();
        newRequestsAdapter.setClickCallback(iClickCallback);
        newRequestBinding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        newRequestBinding.requestRecyclerview.setAdapter(newRequestsAdapter);
        newRequestBinding.requestRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void dismissSwipeRefresh() {
        super.dismissSwipeRefresh();
        if (newRequestBinding.swiperefresh.isRefreshing()) {
            newRequestBinding.swiperefresh.setRefreshing(false);
        }
    }

    @Override
    public void loadBottomSheet() {
        super.loadBottomSheet();
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                newRequestsAdapter.clearSelection();

            }
        });
    }

    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            newRequestsAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = newRequestsAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
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
        bottomSheetPurchasedBinding.secondRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.secondRowLine.setVisibility(View.GONE);
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

            FetchNewRequestResponse itemFromPosition = newRequestsAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.firstRow, tag);

            if (tag == R.id.CUSTOMER) {

                tagsArray.add(R.id.CUSTOMER_CALL_CUSTOMER_CARE);
                textArray.add(getString(R.string.bottom_option_call_customer_care));
                drawablesArray.add(R.drawable.ic_option_call);

                tagsArray.add(R.id.CUSTOMER_LOCATION);
                textArray.add(getString(R.string.bottom_option_location));
                drawablesArray.add(R.drawable.ic_option_location);


            } else if (tag == R.id.PRODUCT) {

                tagsArray.add(R.id.PRODUCT_WARRANTY_DETAILS);
                textArray.add(getString(R.string.bottom_option_warranty_details));
                drawablesArray.add(R.drawable.ic_option_warranty);

                tagsArray.add(R.id.PRODUCT_PAST_HISTORY);
                textArray.add(getString(R.string.bottom_option_past_history));
                drawablesArray.add(R.drawable.ic_option_pasthistory);

                //TODO have to check
            } else if (tag == R.id.SERVICE_CENTER) {

                tagsArray.add(R.id.SERVICE_CENTER_CALL);
                textArray.add(getString(R.string.bottom_option_Call));
                drawablesArray.add(R.drawable.ic_option_call);

            } else if (tag == R.id.STATUS_UPDATE) {

                int status = itemFromPosition.getRequest().getStatus();
                if (status == StatusConstants.COMPLAINT) {
                    textArray.add(getString(R.string.bottom_option_accept));
                    tagsArray.add(R.id.STATUS_UPDATE_ACCEPT);
                    drawablesArray.add(R.drawable.ic_option_accept_request);
                } else if (status != StatusConstants.COMPLAINT) {
                    textArray.add(getString(R.string.bottom_option_assign));
                    tagsArray.add(R.id.STATUS_UPDATE_ASSIGN);
                    drawablesArray.add(R.drawable.ic_option_accept_request);

                    textArray.add(getString(R.string.bottom_option_attending));
                    tagsArray.add(R.id.STATUS_UPDATE_ATTENDING);
                    drawablesArray.add(R.drawable.ic_option_accept_request);
                }


                textArray.add(getString(R.string.bottom_option_reject));
                tagsArray.add(R.id.STATUS_UPDATE_REJECT);
                drawablesArray.add(R.drawable.ic_option_accept_request);


                textArray.add(getString(R.string.bottom_option_hold));
                tagsArray.add(R.id.STATUS_UPDATE_HOLD);
                drawablesArray.add(R.drawable.ic_option_hold);


                textArray.add(getString(R.string.bottom_option_terminate));
                tagsArray.add(R.id.STATUS_UPDATE_TERMINATE);
                drawablesArray.add(R.drawable.ic_option_hold);


                textArray.add(getString(R.string.bottom_option_move_to));
                tagsArray.add(R.id.STATUS_UPDATE_MOVE_TO);
                drawablesArray.add(R.drawable.ic_option_hold);


                textArray.add(getString(R.string.bottom_option_edit));
                tagsArray.add(R.id.STATUS_UPDATE_EDIT_TIME);
                drawablesArray.add(R.drawable.ic_option_hold);

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
            // String[] tagArray = unparsedTag.split(COMMA_SEPARATOR);

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

            } else if (tag == R.id.PRODUCT_WARRANTY_DETAILS) {
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));


            } else if (tag == R.id.PRODUCT_PAST_HISTORY) {
                showPastHisoryDialog();
                return;

            } else if (tag == R.id.SERVICE_CENTER_CALL) {
                callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                return;

            } else if (tag == R.id.STATUS_UPDATE_ACCEPT) {
                doAcceptApi();
            } else if (tag == R.id.STATUS_UPDATE_ASSIGN) {
                fetchAssignDialogData();
            } else if (tag == R.id.STATUS_UPDATE_ATTENDING) {
                showAttendingDialog();

            } else if (tag == R.id.STATUS_UPDATE_REJECT) {
                showUpdateStatusDialog(R.id.STATUS_UPDATE_REJECT);

            } else if (tag == R.id.STATUS_UPDATE_HOLD) {
                showUpdateStatusDialog(R.id.STATUS_UPDATE_HOLD);

            } else if (tag == R.id.STATUS_UPDATE_TERMINATE) {
                showUpdateStatusDialog(R.id.STATUS_UPDATE_TERMINATE);

            } else if (tag == R.id.STATUS_UPDATE_MOVE_TO) {
                showMoveToDialog();

            } else if (tag == R.id.STATUS_UPDATE_EDIT_TIME) {
                showEditTimeDialog();
            }

            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRowLine.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.thirdRow.removeAllViews();
            bottomSheetPurchasedBinding.thirdRow.setWeightSum(tagsArray.size());
            setBottomViewOptions(bottomSheetPurchasedBinding.thirdRow, textArray, drawablesArray, tagsArray, bottomSheetThirdRowClickListener);
        }
    };


    private void showEditTimeDialog() {
        editTimeDialog = new EditTimeDialog.AlertDialogBuilder(getContext(), new EditTimeCallback() {
            @Override
            public void alertDialogCallback(byte dialogStatus) {

                switch (dialogStatus) {
                    case AlertDialogCallback.OK:
                        //TODO have to make api call
                        break;
                    case AlertDialogCallback.CANCEL:
                        editTimeDialog.dismiss();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void dateClicked(String date) {
                showDatePicker(date);

            }

            @Override
            public void timeClicked() {
                showTimePicker();

            }
        }).build();
        editTimeDialog.showDialog();

    }

    private void showTimePicker() {
        timeSlotAlertDialog = new TimeSlotAlertDialog.AlertDialogBuilder(getContext(), new TimeSlotAlertDialogCallback() {
            @Override
            public void selectedTimeSlot(String timeSlot) {
                editTimeDialog.setTimeFromPicker(timeSlot);
            }

            @Override
            public void alertDialogCallback(byte dialogStatus) {
                timeSlotAlertDialog.dismiss();

            }
        }).build();
        timeSlotAlertDialog.showDialog();
    }

    private void showDatePicker(String date) {
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
                datePickerListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        datePicker.setCancelable(false);
        datePicker.show();

    }

    // date Listener
    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                // when dialog box is closed, below method will be called.
                public void onDateSet(DatePicker view, int selectedYear,
                                      int selectedMonth, int selectedDay) {
                    Calendar selectedDateTime = Calendar.getInstance();
                    selectedDateTime.set(selectedYear, selectedMonth, selectedDay);

                    String dobInDD_MM_YYYY = DateUtils.convertDateToOtherFormat(
                            selectedDateTime.getTime(), DateFormatterConstants.DD_MM_YYYY);
                    editTimeDialog.setDateFromPicker(dobInDD_MM_YYYY);
                }
            };

    private View.OnClickListener bottomSheetThirdRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();
            FetchNewRequestResponse itemFromPosition = newRequestsAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.thirdRow, tag);


        }

    };

    private void showAttendingDialog() {
        FetchNewRequestResponse itemFromPosition = newRequestsAdapter.getItemFromPosition(productSelectedPosition);

        UpDateStatus upDateStatus = new UpDateStatus();
        if (itemFromPosition.getRequest().getStatus() == StatusConstants.ACCEPT) {
            upDateStatus.setAssignedTo(userId == -1 ? SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE) : userId);
        }
        upDateStatus.setStatus(new Status(ATTENDING));
        upDateStatus.setRequestid(itemFromPosition.getRequest().getId());
        serviceCenterPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
    }


    private void doAcceptApi() {
        UpDateStatus upDateStatus = new UpDateStatus();
        upDateStatus.setStatus(new Status(ACCEPT));
        upDateStatus.setRequestid(newRequestsAdapter.getItemFromPosition(productSelectedPosition).getRequest().getId());
        serviceCenterPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
    }

    private void showLocationDialog() {
        FetchNewRequestResponse itemFromPosition = newRequestsAdapter.getItemFromPosition(
                productSelectedPosition);

        if (TextUtils.isEmpty(itemFromPosition.getCustomer().getLocation())) {
            AppUtils.shortToast(getActivity(), getString(R.string.error_location));
            return;
        }

        Intent addressIntent = new Intent(getActivity(), RegistrationMapActivity.class);
        addressIntent.putExtra(IntentConstants.LOCATION_COMMA, itemFromPosition.getCustomer().getLocation());
        addressIntent.putExtra(IntentConstants.ADDRESS_COMMA, itemFromPosition.getServiceCenter().getAddress());
        startActivity(addressIntent);
    }


    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO have to do filter list
    }


    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersList) {

        if (usersList == null) {
            usersList = new ArrayList<>();
        }

        if (usersList.size() == 0) {
            showErrorMessage(getString(R.string.add_service_engineer_to_assign));
            return;
        }
        showAssignDialog(usersList);
    }

    @Override
    public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {
        dismissDialog(assignDialog);
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
    public void onDestroy() {
        super.onDestroy();
        serviceCenterPresenter.disposeAll();
    }
}
