package com.incon.service.ui.settings.service;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.callbacks.IEditClickCallback;
import com.incon.service.databinding.ActivityAllServiceCentersBinding;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.addservicecenter.AddServiceCenterActivity;
import com.incon.service.ui.settings.adapters.AllServiceCentersAdapter;
import com.incon.service.ui.settings.userdesignation.AllUsersDesignationsActivity;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 12/19/2017.
 */
public class AllServiceCentersActivity extends BaseActivity implements
        AllServiceCentersContract.View {

    private AllServiceCentersPresenter allServiceCentersPresenter;
    private ActivityAllServiceCentersBinding binding;
    public List<AddServiceCenter> serviceCenterResponseList;
    private AllServiceCentersAdapter allServiceCentersAdapter;
    private int userId = DEFAULT_VALUE;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_service_centers;
    }

    @Override
    protected void initializePresenter() {
        allServiceCentersPresenter = new AllServiceCentersPresenter();
        allServiceCentersPresenter.setView(this);
        setBasePresenter(allServiceCentersPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setActivity(this);
        initViews();

        //fetching servicing centers list to add designations
        userId = SharedPrefsUtils.loginProvider().
                getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE);
    }

    private void initViews() {
        initializeToolbar();

        allServiceCentersAdapter = new AllServiceCentersAdapter();
        allServiceCentersAdapter.setClickCallback(iClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, linearLayoutManager.getOrientation());
        binding.allServiceCentersRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.allServiceCentersRecyclerview.setAdapter(allServiceCentersAdapter);
        binding.allServiceCentersRecyclerview.setLayoutManager(linearLayoutManager);

        List<AddServiceCenter> serviceCenterList = ConnectApplication.getAppContext().getServiceCenterList();
        if (serviceCenterList == null) {
            allServiceCentersPresenter.serviceCentersList(userId);
        } else {
            loadServiceCentersList(serviceCenterList);
        }

    }

    //recyclerview click event
    private IEditClickCallback iClickCallback = new IEditClickCallback() {
        @Override
        public void onEditClickPosition(int position) {
            Intent intent = new Intent(AllServiceCentersActivity.this, AddServiceCenterActivity.class);
            intent.putExtra(IntentConstants.SERVICE_CENTER_DATA, serviceCenterResponseList.get(position));
            startActivityForResult(intent, RequestCodes.ADD_SERVICE_CENTER);

        }

        @Override
        public void onClickPosition(int position) {
            Intent intent = new Intent(AllServiceCentersActivity.this, AllUsersDesignationsActivity.class);
            intent.putExtra(IntentConstants.SERVICE_CENTER_DATA, serviceCenterResponseList.get(position).getId());
            startActivity(intent);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.ADD_SERVICE_CENTER: {
                    allServiceCentersPresenter.serviceCentersList(userId);
                }
                break;
            }
        }
    }

    private void initializeToolbar() {
        binding.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.toolbarNewIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllServiceCentersActivity.this, AddServiceCenterActivity.class);
                startActivityForResult(intent, RequestCodes.ADD_SERVICE_CENTER);
            }
        });
    }

    @Override
    public void loadServiceCentersList(List<AddServiceCenter> serviceCenterResponseList) {
        if (serviceCenterResponseList == null) {
            serviceCenterResponseList = new ArrayList<>();
        }
        this.serviceCenterResponseList = serviceCenterResponseList;
        allServiceCentersAdapter.setServiceCenterResponses(serviceCenterResponseList);
        allServiceCentersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        allServiceCentersPresenter.disposeAll();
    }
}
