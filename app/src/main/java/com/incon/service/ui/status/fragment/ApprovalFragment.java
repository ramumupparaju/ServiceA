package com.incon.service.ui.status.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.R;
import com.incon.service.databinding.FragmentApprovalBinding;
import com.incon.service.ui.BaseFragment;

/**
 * Created by PC on 12/5/2017.
 */

public class ApprovalFragment extends BaseFragment {
    private FragmentApprovalBinding binding;
    private View rootView;

    @Override
    protected void initializePresenter() {

    }

    @Override
    public void setTitle() {

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_approval,
                    container, false);

            rootView = binding.getRoot();
        }
        setTitle();
        return rootView;
    }
}
