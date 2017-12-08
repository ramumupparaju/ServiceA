package com.incon.service.ui.history;

import android.content.res.AssetManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.TextAlertDialogCallback;
import com.incon.service.custom.view.AppCheckBoxListDialog;
import com.incon.service.custom.view.CustomViewPager;
import com.incon.service.databinding.CustomTabBinding;
import com.incon.service.databinding.FragmentHistoryTabBinding;
import com.incon.service.dto.dialog.CheckedModelSpinner;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.history.adapter.HistoryTabPagerAdapter;
import com.incon.service.ui.history.base.BaseTabFragment;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;


public class HistoryTabFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = HistoryTabFragment.class.getSimpleName();
    private FragmentHistoryTabBinding binding;
    private View rootView;
    private Typeface defaultTypeFace;
    private Typeface selectedTypeFace;
    private String[] tabTitles;
    private HistoryTabPagerAdapter adapter;
    private AppCheckBoxListDialog filterBySearchDialog;

    @Override
    protected void initializePresenter() {
    }

    @Override
    public void setTitle() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_history));
    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

        if (rootView == null) {
            binding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_history_tab, container, false);
            rootView = binding.getRoot();
            initViews();
        }
        setTitle();

        return rootView;
    }

    private void initViews() {
        initViewPager();
        binding.searchLayout.searchIconIv.setOnClickListener(this);
        binding.searchLayout.filterIconIv.setOnClickListener(this);
    }

    private void initViewPager() {

        AssetManager assets = getActivity().getAssets();
        defaultTypeFace = Typeface.createFromAsset(assets, "fonts/OpenSans-Regular.ttf");

        selectedTypeFace = Typeface.createFromAsset(assets, "fonts/OpenSans-Bold.ttf");

        tabTitles = getResources().getStringArray(R.array.history_tab);


        final CustomViewPager customViewPager = binding.viewPager;
        final TabLayout tabLayout = binding.tabLayout;

        setTabIcons();
        changeTitleFont(0);

        //set up ViewPager
        adapter = new HistoryTabPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
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

    private void setTabIcons() {
        TabLayout tabLayout = binding.tabLayout;
        for (int i = 0; i < tabTitles.length; i++) {
            CustomTabBinding customTabView = getCustomTabView();
            customTabView.tabTv.setText(tabTitles[i]);
            tabLayout.addTab(tabLayout.newTab().setCustomView(customTabView.getRoot()));
        }
    }

    private CustomTabBinding getCustomTabView() {
        return DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()), R.layout.custom_tab, null, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_icon_iv:
                int currentItem = binding.viewPager.getCurrentItem();
                BaseTabFragment fragmentFromPosition = (BaseTabFragment) adapter.
                        getFragmentFromPosition(currentItem);
                String searchableText = binding.searchLayout.searchEt.getText().toString();
                String filterType = SharedPrefsUtils.cacheProvider().getStringPreference(
                        CachePrefs.FILTER_NAME);
                if (TextUtils.isEmpty(searchableText) || TextUtils.isEmpty(filterType)) {
                    filterType = AppConstants.FilterConstants.NONE;
                }
                fragmentFromPosition.onSearchClickListerner(searchableText, filterType);
                break;
            case R.id.filter_icon_iv:
                showFilterOptionsDialog();
                break;
            default:
                //do nothing
        }
    }

    private void showFilterOptionsDialog() {
        //set previous selected categories as checked
        String selectedFilter = SharedPrefsUtils.cacheProvider().getStringPreference(
                AppConstants.CachePrefs.FILTER_NAME);
        List<CheckedModelSpinner> filterNamesList = new ArrayList<>();
        CharSequence[] items = getResources().getStringArray(
                R.array.select_search);
        for (CharSequence filterName : items) {
            CheckedModelSpinner checkedModelSpinner = new CheckedModelSpinner();
            String name = filterName.toString();
            checkedModelSpinner.setName(name);
            checkedModelSpinner.setChecked(selectedFilter != null
                    && selectedFilter.equalsIgnoreCase(name));
            filterNamesList.add(checkedModelSpinner);
        }
        filterBySearchDialog = new AppCheckBoxListDialog.AlertDialogBuilder(getActivity(), new
                TextAlertDialogCallback() {
                    @Override
                    public void enteredText(String filterName) {
                        SharedPrefsUtils.cacheProvider().setStringPreference(
                                AppConstants.CachePrefs.FILTER_NAME, filterName);
                    }

                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                filterBySearchDialog.dismiss();
                                break;
                            case AlertDialogCallback.CANCEL:
                                filterBySearchDialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.action_filters))
                .spinnerItems(filterNamesList)
                .build();
        filterBySearchDialog.showDialog();
        filterBySearchDialog.setRadioType(true);
    }

}