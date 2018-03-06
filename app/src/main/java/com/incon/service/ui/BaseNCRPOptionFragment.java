package com.incon.service.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.apimodel.components.request.Request;
import com.incon.service.apimodel.components.updatestatus.Status;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.MoveToOptionCallback;
import com.incon.service.custom.view.MoveToOptionDialog;
import com.incon.service.dto.updatestatus.UpDateStatus;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.CheckUpAdapter;
import com.incon.service.ui.status.adapter.NewRequestsAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;
import com.incon.service.ui.status.fragment.CheckUpFragment;
import com.incon.service.ui.status.fragment.NewRequestsFragment;
import com.incon.service.ui.status.fragment.ServiceCenterPresenter;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;

/**
 * Created by PC on 3/6/2018.
 */

public class BaseNCRPOptionFragment extends BaseTabFragment {

    private MoveToOptionDialog moveToOptionDialog;
    private NewRequestsAdapter newRequestsAdapter;
    // todo have to chacke
    private ServiceCenterPresenter newRequestPresenter;
    private ServiceCenterPresenter checkUpPresenter;
    private CheckUpAdapter checkUpAdapter;
    public int userId;



    public void showMoveToDialog() {
        String stringToSkip = "";

        if(this instanceof NewRequestsFragment) {
            stringToSkip = getString(R.string.tab_new_request);
        } else if(this instanceof CheckUpFragment) {
            stringToSkip = getString(R.string.tab_checkup);
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
                }
                else {
                    FetchNewRequestResponse requestResponse = checkUpAdapter.getItemFromPosition(productSelectedPosition);
                    Request request = requestResponse.getRequest();
                    upDateStatus.setRequestid(request.getId());
                    checkUpPresenter.upDateStatus(userId, upDateStatus);
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
