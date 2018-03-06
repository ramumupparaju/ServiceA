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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.incon.service.AppConstants;
import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.apimodel.components.updatestatus.Status;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.callbacks.EditTimeCallback;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.MoveToOptionCallback;
import com.incon.service.callbacks.PassHistoryCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.callbacks.TimeSlotAlertDialogCallback;
import com.incon.service.custom.view.AppEditTextDialog;
import com.incon.service.custom.view.AssignDialog;
import com.incon.service.custom.view.EditTimeDialog;
import com.incon.service.custom.view.MoveToOptionDialog;
import com.incon.service.custom.view.PastHistoryDialog;
import com.incon.service.custom.view.TimeSlotAlertDialog;
import com.incon.service.databinding.FragmentNewrequestBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.BaseNCRPOptionFragment;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.NewRequestsAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;
import com.incon.service.utils.DateUtils;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.incon.service.AppConstants.StatusConstants.ACCEPT;
import static com.incon.service.AppConstants.StatusConstants.ASSIGNED;
import static com.incon.service.AppConstants.StatusConstants.ATTENDING;
import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */
public class NewRequestsFragment extends BaseNCRPOptionFragment implements ServiceCenterContract.View {
    private FragmentNewrequestBinding binding;
    private View rootView;
    private ServiceCenterPresenter newRequestPresenter;
    private NewRequestsAdapter newRequestsAdapter;
    private int serviceCenterId = DEFAULT_VALUE;
    private int userId = DEFAULT_VALUE;
    private MoveToOptionDialog moveToOptionDialog;
    private AppEditTextDialog updateStatusDialog;
    private AssignDialog assignDialog;
    private EditTimeDialog editTimeDialog;
    private TimeSlotAlertDialog timeSlotAlertDialog;
    private PastHistoryDialog pastHistoryDialog;
    private ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void initializePresenter() {
        newRequestPresenter = new ServiceCenterPresenter();
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
            rootView = binding.getRoot();
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
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.requestRecyclerview.setAdapter(newRequestsAdapter);
        binding.requestRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void doRefresh(boolean isForceRefresh) {
        dismissSwipeRefresh();
        HomeActivity activity = (HomeActivity) getActivity();
        int tempServiceCenterId = activity.getServiceCenterId();
        int tempUserId = activity.getUserId();

        if (serviceCenterId == tempServiceCenterId && tempUserId == userId) {
            //no chnages have made, so no need to make api call checks whether pull to refresh or not
            if (!isForceRefresh)
                return;
        } else {
            serviceCenterId = tempServiceCenterId;
            userId = tempUserId;
        }

        if (serviceCenterId == -1 || serviceCenterId == DEFAULT_VALUE) {
            serviceRequest.setServiceIds(null);
        } else {
            serviceRequest.setServiceIds(String.valueOf(serviceCenterId));
        }

        if (userId == -1 || userId == DEFAULT_VALUE) {
            serviceRequest.setAssignedUser(null);
        } else {
            serviceRequest.setAssignedUser(userId);
        }

        getServiceRequestApi();
        //newRequestPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_fetch_new_service_request));
    }

    private void getServiceRequestApi() {
        binding.requestRecyclerview.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();
        newRequestPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_fetch_new_service_request));

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

               /* else {

                    textArray[0] = getString(R.string.bottom_option_assign);
                    textArray[1] = getString(R.string.bottom_option_attending);
                    textArray[2] = getString(R.string.bottom_option_reject);
                    textArray[3] = getString(R.string.bottom_option_hold);
                    textArray[4] = getString(R.string.bottom_option_terminate);
                    textArray[5] = getString(R.string.bottom_option_move_to);
                    textArray[6] = getString(R.string.bottom_option_edit);

                    tagsArray = new int[length];
                    tagsArray[0] = R.id.STATUS_UPDATE_ASSIGN;
                    tagsArray[1] = R.id.STATUS_UPDATE_ATTENDING;
                    tagsArray[2] = R.id.STATUS_UPDATE_REJECT;
                    tagsArray[3] = R.id.STATUS_UPDATE_HOLD;
                    tagsArray[4] = R.id.STATUS_UPDATE_TERMINATE;
                    tagsArray[5] = R.id.STATUS_UPDATE_MOVE_TO;
                    tagsArray[6] = R.id.STATUS_UPDATE_EDIT_TIME;

                    drawablesArray = new int[length];
                    drawablesArray[0] = R.drawable.ic_option_accept_request;
                    drawablesArray[1] = R.drawable.ic_option_accept_request;
                    drawablesArray[2] = R.drawable.ic_option_accept_request;
                    drawablesArray[3] = R.drawable.ic_option_accept_request;
                    drawablesArray[4] = R.drawable.ic_option_hold;
                    drawablesArray[5] = R.drawable.ic_option_hold;
                    drawablesArray[6] = R.drawable.ic_option_hold;
                }*/
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

                // TODO have to get details from back end

                   /* String purchasedDate = DateUtils.convertMillisToStringFormat(
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
                return;*/

            } else if (tag == R.id.PRODUCT_PAST_HISTORY) {
                showPastHisoryDialog();
                return;

            } else if (tag == R.id.SERVICE_CENTER_CALL) {
                callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                return;

            } else if (tag == R.id.STATUS_UPDATE_ACCEPT) {
                // todo have to cal in accept api response
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

  /*  private void showMoveToDialog() {
        ArrayList<Status> statusList = AppUtils.getSubStatusList(StatusConstants.ACCEPT, ((HomeActivity) getActivity()).getStatusList());
        moveToOptionDialog = new MoveToOptionDialog.AlertDialogBuilder(getContext(), new MoveToOptionCallback() {
            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {
                FetchNewRequestResponse requestResponse = newRequestsAdapter.getItemFromPosition(productSelectedPosition);
                Request request = requestResponse.getRequest();
                upDateStatus.setRequestid(request.getId());
                newRequestPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
            }

            @Override
            public void alertDialogCallback(byte dialogStatus) {
                switch (dialogStatus) {
                    case AlertDialogCallback.OK:
                        break;
                    case AlertDialogCallback.CANCEL:
                        moveToOptionDialog.dismiss();
                        break;
                    default:
                        break;
                }

            }
        }).loadStatusList(statusList).build();
        moveToOptionDialog.showDialog();
        moveToOptionDialog.setCancelable(true);

    }*/

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
            Integer tag = (Integer) view.getTag();
            FetchNewRequestResponse itemFromPosition = newRequestsAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.thirdRow, tag);


        }

    };

    private void fetchAssignDialogData() {
        newRequestPresenter.getUsersListOfServiceCenters(serviceCenterId);
    }

    private void showAssignDialog(List<AddUser> userList) {
        assignDialog = new AssignDialog.AlertDialogBuilder(getContext(), new AssignOptionCallback() {
            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {
                FetchNewRequestResponse requestResponse = newRequestsAdapter.getItemFromPosition(productSelectedPosition);
                Request request = requestResponse.getRequest();
                upDateStatus.setRequestid(request.getId());
                newRequestPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
            }


            @Override
            public void alertDialogCallback(byte dialogStatus) {

                switch (dialogStatus) {
                    case AlertDialogCallback.OK:
                        break;
                    case AlertDialogCallback.CANCEL:
                        assignDialog.dismiss();
                        break;
                    default:
                        break;
                }

            }
        }).title(getString(R.string.option_assign)).loadUsersList(userList).statusId(ASSIGNED).build();
        assignDialog.showDialog();
        assignDialog.setCancelable(true);
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

    private void showUpdateStatusDialog(final int dialogType) {

        final UpDateStatus upDateStatus = new UpDateStatus();
        upDateStatus.setRequestid(newRequestsAdapter.getItemFromPosition(productSelectedPosition).getRequest().getId());
        String dialogTitle = "";
        if (dialogType == R.id.STATUS_UPDATE_REJECT) {
            dialogTitle = getString(R.string.bottom_option_reject);
            upDateStatus.setStatus(new Status(StatusConstants.REJECT));
        } else if (dialogType == R.id.STATUS_UPDATE_HOLD) {
            dialogTitle = getString(R.string.bottom_option_hold);
            upDateStatus.setStatus(new Status(StatusConstants.NEW_REQ_HOLD));
        } else if (dialogType == R.id.STATUS_UPDATE_TERMINATE) {
            dialogTitle = getString(R.string.bottom_option_hold);
            upDateStatus.setStatus(new Status(StatusConstants.TERMINATE));
        }
        updateStatusDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String commentString) {
                        upDateStatus.setComments(commentString);
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                newRequestPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, AppConstants.DEFAULT_VALUE), upDateStatus);
                                break;
                            case AlertDialogCallback.CANCEL:
                                updateStatusDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(dialogTitle)
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        updateStatusDialog.showDialog();
        updateStatusDialog.setCancelable(true);
    }


    private void showAttendingDialog() {
        UpDateStatus upDateStatus = new UpDateStatus();
        upDateStatus.setStatus(new Status(ATTENDING));
        upDateStatus.setRequestid(newRequestsAdapter.getItemFromPosition(productSelectedPosition).getRequest().getId());
        newRequestPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
    }


    private void doAcceptApi() {
        UpDateStatus upDateStatus = new UpDateStatus();
        upDateStatus.setStatus(new Status(ACCEPT));
        upDateStatus.setRequestid(newRequestsAdapter.getItemFromPosition(productSelectedPosition).getRequest().getId());
        newRequestPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
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
                    doRefresh(true);
                }
            };


    @Override
    public void loadingNewServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponsesList) {

        if (fetchNewRequestResponsesList == null) {
            fetchNewRequestResponsesList = new ArrayList<>();
        }

        if (fetchNewRequestResponsesList.size() == 0) {
            binding.requestTextview.setVisibility(View.VISIBLE);
            binding.requestRecyclerview.setVisibility(View.GONE);
        } else {
            binding.requestTextview.setVisibility(View.GONE);
            binding.requestRecyclerview.setVisibility(View.VISIBLE);
            newRequestsAdapter.setData(fetchNewRequestResponsesList);

            shimmerFrameLayout.stopShimmerAnimation();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
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

        showAssignDialog(usersList);
    }

    @Override
    public void loadUpDateStatus(UpDateStatusResponse upDateStatusResponse) {
        dismissDialog(assignDialog);
        dismissDialog(updateStatusDialog);

        doRefresh(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newRequestPresenter.disposeAll();
    }
}
