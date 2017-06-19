package com.example.emiliekvist.kvitit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentManager;

/**
 * Created by Mads on 15-06-2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private String tabTitles[] = new String[] {"Mine Kvitteringer", "Kategorier"};
    int mNumOfTabs = 2;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // returner fragmenterne som skal vises på siden.
        switch (position){
            case 0:
                MineKvitTab tab1 = new MineKvitTab();
                return tab1;
            case 1:
                KategoriTab tab2 = new KategoriTab();
                return tab2;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        // Returner det totalte antal af sider.
        return mNumOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
