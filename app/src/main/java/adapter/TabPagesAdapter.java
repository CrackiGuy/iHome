package adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import fragment.TabWindowFragment;

/**
 * Created by Cracki on 11/11/2018.
 */

public class TabPagesAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public TabPagesAdapter(FragmentManager fm) {
        super(fm);
    }
    public static TabPagesAdapter newInstance(FragmentManager fm){
        TabPagesAdapter tabPagesAdapter = new TabPagesAdapter(fm);
        return tabPagesAdapter;
    }
    public void addFragment(Fragment fragment,String title){
        mFragmentTitleList.add(title);
        mFragmentList.add(fragment);
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
