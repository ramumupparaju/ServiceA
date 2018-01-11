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
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.callbacks.EstimationDialogCallback;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.PassHistoryCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppAlertDialog;
import com.incon.service.custom.view.AppEditTextDialog;
import com.incon.service.custom.view.EstimationDialog;
import com.incon.service.custom.view.PastHistoryDialog;
import com.incon.service.custom.view.UpdateStatusDialog;
import com.incon.service.databinding.FragmentCheckupBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.CheckUpAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;
import com.incon.service.utils.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.incon.service.AppConstants.StatusConstants.APPROVAL;
import static com.incon.service.AppConstants.StatusConstants.REPAIR;
import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */
public class CheckUpFragment extends BaseTabFragment implements CheckUpContract.View {
    private FragmentCheckupBinding binding;
    private View rootView;
    private EstimationDialogCallback estimationDialogCallback;
    private EstimationDialog estimationDialog;
    private CheckUpAdapter checkUpAdapter;
    private CheckUpPresenter checkUpPresenter;
    private List<FetchNewRequestResponse> fetchNewRequestResponses;
    private AppAlertDialog detailsDialog;
    private AppEditTextDialog noteDialog;
    private AppEditTextDialog closeDialog;
    private PastHistoryDialog pastHistoryDialog;
    private UpdateStatusDialog assignOptionDialog;
    private int serviceCenterId = DEFAULT_VALUE;
    private int userId = DEFAULT_VALUE;
    private List<AddUser> usersList;

    @Override
    protected void initializePresenter() {
        checkUpPresenter = new CheckUpPresenter();
        checkUpPresenter.setView(this);
        setBasePresenter(checkUpPresenter);
    }

    @Override
    public void setTitle() {
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkup,
                    container, false);
            initViews();
            loadBottomSheet();
            rootView = binding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        checkUpAdapter = new CheckUpAdapter();
        checkUpAdapter.setClickCallback(iClickCallback);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.checkupRecyclerview.setAdapter(checkUpAdapter);
        binding.checkupRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void doRefresh(boolean isForceRefresh) {
        HomeActivity activity = (HomeActivity) getActivity();
        int tempServiceCenterId = activity.getServiceCenterId();
        int tempUserId = activity.getUserId();

        if (serviceCenterId == tempServiceCenterId && tempUserId == userId) {
            //no changes have made, so no need to make api call checks whether pull to refresh or // not
            if (!isForceRefresh)
                return;
        } else {
            serviceCenterId = tempServiceCenterId;
            userId = tempUserId;
        }
        checkUpPresenter.fetchCheckUpServiceRequests(serviceCenterId, userId);
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
            checkUpAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = checkUpAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            checkUpAdapter.notifyDataSetChanged();
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
        bottomDrawables[3] = R.drawable.ic_option_delete;

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
            if (tag == 0) {  // customer
                bottomOptions = new String[1];
                bottomOptions[0] = getString(R.string.bottom_option_call_customer_care);
                topDrawables = new int[1];
                topDrawables[0] = R.drawable.ic_option_call;

            } else if (tag == 1) { // product
                bottomOptions = new String[2];
                bottomOptions[0] = getString(R.string.bottom_option_warranty_details);
                bottomOptions[1] = getString(R.string.bottom_option_past_history);
                topDrawables = new int[2];
                topDrawables[0] = R.drawable.ic_options_features;
                topDrawables[1] = R.drawable.ic_option_pasthistory;
            } else if (tag == 2) { // service center
                bottomOptions = new String[1];
                bottomOptions[0] = getString(R.string.bottom_option_Call);
                topDrawables = new int[1];
                topDrawables[0] = R.drawable.ic_option_call;
            } else { // status update
                bottomOptions = new String[5];
                bottomOptions[0] = getString(R.string.bottom_option_estimation);
                bottomOptions[1] = getString(R.string.bottom_option_hold);
                bottomOptions[2] = getString(R.string.bottom_option_terminate);
                bottomOptions[3] = getString(R.string.bottom_option_move_to);
                bottomOptions[4] = getString(R.string.bottom_option_assign);
                topDrawables = new int[5];
                topDrawables[0] = R.drawable.ic_option_accept_request;
                topDrawables[1] = R.drawable.ic_option_accept_request;
                topDrawables[2] = R.drawable.ic_option_close;
                topDrawables[3] = R.drawable.ic_option_assign;
                topDrawables[4] = R.drawable.ic_option_assign;
            }

            bottomSheetPurchasedBinding.secondRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.secondRow.removeAllViews();
            bottomSheetPurchasedBinding.secondRow.setWeightSum(bottomOptions.length);
            setBottomViewOptions(bottomSheetPurchasedBinding.secondRow, bottomOptions, topDrawables, bottomSheetSecondRowClickListener, unparsedTag);
        }
    };


    private View.OnClickListener bottomSheetSecondRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String unparsedTag = (String) view.getTag();
            String[] tagArray = unparsedTag.split(COMMA_SEPARATOR);

            FetchNewRequestResponse itemFromPosition = checkUpAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.secondRow, unparsedTag);

            String[] bottomOptions = new String[0];
            int[] topDrawables = new int[0];

            int firstRowTag = Integer.parseInt(tagArray[0]);
            int secondRowTag = Integer.parseInt(tagArray[1]);

            // customer
            if (firstRowTag == 0) {

                //call customer care
                if (secondRowTag == 0) {
                    callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                    return;
                }
            } else if (firstRowTag == 1) { // product

                if (secondRowTag == 0) {  // warrenty details
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
                } else if (secondRowTag == 1) { // history
                    showPastHisoryDialog();
                }
            } else if (firstRowTag == 2) { // service center
                if (secondRowTag == 0) {
                    // call
                    callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                }

            } else if (firstRowTag == 3) {  //status update
                if (secondRowTag == 0) {  // estimation
                    showEstimationDialog();
                } else if (secondRowTag == 1) { // hold
                    showHoldDialog();

                } else if (secondRowTag == 2) { // terminate

                    showTerminateDialog();

                }
                else if (secondRowTag == 3) { // move to

                    showMoveToDialog();

                }

                else {  // assign
                    // showAssignDialog();
                    AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                }
            }
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRowLine.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.thirdRow.removeAllViews();
            bottomSheetPurchasedBinding.thirdRow.setWeightSum(bottomOptions.length);
            setBottomViewOptions(bottomSheetPurchasedBinding.thirdRow, bottomOptions, topDrawables, bottomSheetThirdRowClickListener, unparsedTag);
        }
    };

    private void showMoveToDialog() {

    }

    private void showTerminateDialog() {
    }

    private void showHoldDialog() {

    }

    private void showEstimationDialog() {

        estimationDialog = new EstimationDialog.AlertDialogBuilder(getContext(), new EstimationDialogCallback() {

            @Override
            public void dateClicked(String date) {
                showDatePickerToEstimate(date);
            }

            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {
                FetchNewRequestResponse requestResponse = checkUpAdapter.getItemFromPosition(productSelectedPosition);
                Request request = requestResponse.getRequest();
                upDateStatus.setRequestid(request.getId());
                checkUpPresenter.upDateStatus(userId, upDateStatus);
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


    private void showAssignDialog() {
        assignOptionDialog = new UpdateStatusDialog.AlertDialogBuilder(getContext(), new AssignOptionCallback() {

            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {

            }

            @Override
            public void enteredText(String comment) {

            }

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
        }).title(getString(R.string.option_assign))
                .build();
        assignOptionDialog.showDialog();
        assignOptionDialog.setCancelable(true);

    }

    private void showCloseDialog() {
        closeDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
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
                                closeDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_close))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        closeDialog.showDialog();
    }

    private void showNoteDialog() {
        noteDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
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
                                noteDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_note))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        noteDialog.showDialog();

    }

    private void showLocationDialog() {

        FetchNewRequestResponse itemFromPosition = checkUpAdapter.getItemFromPosition(
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

    private View.OnClickListener bottomSheetThirdRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String unparsedTag = (String) view.getTag();
            String[] tagArray = unparsedTag.split(COMMA_SEPARATOR);


            FetchNewRequestResponse itemFromPosition = checkUpAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.thirdRow, unparsedTag);

            int firstRowTag = Integer.parseInt(tagArray[0]);
            int secondRowTag = Integer.parseInt(tagArray[1]);
            int thirdRowTag = Integer.parseInt(tagArray[2]);


            if (firstRowTag == 3) {

                if (secondRowTag == 0) {

                    if (thirdRowTag == 0) {
                        // TODO have set images
                    } else if (thirdRowTag == 1) {
                        // TODO have set images
                    }
                } else if (secondRowTag == 1) {
                    //show diloge
                } else if (secondRowTag == 2) {
                    // TODO have set images
                }

            }


        }

    };


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    checkUpAdapter.clearData();
                    doRefresh(true);

                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        checkUpPresenter.disposeAll();
    }


    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO have to implement search
    }


    @Override
    public void loadingCheckUpRequests(List<FetchNewRequestResponse> fetchNewRequestResponsesList) {
        if (fetchNewRequestResponsesList == null) {
            fetchNewRequestResponsesList = new ArrayList<>();
        }
        if (fetchNewRequestResponsesList.size() == 0) {
            binding.checkupTextview.setVisibility(View.VISIBLE);
            dismissSwipeRefresh();
        } else {
            binding.checkupTextview.setVisibility(View.GONE);
            checkUpAdapter.setData(fetchNewRequestResponsesList);
            dismissSwipeRefresh();
        }

    }

    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersList) {
        if (usersList == null) {
            usersList = new ArrayList<>();
        }

        this.usersList = usersList;

    }

    @Override
    public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {

        if (estimationDialog != null && estimationDialog.isShowing()) {
            estimationDialog.dismiss();
        }

        Integer statusId = upDateStatusResponse.getStatus().getId();
        if (statusId == REPAIR || statusId == APPROVAL) {
            doRefresh(true);
        }
    }


}
