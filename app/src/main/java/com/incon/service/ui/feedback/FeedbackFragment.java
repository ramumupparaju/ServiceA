package com.incon.service.ui.feedback;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.R;
import com.incon.service.apimodel.components.fetchnewrequest.FetchNewRequestResponse;
import com.incon.service.callbacks.IClickCallback;
import com.incon.service.callbacks.IStatusClickCallback;
import com.incon.service.databinding.FragmentFeedbackBinding;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.feedback.adapter.FeedBackAdapter;
import com.incon.service.ui.feedback.adapter.FeedBackContract;
import com.incon.service.ui.feedback.adapter.FeedBackPresenter;
import com.incon.service.ui.home.HomeActivity;
import com.incon.service.utils.SharedPrefsUtils;

/**
 * Created by PC on 12/5/2017.
 */

public class FeedbackFragment extends BaseFragment implements FeedBackContract.View {
    private FragmentFeedbackBinding binding;
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
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_feedback));

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feedback,
                    container, false);

            initViews();

            rootView = binding.getRoot();
        }
        setTitle();
        return rootView;
    }

    private void initViews() {
        feedBackAdapter = new FeedBackAdapter();
        feedBackAdapter.setClickCallback(iClickCallback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        binding.requestRecyclerview.setAdapter(feedBackAdapter);
        binding.requestRecyclerview.setLayoutManager(linearLayoutManager);
        userId = SharedPrefsUtils.loginProvider().getIntegerPreference(
                LoginPrefs.USER_ID, DEFAULT_VALUE);
    }


    private IStatusClickCallback iClickCallback = new IStatusClickCallback() {
        @Override
        public void onClickStatusButton(int statusType) {
            
        }

        @Override
        public void onClickStatus(int productPosition, int statusPosition) {

        }

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
