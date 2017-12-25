package com.incon.service.ui.settings.service;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.incon.service.R;
import com.incon.service.apimodel.components.servicecenterresponse.ServiceCenterResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.custom.view.AppListDialog;
import com.incon.service.custom.view.CustomTextInputLayout;
import com.incon.service.custom.view.PickImageDialog;
import com.incon.service.databinding.ActivityAddDesignationsBinding;
import com.incon.service.databinding.ActivityAllServiceCentersBinding;
import com.incon.service.dto.adddesignation.AddDesignation;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.adddesignations.AddDesignationsActivity;
import com.incon.service.ui.adddesignations.AddDesignationsPresenter;
import com.incon.service.ui.addservicecenter.AddServiceCenterActivity;
import com.incon.service.ui.adduser.AddUserActivity;
import com.incon.service.ui.settings.adapters.AllServiceCentersAdapter;
import com.incon.service.utils.SharedPrefsUtils;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;
import java.util.HashMap;
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
    private AppListDialog appListDialog;

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
    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
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
                showNewOptionDialog();
            }
        });
    }

    private void showNewOptionDialog() {
        String[] items = getResources().getStringArray(R.array.select_new_options);

        appListDialog = new AppListDialog();
        appListDialog.setClickCallback(clickCallback);
        appListDialog.initDialogLayout(this, items);
    }

    IClickCallback clickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            Intent intent = null;
            switch (position) {
                case 0:
                    //load add new center screen;
                    intent = new Intent(AllServiceCentersActivity.this, AddServiceCenterActivity.class);
                    break;
                case 1:
                    //load new user screen
                    intent = new Intent(AllServiceCentersActivity.this, AddUserActivity.class);
                    break;
                case 2:
                    //load designations screen
                    intent = new Intent(AllServiceCentersActivity.this, AddDesignationsActivity.class);
                    break;
                default:
                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }

        }
    };

    /**
     * onclick listener for add designations
     */
    public void onClickNewDesignation() {


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
