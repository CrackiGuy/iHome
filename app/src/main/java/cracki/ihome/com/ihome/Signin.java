package cracki.ihome.com.ihome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signin extends AppCompatActivity {
    private static final String TAG = "Signin";
    private static final int REQUEST_SIGNUP = 0;
    SessionManger sessionManger;
    Session session;

    private EditText textEmail;
    private EditText textPassword;
    private Button btn_login;

    private ConnectionCheck check = new ConnectionCheck(this);

    private String URL_Login = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        sessionManger = new SessionManger(this);
        check.checkInternet();
        session = Session.newInstance(this);
        URL_Login = session.get("server")+"/ihome/api/auth/signin.php";

        textEmail = findViewById(R.id.input_email);
        textPassword = findViewById(R.id.input_password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.checkInternet();
                login();
            }
        });

    }
    public void login() {
        Log.d(TAG, "Singin");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        btn_login.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Signin.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        final String email = textEmail.getText().toString();
        final String password = textPassword.getText().toString();

        // TODO: Implement your own authentication logic here.
        StringRequest stringRequest = new StringRequest(Request.Method.POST,URL_Login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if(success.equals("1")){
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();

                                    sessionManger.createSession(name,email,id);

                                    new android.os.Handler().postDelayed(
                                            new Runnable() {
                                                public void run() {
                                                    onLoginSuccess();
                                                    progressDialog.dismiss();
                                                }
                                            }, 1000);

                                    Intent intent = new Intent(Signin.this,Home.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("email",email);
                                    startActivity(intent);
                                }
                            }else{
                                progressDialog.dismiss();
                                onLoginFailed();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            progressDialog.dismiss();
                            onLoginFailed();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Signin.this,"No Connection!!",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        onLoginFailed();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        btn_login.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btn_login.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textEmail.setError("enter a valid email address");
            valid = false;
        } else {
            textEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            textPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            textPassword.setError(null);
        }

        return valid;
    }
}
