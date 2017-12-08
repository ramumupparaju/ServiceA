package com.incon.service.ui.status.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.incon.service.ui.status.fragment.ApprovalFragment;
import com.incon.service.ui.status.fragment.CheckUpFragment;
import com.incon.service.ui.status.fragment.NewRequestsFragment;
import com.incon.service.ui.status.fragment.PaymentFragment;
import com.incon.service.ui.status.fragment.RepairFragment;

import java.util.HashMap;

/**
 * Created by PC on 12/5/2017.
 */

public class StatusTabPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    public StatusTabPagerAdapter(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment tabFragment = fragmentHashMap.get(position);
        if (tabFragment != null) {
            return tabFragment;
        }

        switch (position) {
            case 0:
                tabFragment = new NewRequestsFragment();
                break;
            case 1:
                tabFragment = new CheckUpFragment();
                break;
            case 2:
                tabFragment = new ApprovalFragment();
                break;
            case 3:
                tabFragment = new RepairFragment();
                break;
            case 4:
                tabFragment = new PaymentFragment();
                break;

            default:
                break;
        }
        fragmentHashMap.put(position, tabFragment);
        return tabFragment;
    }

    public Fragment getFragmentFromPosition(int position) {
        return fragmentHashMap.get(position);
    }


    @Override
    public int getCount() {
        return numOfTabs;
    }
}
