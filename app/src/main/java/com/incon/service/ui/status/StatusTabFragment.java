package com.incon.service.ui.status;

import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.incon.service.ConnectApplication;
import com.incon.service.R;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
import com.incon.service.custom.view.CustomViewPager;
import com.incon.service.databinding.CustomTabBinding;
import com.incon.service.databinding.FragmentStatusTabBinding;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.StatusTabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 12/5/2017.
 */

public class StatusTabFragment extends BaseFragment {
    private static final String TAG = StatusTabFragment.class.getSimpleName();
    private View rootView;
    private FragmentStatusTabBinding binding;
    private StatusTabPagerAdapter adapter;
    private Typeface defaultTypeFace;
    private Typeface selectedTypeFace;
    private String[] tabTitles;

    @Override
    protected void initializePresenter() {

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

        initViewPager();
        loadServiceCentersSpinner();
        loadUsersSpinner();


    }

    private void loadUsersSpinner() {

    }

    private void loadServiceCentersSpinner() {
        // getting service centers list
        List<ServiceCenterResponse> serviceCenterList = ConnectApplication.getAppContext().getServiceCenterList();
        List<String> serviceArray = new ArrayList<>(serviceCenterList.size());
        for (ServiceCenterResponse serviceCenterResponse : serviceCenterList) {
            serviceArray.add(serviceCenterResponse.getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.view_spinner, serviceArray);
        arrayAdapter.setDropDownViewResource(R.layout.view_spinner);
        binding.spinnerServiceCenters.setAdapter(arrayAdapter);
        binding.spinnerServiceCenters.setText(serviceArray.get(0));

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

                int position = tab.getPosition();
                customViewPager.setCurrentItem(position);
                changeTitleFont(position);
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
        return DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()), R.layout.custom_tab, null, false);
    }

    private void setTabIcons() {
        TabLayout tabLayout = binding.tabLayout;
        for (int i = 0; i < tabTitles.length; i++) {
            CustomTabBinding customTabView = getCustomTabView();
            customTabView.tabTv.setText(tabTitles[i]);
            tabLayout.addTab(tabLayout.newTab().setCustomView(customTabView.getRoot()));
        }
    }

}
