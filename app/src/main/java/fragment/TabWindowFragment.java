package fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.WindowAdapter;
import cracki.ihome.com.ihome.ConnectionCheck;
import cracki.ihome.com.ihome.R;
import cracki.ihome.com.ihome.Session;
import cracki.ihome.com.ihome.WindowBtn;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabWindowFragment extends Fragment {
    ArrayList<WindowBtn> btnWindow;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    ConnectionCheck check;
    Session session;
    private String url = "";

    public TabWindowFragment() {
        // Required empty public constructor
    }
    public static TabWindowFragment newInstance(){
        TabWindowFragment tabWindowFragment = new TabWindowFragment();
        return  tabWindowFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_window, container, false);

        check = new ConnectionCheck(getContext());
        check.checkInternet();

        recyclerView = view.findViewById(R.id.grid);
        final SwipeRefreshLayout swipe = (SwipeRefreshLayout)view.findViewById(R.id.swipe);

        btnWindow = new ArrayList<WindowBtn>();
        adapter = new WindowAdapter(getContext(),btnWindow);

        session = Session.newInstance(view.getContext());
        url = session.get("server")+"/ihome/api/door/window.json";

        if (!check.isConnected()){
            String json_array = session.get("WindowJson");
            try {
                JSONArray jsoArray=new JSONArray(json_array);
                for (int i = 0; i < jsoArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsoArray.getJSONObject(i);
                        WindowBtn windowBtn = new WindowBtn();
                        windowBtn.setName(jsonObject.getString("name"));
                        windowBtn.setStatus(jsonObject.getString("status"));
                        btnWindow.add(windowBtn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            getData();
        }

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe.setRefreshing(true);
                btnWindow = new ArrayList<WindowBtn>();
                adapter = new WindowAdapter(getContext(),btnWindow);
                getData();
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                recyclerView.setAdapter(adapter);
                swipe.setRefreshing(false);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void getData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                session.set("WindowJson",response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        WindowBtn windowBtn = new WindowBtn();
                        windowBtn.setName(jsonObject.getString("name"));
                        windowBtn.setStatus(jsonObject.getString("status"));
                        btnWindow.add(windowBtn);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonArrayRequest);
    }
}