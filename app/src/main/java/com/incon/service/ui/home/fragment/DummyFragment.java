package com.incon.service.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incon.service.R;


/**
 * Created on 13 Jun 2017 4:01 PM.
 *
 */
public class DummyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_spinner, container, false);
    }
}
