package com.incon.service.ui.status;

import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.custom.view.CustomViewPager;
import com.incon.service.databinding.CustomTabBinding;
import com.incon.service.databinding.FragmentStatusTabBinding;
import com.incon.service.dto.addservicecenter.AddServiceCenter;
import com.incon.service.dto.adduser.AddUser;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.StatusTabPagerAdapter;
import com.incon.service.ui.status.base.base.BaseProductOptionsFragment;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 12/5/2017.
 */

public class StatusTabFragment extends BaseFragment implements StatusTabContract.View {
    private static final String TAG = StatusTabFragment.class.getSimpleName();
    private FragmentStatusTabBinding binding;
    private View rootView;
    private StatusTabPresenter newRequestPresenter;
    private StatusTabPagerAdapter adapter;
    private int currentTabPosition;
    private Typeface defaultTypeFace;
    private Typeface selectedTypeFace;
    private String[] tabTitles;

    private List<AddServiceCenter> serviceCenterList;
    private int serviceCentersSelectedPosition = -1;
    private List<AddUser> usersListOfServiceCenters;
    private int usersSelectedPosition = -1;

    @Override
    protected void initializePresenter() {
        newRequestPresenter = new StatusTabPresenter();
        newRequestPresenter.setView(this);
        setBasePresenter(newRequestPresenter);
    }

    @Override
    public void setTitle() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_status));
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_status_tab, container, false);
            rootView = binding.getRoot();
            initViews();
        }
        setTitle();

        return rootView;
    }

    private void initViews() {
        binding.spinnerServiceCenters.setVisibility(View.GONE);
        binding.spinnerUsers.setVisibility(View.GONE);

        int userType = SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_TYPE, DEFAULT_VALUE);
        if (userType == UserConstants.SUPER_ADMIN_TYPE) {
            binding.spinnerServiceCenters.setVisibility(View.VISIBLE);
            loadServiceCentersSpinner();
        } else if (userType == UserConstants.ADMIN_TYPE) {
            int serviceCenterId = SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs
                    .SERVICE_CENTER_ID, DEFAULT_VALUE);
            doAllUsersInServiceCenterApi(serviceCenterId);
        } else {
            initViewPager();
        }
    }

    private void doAllUsersInServiceCenterApi(int serviceCenterId) {
        newRequestPresenter.getUsersListOfServiceCenters(serviceCenterId);
    }

    private void loadUsersSpinner(List<AddUser> usersListOfServiceCenters) {
        binding.spinnerUsers.setVisibility(View.VISIBLE);

        List<String> usersArray = new ArrayList<>(usersListOfServiceCenters.size());

        if (usersListOfServiceCenters == null || usersListOfServiceCenters.size() == 0) {
            usersSelectedPosition = MINUS_ONE;
            binding.spinnerUsers.setText("");
            binding.spinnerUsers.setOnItemClickListener(null);
        } else {
            for (AddUser usersListOfServiceCenter : usersListOfServiceCenters) {
                usersArray.add(usersListOfServiceCenter.getName());
            }

            usersSelectedPosition = 0;
            binding.spinnerUsers.setText(usersArray.get(0));
            binding.spinnerUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (usersSelectedPosition != position) {
                        usersSelectedPosition = position;
                        refreshFragmentByPosition(usersSelectedPosition);

                    }

                    //For avoiding double tapping issue
                    if (binding.spinnerUsers.getOnItemClickListener() != null) {
                        binding.spinnerUsers.onItemClick(parent, view, position, id);
                    }
                }
            });
            refreshFragmentByPosition(MINUS_ONE); // load all requests based on service center
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, usersArray);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerUsers.setAdapter(arrayAdapter);
    }

    private void refreshFragmentByPosition(int usersSelectedPosition) {
        ((HomeActivity) getActivity()).setUserId(usersSelectedPosition ==
                MINUS_ONE ? usersSelectedPosition : usersListOfServiceCenters.get
                (usersSelectedPosition).getId());
        BaseProductOptionsFragment fragmentFromPosition = (BaseProductOptionsFragment) adapter.getFragmentFromPosition(currentTabPosition);
        fragmentFromPosition.doRefresh(false);

    }

    private void loadServiceCentersSpinner() {
        serviceCentersSelectedPosition = 0;
        // getting service centers list
        serviceCenterList = ConnectApplication.getAppContext().getServiceCenterList();
        List<String> serviceArray = new ArrayList<>(serviceCenterList.size());
        for (AddServiceCenter serviceCenterResponse : serviceCenterList) {
            serviceArray.add(serviceCenterResponse.getName());
        }

        ((HomeActivity) getActivity()).setServiceCenterId(serviceCenterList.get(serviceCentersSelectedPosition).getId());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, serviceArray);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerServiceCenters.setAdapter(arrayAdapter);
        binding.spinnerServiceCenters.setText(serviceArray.get(0));
        binding.spinnerServiceCenters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (serviceCentersSelectedPosition != position) {
                    serviceCentersSelectedPosition = position;

                    usersSelectedPosition = -1;
                    usersListOfServiceCenters.clear();
                    loadUsersSpinner(usersListOfServiceCenters);
                    Integer serviceCenterId = serviceCenterList.get(serviceCentersSelectedPosition).getId();
                    ((HomeActivity) getActivity()).setServiceCenterId(serviceCenterId);
                    doAllUsersInServiceCenterApi(serviceCenterId);
                }

                //For avoiding double tapping issue
                if (binding.spinnerServiceCenters.getOnItemClickListener() != null) {
                    binding.spinnerServiceCenters.onItemClick(parent, view, position, id);
                }
            }
        });
        doAllUsersInServiceCenterApi(serviceCenterList.get(serviceCentersSelectedPosition).getId());
    }

    private void initViewPager() {
        AssetManager assets = getActivity().getAssets();
        defaultTypeFace = Typeface.createFromAsset(assets, "fonts/OpenSans-Regular.ttf");

        selectedTypeFace = Typeface.createFromAsset(assets, "fonts/OpenSans-Bold.ttf");

        tabTitles = getResources().getStringArray(R.array.status_tab);

        final CustomViewPager customViewPager = binding.viewPager;
        final TabLayout tabLayout = binding.tabLayout;
        setTabIcons();
        changeTitleFont(0);

        adapter = new StatusTabPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        customViewPager.setAdapter(adapter);


        customViewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                currentTabPosition = tab.getPosition();
                customViewPager.setCurrentItem(currentTabPosition);
                changeTitleFont(currentTabPosition);
                refreshFragmentByPosition(usersSelectedPosition);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void changeTitleFont(int position) {
        for (int i = 0; i < tabTitles.length; i++) {
            View view = binding.tabLayout.getTabAt(i).getCustomView();
            CustomTabBinding customTabView = DataBindingUtil.bind(view);
            customTabView.tabTv.setTypeface(i == position
                    ? selectedTypeFace
                    : defaultTypeFace);
        }
    }

    private CustomTabBinding getCustomTabView() {
        return DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.custom_tab, null, false);
    }

    private void setTabIcons() {
        TabLayout tabLayout = binding.tabLayout;
        for (int i = 0; i < tabTitles.length; i++) {
            CustomTabBinding customTabView = getCustomTabView();
            customTabView.tabTv.setText(tabTitles[i]);
            tabLayout.addTab(tabLayout.newTab().setCustomView(customTabView.getRoot()));
        }
    }

    @Override
    public void loadUsersListOfServiceCenters(List<AddUser> usersListOfServiceCenters) {

        //adding all to the users list
        AddUser allUsersData = new AddUser();
        allUsersData.setId(-1);
        allUsersData.setName(LABEL_ALL);
        usersListOfServiceCenters.add(0, allUsersData);
        this.usersListOfServiceCenters = usersListOfServiceCenters;

        if (adapter == null) // prevents double creation of tab items
            initViewPager();

        loadUsersSpinner(usersListOfServiceCenters);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        newRequestPresenter.disposeAll();
    }
}
