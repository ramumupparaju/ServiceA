package com.incon.service.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.incon.service.AppConstants;
import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.apimodel.components.updatestatus.Status;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.AssignOptionCallback;
import com.incon.service.callbacks.MoveToOptionCallback;
import com.incon.service.callbacks.PassHistoryCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppEditTextDialog;
import com.incon.service.custom.view.AssignDialog;
import com.incon.service.custom.view.MoveToOptionDialog;
import com.incon.service.custom.view.PastHistoryDialog;
import com.incon.service.databinding.FragmentApprovalBinding;
import com.incon.service.databinding.FragmentCheckupBinding;
import com.incon.service.databinding.FragmentCompleatBinding;
import com.incon.service.databinding.FragmentHoldBinding;
import com.incon.service.databinding.FragmentNewrequestBinding;
import com.incon.service.databinding.FragmentPaymentBinding;
import com.incon.service.databinding.FragmentRepairBinding;
import com.incon.service.databinding.FragmentTerminateBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.ApprovalAdapter;
import com.incon.service.ui.status.adapter.CheckUpAdapter;
import com.incon.service.ui.status.adapter.CompleatAdapter;
import com.incon.service.ui.status.adapter.HoldAdapter;
import com.incon.service.ui.status.adapter.NewRequestsAdapter;
import com.incon.service.ui.status.adapter.PaymentAdapter;
import com.incon.service.ui.status.adapter.RepairAdapter;
import com.incon.service.ui.status.adapter.TerminateAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;
import com.incon.service.ui.status.fragment.ApprovalFragment;
import com.incon.service.ui.status.fragment.CheckUpFragment;
import com.incon.service.ui.status.fragment.CompleatFragment;
import com.incon.service.ui.status.fragment.HoldFragment;
import com.incon.service.ui.status.fragment.NewRequestsFragment;
import com.incon.service.ui.status.fragment.PaymentFragment;
import com.incon.service.ui.status.fragment.RepairFragment;
import com.incon.service.ui.status.fragment.ServiceCenterPresenter;
import com.incon.service.ui.status.fragment.TerminateFragment;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppConstants.StatusConstants.ASSIGNED;

/**
 * Created by PC on 3/6/2018.
 */

public class BaseNCRPOptionFragment extends BaseTabFragment {

    public MoveToOptionDialog moveToOptionDialog;

    ////// specific to new request fragment
    public NewRequestsAdapter newRequestsAdapter;
    public ServiceCenterPresenter newRequestPresenter;
    public FragmentNewrequestBinding newRequestBinding;

    ////// specific to check up fragment
    public CheckUpAdapter checkUpAdapter;
    public ServiceCenterPresenter checkUpPresenter;
    public FragmentCheckupBinding checkupBinding;

    ////// specific to approval fragment
    public ApprovalAdapter approvalAdapter;
    public ServiceCenterPresenter approvalPresenter;
    public FragmentApprovalBinding approvalBinding;

    ////// specific to repair fragment
    public RepairAdapter repairAdapter;
    public FragmentRepairBinding repairBinding;
    public ServiceCenterPresenter repairPresenter;

    ////// specific to payment fragment
    public PaymentAdapter paymentAdapter;
    public FragmentPaymentBinding paymentBinding;
    public ServiceCenterPresenter paymentPresenter;

    ////// specific to hold fragment
    public HoldAdapter holdAdapter;
    public ServiceCenterPresenter holdPresenter;
    public FragmentHoldBinding holdBinding;

    ////// specific to compleat fragment
    public CompleatAdapter compleatAdapter;
    public ServiceCenterPresenter compleatPresenter;
    public FragmentCompleatBinding compleatBinding;

    ////// specific to terminate fragment
    public TerminateAdapter terminatetAdapter;
    public ServiceCenterPresenter terminatePresenter;
    public FragmentTerminateBinding terminateBinding;


    public ShimmerFrameLayout shimmerFrameLayout;
    public int serviceCenterId = DEFAULT_VALUE;
    public int userId = DEFAULT_VALUE;
    public PastHistoryDialog pastHistoryDialog;
    public AppEditTextDialog updateStatusDialog;
    public UpDateStatus upDateStatus;
    public AssignDialog assignDialog;


    public SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    doRefresh(true);
                }
            };

    public void dismissSwipeRefresh() {
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

        getServiceRequestApi();
    }

    private void getServiceRequestApi() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmerAnimation();

        if (this instanceof NewRequestsFragment) {
            newRequestBinding.requestRecyclerview.setVisibility(View.GONE);
            newRequestPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_fetch_new_service_request));
        } else if (this instanceof CheckUpFragment) {
            checkupBinding.checkupRecyclerview.setVisibility(View.GONE);
            checkUpPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_fetch_new_service_request));
        } else if (this instanceof ApprovalFragment) {
            approvalBinding.apprvalRecyclerview.setVisibility(View.GONE);
            approvalPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_fetch_approval_service_request));
        } else if (this instanceof RepairFragment) {
            repairBinding.requestRecyclerview.setVisibility(View.GONE);
            repairPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_fetch_new_service_request));
        } else if (this instanceof PaymentFragment) {
            paymentBinding.paymentRecyclerview.setVisibility(View.GONE);
            paymentPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_fetch_new_service_request));
        } else if (this instanceof HoldFragment) {
            holdBinding.holdRecyclerview.setVisibility(View.GONE);
            holdPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_hold_service_request));
        } else if (this instanceof TerminateFragment) {
            terminateBinding.terminateRecyclerview.setVisibility(View.GONE);
            terminatePresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_terminate_service_request));
        } else if (this instanceof CompleatFragment) {
            compleatBinding.compleatRecyclerview.setVisibility(View.GONE);
            compleatPresenter.fetchServiceRequestsUsingRequestType(serviceRequest, getString(R.string.progress_compleat_service_request));
        }
    }

    public void loadingNewServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponsesList) {
        if (fetchNewRequestResponsesList == null) {
            fetchNewRequestResponsesList = new ArrayList<>();
        }
        if (fetchNewRequestResponsesList.size() == 0) {
            if (this instanceof NewRequestsFragment) {
                newRequestBinding.requestTextview.setVisibility(View.VISIBLE);
                newRequestBinding.requestRecyclerview.setVisibility(View.GONE);
            } else if (this instanceof CheckUpFragment) {
                checkupBinding.checkupTextview.setVisibility(View.VISIBLE);
                checkupBinding.checkupRecyclerview.setVisibility(View.GONE);
            } else if (this instanceof ApprovalFragment) {
                approvalBinding.apprvalTextview.setVisibility(View.VISIBLE);
                approvalBinding.apprvalRecyclerview.setVisibility(View.GONE);
            } else if (this instanceof RepairFragment) {
                repairBinding.repairTextview.setVisibility(View.VISIBLE);
                repairBinding.requestRecyclerview.setVisibility(View.GONE);
            } else if (this instanceof PaymentFragment) {
                paymentBinding.paymentTextview.setVisibility(View.VISIBLE);
                paymentBinding.paymentRecyclerview.setVisibility(View.GONE);
            } else if (this instanceof HoldFragment) {
                holdBinding.holdTextview.setVisibility(View.VISIBLE);
                holdBinding.holdRecyclerview.setVisibility(View.GONE);
            } else if (this instanceof TerminateFragment) {
                terminateBinding.terminateTextview.setVisibility(View.VISIBLE);
                terminateBinding.terminateRecyclerview.setVisibility(View.GONE);
            } else if (this instanceof CompleatFragment) {
                compleatBinding.compleatTextview.setVisibility(View.VISIBLE);
                compleatBinding.compleatRecyclerview.setVisibility(View.GONE);
            }

        } else {
            if (this instanceof NewRequestsFragment) {
                newRequestBinding.requestTextview.setVisibility(View.GONE);
                newRequestBinding.requestRecyclerview.setVisibility(View.VISIBLE);
                newRequestsAdapter.setData(fetchNewRequestResponsesList);
            } else if (this instanceof CheckUpFragment) {
                checkupBinding.checkupTextview.setVisibility(View.GONE);
                checkupBinding.checkupRecyclerview.setVisibility(View.VISIBLE);
                checkUpAdapter.setData(fetchNewRequestResponsesList);
            } else if (this instanceof ApprovalFragment) {
                approvalBinding.apprvalTextview.setVisibility(View.GONE);
                approvalBinding.apprvalRecyclerview.setVisibility(View.VISIBLE);
                approvalAdapter.setData(fetchNewRequestResponsesList);
            } else if (this instanceof RepairFragment) {
                repairBinding.repairTextview.setVisibility(View.GONE);
                repairBinding.requestRecyclerview.setVisibility(View.VISIBLE);
                repairAdapter.setData(fetchNewRequestResponsesList);
            } else if (this instanceof PaymentFragment) {
                paymentBinding.paymentTextview.setVisibility(View.GONE);
                paymentBinding.paymentRecyclerview.setVisibility(View.VISIBLE);
                paymentAdapter.setData(fetchNewRequestResponsesList);
            } else if (this instanceof HoldFragment) {
                holdBinding.holdTextview.setVisibility(View.GONE);
                holdBinding.holdRecyclerview.setVisibility(View.VISIBLE);
                holdAdapter.setData(fetchNewRequestResponsesList);
            } else if (this instanceof TerminateFragment) {
                terminateBinding.terminateTextview.setVisibility(View.GONE);
                terminateBinding.terminateRecyclerview.setVisibility(View.VISIBLE);
                terminatetAdapter.setData(fetchNewRequestResponsesList);
            } else if (this instanceof CompleatFragment) {
                compleatBinding.compleatTextview.setVisibility(View.GONE);
                compleatBinding.compleatRecyclerview.setVisibility(View.VISIBLE);
                compleatAdapter.setData(fetchNewRequestResponsesList);
            }
        }

        shimmerFrameLayout.stopShimmerAnimation();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    public void showMoveToDialog() {
        String stringToSkip = "";

        if (this instanceof NewRequestsFragment) {
            stringToSkip = getString(R.string.tab_new_request);
        } else if (this instanceof CheckUpFragment) {
            stringToSkip = getString(R.string.tab_checkup);
        } else if (this instanceof RepairFragment) {
            stringToSkip = getString(R.string.tab_repair);
        }


        ArrayList<Status> statusList = AppUtils.getSubStatusList(stringToSkip, ((HomeActivity) getActivity()).getStatusList());
        moveToOptionDialog = new MoveToOptionDialog.AlertDialogBuilder(getContext(), new MoveToOptionCallback() {
            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {
                if (BaseNCRPOptionFragment.this instanceof NewRequestsFragment) {
                    FetchNewRequestResponse requestResponse = newRequestsAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    newRequestPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
                } else if (BaseNCRPOptionFragment.this instanceof CheckUpFragment) {
                    FetchNewRequestResponse requestResponse = checkUpAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    checkUpPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);

                } else {
                    FetchNewRequestResponse requestResponse = repairAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    repairPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);

                }

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

        if (this instanceof NewRequestsFragment) {
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
        } else if (this instanceof CheckUpFragment) {
            upDateStatus.setRequestid(checkUpAdapter.getItemFromPosition(productSelectedPosition).getRequest().getId());
            if (dialogType == R.id.STATUS_UPDATE_HOLD) {
                dialogTitle = getString(R.string.bottom_option_hold);
                upDateStatus.setStatus(new Status(StatusConstants.CHECKUP_HOLD));
            } else if (dialogType == R.id.STATUS_UPDATE_TERMINATE) {
                dialogTitle = getString(R.string.bottom_option_terminate);
                upDateStatus.setStatus(new Status(StatusConstants.TERMINATE));
            }
        } else if (this instanceof RepairFragment) {
            upDateStatus.setRequestid(repairAdapter.getItemFromPosition(productSelectedPosition).getRequest().getId());
            if (dialogType == R.id.STATUS_UPDATE_HOLD) {
                dialogTitle = getString(R.string.bottom_option_hold);
                upDateStatus.setStatus(new Status(StatusConstants.REPAIR_HOLD));
            } else if (dialogType == R.id.STATUS_UPDATE_TERMINATE) {
                dialogTitle = getString(R.string.bottom_option_terminate);
                upDateStatus.setStatus(new Status(StatusConstants.TERMINATE));
            } else if (dialogType == R.id.STATUS_UPDATE_REPAIR_DONE) {
                dialogTitle = getString(R.string.bottom_option_repair_done);
                upDateStatus.setStatus(new Status(StatusConstants.REPAIR_DONE));
            }
        } else if (this instanceof PaymentFragment) {
            upDateStatus.setRequestid(paymentAdapter.getItemFromPosition(productSelectedPosition).getRequest().getId());
            if (dialogType == R.id.STATUS_UPDATE_TERMINATE) {
                dialogTitle = getString(R.string.bottom_option_terminate);
                upDateStatus.setStatus(new Status(StatusConstants.TERMINATE));
            }
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
        if (this instanceof NewRequestsFragment) {
            newRequestPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, AppConstants.DEFAULT_VALUE), upDateStatus);
        } else if (this instanceof CheckUpFragment) {
            checkUpPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, AppConstants.DEFAULT_VALUE), upDateStatus);
        } else if (this instanceof RepairFragment) {
            repairPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, AppConstants.DEFAULT_VALUE), upDateStatus);
        } else if (this instanceof PaymentFragment) {
            paymentPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, AppConstants.DEFAULT_VALUE), upDateStatus);
        }
    }

    public void fetchAssignDialogData() {
        if (this instanceof NewRequestsFragment) {
            newRequestPresenter.getUsersListOfServiceCenters(serviceCenterId);
        } else if (this instanceof CheckUpFragment) {
            checkUpPresenter.getUsersListOfServiceCenters(serviceCenterId);
        } else if (this instanceof RepairFragment) {
            repairPresenter.getUsersListOfServiceCenters(serviceCenterId);
        }

        // todo have  to know and remove
        /*else if (this instanceof PaymentFragment) {
            paymentPresenter.getUsersListOfServiceCenters(serviceCenterId);
        }*/
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
                if (BaseNCRPOptionFragment.this instanceof NewRequestsFragment) {
                    FetchNewRequestResponse requestResponse = newRequestsAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    newRequestPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
                } else if (BaseNCRPOptionFragment.this instanceof CheckUpFragment) {
                    FetchNewRequestResponse requestResponse = checkUpAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    checkUpPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
                } else if (BaseNCRPOptionFragment.this instanceof RepairFragment) {
                    FetchNewRequestResponse requestResponse = repairAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    repairPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
                }
                // todo have  to know and remove
             /*   else if (BaseNCRPOptionFragment.this instanceof PaymentFragment) {
                    FetchNewRequestResponse requestResponse = paymentAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    paymentPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
                }*/

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


}
