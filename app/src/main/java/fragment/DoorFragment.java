package fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import adapter.TabPagesAdapter;
import cracki.ihome.com.ihome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoorFragment extends Fragment {

    ViewPager viewPager;
    TabLayout tabLayout;

    public DoorFragment() {
        // Required empty public constructor
    }

    public static DoorFragment newInstance(){
        DoorFragment doorFragment = new DoorFragment();
        return  doorFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_door, container,false);

        viewPager = (ViewPager)view.findViewById(R.id.pager);
        tabLayout = (TabLayout)view.findViewById(R.id.tab);

        TabPagesAdapter tabPagesAdapter = TabPagesAdapter.newInstance(getChildFragmentManager());
        tabPagesAdapter.addFragment(TabDoorFragment.newInstance(),"Doors");
        tabPagesAdapter.addFragment(TabWindowFragment.newInstance(),"Windows");
        viewPager.setAdapter(tabPagesAdapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }
}
