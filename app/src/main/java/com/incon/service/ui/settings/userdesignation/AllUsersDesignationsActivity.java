package com.incon.service.ui.settings.userdesignation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.incon.service.R;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
import com.incon.service.apimodel.components.userslistofservicecenters.UsersListOfServiceCenters;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.ActivityAllUsersDesignationsBinding;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.adddesignations.AddDesignationsActivity;
import com.incon.service.ui.adduser.AddUserActivity;
import com.incon.service.ui.settings.userdesignation.adapter.DesignationsListAdapter;
import com.incon.service.ui.settings.userdesignation.adapter.UsersListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 12/19/2017.
 */
public class AllUsersDesignationsActivity extends BaseActivity implements
        AllUsersDesignationsContract.View {

    private ActivityAllUsersDesignationsBinding binding;
    private AllUsersDesignationsPresenter allUsersDesignationsPresenter;

    private UsersListAdapter usersListAdapter;
    private List<UsersListOfServiceCenters> usersList;

    private DesignationsListAdapter designationsListAdapter;
    private List<UsersListOfServiceCenters> designationsList;


    private int serviceCenterId;
    private boolean isDesignation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_all_users_designations;
    }

    @Override
    protected void initializePresenter() {
        allUsersDesignationsPresenter = new AllUsersDesignationsPresenter();
        allUsersDesignationsPresenter.setView(this);
        setBasePresenter(allUsersDesignationsPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setActivity(this);
        initViews();

        serviceCenterId = getIntent().getIntExtra(IntentConstants.SERVICE_CENTER_DATA, DEFAULT_VALUE);
        //fetching servicing centers list to add designations
        /*allUsersDesignationsPresenter.doUsersDesignationsApi(SharedPrefsUtils.loginProvider().
                getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE), serviceCenterId);*/
        allUsersDesignationsPresenter.doUsersDesignationsApi(1, 153);
    }

    public void onCheckedChanged(boolean checked) {
        this.isDesignation = checked;
        setListUi();
    }

    private void initViews() {
        initializeToolbar();

        usersList = new ArrayList<>();
        designationsList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, linearLayoutManager.getOrientation());

        designationsListAdapter = new DesignationsListAdapter();
        designationsListAdapter.setClickCallback(iClickCallback);
        binding.allDesignationsRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.allDesignationsRecyclerview.setAdapter(designationsListAdapter);
        binding.allDesignationsRecyclerview.setLayoutManager(linearLayoutManager);

        usersListAdapter = new UsersListAdapter();
        usersListAdapter.setClickCallback(iClickCallback);
        binding.allUsersRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.allUsersRecyclerview.setAdapter(usersListAdapter);
        binding.allUsersRecyclerview.setLayoutManager(linearLayoutManager);

        setListUi();

    }

    private void setListUi() {
        binding.allDesignationsRecyclerview.setVisibility(isDesignation ? View.VISIBLE : View.GONE);
        binding.allUsersRecyclerview.setVisibility(isDesignation ? View.GONE : View.VISIBLE);
    }

    //recyclerview click event
    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            if (isDesignation) {
                //TODO have to show designation updation screen
            } else {
                //TODO have to show user screen with populated data
            }
        }
    };


    private AddServiceCenter getServiceCenterRequestFromResponse(ServiceCenterResponse serviceCenterResponse) {
        AddServiceCenter addServiceCenter = new AddServiceCenter();
        addServiceCenter.setId(serviceCenterResponse.getId());
        addServiceCenter.setAddress(serviceCenterResponse.getAddress());
        addServiceCenter.setBrandId(serviceCenterResponse.getBrandId());
        addServiceCenter.setCategoryId(serviceCenterResponse.getCategoryId());
        addServiceCenter.setContactNo(serviceCenterResponse.getContactNo());
        //TODO hae to change as parameter
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
                Intent intent = new Intent(AllUsersDesignationsActivity.this, isDesignation ? AddDesignationsActivity.class : AddUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadUsersDesignationsList(List<UsersListOfServiceCenters> usersListOfServiceCenters) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        allUsersDesignationsPresenter.disposeAll();
    }
}
