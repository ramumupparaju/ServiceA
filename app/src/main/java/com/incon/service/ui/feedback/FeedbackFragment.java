package com.incon.service.ui.feedback;

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
import com.incon.service.databinding.BottomSheetCheckupFragmentBinding;
import com.incon.service.databinding.CustomBottomViewBinding;
import com.incon.service.databinding.FragmentCheckupBinding;
import com.incon.service.databinding.FragmentFeedbackBinding;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.feedback.adapter.FeedBackAdapter;
import com.incon.service.ui.feedback.adapter.FeedBackContract;
import com.incon.service.ui.feedback.adapter.FeedBackPresenter;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.ui.status.adapter.CheckUpAdapter;
import com.incon.service.ui.status.fragment.CheckUpContract;
import com.incon.service.ui.status.fragment.CheckUpPresenter;
import com.incon.service.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 12/5/2017.
 */

public class FeedbackFragment extends BaseFragment implements FeedBackContract.View {
    private FragmentFeedbackBinding fragmentFeedbackBinding;
    private View rootView;
    private FeedBackAdapter feedBackAdapter;
    private FeedBackPresenter feedBackPresenter;
    private int userId;

    @Override
    protected void initializePresenter() {
        feedBackPresenter = new FeedBackPresenter();
        feedBackPresenter.setView(this);
        setBasePresenter(feedBackPresenter);

    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            fragmentFeedbackBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback,
                    container, false);

            initViews();

            rootView = fragmentFeedbackBinding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        feedBackAdapter = new FeedBackAdapter();
        feedBackAdapter.setClickCallback(iClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getContext(), linearLayoutManager.getOrientation());
        fragmentFeedbackBinding.requestRecyclerview.addItemDecoration(dividerItemDecoration);
        fragmentFeedbackBinding.requestRecyclerview.setAdapter(feedBackAdapter);
        fragmentFeedbackBinding.requestRecyclerview.setLayoutManager(linearLayoutManager);
        userId = SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE);
        loadReturnHistory(null);
    }


    private void dismissSwipeRefresh() {
        if (fragmentFeedbackBinding.swiperefresh.isRefreshing()) {
            fragmentFeedbackBinding.swiperefresh.setRefreshing(false);
        }
    }


    private IClickCallback iClickCallback = new IClickCallback() {
        @Override
        public void onClickPosition(int position) {

            feedBackAdapter.clearSelection();
            ProductInfoResponse returnHistoryResponse = feedBackAdapter.
                    getItemFromPosition(position);
            returnHistoryResponse.setSelected(true);
            feedBackAdapter.notifyDataSetChanged();

        }
    };


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    feedBackAdapter.clearData();
                    feedBackPresenter.returnHistory(userId);

                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
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
            fragmentFeedbackBinding.returnTextview.setVisibility(View.VISIBLE);
            dismissSwipeRefresh();
        } else {
            feedBackAdapter.setData(returnHistoryResponseList);
            dismissSwipeRefresh();
        }
    }
}
