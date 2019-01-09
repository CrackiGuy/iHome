package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cracki.ihome.com.ihome.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabDoorFragment extends Fragment {


    public TabDoorFragment() {
        // Required empty public constructor
    }
    public static TabDoorFragment newInstance(){
        TabDoorFragment tabDoorFragment = new TabDoorFragment();
        return  tabDoorFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_door, container, false);
    }

}
