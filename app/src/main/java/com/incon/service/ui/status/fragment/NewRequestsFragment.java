package com.incon.service.ui.status.fragment;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.login.ServiceCenterResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.callbacks.EditTimeCallback;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.PassHistoryCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.callbacks.TimeSlotAlertDialogCallback;
import com.incon.service.custom.view.AppAlertDialog;
import com.incon.service.custom.view.AppEditTextDialog;
import com.incon.service.custom.view.AssignOptionDialog;
import com.incon.service.custom.view.EditTimeDialog;
import com.incon.service.custom.view.PastHistoryDialog;
import com.incon.service.custom.view.TimeSlotAlertDialog;
import com.incon.service.databinding.FragmentNewrequestBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.ui.status.adapter.NewRequestsAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;
import com.incon.service.utils.DateUtils;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */

public class NewRequestsFragment extends BaseTabFragment implements NewRequestContract.View {
    private FragmentNewrequestBinding binding;
    private View rootView;
    private NewRequestPresenter newRequestPresenter;
    private NewRequestsAdapter newRequestsAdapter;

    private AppAlertDialog detailsDialog;
    private AppEditTextDialog acceptRejectDialog;
    private AppEditTextDialog attendingDialog;
    private AssignOptionDialog assignOptionDialog;
    private EditTimeDialog editTimeDialog;
    private TimeSlotAlertDialog timeSlotAlertDialog;
    private AppEditTextDialog holdDialog;
    private String merchantComment;
    private PastHistoryDialog pastHistoryDialog;
    private int serviceCenterId;
    private ArrayList<ServiceCenterResponse> serviceCenterResponseList;

    @Override
    protected void initializePresenter() {
        newRequestPresenter = new NewRequestPresenter();
        newRequestPresenter.setView(this);
        setBasePresenter(newRequestPresenter);
    }

    @Override
    public void setTitle() {
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_newrequest,
                    container, false);
            initViews();
            loadBottomSheet();
            rootView = binding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        newRequestsAdapter = new NewRequestsAdapter();
        newRequestsAdapter.setClickCallback(iClickCallback);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.requestRecyclerview.setAdapter(newRequestsAdapter);
        binding.requestRecyclerview.setLayoutManager(linearLayoutManager);
        serviceCenterId = SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.SERVICE_CENTER_ID, DEFAULT_VALUE);
        newRequestPresenter.fetchNewServiceRequests(serviceCenterId);
    }

    private void dismissSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.setRefreshing(false);
        }
    }

    @Override
    public void loadBottomSheet() {
        super.loadBottomSheet();
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
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
            newRequestsAdapter.notifyDataSetChanged();
            productSelectedPosition = position;
            createBottomSheetFirstRow(position);
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetFirstRow(int position) {
        int length;
        int[] bottomDrawables;
        String[] bottomNames;
        length = 4;
        bottomNames = new String[4];
        bottomNames[0] = getString(R.string.bottom_option_customer);
        bottomNames[1] = getString(R.string.bottom_option_product);
        bottomNames[2] = getString(R.string.bottom_option_service_center);
        bottomNames[3] = getString(R.string.bottom_option_status_update);

        bottomDrawables = new int[4];
        bottomDrawables[0] = R.drawable.ic_option_customer;
        bottomDrawables[1] = R.drawable.ic_option_product;
        bottomDrawables[2] = R.drawable.ic_option_find_service_center;
        bottomDrawables[3] = R.drawable.ic_option_service_support;

        bottomSheetPurchasedBinding.firstRow.setVisibility(View.VISIBLE);
        bottomSheetPurchasedBinding.secondRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.firstRow.removeAllViews();
        bottomSheetPurchasedBinding.firstRow.setWeightSum(length);
        setBottomViewOptions(bottomSheetPurchasedBinding.firstRow, bottomNames, bottomDrawables, bottomSheetFirstRowClickListener, "-1");

    }

    // bottom sheet click event
    private View.OnClickListener bottomSheetFirstRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String unparsedTag = (String) view.getTag();
            Integer tag = Integer.valueOf(unparsedTag);
            String[] bottomOptions;
            int[] topDrawables;
            changeSelectedViews(bottomSheetPurchasedBinding.firstRow, unparsedTag);
            if (tag == 0) { // customer
                bottomOptions = new String[2];
                bottomOptions[0] = getString(R.string.bottom_option_call_customer_care);
                bottomOptions[1] = getString(R.string.bottom_option_location);
                topDrawables = new int[2];
                topDrawables[0] = R.drawable.ic_option_call;
                topDrawables[1] = R.drawable.ic_option_location;

            } else if (tag == 1) { // product
                bottomOptions = new String[2];
                bottomOptions[0] = getString(R.string.bottom_option_warranty_details);
                bottomOptions[1] = getString(R.string.bottom_option_past_history);
                topDrawables = new int[2];
                topDrawables[0] = R.drawable.ic_options_features;
                topDrawables[1] = R.drawable.ic_option_pasthistory;
            } else if (tag == 2) {  // service center
                bottomOptions = new String[1];
                bottomOptions[0] = getString(R.string.bottom_option_Call);
                topDrawables = new int[1];
                topDrawables[0] = R.drawable.ic_option_call;
            } else { // status update
                bottomOptions = new String[4];
                bottomOptions[0] = getString(R.string.bottom_option_accept);
                bottomOptions[1] = getString(R.string.bottom_option_reject);
                bottomOptions[2] = getString(R.string.bottom_option_hold);
                bottomOptions[3] = getString(R.string.bottom_option_edit);
                topDrawables = new int[4];
                topDrawables[0] = R.drawable.ic_option_accept_request;
                topDrawables[1] = R.drawable.ic_option_accept_request;
                topDrawables[2] = R.drawable.ic_option_hold;
                topDrawables[3] = R.drawable.ic_option_hold;
            }

            bottomSheetPurchasedBinding.secondRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.secondRow.removeAllViews();
            bottomSheetPurchasedBinding.secondRow.setWeightSum(bottomOptions.length);
            setBottomViewOptions(bottomSheetPurchasedBinding.secondRow, bottomOptions, topDrawables, bottomSheetSecondRowClickListener, unparsedTag);
        }
    };


    // bottom sheet top view click event
    private View.OnClickListener bottomSheetSecondRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String unparsedTag = (String) view.getTag();
            String[] tagArray = unparsedTag.split(COMMA_SEPARATOR);

            FetchNewRequestResponse itemFromPosition = newRequestsAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.secondRow, unparsedTag);

            String[] bottomOptions = new String[0];
            int[] topDrawables = new int[0];

            int firstRowTag = Integer.parseInt(tagArray[0]);
            int secondRowTag = Integer.parseInt(tagArray[1]);


            if (firstRowTag == 0) { // customer

                if (secondRowTag == 0) {  //call customer care

                    callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                    return;
                } else if (secondRowTag == 1) { // location
                    showLocationDialog();
                    return;
                }

            } else if (firstRowTag == 1) { // product

                if (secondRowTag == 0) { // warranty details
                    AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                    // TODO have to get details from back end
                    /*String purchasedDate = DateUtils.convertMillisToStringFormat(
                            itemFromPosition.getPurchasedDate(), DateFormatterConstants.DD_MM_YYYY);
                    String warrantyEndDate = DateUtils.convertMillisToStringFormat(
                            itemFromPosition.getWarrantyEndDate(), DateFormatterConstants.DD_MM_YYYY);
                    long noOfDays = DateUtils.convertDifferenceDateIndays(
                            itemFromPosition.getWarrantyEndDate(), System.currentTimeMillis());
                    String warrantyConditions = itemFromPosition.getWarrantyConditions();
                    showInformationDialog(getString(
                            R.string.bottom_option_warranty), getString(
                            R.string.purchased_warranty_status_now)
                            + noOfDays + " Days Left "
                            + "\n"
                            + getString(
                            R.string.purchased_purchased_date)
                            + purchasedDate
                            + "\n"
                            + getString(
                            R.string.purchased_warranty_covers_date)
                            + warrantyConditions
                            + "\n"
                            + getString(
                            R.string.purchased_warranty_ends_on) + warrantyEndDate);
                    return;
*/
                } else if (secondRowTag == 1) { // past history
                    showPastHisoryDialog();
                }
            } else if (firstRowTag == 2) { // service center
                if (secondRowTag == 0) { // cal
                    callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                }
            } else if (firstRowTag == 3) {   // status update
                if (secondRowTag == 0) {  // accept
                    bottomOptions = new String[2];
                    bottomOptions[0] = getString(R.string.bottom_option_assign);
                    bottomOptions[1] = getString(R.string.bottom_option_attending);
                    topDrawables = new int[2];
                    topDrawables[0] = R.drawable.ic_options_feedback;
                    topDrawables[1] = R.drawable.ic_option_pasthistory;

                    // showAcceptRejectDialog(true);

                } else if (secondRowTag == 1) { // reject
                    showAcceptRejectDialog(false);

                } else if (secondRowTag == 2) { // hold
                    //showHoldDialog();
                    bottomOptions = new String[1];
                    bottomOptions[0] = getString(R.string.bottom_option_assign);
                    topDrawables = new int[1];
                    topDrawables[0] = R.drawable.ic_options_feedback;
                } else {
                    //  edit time

                    showEditTimeDialog();
                }


            }
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRowLine.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.thirdRow.removeAllViews();
            bottomSheetPurchasedBinding.thirdRow.setWeightSum(bottomOptions.length);
            setBottomViewOptions(bottomSheetPurchasedBinding.thirdRow, bottomOptions, topDrawables, bottomSheetThirdRowClickListener, unparsedTag);
        }
    };

    private void showEditTimeDialog() {

        editTimeDialog = new EditTimeDialog.AlertDialogBuilder(getContext(), new EditTimeCallback() {
            @Override
            public void alertDialogCallback(byte dialogStatus) {

                switch (dialogStatus) {
                    case AlertDialogCallback.OK:
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
            String unparsedTag = (String) view.getTag();
            String[] tagArray = unparsedTag.split(COMMA_SEPARATOR);


            FetchNewRequestResponse itemFromPosition = newRequestsAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.thirdRow, unparsedTag);

            int firstRowTag = Integer.parseInt(tagArray[0]);
            int secondRowTag = Integer.parseInt(tagArray[1]);
            int thirdRowTag = Integer.parseInt(tagArray[2]);

            // status update
            if (firstRowTag == 3) {

                if (secondRowTag == 0) { // accept

                    if (thirdRowTag == 0) { // assign

                        // showAssignDialog();
                        loadAssignDialogData();

                    } else if (thirdRowTag == 1) {
                        // attending
                        showAttendingDialog();

                    }
                } else if (secondRowTag == 1) {
                    //show diloge
                } else if (secondRowTag == 2) {
                    // showAssignDialog();
                }

            }


        }

    };

    private void loadAssignDialogData() {
        loadUsersDataFromServiceCenterId(serviceCenterResponseList.get(0).getId());
    }

    private void loadUsersDataFromServiceCenterId(Integer serviceCenterId) {
        newRequestPresenter.getUsersListOfServiceCenters(serviceCenterId);
    }

    private void showPastHisoryDialog() {
        pastHistoryDialog = new PastHistoryDialog.AlertDialogBuilder(getContext(), new PassHistoryCallback() {
            @Override
            public void alertDialogCallback(byte dialogStatus) {

                switch (dialogStatus) {
                    case AlertDialogCallback.OK:

                        break;
                    case AlertDialogCallback.CANCEL:

                        break;
                    default:
                        break;
                }

            }
        }).title(getString(R.string.option_past_history))
                .build();
        pastHistoryDialog.showDialog();
        pastHistoryDialog.setCancelable(true);
    }

    private void showHoldDialog() {
        holdDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
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
                                holdDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_hold))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        holdDialog.showDialog();
    }

    private void showAcceptRejectDialog(final boolean isAccept) {
        acceptRejectDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String commentString) {
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:

                                acceptRejectDialog.dismiss();
                                break;
                            case AlertDialogCallback.CANCEL:
                                acceptRejectDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(isAccept ? getString(R.string.bottom_option_accept) : getString(
                R.string.bottom_option_reject))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        acceptRejectDialog.showDialog();
    }


    private void showAttendingDialog() {
        attendingDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String commentString) {
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:

                                attendingDialog.dismiss();
                                break;
                            case AlertDialogCallback.CANCEL:
                                attendingDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_attending))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        attendingDialog.showDialog();

    }

    private void showAssignDialog(List<AddUser> userList) {
        assignOptionDialog = new AssignOptionDialog.AlertDialogBuilder(getContext(), new AssignOptionCallback() {
            @Override
            public void getUsersListFromServiceCenterId(int serviceCenterId) {
                loadUsersDataFromServiceCenterId(serviceCenterId);
            }

            @Override
            public void alertDialogCallback(byte dialogStatus) {

                switch (dialogStatus) {
                    case AlertDialogCallback.OK:
                        break;
                    case AlertDialogCallback.CANCEL:
                        assignOptionDialog.dismiss();
                        break;
                    default:
                        break;
                }

            }
        }).title(getString(R.string.option_assign)).loadUsersList(userList).build();
        assignOptionDialog.showDialog();
        assignOptionDialog.setCancelable(true);


    }

    private void showInformationDialog(String title, String messageInfo) {
        detailsDialog = new AppAlertDialog.AlertDialogBuilder(getActivity(), new
                AlertDialogCallback() {
                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                detailsDialog.dismiss();
                                break;
                            case AlertDialogCallback.CANCEL:
                                detailsDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(title).content(messageInfo)
                .build();
        detailsDialog.showDialog();
        detailsDialog.setCancelable(true);
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

    // data re load
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    newRequestsAdapter.clearData();
                    newRequestPresenter.fetchNewServiceRequests(serviceCenterId);
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        newRequestPresenter.disposeAll();
    }


    @Override
    public void loadingNewServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponsesList) {

        if (fetchNewRequestResponsesList == null) {
            fetchNewRequestResponsesList = new ArrayList<>();
        }
        // TODO have to  remove below code
        if (fetchNewRequestResponsesList.size() == 0) {
            FetchNewRequestResponse fetchNewRequestResponse = new FetchNewRequestResponse();
            fetchNewRequestResponse.setCustomer(fetchNewRequestResponse.getCustomer());
            fetchNewRequestResponsesList.add(fetchNewRequestResponse);
        }
        if (fetchNewRequestResponsesList.size() == 0) {
            binding.requestTextview.setVisibility(View.VISIBLE);
            dismissSwipeRefresh();
        } else {
            newRequestsAdapter.setData(fetchNewRequestResponsesList);
            dismissSwipeRefresh();
        }
    }

    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO have to do filter list
    }


    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersList) {
        if (assignOptionDialog != null && assignOptionDialog.isShowing()) {
            assignOptionDialog.setUsersData(usersList);
        } else {
            showAssignDialog(usersList);
        }

    }
}
