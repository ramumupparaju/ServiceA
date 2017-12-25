package com.incon.service.ui.reports;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.R;
import com.incon.service.databinding.FragmentReportsBinding;
import com.incon.service.ui.BaseFragment;
import com.incon.service.ui.home.HomeActivity;

/**
 * Created by PC on 12/5/2017.
 */

public class ReportsFragment extends BaseFragment {
    FragmentReportsBinding binding;

    private View rootView;
    @Override
    protected void initializePresenter() {

    }

    @Override
    public void setTitle() {
        ((HomeActivity) getActivity()).setToolbarTitle(getString(R.string.title_report));

    }

    @Override
    protected View onPrepareView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            // handle events from here using android binding
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reports,
                    container, false);

            rootView = binding.getRoot();
        }
        setTitle();
        return rootView;
    }
}