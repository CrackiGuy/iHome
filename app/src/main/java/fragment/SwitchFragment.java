package fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.SwitchAdapter;
import cracki.ihome.com.ihome.ConnectionCheck;
import cracki.ihome.com.ihome.R;
import cracki.ihome.com.ihome.Session;
import cracki.ihome.com.ihome.SwitchBtn;


public class SwitchFragment extends Fragment {
    ArrayList<SwitchBtn> btnSwitch;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;
    ConnectionCheck check;
    Session session;

    private String url = "";

    public SwitchFragment() {
        // Required empty public constructor
    }

    public static SwitchFragment newInstance() {
        SwitchFragment fragment = new SwitchFragment();
        return fragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_switch, container, false);

        check = new ConnectionCheck(getContext());
        check.checkInternet();
        recyclerView = view.findViewById(R.id.grid);
        btnSwitch = new ArrayList<>();
        adapter = new SwitchAdapter(getContext(),btnSwitch);

        session = Session.newInstance(view.getContext());
        url = session.get("server")+"/ihome/api/switch/devices.json";

        if (!check.isConnected()){
            String json_array = session.get("SwitchJson");
            try {
                JSONArray jsoArray=new JSONArray(json_array);
                for (int i = 0; i < jsoArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsoArray.getJSONObject(i);
                        SwitchBtn switchBtn = new SwitchBtn();
                        switchBtn.setName(jsonObject.getString("name"));
                        switchBtn.setIo(jsonObject.getString("io"));
                        switchBtn.setStatus(jsonObject.getString("status"));
                        //Toast.makeText(getContext(),strJson,Toast.LENGTH_SHORT).show();
                        btnSwitch.add(switchBtn);
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
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);

        return view;
    }
    private void getData() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                session.set("SwitchJson",response.toString());
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        SwitchBtn switchBtn = new SwitchBtn();
                        switchBtn.setName(jsonObject.getString("name"));
                        switchBtn.setIo(jsonObject.getString("io"));
                        switchBtn.setStatus(jsonObject.getString("status"));
                        //Toast.makeText(getContext(),strJson,Toast.LENGTH_SHORT).show();
                        btnSwitch.add(switchBtn);
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
