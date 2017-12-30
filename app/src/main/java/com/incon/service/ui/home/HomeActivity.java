package com.incon.service.ui.home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.incon.service.AppConstants;
import com.incon.service.R;
import com.incon.service.apimodel.components.servicecenter.ServiceCenterResponse;
import com.incon.service.databinding.ActivityHomeBinding;
import com.incon.service.databinding.ToolBarBinding;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.feedback.FeedbackFragment;
import com.incon.service.ui.notifications.fragment.NotificationsFragment;
import com.incon.service.ui.reports.ReportsFragment;
import com.incon.service.ui.settings.SettingsActivity;
import com.incon.service.ui.status.StatusTabFragment;
import com.incon.service.utils.DeviceUtils;
import com.incon.service.utils.SharedPrefsUtils;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.LinkedHashMap;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomeContract.View {

    private static final int TAB_Status = 0;
    private static final int TAB_Reports = 1;
    private static final int TAB_FeedBack = 2;

    private View rootView;
    private HomePresenter homePresenter;
    private ActivityHomeBinding binding;
    private ToolBarBinding toolBarBinding;

    private LinkedHashMap<Integer, Fragment> tabFragments = new LinkedHashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initializePresenter() {
        homePresenter = new HomePresenter();
        homePresenter.setView(this);
        setBasePresenter(homePresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setActivity(this);
        rootView = binding.getRoot();
        disableAllAnimation(binding.bottomNavigationView);
        binding.bottomNavigationView.setTextVisibility(true);
        setBottomNavigationViewListeners();
        handleBottomViewOnKeyBoardUp();

        SharedPrefsUtils.cacheProvider().setBooleanPreference(AppConstants.CachePrefs.IS_SCAN_FIRST, true);

        binding.bottomNavigationView.setCurrentItem(TAB_Status);

        //changed preference as otp verified
        SharedPrefsUtils.loginProvider().setBooleanPreference(AppConstants.LoginPrefs.IS_REGISTERED, false);
        SharedPrefsUtils.loginProvider().setBooleanPreference(AppConstants.LoginPrefs.IS_FORGOT_PASSWORD, false);

        //hockey app update checking
//        UpdateManager.register(this);
        initializeToolBar();
        getSupportFragmentManager().addOnBackStackChangedListener(backStackChangedListener);

    }

    public void setToolbarTitle(String title) {
        toolBarBinding.toolbarTitleTv.setText(title);
    }

    protected void initializeToolBar() {
        LayoutInflater layoutInflater = getLayoutInflater();
        toolBarBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.tool_bar, null, false);
        setSupportActionBar(toolBarBinding.toolbar);
        setToolbarTitle(getString(R.string.title_history));

        toolBarBinding.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            }
        });
        toolBarBinding.toolbarRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // onUserQrCodeClick();
            }
        });
        replaceToolBar(toolBarBinding.toolbar);
    }

    private FragmentManager.OnBackStackChangedListener backStackChangedListener =
            new FragmentManager.OnBackStackChangedListener() {
                public void onBackStackChanged() {
                    BaseFragment currentFragment = (BaseFragment) getSupportFragmentManager().
                            findFragmentById(R.id.container);
                    currentFragment.setTitle();
                }
            };

    public void replaceToolBar(View toolBarView) {
        if (toolBarView == null) {
            return;
        }
        binding.containerToolbar.removeAllViews();
        binding.containerToolbar.addView(toolBarView);
    }

    private void setBottomNavigationViewListeners() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        setBottomTabStatus(item.getItemId());
                        return true;
                    }
                });
    }

    private void setBottomTabStatus(int selectedItemId) {
        Class<? extends Fragment> aClass = null;
        switch (selectedItemId) {
            case R.id.action_status:
                aClass = StatusTabFragment.class;
                break;
            case R.id.action_reports:
                aClass = ReportsFragment.class;
                break;
            case R.id.action_feedback:
                aClass = FeedbackFragment.class;
                break;
            case R.id.action_notifications:
                aClass = NotificationsFragment.class;
                break;

            default:
                break;
        }

        Fragment tabFragment = tabFragments.get(selectedItemId);
        if (tabFragment == null) {
            Fragment fragment = replaceFragment(aClass, null);
            tabFragments.put(selectedItemId, fragment);
        } else {
            replaceFragment(tabFragment.getClass(), null);
        }
    }

    private void handleBottomViewOnKeyBoardUp() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                        binding.bottomNavigationView.setVisibility(View.VISIBLE);
                        if (heightDiff > DeviceUtils.dpToPx(HomeActivity.this, 200)) {
                            // if more than 200 dp, it's probably a keyboard...
                            binding.bottomNavigationView.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void disableAllAnimation(BottomNavigationViewEx bnve) {
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
    }

    @Override
    public void serviceCenters(List<ServiceCenterResponse> serviceCentersList) {
        //TODO have to load users data
    }
}
