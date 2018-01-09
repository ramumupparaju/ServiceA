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
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.ApprovalAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 12/5/2017.
 */

public class ApprovalFragment extends BaseTabFragment implements ApprovalContract.View {
    private FragmentApprovalBinding binding;
    private View rootView;
    private ApprovalAdapter approvalAdapter;
    private ApprovalPresenter approvalPresenter;
    private int serviceCenterId = DEFAULT_VALUE;
    private int userId = DEFAULT_VALUE;
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
        binding.apprvalRecyclerview.setAdapter(approvalAdapter);
        binding.apprvalRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void doRefresh() {
        HomeActivity activity = (HomeActivity) getActivity();
        int tempServiceCenterId = activity.getServiceCenterId();
        int tempUserId = activity.getUserId();

        if (serviceCenterId == tempServiceCenterId && tempUserId == userId) {
            //no chnages have made, so no need to make api call
            return;
        } else {
            serviceCenterId = tempServiceCenterId;
            userId = tempUserId;
        }
        approvalPresenter.fetchApprovalServiceRequests(serviceCenterId, userId);
    }

    private void dismissSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.setRefreshing(false);
        }
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


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    approvalAdapter.clearData();
                    doRefresh();
                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        approvalPresenter.disposeAll();
    }

    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO have to do filter list
    }

    @Override
    public void loadingApprovalServiceRequests(List<FetchNewRequestResponse> fetchNewRequestResponsesList) {
        if (fetchNewRequestResponsesList == null) {
            fetchNewRequestResponsesList = new ArrayList<>();
        }

        if (fetchNewRequestResponsesList.size() == 0) {
            binding.apprvalTextview.setVisibility(View.VISIBLE);
            dismissSwipeRefresh();
        } else {
            binding.apprvalTextview.setVisibility(View.GONE);
            approvalAdapter.setData(fetchNewRequestResponsesList);
            dismissSwipeRefresh();
        }
    }
}
