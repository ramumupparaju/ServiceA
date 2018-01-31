package com.incon.service.ui.status.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.apimodel.components.updatestatus.Status;
import com.incon.service.apimodel.components.updatestatus.UpDateStatusResponse;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.MoveToOptionCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppAlertDialog;
import com.incon.service.custom.view.AppEditTextDialog;
import com.incon.service.custom.view.AssignDialog;
import com.incon.service.custom.view.MoveToOptionDialog;
import com.incon.service.databinding.FragmentRepairBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.RegistrationMapActivity;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.RepairAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppConstants.StatusConstants.ASSIGNED;
import static com.incon.service.AppConstants.StatusConstants.ATTENDING;
import static com.incon.service.AppUtils.callPhoneNumber;

/**
 * Created by PC on 12/5/2017.
 */

public class RepairFragment extends BaseTabFragment implements ServiceCenterContract.View {
    private FragmentRepairBinding binding;
    private View rootView;
    private ServiceCenterPresenter repairPresenter;
    private RepairAdapter repairAdapter;
    private int serviceCenterId = DEFAULT_VALUE;
    private int userId = DEFAULT_VALUE;
    private MoveToOptionDialog moveToOptionDialog;
    private AppEditTextDialog terminateDialog;
    private AppEditTextDialog holdDialog;

    private AppAlertDialog detailsDialog;
    private AssignDialog statusDialog;
    private AppEditTextDialog closeDialog;
    private AppEditTextDialog repairDialog;

    @Override
    protected void initializePresenter() {
        repairPresenter = new ServiceCenterPresenter();
        repairPresenter.setView(this);
        setBasePresenter(repairPresenter);
    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_repair,
                    container, false);

            initViews();
            loadBottomSheet();

            rootView = binding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        serviceRequest = new ServiceRequest();
        serviceRequest.setStatus(AppUtils.ServiceRequestTypes.REPAIR.name());

        repairAdapter = new RepairAdapter();
        repairAdapter.setClickCallback(iClickCallback);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.requestRecyclerview.setAdapter(repairAdapter);
        binding.requestRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void doRefresh(boolean isForceRefresh) {
        HomeActivity activity = (HomeActivity) getActivity();
        int tempServiceCenterId = activity.getServiceCenterId();
        int tempUserId = activity.getUserId();

        if (serviceCenterId == tempServiceCenterId && tempUserId == userId) {
            //no chnages have made, so no need to make api call checks whether pull to refresh or
            // not

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

        serviceRequest.setFromDate(fromDate);
        serviceRequest.setToDate(toDate);
        repairPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_fetch_new_service_request));
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
            repairAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = repairAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            repairAdapter.notifyDataSetChanged();
            productSelectedPosition = position;
            createBottomSheetFirstRow();
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetFirstRow() {
        int length;
        int[] drawablesArray;
        String[] textArray;
        length = 4;
        int[] tagsArray;
        textArray = new String[length];
        textArray[0] = getString(R.string.bottom_option_customer);
        textArray[1] = getString(R.string.bottom_option_product);
        textArray[2] = getString(R.string.bottom_option_service_center);
        textArray[3] = getString(R.string.bottom_option_status_update);

        tagsArray = new int[length];
        tagsArray[0] = R.id.CUSTOMER;
        tagsArray[1] = R.id.PRODUCT;
        tagsArray[2] = R.id.SERVICE_CENTER;
        tagsArray[3] = R.id.STATUS_UPDATE;

        drawablesArray = new int[length];
        drawablesArray[0] = R.drawable.ic_option_customer;
        drawablesArray[1] = R.drawable.ic_option_product;
        drawablesArray[2] = R.drawable.ic_option_find_service_center;
        drawablesArray[3] = R.drawable.ic_option_delete;

        bottomSheetPurchasedBinding.firstRow.setVisibility(View.VISIBLE);
        bottomSheetPurchasedBinding.secondRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
        bottomSheetPurchasedBinding.firstRow.removeAllViews();
        bottomSheetPurchasedBinding.firstRow.setWeightSum(length);
        setBottomViewOptions(bottomSheetPurchasedBinding.firstRow, textArray, drawablesArray, tagsArray, bottomSheetFirstRowClickListener);

    }

    // bottom sheet click event
    private View.OnClickListener bottomSheetFirstRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();
            String[] textArray = new String[0];
            int[] drawablesArray = new int[0];
            int[] tagsArray = new int[0];

            changeSelectedViews(bottomSheetPurchasedBinding.firstRow, tag);


            if (tag == R.id.CUSTOMER) {
                int length = 1;
                textArray = new String[length];
                textArray[0] = getString(R.string.bottom_option_call_customer_care);

                tagsArray = new int[length];
                tagsArray[0] = R.id.CUSTOMER_CALL_CUSTOMER_CARE;

                drawablesArray = new int[length];
                drawablesArray[0] = R.drawable.ic_option_call;
            } else if (tag == R.id.PRODUCT) {
                int length = 2;
                textArray = new String[length];
                textArray[0] = getString(R.string.bottom_option_warranty_details);
                textArray[1] = getString(R.string.bottom_option_past_history);

                tagsArray = new int[length];
                tagsArray[0] = R.id.PRODUCT_WARRANTY_DETAILS;
                tagsArray[1] = R.id.PRODUCT_PAST_HISTORY;

                drawablesArray = new int[length];
                drawablesArray[0] = R.drawable.ic_option_warranty;
                drawablesArray[1] = R.drawable.ic_option_pasthistory;
            } else if (tag == R.id.SERVICE_CENTER) {
                int length = 1;
                textArray = new String[length];
                textArray[0] = getString(R.string.bottom_option_Call);

                tagsArray = new int[length];
                tagsArray[0] = R.id.SERVICE_CENTER_CALL;

                drawablesArray = new int[length];
                drawablesArray[0] = R.drawable.ic_option_call;
            } else if (tag == R.id.STATUS_UPDATE) {
                int length = 6;
                textArray = new String[length];
                textArray[0] = getString(R.string.bottom_option_repair_done);
                textArray[1] = getString(R.string.bottom_option_hold);
                textArray[2] = getString(R.string.bottom_option_terminate);
                textArray[3] = getString(R.string.bottom_option_move_to);
                textArray[4] = getString(R.string.bottom_option_assign);
                textArray[5] = getString(R.string.bottom_option_close);

                tagsArray = new int[length];
                tagsArray[0] = R.id.STATUS_UPDATE_REPAIR_DONE;
                tagsArray[1] = R.id.STATUS_UPDATE_HOLD;
                tagsArray[2] = R.id.STATUS_UPDATE_TERMINATE;
                tagsArray[3] = R.id.STATUS_UPDATE_MOVE_TO;
                tagsArray[4] = R.id.STATUS_UPDATE_ASSIGN;
                tagsArray[5] = R.id.STATUS_UPDATE_CLOSE;

                drawablesArray = new int[length];
                drawablesArray[0] = R.drawable.ic_option_accept_request;
                drawablesArray[1] = R.drawable.ic_option_accept_request;
                drawablesArray[2] = R.drawable.ic_option_hold;
                drawablesArray[3] = R.drawable.ic_option_hold;
                drawablesArray[4] = R.drawable.ic_option_hold;
                drawablesArray[5] = R.drawable.ic_option_hold;

            }


          /*  if (tag == 0) {  // customer
                textArray = new String[1];
                textArray[0] = getString(R.string.bottom_option_call_customer_care);
                drawablesArray = new int[1];
                drawablesArray[0] = R.drawable.ic_option_call;

            } else if (tag == 1) { // product
                textArray = new String[2];
                textArray[0] = getString(R.string.bottom_option_warranty_details);
                textArray[1] = getString(R.string.bottom_option_past_history);
                drawablesArray = new int[2];
                drawablesArray[0] = R.drawable.ic_options_features;
                drawablesArray[1] = R.drawable.ic_option_pasthistory;
            } else if (tag == 2) { // service center
                textArray = new String[1];
                textArray[0] = getString(R.string.bottom_option_Call);
                drawablesArray = new int[1];
                drawablesArray[0] = R.drawable.ic_option_call;
            } else { // status update
                textArray = new String[6];
                textArray[0] = getString(R.string.bottom_option_repair_done);
                textArray[1] = getString(R.string.bottom_option_hold);
                textArray[2] = getString(R.string.bottom_option_terminate);
                textArray[3] = getString(R.string.bottom_option_move_to);
                textArray[4] = getString(R.string.bottom_option_assign);
                textArray[5] = getString(R.string.bottom_option_close);
                drawablesArray = new int[6];
                drawablesArray[0] = R.drawable.ic_option_repair_done;
                drawablesArray[1] = R.drawable.ic_option_hold;
                drawablesArray[2] = R.drawable.ic_option_assign;
                drawablesArray[3] = R.drawable.ic_option_close;
                drawablesArray[4] = R.drawable.ic_option_close;
                drawablesArray[5] = R.drawable.ic_option_close;
            }
            */


            bottomSheetPurchasedBinding.secondRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.secondRowLine.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.secondRow.removeAllViews();
            bottomSheetPurchasedBinding.secondRow.setWeightSum(textArray.length);
            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.GONE);
            setBottomViewOptions(bottomSheetPurchasedBinding.secondRow, textArray, drawablesArray, tagsArray, bottomSheetSecondRowClickListener);
        }
    };


    // bottom sheet top view click event
    private View.OnClickListener bottomSheetSecondRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();

            FetchNewRequestResponse itemFromPosition = repairAdapter.getItemFromPosition(
                    productSelectedPosition);
            changeSelectedViews(bottomSheetPurchasedBinding.secondRow, tag);
            String[] textArray = new String[0];
            int[] drawablesArray = new int[0];
            int[] tagsArray = new int[0];

            if (tag == R.id.CUSTOMER_CALL_CUSTOMER_CARE) {
                callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
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
                AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));

                //  showPastHisoryDialog();
                // return;

            } else if (tag == R.id.SERVICE_CENTER_CALL) {
                callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                return;

            } else if (tag == R.id.STATUS_UPDATE_REPAIR_DONE) {
                showRepairDone();

            } else if (tag == R.id.STATUS_UPDATE_HOLD) {
                showHoldDialog();

            } else if (tag == R.id.STATUS_UPDATE_TERMINATE) {
                showTerminateDialog();

            } else if (tag == R.id.STATUS_UPDATE_MOVE_TO) {
                showMoveToDialog();

            } else if (tag == R.id.STATUS_UPDATE_ASSIGN) {
                //   showAssignDialog();
                fetchAssignDialogData();

            }else if (tag == R.id.STATUS_UPDATE_CLOSE) {
                showCloseDialog();

            }


          /*  // customer
            if (firstRowTag == 0) {
                if (secondRowTag == 0) {    //call customer care
                    callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                    return;
                }

            } else if (firstRowTag == 1) { // product

                if (secondRowTag == 0) { // warrenty details
                    AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                    // TODO have to get details from back end
                    *//*String purchasedDate = DateUtils.convertMillisToStringFormat(
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
*//*
                } else if (secondRowTag == 1) { // history
                    AppUtils.shortToast(getActivity(), getString(R.string.coming_soon));
                }
            } else if (firstRowTag == 2) { // service center
                if (secondRowTag == 0) { // call
                    callPhoneNumber(getActivity(), itemFromPosition.getCustomer().getMobileNumber());
                }
            } else if (firstRowTag == 3) { // status upadates
                if (secondRowTag == 0) { // repair done
                    showRepairDone();
                } else if (secondRowTag == 1) { // hold
                    showHoldDialog();
                } else if (secondRowTag == 2) { // terminate
                    showTerminateDialog();
                } else if (secondRowTag == 3) { // move to
                    showMoveToDialog();
                } else if (secondRowTag == 4) { // assign
                    //   showAssignDialog();
                    fetchAssignDialogData();

                } else { // close
                    showCloseDialog();
                }
            }

            */

            bottomSheetPurchasedBinding.thirdRow.setVisibility(View.VISIBLE);
            bottomSheetPurchasedBinding.thirdRowLine.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.secondRowLine.setVisibility(View.GONE);
            bottomSheetPurchasedBinding.thirdRow.removeAllViews();
            bottomSheetPurchasedBinding.thirdRow.setWeightSum(textArray.length);
        }
    };

    private void fetchAssignDialogData() {
        repairPresenter.getUsersListOfServiceCenters(serviceCenterId);


    }

    private void showTerminateDialog() {
        terminateDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
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
                                terminateDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_terminate))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        terminateDialog.showDialog();
        terminateDialog.setCancelable(true);


    }

    private void showMoveToDialog() {
        ArrayList<Status> statusList = AppUtils.getSubStatusList(getString(R.string.tab_repair), ((HomeActivity) getActivity()).getStatusList());
        moveToOptionDialog = new MoveToOptionDialog.AlertDialogBuilder(getContext(), new MoveToOptionCallback() {
            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {
                FetchNewRequestResponse requestResponse = repairAdapter.getItemFromPosition(productSelectedPosition);
                Request request = requestResponse.getRequest();
                upDateStatus.setRequestid(request.getId());
                repairPresenter.upDateStatus(userId, upDateStatus);
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
        holdDialog.setCancelable(true);
    }

    private void showRepairDone() {
        repairDialog = new AppEditTextDialog.AlertDialogBuilder(getActivity(), new
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
                                repairDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.bottom_option_repair_done))
                .leftButtonText(getString(R.string.action_cancel))
                .rightButtonText(getString(R.string.action_submit))
                .build();
        repairDialog.showDialog();
        repairDialog.setCancelable(true);

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
        closeDialog.setCancelable(true);
    }

    private void showAssignDialog(List<AddUser> userList) {
        statusDialog = new AssignDialog.AlertDialogBuilder(getContext(), new AssignOptionCallback() {

            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {

                FetchNewRequestResponse requestResponse = repairAdapter.getItemFromPosition(productSelectedPosition);
                Request request = requestResponse.getRequest();
                upDateStatus.setRequestid(request.getId());
                repairPresenter.upDateStatus(userId, upDateStatus);

            }


            @Override
            public void alertDialogCallback(byte dialogStatus) {

                switch (dialogStatus) {
                    case AlertDialogCallback.OK:

                        break;
                    case AlertDialogCallback.CANCEL:
                        statusDialog.dismiss();

                        break;
                    default:
                        break;
                }

            }
        }).title(getString(R.string.option_assign)).loadUsersList(userList).statusId(ASSIGNED).build();
        statusDialog.showDialog();
        statusDialog.setCancelable(true);

    }

    private void showLocationDialog() {

        FetchNewRequestResponse itemFromPosition = repairAdapter.getItemFromPosition(
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


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    repairAdapter.clearData();

                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        repairPresenter.disposeAll();
    }


    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO have to implement search click listener
    }


    @Override
    public void loadingNewServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponsesList) {


        if (fetchNewRequestResponsesList == null) {
            fetchNewRequestResponsesList = new ArrayList<>();
        }

        if (fetchNewRequestResponsesList.size() == 0) {
            binding.repairTextview.setVisibility(View.VISIBLE);
            dismissSwipeRefresh();
        } else {
            binding.repairTextview.setVisibility(View.GONE);
            repairAdapter.setData(fetchNewRequestResponsesList);
            dismissSwipeRefresh();
        }
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
        if (statusDialog != null && statusDialog.isShowing()) {
            statusDialog.dismiss();
        }
        // todo have to know
        try {
            Integer statusId = Integer.valueOf(upDateStatusResponse.getRequest().getStatus());
            if (statusId == ASSIGNED || statusId == ATTENDING) {
                doRefresh(true);
            }
        } catch (Exception e) {
            //TODO have to handle
        }


    }


}
