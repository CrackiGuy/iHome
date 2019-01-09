package cracki.ihome.com.ihome;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.*;

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

import fragment.SwitchFragment;

/**
 * Created by Cracki on 11/6/2018.
 */

public class SwitchHolder extends RecyclerView.ViewHolder {
    private String name,status;
    public TextView txt_title;
    public Switch switch_one;
    private String url = "";
    Session session;

    public SwitchHolder(final View itemView) {
        super(itemView);
        txt_title = itemView.findViewById(R.id.card_title);
        switch_one = itemView.findViewById(R.id.switch_one);

        session = Session.newInstance(itemView.getContext());
        url = session.get("server")+"/ihome/api/switch/receive.php";

        switch_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txt_title.getText().toString();
                status = switch_one.isChecked()?"1":"0";
                setData(name,status);

            }
        });
    }
    private void setData(final String name, final String status) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){
                                Toast.makeText(itemView.getContext(),name+" is "+ status,Toast.LENGTH_SHORT).show();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(itemView.getContext(),"Error!",Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(itemView.getContext(),"Error!"+error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("status",status);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(itemView.getContext());
        requestQueue.add(stringRequest);
    }
}
