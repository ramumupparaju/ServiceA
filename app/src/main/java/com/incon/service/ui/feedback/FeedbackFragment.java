package com.incon.service.ui.feedback;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.databinding.FragmentFeedbackBinding;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.feedback.adapter.FeedBackAdapter;
import com.incon.service.ui.feedback.adapter.FeedBackContract;
import com.incon.service.ui.feedback.adapter.FeedBackPresenter;
import com.incon.service.utils.SharedPrefsUtils;

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
            FetchNewRequestResponse fetchNewRequestResponse = feedBackAdapter.
                    getItemFromPosition(position);
            fetchNewRequestResponse.setSelected(true);
            feedBackAdapter.notifyDataSetChanged();

        }
    };


    private SwipeRefreshLayout.OnRefreshListener onRefreshListener =
            new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    feedBackAdapter.clearData();

                }
            };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
