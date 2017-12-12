package com.incon.service.ui.register.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import com.incon.service.ui.register.fragment.RegistrationStoreFragment;
import com.incon.service.ui.register.fragment.RegistrationUserFragment;

import java.util.HashMap;

/**
 * Created on 08 Jun 2017 3:53 PM.
 *
 */
public class RegistrationPagerAdapter extends FragmentStatePagerAdapter {
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    private static final String TAG = RegistrationPagerAdapter.class.getName();


    public RegistrationPagerAdapter(FragmentManager fm) {
        super(fm);
            initFragments();
    }

    @Override
    public Fragment getItem(int position) {
        if (fragmentHashMap.containsKey(position)) {
            return fragmentHashMap.get(position);
        }
        return null;
    }

    private void initFragments() {
        fragmentHashMap.put(0, new RegistrationUserFragment());
        fragmentHashMap.put(1, new RegistrationStoreFragment());
    }


    @Override
    public int getCount() {
        return fragmentHashMap.size();
    }


}
