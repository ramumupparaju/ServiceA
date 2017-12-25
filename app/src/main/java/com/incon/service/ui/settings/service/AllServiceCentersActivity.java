package com.incon.service.ui.settings.service;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.incon.service.R;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
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

    public List<ServiceCenterResponse> serviceCenterResponseList;
    private AllServiceCentersAdapter allServiceCentersAdapter;

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
        allServiceCentersPresenter.serviceCentersList(SharedPrefsUtils.loginProvider().
                getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE));
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

    }

    //recyclerview click event
    private IEditClickCallback iClickCallback = new IEditClickCallback() {
        @Override
        public void onEditClickPosition(int position) {
            AddServiceCenter addServiceCenter = getServiceCenterRequestFromResponse(serviceCenterResponseList.get(position));
            Intent intent = new Intent(AllServiceCentersActivity.this, AddServiceCenterActivity.class);
            intent.putExtra(IntentConstants.SERVICE_CENTER_DATA, addServiceCenter);
            startActivity(intent);
        }

        @Override
        public void onClickPosition(int position) {
            Intent intent = new Intent(AllServiceCentersActivity.this, AllUsersDesignationsActivity.class);
            intent.putExtra(IntentConstants.SERVICE_CENTER_DATA, serviceCenterResponseList.get(position).getId());
            startActivity(intent);
        }
    };

    private AddServiceCenter getServiceCenterRequestFromResponse(ServiceCenterResponse serviceCenterResponse) {
        AddServiceCenter addServiceCenter = new AddServiceCenter();
        addServiceCenter.setId(serviceCenterResponse.getId());
        addServiceCenter.setAddress(serviceCenterResponse.getAddress());
        addServiceCenter.setBrandId(serviceCenterResponse.getBrandId());
        addServiceCenter.setCategoryId(serviceCenterResponse.getCategoryId());
        addServiceCenter.setContactNo(serviceCenterResponse.getContactNo());
        //TODO have to change as parameter
        addServiceCenter.setCreatedDate(String.valueOf(serviceCenterResponse.getCreatedDate()));
        addServiceCenter.setDivisionId(serviceCenterResponse.getDivisionId());
        addServiceCenter.setEmail(serviceCenterResponse.getEmail());
        addServiceCenter.setLocation(serviceCenterResponse.getLocation());
        addServiceCenter.setName(serviceCenterResponse.getName());
        addServiceCenter.setGstn(serviceCenterResponse.getGstn());
        return addServiceCenter;
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
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadServiceCentersList(List<ServiceCenterResponse> serviceCenterResponseList) {
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
