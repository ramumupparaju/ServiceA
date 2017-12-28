package com.incon.service.ui.status.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.FragmentApprovalBinding;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.status.adapter.ApprovalAdapter;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.List;

/**
 * Created by PC on 12/5/2017.
 */

public class ApprovalFragment extends BaseFragment implements ApprovalContract.View {
    private FragmentApprovalBinding binding;
    private View rootView;
    private ApprovalAdapter approvalAdapter;
    private ApprovalPresenter approvalPresenter;
    private int userId;
    private List<FetchNewRequestResponse> fetchNewRequestResponses;

    @Override
    protected void initializePresenter() {
        approvalPresenter = new ApprovalPresenter();
        approvalPresenter.setView(this);
        setBasePresenter(approvalPresenter);

    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_approval,
                    container, false);
            initViews();
            rootView = binding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        approvalAdapter = new ApprovalAdapter();
        approvalAdapter.setClickCallback(iClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.requestRecyclerview.setAdapter(approvalAdapter);
        binding.requestRecyclerview.setLayoutManager(linearLayoutManager);
        userId = SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE);
        userId = 1;
        approvalPresenter.fetchApprovalServiceRequests(userId);
    }

    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {

            approvalAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = approvalAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            approvalAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void fetchApprovalServiceRequests(Object o) {

    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    approvalAdapter.clearData();
                    approvalPresenter.fetchApprovalServiceRequests(userId);

                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        approvalPresenter.disposeAll();
    }
}
