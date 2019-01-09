package fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cracki.ihome.com.ihome.ConnectionCheck;
import cracki.ihome.com.ihome.Home;
import cracki.ihome.com.ihome.R;
import cracki.ihome.com.ihome.Session;
import cracki.ihome.com.ihome.SwitchBtn;

public class TempFragment extends Fragment {
    TextView textTemp,textHum;
    Session session;
    ConnectionCheck check;

    private String url = "";

    public TempFragment() {
        // Required empty public constructor
    }

    public static TempFragment newInstance(){
        TempFragment tempFragment = new TempFragment();
        return  tempFragment;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temp, container, false);
        textTemp = view.findViewById(R.id.textTemp);
        textHum = view.findViewById(R.id.textHum);

        check = new ConnectionCheck(getContext());
        check.checkInternet();

        session = Session.newInstance(view.getContext());
        url = session.get("server")+"/ihome/api/arduino/temp.json";
        getData();
        textTemp.setText(session.get("temperature"));
        textHum.setText(session.get("humidity"));
        return view;
    }
    private void getData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        session.set("temperature",jsonObject.getString("temperature")+"°C");
                        session.set("humidity",jsonObject.getString("humidity")+"%");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
