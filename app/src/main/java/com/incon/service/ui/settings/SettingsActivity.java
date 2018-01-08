package com.incon.service.ui.settings;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.incon.service.AppConstants;
import com.incon.service.AppUtils;
import com.incon.service.BuildConfig;
import com.incon.service.R;
import com.incon.service.callbacks.AlertDialogCallback;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.custom.view.AppAlertVerticalTwoButtonsDialog;
import com.incon.service.databinding.ActivitySettingsBinding;
import com.incon.service.dto.settings.SettingsItem;
import com.incon.service.ui.BaseActivity;
import com.incon.service.ui.changepassword.ChangePasswordActivity;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.settings.adapters.SettingsAdapter;
import com.incon.service.ui.settings.service.AllServiceCentersActivity;
import com.incon.service.ui.settings.update.UpDateUserProfileActivity;
import com.incon.service.ui.settings.userdesignation.AllUsersDesignationsActivity;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;

import static com.incon.service.AppConstants.LoginPrefs.USER_NAME;
import static com.incon.service.AppConstants.UserConstants.SUPER_ADMIN_TYPE;


/**
 * Created on 26 Jul 2017 3:47 PM.
 */
public class SettingsActivity extends BaseActivity implements SettingsContract.View,
        IClickCallback {

    private SettingsPresenter menuPresenter;
    private ActivitySettingsBinding binding;
    private SettingsAdapter menuAdapter;
    private ArrayList<SettingsItem> menuItems;
    private AppAlertVerticalTwoButtonsDialog dialog;
    private int position = -1;
    private int userType;
    private int isAdmin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initializePresenter() {
        menuPresenter = new SettingsPresenter();
        menuPresenter.setView(this);
    }

    @Override
    protected void onCreateView(Bundle saveInstanceState) {
        // handle events from here using android binding
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        binding.setActivity(this);
        binding.appVersion.setText(getString(R.string.app_version) + BuildConfig.VERSION_NAME);
        binding.toolbarLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToHome = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(backToHome);
            }
        });

        userType = SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_TYPE, DEFAULT_VALUE);
        if (userType == UserConstants.SUPER_ADMIN_TYPE) {
            isAdmin = BooleanConstants.IS_TRUE;
        } else {
            isAdmin = SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.USER_IS_ADMIN, BooleanConstants.IS_FALSE);
        }

        initViews();
        initializeAdapter();
        prepareMenuData();
        menuAdapter.setData(menuItems);
    }

    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,
                linearLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.list_divider));
        binding.recyclerviewMenu.addItemDecoration(dividerItemDecoration);
        binding.recyclerviewMenu.setLayoutManager(linearLayoutManager);
    }

    private void initializeAdapter() {
        menuAdapter = new SettingsAdapter();
        menuAdapter.setClickCallback(this);
        binding.recyclerviewMenu.setAdapter(menuAdapter);
    }

    private void prepareMenuData() {

        int[] icons = {
                R.drawable.ic_menu_change_password,
                R.drawable.ic_menu_change_password,
                R.drawable.ic_menu_timings,
                R.drawable.ic_menu_contact_details,
                R.drawable.ic_menu_logout_svg};
        String[] menuTitles = getResources().getStringArray(R.array.side_menu_items_list);

        menuItems = new ArrayList<>();
        SettingsItem menuItemHeader = new SettingsItem();
        menuItemHeader.setRowType(SettingsAdapter.ROW_TYPE_HEADER);
        menuItemHeader.setText(SharedPrefsUtils.loginProvider().
                getStringPreference(USER_NAME));
        menuItems.add(menuItemHeader);
        for (int menuPos = 0; menuPos < menuTitles.length; menuPos++) {
            SettingsItem menuItem = new SettingsItem();
            menuItem.setRowType(SettingsAdapter.ROW_TYPE_ITEM);
            menuItem.setIcon(icons[menuPos]);
            if (menuPos == 0) { //checks user type based on user type we are changing options
                if (userType == SUPER_ADMIN_TYPE) {
                    menuItem.setText(menuTitles[menuPos]);
                } else if (isAdmin == BooleanConstants.IS_TRUE) {
                    menuItem.setText(getString(R.string.action_service_center));
                } else {
                    continue;
                }
            } else {
                menuItem.setText(menuTitles[menuPos]);
            }
            menuItems.add(menuItem);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (menuItems != null) {
            SettingsItem menuItemHeader = menuItems.get(0);
            menuItemHeader.setText(SharedPrefsUtils.loginProvider().
                    getStringPreference(USER_NAME));
            menuAdapter.setData(menuItems);
        }
    }


    @Override
    public void onClickPosition(int position) {
        this.position = position;
        if (position != 0) { // based on user type we are changing option click actions
            if (isAdmin == BooleanConstants.IS_FALSE) {
                ++position;
            }
        }
        switch (position) {

            case MenuConstants.PROFILE:
                Intent userProfileIntent = new Intent(this, UpDateUserProfileActivity.class);
                startActivity(userProfileIntent);
                break;

            case MenuConstants.ALL_SERVICE_CENTERS:
                if (userType == UserConstants.SUPER_ADMIN_TYPE) { // Based on usertype we are launching different activities
                    Intent addUserIntent = new Intent(this, AllServiceCentersActivity.class);
                    startActivity(addUserIntent);
                } else {
                    Intent intent = new Intent(this, AllUsersDesignationsActivity.class);
                    intent.putExtra(IntentConstants.SERVICE_CENTER_DATA, SharedPrefsUtils.loginProvider().getIntegerPreference(LoginPrefs.SERVICE_CENTER_ID, DEFAULT_VALUE));
                    startActivity(intent);
                }
                break;

            case MenuConstants.CHANGE_PWD:
                Intent changePasswordIntent = new Intent(this, ChangePasswordActivity.class);
                startActivity(changePasswordIntent);
                break;

            case MenuConstants.TIMEINGS:
                AppUtils.shortToast(SettingsActivity.this, getString(
                        R.string.title_menu_timings));
                break;

            case MenuConstants.CONTACTDETAILS:
                AppUtils.shortToast(SettingsActivity.this, getString(
                        R.string.title_menu_contact_details));
                break;

            case MenuConstants.LOGOUT:
                showLogoutDialog();
                break;

            default:
                break;
        }
    }

    //  logout dialog
    private void showLogoutDialog() {
        dialog = new AppAlertVerticalTwoButtonsDialog.AlertDialogBuilder(this, new
                AlertDialogCallback() {
                    @Override
                    public void alertDialogCallback(byte dialogStatus) {
                        switch (dialogStatus) {
                            case AlertDialogCallback.OK:
                                dialog.dismiss();
                                break;
                            case AlertDialogCallback.CANCEL:
                                onLogoutClick();
                                dialog.dismiss();
                                break;
                            default:
                                break;
                        }
                    }
                }).title(getString(R.string.dialog_logout))
                .button1Text(getString(R.string.action_cancel))
                .button2Text(getString(R.string.action_logout))
                .build();
        dialog.showDialog();
        dialog.setButtonBlueUnselectBackground();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        menuPresenter.disposeAll();
    }

    @Override
    public void loadDefaultsData() {
        if (position != -1) {
            onClickPosition(position);
        }

    }
}
