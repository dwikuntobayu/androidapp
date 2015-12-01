package com.mdwikuntobayu.androidbasic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mdwikuntobayu on 12/11/15.
 */
public class InstanceFragmentPA extends FragmentPagerAdapter {

    public InstanceFragmentPA(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return FragmentPager.newInstance("Mohamad Dwikuntobayu", "dwikunto.bayu@kiranatama.com");
            case 1:
                return FragmentPager.newInstance("Jon Deep", "jon.dip@mail.com");
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}