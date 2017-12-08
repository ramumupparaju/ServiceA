package com.incon.service.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incon.service.AppConstants;
import com.incon.service.R;


public abstract class BaseFragment extends Fragment implements BaseView, AppConstants {

    protected BasePresenter presenter;
    private View v;

    protected abstract void initializePresenter();
    public abstract void setTitle();

    public boolean doRefresh;

    protected abstract View onPrepareView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState);

    public void setBasePresenter(BasePresenter basePresenter) {
        presenter = basePresenter;
        if (presenter != null) {
            this.presenter.initialize(null);
        }
    }

    public void dismissDialog(Dialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        initializePresenter();
        v = onPrepareView(inflater, container, savedInstanceState);
        return v;
    }

    // changeing text colore
    public void changeBackgroundText(Integer tag, View view) {
        if (view instanceof LinearLayout) {
            View topRootView = (View) view.getParent();
            View topRootView1 = (View) topRootView.getParent();
//                    here we get count of 4
            for (int j = 0; j < ((ViewGroup) topRootView1).getChildCount(); j++) {
                View childView1 = ((ViewGroup) topRootView1).getChildAt(j);
                if (j == tag) {
                    if (childView1 instanceof LinearLayout) {
                        for (int k = 0; k < ((ViewGroup) childView1).getChildCount(); k++) {
                            View childView2 = ((ViewGroup) childView1).getChildAt(k);
                            if (childView2 instanceof LinearLayout) {
                                for (int l = 0;
                                     l < ((ViewGroup) childView2).getChildCount(); l++) {
                                    View childView3
                                            = ((ViewGroup) childView2).getChildAt(l);
                                    if (childView3 instanceof TextView) {
                                        ((TextView) childView3).setTextColor(
                                                ContextCompat.getColor(
                                                        getActivity(), R.color.colorPrimary));
                                    }
                                }
                            }
                        }
                    }
                } else {
                    if (childView1 instanceof LinearLayout) {
                        for (int k = 0; k < ((ViewGroup) childView1).getChildCount(); k++) {
                            View childView2 = ((ViewGroup) childView1).getChildAt(k);
                            if (childView2 instanceof LinearLayout) {
                                for (int l = 0;
                                     l < ((ViewGroup) childView2).getChildCount(); l++) {
                                    View childView3
                                            = ((ViewGroup) childView2).getChildAt(l);
                                    if (childView3 instanceof TextView) {
                                        ((TextView) childView3).setTextColor(getResources()
                                                .getColor(R.color.colorAccent));
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void refreshData(){

    }

    @Override
    public void showProgress(String message) {
        ((BaseActivity) getActivity()).showProgress(message);
    }

    @Override
    public void hideProgress() {
        ((BaseActivity) getActivity()).hideProgress();
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        ((BaseActivity) getActivity()).showErrorMessage(errorMessage);
    }

    @Override
    public void handleException(Pair<Integer, String> error) {
        ((BaseActivity) getActivity()).handleException(error);
    }

}
