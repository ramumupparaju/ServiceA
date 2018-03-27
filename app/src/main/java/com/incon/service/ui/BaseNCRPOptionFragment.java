package com.incon.service.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.incon.service.AppConstants;
import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.assigneduser.AssignedUser;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.apimodel.components.status.StatusList;
import com.incon.service.apimodel.components.updatestatus.Status;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.callbacks.IStatusClickCallback;
import com.incon.service.callbacks.MoveToOptionCallback;
import com.incon.service.callbacks.PassHistoryCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppEditTextDialog;
import com.incon.service.custom.view.AppStatusDialog;
import com.incon.service.custom.view.AssignDialog;
import com.incon.service.custom.view.MoveToOptionDialog;
import com.incon.service.custom.view.PastHistoryDialog;
import com.incon.service.databinding.FragmentNewrequestBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.servicerequest.ServiceRequest;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.NewRequestsAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;
import com.incon.service.ui.status.fragment.ApprovalFragment;
import com.incon.service.ui.status.fragment.CheckUpFragment;
import com.incon.service.ui.status.fragment.CompleteFragment;
import com.incon.service.ui.status.fragment.HoldFragment;
import com.incon.service.ui.status.fragment.NewRequestsFragment;
import com.incon.service.ui.status.fragment.PaymentFragment;
import com.incon.service.ui.status.fragment.RepairFragment;
import com.incon.service.ui.status.fragment.ServiceCenterPresenter;
import com.incon.service.ui.status.fragment.TerminateFragment;
import com.incon.service.utils.DateUtils;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppConstants.StatusConstants.ASSIGNED;

/**
 * Created by PC on 3/6/2018.
 */

public class BaseNCRPOptionFragment extends BaseTabFragment {

    public MoveToOptionDialog moveToOptionDialog;

    public ServiceCenterPresenter serviceCenterPresenter;
    public FragmentNewrequestBinding newRequestBinding;
    public NewRequestsAdapter newRequestsAdapter;


    public ShimmerFrameLayout shimmerFrameLayout;
    public int serviceCenterId = DEFAULT_VALUE;
    public int userId = DEFAULT_VALUE;
    public PastHistoryDialog pastHistoryDialog;
    public AppEditTextDialog updateStatusDialog;
    public UpDateStatus upDateStatus;
    public AssignDialog assignDialog;
    private AppStatusDialog statusDialog;


    public IStatusClickCallback iClickCallback = new IStatusClickCallback() {
        @Override
        public void onClickStatusButton(int statusType) {

        }

        @Override
        public void onClickStatus(int productPosition, int statusPosition) {
            FetchNewRequestResponse serviceStatus = newRequestsAdapter.getItemFromPosition(productPosition);
            List<StatusList> statusList = serviceStatus.getStatusList();
            StatusList status = statusList.get(statusPosition);
            ServiceRequest serviceRequest = status.getRequest();
            String phoneNumber = TextUtils.isEmpty(status.getAssignedUser().getMobileNumber()) ? status.getServiceCenter().getContactNo() : status.getAssignedUser().getMobileNumber();
            showStatusDialog(AppUtils.getStatusName(Integer.parseInt(serviceRequest.getStatus())), formattedDescription(status), phoneNumber);
        }

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

    private String formattedDescription(StatusList status) {
        StringBuilder stringBuilder = new StringBuilder();
        AssignedUser assignedUser = status.getAssignedUser();
        if (assignedUser != null) {
            if (!TextUtils.isEmpty(assignedUser.getName()))
                stringBuilder.append("Name : ").append(assignedUser.getName()).append(NEW_LINE);
            if (!TextUtils.isEmpty(assignedUser.getMobileNumber()))
                stringBuilder.append("Mobile num : ").append(assignedUser.getMobileNumber()).append(NEW_LINE);
            if (!TextUtils.isEmpty(assignedUser.getDesignation())) {
                stringBuilder.append("Desig : ").append(assignedUser.getDesignation()).append(NEW_LINE);
            }

            stringBuilder.append("Date : ").append(DateUtils.convertMillisToStringFormat(status.getRequest().getCreatedDate(), DateFormatterConstants.LOCAL_DATE_DD_MM_YYYY_HH_MM));
        }
        return stringBuilder.toString();
    }

    public void createBottomSheetFirstRow() {
//overrided in each sub class
    }

    private void showStatusDialog(String title, String messageInfo, String phoneNumber) {
        statusDialog = new AppStatusDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String phoneNumber) {
                        AppUtils.callPhoneNumber(getActivity(), phoneNumber);
                        statusDialog.dismiss();
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case TextAlertDialogCallback.OK:
                                break;
                            default:
                                break;
                        }
                    }
                }).title(title).description(messageInfo).phoneNumber(phoneNumber)
                .build();
        statusDialog.showDialog();
        statusDialog.setCancelable(true);
    }

    public void initViews() {

        String statusType = "";
        if (this instanceof NewRequestsFragment) {
            statusType = AppUtils.ServiceRequestTypes.NEW.name();
        } else if (this instanceof NewRequestsFragment) {
            statusType = AppUtils.ServiceRequestTypes.CHECKUP.name();
        } else if (this instanceof NewRequestsFragment) {
            statusType = AppUtils.ServiceRequestTypes.APPROVAL.name();
        } else if (this instanceof NewRequestsFragment) {
            statusType = AppUtils.ServiceRequestTypes.REPAIR.name();
        } else if (this instanceof NewRequestsFragment) {
            statusType = AppUtils.ServiceRequestTypes.PAYMENT.name();
        } else if (this instanceof NewRequestsFragment) {
            statusType = AppUtils.ServiceRequestTypes.HOLD.name();
        } else if (this instanceof NewRequestsFragment) {
            statusType = AppUtils.ServiceRequestTypes.COMPLETED.name();
        } else if (this instanceof NewRequestsFragment) {
            statusType = AppUtils.ServiceRequestTypes.TERMINATE.name();
        }
        serviceRequest = new ServiceRequest();
        serviceRequest.setStatus(statusType);
        newRequestsAdapter = new NewRequestsAdapter();
        newRequestsAdapter.setClickCallback(iClickCallback);
        newRequestBinding.swiperefresh.setOnRefreshListener(onRefreshListener);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        newRequestBinding.requestRecyclerview.setAdapter(newRequestsAdapter);
        newRequestBinding.requestRecyclerview.setLayoutManager(linearLayoutManager);
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    doRefresh(true);
                }
            };

    @Override
    public void showErrorMessage(String errorMessage) {
        if (bottomSheetDialog.isShowing()) {
            AppUtils.shortToast(getActivity(), errorMessage);
        } else {
            super.showErrorMessage(errorMessage);
        }
    }

    public void dismissSwipeRefresh() {
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

    public void showLocationDialog() {
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
    public void doRefresh(boolean isForceRefresh) {
        dismissDialog(bottomSheetDialog);
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

        if (!(BaseNCRPOptionFragment.this instanceof NewRequestsFragment)) { //for new requests to have to fetch all dates else have to show only based  on dates
            serviceRequest.setReqDate(toDate);
        }
        getServiceRequestApi();
    }

    private void getServiceRequestApi() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();

        String messageForApi = "";
        if (this instanceof NewRequestsFragment) {
            messageForApi = getString(R.string.progress_fetch_new_service_request);
        } else if (this instanceof CheckUpFragment) {
            messageForApi = getString(R.string.progress_fetch_new_service_request);
        } else if (this instanceof ApprovalFragment) {
            messageForApi = getString(R.string.progress_fetch_approval_service_request);
        } else if (this instanceof RepairFragment) {
        } else if (this instanceof PaymentFragment) {
            messageForApi = getString(R.string.progress_fetch_new_service_request);
        } else if (this instanceof HoldFragment) {
            messageForApi = getString(R.string.progress_hold_service_request);
        } else if (this instanceof TerminateFragment) {
            messageForApi = getString(R.string.progress_terminate_service_request);
        } else if (this instanceof CompleteFragment) {
            messageForApi = getString(R.string.progress_compleat_service_request);
        }
        newRequestBinding.requestRecyclerview.setVisibility(View.GONE);
        serviceCenterPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, messageForApi);
    }

    public void loadingNewServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponsesList) {
        if (fetchNewRequestResponsesList == null) {
            fetchNewRequestResponsesList = new ArrayList<>();
        }
        if (fetchNewRequestResponsesList.size() == 0) {
            newRequestBinding.requestTextview.setVisibility(View.VISIBLE);
            newRequestBinding.requestRecyclerview.setVisibility(View.GONE);
        } else {
            newRequestBinding.requestTextview.setVisibility(View.GONE);
            newRequestBinding.requestRecyclerview.setVisibility(View.VISIBLE);
            newRequestsAdapter.setData(fetchNewRequestResponsesList);
        }

        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    public void showMoveToDialog() {
        String stringToSkip = "";

        if (this instanceof NewRequestsFragment) {
            stringToSkip = getString(R.string.tab_new_request);
        } else if (this instanceof ApprovalFragment) {
            stringToSkip = getString(R.string.tab_approval);
        } else if (this instanceof CheckUpFragment) {
            stringToSkip = getString(R.string.tab_checkup);
        } else if (this instanceof RepairFragment) {
            stringToSkip = getString(R.string.tab_repair);
        }

        ArrayList<Status> statusList = AppUtils.getSubStatusList(stringToSkip, ((HomeActivity) getActivity()).getStatusList());
        moveToOptionDialog = new MoveToOptionDialog.AlertDialogBuilder(getContext(), new MoveToOptionCallback() {
            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {
                FetchNewRequestResponse requestResponse = newRequestsAdapter.getItemFromPosition(productSelectedPosition);
                Request request = requestResponse.getRequest();
                upDateStatus.setRequestid(request.getId());
                serviceCenterPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);

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

    public void showUpdateStatusDialog(final int dialogType) {
        String dialogTitle = "";
        upDateStatus = new UpDateStatus();

        upDateStatus.setRequestid(newRequestsAdapter.getItemFromPosition(productSelectedPosition).getRequest().getId());
        if (dialogType == R.id.STATUS_UPDATE_REJECT) {
            dialogTitle = getString(R.string.bottom_option_reject);
            upDateStatus.setStatus(new Status(StatusConstants.REJECT));
        } else if (dialogType == R.id.STATUS_UPDATE_HOLD) {
            dialogTitle = getString(R.string.bottom_option_hold);
            upDateStatus.setStatus(new Status(StatusConstants.NEW_REQ_HOLD));
        } else if (dialogType == R.id.STATUS_UPDATE_TERMINATE) {
            dialogTitle = getString(R.string.bottom_option_terminate);
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
                                doUpdateStatusApi();
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

    public void doUpdateStatusApi() {
        serviceCenterPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, AppConstants.DEFAULT_VALUE), upDateStatus);
    }

    public void fetchAssignDialogData() {
        serviceCenterPresenter.getUsersListOfServiceCenters(serviceCenterId);
    }

    public void showAssignDialog(List<AddUser> userList) {
        int currentUserId = ((HomeActivity) getActivity()).getUserId();
        if (currentUserId != MINUS_ONE) {
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getId() == currentUserId) {
                    userList.remove(i);
                    break;
                }
            }
        }
        assignDialog = new AssignDialog.AlertDialogBuilder(getContext(), new AssignOptionCallback() {

            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {
                FetchNewRequestResponse requestResponse = newRequestsAdapter.getItemFromPosition(productSelectedPosition);
                Request request = requestResponse.getRequest();
                upDateStatus.setRequestid(request.getId());
                serviceCenterPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);


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

    public void showPastHisoryDialog() {
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

    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {

    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceCenterPresenter.disposeAll();
    }
}
