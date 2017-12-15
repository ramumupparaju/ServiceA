package com.incon.service.ui.register;

import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.incon.service.AppUtils;
import com.incon.service.R;
import com.incon.service.databinding.ActivityRegistrationBinding;
import com.incon.service.databinding.ViewRegistrationBottomButtonsBinding;
import com.incon.service.dto.registration.Registration;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.register.adapter.RegistrationPagerAdapter;
import com.incon.service.ui.register.fragment.RegistrationUserFragment;
import com.incon.service.utils.DeviceUtils;


public class RegistrationActivity extends BaseActivity implements RegistrationContract.View {

    private View rootView;
    public RegistrationPresenter registrationPresenter;
    private ActivityRegistrationBinding binding;
    private ViewRegistrationBottomButtonsBinding buttonsBinding;
    private RegistrationPagerAdapter registrationPagerAdapter;
    private int currentPagePos;
    private Registration registration;
    private View.OnClickListener buttonClickListner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_left:
                    navigateToBack();
                    break;
                case R.id.button_right:
                    Fragment currentRegistionFragment = registrationPagerAdapter.getItem(
                            binding.viewpagerRegister.getCurrentItem());
                    if (currentRegistionFragment instanceof RegistrationUserFragment) {
                        ((RegistrationUserFragment) currentRegistionFragment).onClickNext();
                    } else if (currentRegistionFragment instanceof RegistrationStoreFragment) {
                        ((RegistrationStoreFragment) currentRegistionFragment).onClickNext();
                    }
                    break;
                default:
                    //do nothing
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    protected void initializePresenter() {
        registrationPresenter = new RegistrationPresenter();
        registrationPresenter.setView(this);
        setBasePresenter(registrationPresenter);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        buttonsBinding = binding.includeRegisterBottomButtons;
        rootView = binding.getRoot();

        loadData();
    }

    private void loadData() {
        registrationPresenter.defaultsApi();
        registration = new Registration();

        buttonsBinding.buttonLeft.setOnClickListener(buttonClickListner);
        buttonsBinding.buttonLeft.setText(getString(R.string.action_back));
        buttonsBinding.buttonRight.setOnClickListener(buttonClickListner);
        buttonsBinding.buttonRight.setText(getString(R.string.action_next));
        handleBottomViewOnKeyBoardUp();

    }

    public Registration getRegistration() {
        return registration;
    }

    private void handleBottomViewOnKeyBoardUp() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                        binding.includeRegisterBottomButtons.getRoot().setVisibility(View.VISIBLE);
                        if (heightDiff > DeviceUtils.dpToPx(RegistrationActivity.this, 200)) {
                            // if more than 200 dp, it's probably a keyboard...
                            binding.includeRegisterBottomButtons.getRoot().setVisibility(View.GONE);
                        }
                    }
                });
    }

    // Instantiate a PagerAdapter and sets to viewpager.
    private void initializePagerAdapter() {
        registrationPagerAdapter = new RegistrationPagerAdapter(getSupportFragmentManager());
        binding.viewpagerRegister.setAdapter(registrationPagerAdapter);
        binding.viewpagerRegister.addOnPageChangeListener(registerOnPageChangeListener);
        registerOnPageChangeListener.onPageSelected(0);
    }

    /**
     * these method is called from Registrationuser and store screen when user fills valid data
     */
    @Override
    public void navigateToNext() {
        currentPagePos = binding.viewpagerRegister.getCurrentItem();
        if (currentPagePos < binding.viewpagerRegister.getChildCount()) {
            binding.viewpagerRegister.setCurrentItem(++currentPagePos);
        }
    }

    @Override
    public void navigateToBack() {
        currentPagePos = binding.viewpagerRegister.getCurrentItem();
        if (currentPagePos > 0) {
            binding.viewpagerRegister.setCurrentItem(--currentPagePos);
        } else {
            finish();
        }
    }

    @Override
    public void startRegistration(boolean startRegistration) {
        if (!startRegistration) {
            AppUtils.shortToast(this, getString(R.string.error_network));
            finish();
        } else {
            initializePagerAdapter();
        }
    }

    @Override
    public void onBackPressed() {
        navigateToBack();
    }

    private ViewPager.OnPageChangeListener registerOnPageChangeListener
            = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position,
                                   float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            /*PageListener fragment = (PageListener) registrationPagerAdapter
                    .getItem(position);
            fragment.setViewPagerListener(RegistrationActivity.this);
            fragment.onPageDisplayed(binding.includeRegisterBottomButtons, binding);*/
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    public void focusOnView(final ScrollView scrollView, final View view) {
        final Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
        view.requestRectangleOnScreen(rect, false);
        /*scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, editTextView.getBottom());
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        registrationPresenter.disposeAll();
    }

}