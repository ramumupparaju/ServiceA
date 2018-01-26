package com.incon.service.ui.notifications.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import com.incon.service.databinding.FragmentNotificationsBinding;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.notifications.fragment.adapter.NotificationsAdapter;
import com.incon.service.ui.status.base.base.BaseProductOptionsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 10/2/2017.
 */

public class NotificationsFragment extends BaseProductOptionsFragment {
    private View rootView;
    private FragmentNotificationsBinding binding;
    private NotificationsAdapter notificationsAdapter;
    private List<NotificationsResponse> notificationsList;

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

    @Override
    public void loadBottomSheet() {
        super.loadBottomSheet();
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //TODO have to implement
//                notificationsAdapter.clearSelection();
            }
        });
    }

    @Override
    public void doRefresh(boolean isForceRefresh) {

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
//        todo create bottom sheet
    }

    private View.OnClickListener bottomViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //todo create bottom sheet
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
