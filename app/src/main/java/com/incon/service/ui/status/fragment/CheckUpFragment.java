package com.incon.service.ui.status.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.FragmentCheckupBinding;
import com.incon.service.ui.status.adapter.CheckUpAdapter;
import com.incon.service.ui.status.base.base.BaseTabFragment;
import com.incon.service.utils.SharedPrefsUtils;

/**
 * Created by PC on 12/5/2017.
 */

public class CheckUpFragment extends BaseTabFragment implements CheckUpContract.View {
    private FragmentCheckupBinding fragmentCheckupBinding;
    private View rootView;
    private CheckUpAdapter checkUpAdapter;
    private CheckUpPresenter checkUpPresenter;
    private int userId;

    @Override
    protected void initializePresenter() {
        checkUpPresenter = new CheckUpPresenter();
        checkUpPresenter.setView(this);
        setBasePresenter(checkUpPresenter);

    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            fragmentCheckupBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_checkup,
                    container, false);

            initViews();
            loadBottomSheet();

            rootView = fragmentCheckupBinding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        checkUpAdapter = new CheckUpAdapter();
        checkUpAdapter.setClickCallback(iClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        fragmentCheckupBinding.requestRecyclerview.addItemDecoration(dividerItemDecoration);
        fragmentCheckupBinding.requestRecyclerview.setAdapter(checkUpAdapter);
        fragmentCheckupBinding.requestRecyclerview.setLayoutManager(linearLayoutManager);
        userId = SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE);
    }


    private void dismissSwipeRefresh() {
        if (fragmentCheckupBinding.swiperefresh.isRefreshing()) {
            fragmentCheckupBinding.swiperefresh.setRefreshing(false);
        }
    }

    @Override
    public void loadBottomSheet() {
        super.loadBottomSheet();
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });

    }


    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {

            checkUpAdapter.clearSelection();
            FetchNewRequestResponse fetchNewRequestResponse = checkUpAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            checkUpAdapter.notifyDataSetChanged();

            createBottomSheetView(position);
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetView(int position) {
        productSelectedPosition = position;
//        bottomSheetCheckupFragmentBinding.topRow.setVisibility(View.GONE);

        String[] bottomNames = new String[4];
        bottomNames[0] = getString(R.string.bottom_option_customer);
        bottomNames[1] = getString(R.string.bottom_option_product);
        bottomNames[2] = getString(R.string.bottom_option_service_center);
        bottomNames[3] = getString(R.string.bottom_option_status_update);

        int[] bottomDrawables = new int[4];
        bottomDrawables[0] = R.drawable.ic_option_customer;
        bottomDrawables[1] = R.drawable.ic_option_product;
        bottomDrawables[2] = R.drawable.ic_option_find_service_center;
        bottomDrawables[3] = R.drawable.ic_option_delete;

        /*bottomSheetCheckupFragmentBinding.bottomRow.removeAllViews();
        int length = bottomNames.length;
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        0, ViewGroup.LayoutParams.WRAP_CONTENT, length);
        params.setMargins(1, 1, 1, 1);
        for (int i = 0; i < length; i++) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setWeightSum(1f);
            linearLayout.setGravity(Gravity.CENTER);
            CustomBottomViewBinding customBottomView = getCustomBottomView();
            customBottomView.viewTv.setText(bottomNames[i]);
            customBottomView.viewTv.setTextSize(10f);
            customBottomView.viewLogo.setImageResource(bottomDrawables[i]);
            View bottomRootView = customBottomView.getRoot();
            bottomRootView.setTag(i);
            linearLayout.addView(bottomRootView);
            bottomRootView.setOnClickListener(bottomViewClickListener);
            bottomSheetCheckupFragmentBinding.bottomRow.addView(linearLayout, params);
        }*/
    }


    private View.OnClickListener bottomViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();
            String[] bottomOptions;
            int[] topDrawables;
            changeBackgroundText(tag, view);
            if (tag == 0) {
                bottomOptions = new String[3];
                bottomOptions[0] = getString(R.string.bottom_option_Call);
                bottomOptions[1] = getString(R.string.bottom_option_location);
                bottomOptions[2] = getString(R.string.bottom_option_edit);
                topDrawables = new int[3];
                topDrawables[0] = R.drawable.ic_option_call;
                topDrawables[1] = R.drawable.ic_option_location;
                topDrawables[2] = R.drawable.ic_option_feedback;
            } else if (tag == 1) {
                bottomOptions = new String[3];
                bottomOptions[0] = getString(R.string.bottom_option_warranty_details);
                bottomOptions[1] = getString(R.string.bottom_option_past_history);
                bottomOptions[2] = getString(R.string.bottom_option_special_instructions);
                topDrawables = new int[3];
                topDrawables[0] = R.drawable.ic_options_features;
                topDrawables[1] = R.drawable.ic_option_pasthistory;
                topDrawables[2] = R.drawable.ic_option_sp_instructions;
            } else if (tag == 2) {
                bottomOptions = new String[2];
                bottomOptions[0] = getString(R.string.bottom_option_Call);
                bottomOptions[1] = getString(R.string.bottom_option_assign);
                topDrawables = new int[2];
                topDrawables[0] = R.drawable.ic_option_call;
                topDrawables[1] = R.drawable.ic_option_assign;
                changeBackgroundText(tag, view);
            } else {
                bottomOptions = new String[3];
                bottomOptions[0] = getString(R.string.bottom_option_estimation);
                bottomOptions[1] = getString(R.string.bottom_option_note);
                bottomOptions[2] = getString(R.string.bottom_option_close);
                topDrawables = new int[3];
                topDrawables[0] = R.drawable.ic_option_estimation;
                topDrawables[1] = R.drawable.ic_option_pasthistory;
                topDrawables[2] = R.drawable.ic_option_close;
            }
            /*bottomSheetCheckupFragmentBinding.secondtopRow.removeAllViews();
            bottomSheetCheckupFragmentBinding.topRow.removeAllViews();
            int length1 = bottomOptions.length;
            bottomSheetCheckupFragmentBinding.topRow.setVisibility(View.VISIBLE);
            int length = length1;
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            0,
                            ViewGroup.LayoutParams.MATCH_PARENT, length);
            params.setMargins(1, 1, 1, 1);
            for (int i = 0; i < length; i++) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setWeightSum(1f);
                linearLayout.setGravity(Gravity.CENTER_HORIZONTAL);
                CustomBottomViewBinding customBottomView = getCustomBottomView();
                customBottomView.viewTv.setText(bottomOptions[i]);
                customBottomView.viewTv.setTextSize(10f);
                customBottomView.viewLogo.setImageResource(topDrawables[i]);
                View topRootView = customBottomView.getRoot();
                topRootView.setTag(i);
                // topRootView.setOnClickListener(topViewClickListener);
                linearLayout.addView(topRootView);
                bottomSheetCheckupFragmentBinding.topRow.addView(linearLayout, params);
            }*/
        }
    };


    private View.OnClickListener topViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView viewById = (TextView) view.findViewById(R.id.view_tv);
            String topClickedText = viewById.getText().toString();
            Integer tag = (Integer) view.getTag();
            changeBackgroundText(tag, view);
            String[] bottomOptions;
            int[] topDrawables;

            if (tag == 0 && topClickedText.equals(getString(
                    R.string.bottom_option_main_features))) {
                bottomOptions = new String[0];
                topDrawables = new int[0];
            } else if (tag == 1 && topClickedText.equals(getString(
                    R.string.bottom_option_details))) {
                bottomOptions = new String[5];
                bottomOptions[0] = getString(R.string.bottom_option_return_policy);
                bottomOptions[1] = getString(R.string.bottom_option_special_instructions);
                bottomOptions[2] = getString(R.string.bottom_option_how_to_use);
                bottomOptions[3] = getString(R.string.bottom_option_warranty);
                bottomOptions[4] = getString(R.string.bottom_option_share);
                topDrawables = new int[5];
                topDrawables[0] = R.drawable.ic_option_return_policy;
                topDrawables[1] = R.drawable.ic_option_sp_instructions;
                topDrawables[2] = R.drawable.ic_option_howtouse;
                topDrawables[3] = R.drawable.ic_option_warranty;
                topDrawables[4] = R.drawable.ic_option_share;
            } else if (tag == 2 && topClickedText.equals(getString(
                    R.string.bottom_option_feedback))) {
                bottomOptions = new String[0];
                topDrawables = new int[0];
//                showFeedBackDialog();
            } else if (tag == 0 && topClickedText.equals(getString(
                    R.string.bottom_option_Call))) {
                bottomOptions = new String[0];
                topDrawables = new int[0];
            } else if (tag == 1 && topClickedText.equals(getString(
                    R.string.bottom_option_location))) {
                bottomOptions = new String[0];
                topDrawables = new int[0];
            } else if (tag == 2 && topClickedText.equals(getString(
                    R.string.bottom_option_review))) {
                bottomOptions = new String[0];
                topDrawables = new int[0];
//                showFeedBackDialog();
            } else {
                bottomOptions = new String[0];
                topDrawables = new int[0];
            }

            /*bottomSheetCheckupFragmentBinding.secondtopRow.removeAllViews();
            int length1 = bottomOptions.length;
            bottomSheetCheckupFragmentBinding.secondtopRow.setVisibility(View.VISIBLE);
            int length = length1;
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            0, ViewGroup.LayoutParams.WRAP_CONTENT, length);
            params.setMargins(1, 1, 1, 1);
            for (int i = 0; i < length; i++) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setWeightSum(1f);
                linearLayout.setGravity(Gravity.CENTER);
                CustomBottomViewBinding customBottomView = getCustomBottomView();
                customBottomView.viewTv.setText(bottomOptions[i]);
                customBottomView.viewTv.setTextSize(10f);
                customBottomView.viewLogo.setImageResource(topDrawables[i]);
                View bottomRootView = customBottomView.getRoot();
                bottomRootView.setTag(i);
                linearLayout.addView(bottomRootView);
                bottomRootView.setOnClickListener(secondtopViewClickListener);
                bottomSheetCheckupFragmentBinding.secondtopRow.addView(linearLayout, params);
            }*/
        }
    };


    private View.OnClickListener secondtopViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView viewById = (TextView) view.findViewById(R.id.view_tv);
            String topClickedText = viewById.getText().toString();
            Integer tag = (Integer) view.getTag();
            changeBackgroundText(tag, view);
            if (tag == 0 && topClickedText.equals(getString(
                    R.string.bottom_option_return_policy))) {

            } else if (tag == 1 && topClickedText.equals(getString(
                    R.string.bottom_option_special_instructions))) {

            } else if (tag == 2 && topClickedText.equals(getString(
                    R.string.bottom_option_how_to_use))) {
            } else if (tag == 3 && topClickedText.equals(getString(
                    R.string.bottom_option_warranty))) {

            } else if (tag == 4 && topClickedText.equals(getString(
                    R.string.bottom_option_share))) {
            } else if (tag == 0 && topClickedText.equals(getString(
                    R.string.bottom_option_feedback))) {
            }
        }
    };

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    checkUpAdapter.clearData();

                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onSearchClickListerner(String searchableText, String searchType) {
        //TODO have to implement search
    }
}
