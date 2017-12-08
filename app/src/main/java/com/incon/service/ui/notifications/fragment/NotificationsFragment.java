package com.incon.service.ui.notifications.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incon.service.R;
import com.incon.service.apimodel.components.notifications.NotificationsResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.BottomSheetNotificationsBinding;
import com.incon.service.databinding.CustomBottomViewBinding;
import com.incon.service.databinding.FragmentNotificationsBinding;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.notifications.fragment.adapter.NotificationsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by PC on 10/2/2017.
 */

public class NotificationsFragment extends BaseFragment {
    private View rootView;
    private FragmentNotificationsBinding binding;
    private NotificationsAdapter notificationsAdapter;
    private List<NotificationsResponse> notificationsList;
    private BottomSheetNotificationsBinding bottomSheetNotificationsBinding;

    private BottomSheetDialog bottomSheetDialog;

    @Override
    protected void initializePresenter() {

    }

    @Override
    public void setTitle() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_notifications));
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notifications,
                    container, false);


            loadBottomSheet();
           // initViews();
            rootView = binding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void loadBottomSheet() {
        bottomSheetNotificationsBinding = DataBindingUtil.inflate(LayoutInflater.from(
                getActivity()), R.layout.bottom_sheet_notifications, null, false);

        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(bottomSheetNotificationsBinding.getRoot());
        /*dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);*/
    }

    private void initViews() {
        binding.swiperefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        binding.swiperefresh.setOnRefreshListener(onRefreshListener);

        notificationsList = new ArrayList<>();
        notificationsAdapter = new NotificationsAdapter();
        notificationsAdapter.setData(notificationsList);
        notificationsAdapter.setClickCallback(iClickCallback);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        binding.notificationRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.notificationRecyclerview.setAdapter(notificationsAdapter);
        binding.notificationRecyclerview.setLayoutManager(linearLayoutManager);

        addTestData();
    }

    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            //createBottomSheetView(position);
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetView(int position) {

        bottomSheetNotificationsBinding.sheetTitle.setText("item : " + position);

        bottomSheetNotificationsBinding.topRow.setVisibility(View.GONE);
        bottomSheetNotificationsBinding.bottomRow.removeAllViews();
//TODO have to create based on response
        int noOfViews = new Random().nextInt(4);
        for (int i = 0; i < noOfViews; i++) {
            CustomBottomViewBinding customBottomView = getCustomBottomView();
            customBottomView.viewTv.setText("position :" + i);
            View bottomRootView = customBottomView.getRoot();
            bottomRootView.setOnClickListener(bottomViewClickListener);
            bottomSheetNotificationsBinding.bottomRow.addView(bottomRootView);
        }
    }

    private View.OnClickListener bottomViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomSheetNotificationsBinding.topRow.removeAllViews();
            bottomSheetNotificationsBinding.topRow.setVisibility(View.VISIBLE);
            TextView viewById = (TextView) view.findViewById(R.id.view_tv);
            String bottomClickedText = viewById.getText().toString();
            int noOfViews = new Random().nextInt(4);
            for (int i = 0; i < noOfViews; i++) {
                CustomBottomViewBinding customBottomView = getCustomBottomView();
                customBottomView.viewTv.setText(bottomClickedText + i);
                View topRootView = customBottomView.getRoot();
                topRootView.setOnClickListener(topViewClickListener);
                bottomSheetNotificationsBinding.topRow.addView(topRootView);
            }
        }
    };

    private View.OnClickListener topViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView viewById = (TextView) view.findViewById(R.id.view_tv);
            String topClickedText = viewById.getText().toString();
            showErrorMessage(topClickedText);
        }
    };

    private CustomBottomViewBinding getCustomBottomView() {
        return DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()), R.layout.custom_bottom_view, null, false);
    }


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    notificationsAdapter.clearData();
                    // Get alerts of account api
                    addTestData();
                }
            };

    private void addTestData() {
        for (int i = 0; i < 5; i++) {
            NotificationsResponse taskResponse = new NotificationsResponse();
            taskResponse.setId(i);
            taskResponse.setPositionText();
            notificationsList.add(taskResponse);
        }
        notificationsAdapter.setData(notificationsList);
        dismissSwipeRefresh();
    }

    private void dismissSwipeRefresh() {
        if (binding.swiperefresh.isRefreshing()) {
            binding.swiperefresh.setRefreshing(false);
        }
    }
}
