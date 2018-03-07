package com.incon.service.ui;

import android.os.Bundle;
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
import com.incon.service.databinding.FragmentCheckupBinding;
import com.incon.service.databinding.FragmentNewrequestBinding;
import com.incon.service.databinding.FragmentPaymentBinding;
import com.incon.service.databinding.FragmentRepairBinding;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.CheckUpAdapter;
import com.incon.service.ui.status.adapter.NewRequestsAdapter;
import com.incon.service.ui.status.adapter.PaymentAdapter;
import com.incon.service.ui.status.adapter.RepairAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;
import com.incon.service.ui.status.fragment.CheckUpFragment;
import com.incon.service.ui.status.fragment.NewRequestsFragment;
import com.incon.service.ui.status.fragment.PaymentFragment;
import com.incon.service.ui.status.fragment.RepairFragment;
import com.incon.service.ui.status.fragment.ServiceCenterPresenter;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

import static com.incon.service.AppConstants.StatusConstants.ASSIGNED;

/**
 * Created by PC on 3/6/2018.
 */

public class BaseNCRPOptionFragment extends BaseTabFragment {

    public MoveToOptionDialog moveToOptionDialog;

    ////// specific to  new request fragment
    public NewRequestsAdapter newRequestsAdapter;
    public ServiceCenterPresenter newRequestPresenter;
    public FragmentNewrequestBinding newRequestBinding;

    ////// specific to  check up fragment
    public ServiceCenterPresenter checkUpPresenter;
    public CheckUpAdapter checkUpAdapter;
    public FragmentCheckupBinding checkupBinding;


    ////// specific to repair fragment
    public ServiceCenterPresenter repairPresenter;
    public RepairAdapter repairAdapter;
    public FragmentRepairBinding repairBinding;


    ////// specific to payment fragment
    public ServiceCenterPresenter paymentPresenter;
    public FragmentPaymentBinding paymentBinding;
    public PaymentAdapter paymentAdapter;


    public ShimmerFrameLayout shimmerFrameLayout;
   // public int userId;
   public int serviceCenterId = DEFAULT_VALUE;
    public int userId = DEFAULT_VALUE;
    public PastHistoryDialog pastHistoryDialog;
    public AppEditTextDialog updateStatusDialog;
    public UpDateStatus upDateStatus;
    public AssignDialog assignDialog;


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
                    checkUpPresenter.upDateStatus(userId, upDateStatus);
                } else {
                    FetchNewRequestResponse requestResponse = repairAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    repairPresenter.upDateStatus(userId, upDateStatus);
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
            }
        }

        else if (this instanceof PaymentFragment) {
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
        } else if (this instanceof CheckUpFragment) {
            repairPresenter.getUsersListOfServiceCenters(serviceCenterId);
        }
        paymentPresenter.getUsersListOfServiceCenters(serviceCenterId);

    }




    public void showAssignDialog(List<AddUser> userList) {
        assignDialog = new AssignDialog.AlertDialogBuilder(getContext(), new AssignOptionCallback() {

            @Override
            public void doUpDateStatusApi(UpDateStatus upDateStatus) {
                if (BaseNCRPOptionFragment.this instanceof NewRequestsFragment) {
                    FetchNewRequestResponse requestResponse = newRequestsAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    newRequestPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
                }

                else if (BaseNCRPOptionFragment.this instanceof CheckUpFragment) {
                    FetchNewRequestResponse requestResponse = checkUpAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    checkUpPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
                }

                else if (BaseNCRPOptionFragment.this instanceof RepairFragment) {
                    FetchNewRequestResponse requestResponse = repairAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    repairPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
                }

                else  {
                    FetchNewRequestResponse requestResponse = paymentAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    paymentPresenter.upDateStatus(SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_ID, -1), upDateStatus);
                }


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
    public void doRefresh(boolean isForceRefresh) {

    }
}
