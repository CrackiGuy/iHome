package fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cracki.ihome.com.ihome.InputFilterMinMax;
import cracki.ihome.com.ihome.R;
import cracki.ihome.com.ihome.Session;

import static cracki.ihome.com.ihome.R.id.parent_matrix;
import static cracki.ihome.com.ihome.R.id.view;
import static cracki.ihome.com.ihome.R.id.water_height;

/**
 * A simple {@link Fragment} subclass.
 */
public class WaterFragment extends Fragment{
    ProgressBar progressBar;
    Animation animation;

    Session session;

    ImageButton imgBtnUpdate,imgBtnHeight;
    TextView waterValue,waterHeight,min,max;
    CardView editMin,editMax;
    Switch switchPump,switchAuto;
    EditText editText;

    private String url = "";
    private String url_set = "";


    public WaterFragment() {
        // Required empty public constructor
    }

    public static WaterFragment newInstance(){
        WaterFragment waterFragment = new WaterFragment();
        return waterFragment;
    }
    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water, container, false);

        session = Session.newInstance(view.getContext());

        url = session.get("server")+"/ihome/api/arduino/water.json";
        url_set = session.get("server")+"/ihome/api/arduino/water.php";


        imgBtnUpdate = view.findViewById(R.id.water_update);
        imgBtnHeight = view.findViewById(water_height);
        waterValue = view.findViewById(R.id.water_value);
        waterHeight = view.findViewById(R.id.txt_deep);

        switchPump = view.findViewById(R.id.switch_pump);
        switchAuto = view.findViewById(R.id.water_auto);

        editMax = view.findViewById(R.id.edit_max);
        editMin = view.findViewById(R.id.edit_min);

        min = (TextView)view.findViewById(R.id.min);
        max = (TextView)view.findViewById(R.id.max);

        animation = (Animation) AnimationUtils.loadAnimation(view.getContext(),R.anim.progress_water);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.startAnimation(animation);

        ButtonAction();
        getData();

        min.setText("Min     : "+session.get("min")+"%");
        max.setText("Max    : "+session.get("max")+"%");


        return view;
    }

    private void ButtonAction() {
        imgBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation a = (Animation) AnimationUtils.loadAnimation(getView().getContext(),R.anim.rotate);
                imgBtnUpdate.startAnimation(a);
                progressBar.startAnimation(animation);
                getData();
            }
        });

        imgBtnHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = (Animation) AnimationUtils.loadAnimation(getView().getContext(),R.anim.bounce);
                imgBtnHeight.startAnimation(animation);
                showDialogHeight();
            }
        });

        switchPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.set("pump",switchPump.isChecked()?"1":"0");
                sendData(session.get("height"),session.get("percent"),session.get("min"),session.get("max"),session.get("pump"),session.get("auto"));
            }
        });

        switchAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.set("auto",switchAuto.isChecked()?"1":"0");
                sendData(session.get("height"),session.get("percent"),session.get("min"),session.get("max"),session.get("pump"),session.get("auto"));
            }
        });

        editMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogMix();
            }
        });

        editMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogMax();
            }
        });
    }
    private void getData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        session.set("distance",jsonObject.getString("distance"));
                        session.set("height",jsonObject.getString("height"));
                        session.set("percent",jsonObject.getString("percent"));
                        session.set("pump",jsonObject.getString("pump"));
                        session.set("max",jsonObject.getString("max"));
                        session.set("min",jsonObject.getString("min"));
                        session.set("auto",jsonObject.getString("auto"));
                        Calculation();

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
    private void sendData(final String height, final String percent,final String min,final String max,final String pump,final String auto) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_set,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                            }

                        }catch (JSONException e){
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("height",height);
                params.put("percent",percent);
                params.put("min",min);
                params.put("max",max);
                params.put("pump",pump);
                params.put("auto",auto);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }
    private void Calculation(){
        float height = Float.parseFloat(session.get("height"));
        float distance = Float.parseFloat(session.get("distance"));
        float water = ((height-distance)/height)*100;

        session.set("percent", String.valueOf((int)water)+"%");

        sendData(session.get("height"),session.get("percent"),session.get("min"),session.get("max"),session.get("pump"),session.get("auto"));

        waterValue.setText(session.get("percent"));
        waterHeight.setText(session.get("height")+" cm");
        progressBar.setProgress((int)water);

        Boolean status = true;
        if (session.get("pump").equals("1")){
            status = true;
        }else if (session.get("pump").equals("0")){
            status = false;
        }
        switchPump.setChecked(status);

        if (session.get("auto").equals("1")){
            status = true;
        }else if (session.get("auto").equals("0")){
            status = false;
        }
        switchAuto.setChecked(status);
    }

    protected void showDialogHeight(){
        final Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.alert_water_height, null);
        dialog.setContentView(view);

        editText = (EditText) view.findViewById(R.id.input_height);
        TextView title = (TextView) view.findViewById(R.id.dig_title);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView save = (TextView) view.findViewById(R.id.save);

        editText.setFilters(new InputFilter[]{new InputFilterMinMax("1", "400")});

        title.setText("Change tank Height!");
        editText.setText(session.get("height"));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = editText.getText().toString();
                session.set("height",str);
                sendData(session.get("height"),session.get("percent"),session.get("min"),session.get("max"),session.get("pump"),session.get("auto"));
                waterHeight.setText(str+" cm");
                dialog.dismiss();
            }
        });
        dialog.show();
    };
    protected void showDialogMax(){
        final Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.alert_water_height, null);
        dialog.setContentView(view);

        editText = (EditText) view.findViewById(R.id.input_height);
        TextView title = (TextView) view.findViewById(R.id.dig_title);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView save = (TextView) view.findViewById(R.id.save);

        editText.setFilters(new InputFilter[]{new InputFilterMinMax("1", "90")});

        title.setText("Change Maximum Height!");
        editText.setText(session.get("max"));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.set("max",editText.getText().toString());
                max.setText("Max    : "+session.get("max")+"%");
                sendData(session.get("height"),session.get("percent"),session.get("min"),session.get("max"),session.get("pump"),session.get("auto"));
                dialog.dismiss();
            }
        });
        dialog.show();
    };
    protected void showDialogMix(){
        final Dialog dialog = new Dialog(getActivity(), R.style.PauseDialog);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.setCancelable(true);

        View view  = getActivity().getLayoutInflater().inflate(R.layout.alert_water_height, null);
        dialog.setContentView(view);

        editText = (EditText) view.findViewById(R.id.input_height);
        TextView title = (TextView) view.findViewById(R.id.dig_title);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView save = (TextView) view.findViewById(R.id.save);

        editText.setFilters(new InputFilter[]{new InputFilterMinMax("1", "90")});

        title.setText("Change Miniman Height!");
        editText.setText(session.get("min"));

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.set("min",editText.getText().toString());
                min.setText("Min     : "+session.get("min")+"%");
                sendData(session.get("height"),session.get("percent"),session.get("min"),session.get("max"),session.get("pump"),session.get("auto"));
                dialog.dismiss();
            }
        });
        dialog.show();
    };
}
