package com.incon.service.ui.settings.userdesignation;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.incon.service.R;
import com.incon.service.apimodel.components.adddesignation.DesignationResponse;
import com.incon.service.apimodel.components.userslistofservicecenters.UsersListOfServiceCenters;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.IEditClickCallback;
import com.incon.service.databinding.ActivityAllUsersDesignationsBinding;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.adddesignations.AddDesignationsActivity;
import com.incon.service.ui.adduser.AddUserActivity;
import com.incon.service.ui.settings.userdesignation.adapter.DesignationsListAdapter;
import com.incon.service.ui.settings.userdesignation.adapter.UsersListAdapter;
import com.incon.service.utils.SharedPrefsUtils;

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
    private List<DesignationResponse> designationsList;


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
        allUsersDesignationsPresenter.doUsersDesignationsApi(SharedPrefsUtils.loginProvider().
                getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE), serviceCenterId);
    }

    public void onCheckedChanged(boolean checked) {
        this.isDesignation = checked;
        setListUi();
    }

    private void initViews() {
        initializeToolbar();

        usersList = new ArrayList<>();
        designationsList = new ArrayList<>();

        designationsListAdapter = new DesignationsListAdapter(designationsList);
        designationsListAdapter.setClickCallback(iClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, linearLayoutManager.getOrientation());
        binding.allDesignationsRecyclerview.addItemDecoration(dividerItemDecoration);
        binding.allDesignationsRecyclerview.setAdapter(designationsListAdapter);
        binding.allDesignationsRecyclerview.setLayoutManager(linearLayoutManager);

        usersListAdapter = new UsersListAdapter(usersList);
        usersListAdapter.setClickCallback(iClickCallback);
        linearLayoutManager = new LinearLayoutManager(this);
        dividerItemDecoration = new DividerItemDecoration(
                this, linearLayoutManager.getOrientation());
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
    private IEditClickCallback iClickCallback = new IEditClickCallback() {
        @Override
        public void onEditClickPosition(int position) {
            //TODO have to pass object
        }

        @Override
        public void onClickPosition(int position) {
            if (isDesignation) {
                DesignationResponse designationResponse = designationsList.get(position);
                Intent intent = new Intent(AllUsersDesignationsActivity.this, AddDesignationsActivity.class);
                intent.putExtra(IntentConstants.DESIGNATION_DATA, designationResponse);
                startActivity(intent);
            } else {
                UsersListOfServiceCenters usersListOfServiceCenters = usersList.get(position);
                Intent intent = new Intent(AllUsersDesignationsActivity.this, AddUserActivity.class);
                intent.putExtra(IntentConstants.USER_DATA, usersListOfServiceCenters);
                startActivity(intent);
            }
        }
    };

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
                intent.putExtra(IntentConstants.SERVICE_CENTER_DATA, serviceCenterId);
                startActivity(intent);
            }
        });
    }

    @Override
    public void loadUsersDesignationsList(List<UsersListOfServiceCenters> usersList,
                                          List<DesignationResponse> designationsList) {

        if (usersList == null) {
            usersList = new ArrayList<>();
        }
        if (designationsList == null) {
            designationsList = new ArrayList<>();
        }

        this.usersList = usersList;
        this.designationsList = designationsList;

        usersListAdapter.setData(usersList);
        usersListAdapter.notifyDataSetChanged();

        designationsListAdapter.setData(designationsList);
        designationsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        allUsersDesignationsPresenter.disposeAll();
    }
}
