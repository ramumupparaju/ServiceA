package com.incon.service.ui.status.fragment;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incon.service.R;
import com.incon.service.apimodel.components.productinforesponse.ProductInfoResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.BottomSheetNewRequestFragmentBinding;
import com.incon.service.databinding.CustomBottomViewBinding;
import com.incon.service.databinding.FragmentNewrequestBinding;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.status.adapter.NewRequestsAdapter;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 12/5/2017.
 */

public class NewRequestsFragment extends BaseFragment implements NewRequestContract.View {
    private FragmentNewrequestBinding fragmentNewrequestBinding;
    private View rootView;
    private int userId;
    private NewRequestPresenter newRequestPresenter;
    private NewRequestsAdapter newRequestsAdapter;
    private int productSelectedPosition = -1;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetNewRequestFragmentBinding bottomSheetNewRequestBinding;
    @Override
    protected void initializePresenter() {

        newRequestPresenter = new NewRequestPresenter();
        newRequestPresenter.setView(this);
        setBasePresenter(newRequestPresenter);

    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            fragmentNewrequestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_newrequest,
                    container, false);
            initViews();
            loadBottomSheet();

            rootView = fragmentNewrequestBinding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        newRequestsAdapter = new NewRequestsAdapter();
        newRequestsAdapter.setClickCallback(iClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        fragmentNewrequestBinding.requestRecyclerview.addItemDecoration(dividerItemDecoration);
        fragmentNewrequestBinding.requestRecyclerview.setAdapter(newRequestsAdapter);
        fragmentNewrequestBinding.requestRecyclerview.setLayoutManager(linearLayoutManager);
        userId = SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE);
        loadReturnHistory(null);
    }

    private void dismissSwipeRefresh() {
        if (fragmentNewrequestBinding.swiperefresh.isRefreshing()) {
            fragmentNewrequestBinding.swiperefresh.setRefreshing(false);
        }
    }

    private void loadBottomSheet() {
        bottomSheetNewRequestBinding = DataBindingUtil.inflate(LayoutInflater.from(
                getActivity()), R.layout.bottom_sheet_new_request_fragment, null, false);
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        bottomSheetDialog.setContentView(bottomSheetNewRequestBinding.getRoot());
        bottomSheetDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });

    }


    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {
            newRequestsAdapter.clearSelection();
            ProductInfoResponse returnHistoryResponse = newRequestsAdapter.
                    getItemFromPosition(position);
            returnHistoryResponse.setSelected(true);
            newRequestsAdapter.notifyDataSetChanged();

            createBottomSheetView(position);
            bottomSheetDialog.show();
        }
    };

    private void createBottomSheetView(int position) {
        productSelectedPosition = position;
        bottomSheetNewRequestBinding.topRow.setVisibility(View.GONE);

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

        bottomSheetNewRequestBinding.bottomRow.removeAllViews();
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
            bottomSheetNewRequestBinding.bottomRow.addView(linearLayout, params);
        }
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
                bottomOptions[0] = getString(R.string.bottom_option_accept);
                bottomOptions[1] = getString(R.string.bottom_option_reject);
                bottomOptions[2] = getString(R.string.bottom_option_hold);
                topDrawables = new int[3];
                topDrawables[0] = R.drawable.ic_option_accept_request;
                topDrawables[1] = R.drawable.ic_option_accept_request;
                topDrawables[2] = R.drawable.ic_option_hold;
            }
            bottomSheetNewRequestBinding.secondtopRow.removeAllViews();
            bottomSheetNewRequestBinding.topRow.removeAllViews();
            int length1 = bottomOptions.length;
            bottomSheetNewRequestBinding.topRow.setVisibility(View.VISIBLE);
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
                bottomSheetNewRequestBinding.topRow.addView(linearLayout, params);
            }
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

            bottomSheetNewRequestBinding.secondtopRow.removeAllViews();
            int length1 = bottomOptions.length;
            bottomSheetNewRequestBinding.secondtopRow.setVisibility(View.VISIBLE);
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
                bottomSheetNewRequestBinding.secondtopRow.addView(linearLayout, params);
            }
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

    private CustomBottomViewBinding getCustomBottomView() {
        return DataBindingUtil.inflate(
                LayoutInflater.from(getActivity()), R.layout.custom_bottom_view, null, false);
    }
    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    newRequestsAdapter.clearData();
                    newRequestPresenter.returnHistory(userId);


                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
        newRequestPresenter.disposeAll();
    }


    @Override
    public void loadReturnHistory(List<ProductInfoResponse> returnHistoryResponseList) {

        if (returnHistoryResponseList == null) {
            returnHistoryResponseList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                ProductInfoResponse productInfoResponse = new ProductInfoResponse();
                returnHistoryResponseList.add(productInfoResponse);
            }
        }
        if (returnHistoryResponseList.size() == 0) {
            fragmentNewrequestBinding.returnTextview.setVisibility(View.VISIBLE);
            dismissSwipeRefresh();
        } else {
            newRequestsAdapter.setData(returnHistoryResponseList);
            dismissSwipeRefresh();
        }
    }
}
