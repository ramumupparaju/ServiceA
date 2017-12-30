package com.incon.service.ui.settings.userdesignation;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.incon.service.R;
import com.incon.service.apimodel.components.adddesignation.DesignationData;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.ActivityAllUsersDesignationsBinding;
import com.incon.service.dto.adduser.AddUser;
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
    private ArrayList<AddUser> usersList;

    private DesignationsListAdapter designationsListAdapter;
    private ArrayList<DesignationData> designationsList;


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
        doUserDesignationsApi();
    }

    private void doUserDesignationsApi() {
        allUsersDesignationsPresenter.doUsersDesignationsApi(SharedPrefsUtils.loginProvider().
                getIntegerPreference(LoginPrefs.USER_ID, DEFAULT_VALUE), serviceCenterId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCodes.ADD_USER_DESIGNATION: {
                    doUserDesignationsApi();
                }
                break;
            }
        }
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

        if (isDesignation && designationsList.size() == 0) {
            binding.emptyData.setVisibility(View.VISIBLE);
        } else if (!isDesignation && usersList.size() == 0) {
            binding.emptyData.setVisibility(View.VISIBLE);
        } else {
            binding.emptyData.setVisibility(View.GONE);
        }
        binding.allDesignationsRecyclerview.setVisibility(isDesignation ? View.VISIBLE : View.GONE);
        binding.allUsersRecyclerview.setVisibility(isDesignation ? View.GONE : View.VISIBLE);
    }

    //recyclerview click event
    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            if (isDesignation) {
                DesignationData designationResponse = designationsList.get(position);
                Intent intent = new Intent(AllUsersDesignationsActivity.this, AddDesignationsActivity.class);
                intent.putExtra(IntentConstants.DESIGNATION_DATA, designationResponse);
                intent.putExtra(IntentConstants.SERVICE_CENTER_DATA, serviceCenterId);
                startActivityForResult(intent, RequestCodes.ADD_USER_DESIGNATION);
            } else {
                AddUser usersListOfServiceCenters = usersList.get(position);
                Intent intent = new Intent(AllUsersDesignationsActivity.this, AddUserActivity.class);
                intent.putParcelableArrayListExtra(IntentConstants.DESIGNATION_DATA, designationsList);
                intent.putExtra(IntentConstants.USER_DATA, usersListOfServiceCenters);
                intent.putParcelableArrayListExtra(IntentConstants.USER_DATA_LIST, usersList);
                startActivityForResult(intent, RequestCodes.ADD_USER_DESIGNATION);
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
                if (!isDesignation) {
                    intent.putParcelableArrayListExtra(IntentConstants.DESIGNATION_DATA, designationsList);
                    intent.putParcelableArrayListExtra(IntentConstants.USER_DATA_LIST, usersList);
                }
                startActivityForResult(intent, RequestCodes.ADD_USER_DESIGNATION);
            }
        });
    }

    @Override
    public void loadUsersDesignationsList(List<AddUser> usersList,
                                          List<DesignationData> designationsList) {

        if (usersList == null) {
            usersList = new ArrayList<>();
        }
        if (designationsList == null) {
            designationsList = new ArrayList<>();
        }

        this.usersList = (ArrayList<AddUser>) usersList;
        this.designationsList = (ArrayList<DesignationData>) designationsList;

        usersListAdapter.setData(usersList);
        designationsListAdapter.setData(designationsList);

        setListUi();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        allUsersDesignationsPresenter.disposeAll();
    }
}
