package cracki.ihome.com.ihome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Signup extends AppCompatActivity {
    private static final String TAG = "Signup";
    ConnectionCheck conn = new ConnectionCheck(this);

    private String URL_REG = "";
    Session session;

    EditText nameText;
    EditText addressText;
    EditText emailText;
    EditText mobileText;
    EditText passwordText;
    EditText reEnterPasswordText;
    Button signupButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        conn.checkInternet();

        session = Session.newInstance(this);
        URL_REG = session.get("server")+"/ihome/api/auth/reg.php";

        signupButton = findViewById(R.id.btn_signup);
        nameText = findViewById(R.id.input_name);
        addressText = findViewById(R.id.input_address);
        emailText = findViewById(R.id.input_email);
        mobileText = findViewById(R.id.input_mobile);
        passwordText = findViewById(R.id.input_password);
        reEnterPasswordText = findViewById(R.id.input_reEnterPassword);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Signup.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        final String name = nameText.getText().toString();
        final String address = addressText.getText().toString();
        final String email = emailText.getText().toString();
        final String mobile = mobileText.getText().toString();
        final String password = passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        onSignupSuccess();
                                        progressDialog.dismiss();
                                    }
                                },1000);
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(Signup.this,"Register Error!"+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onSignupFailed();
                        progressDialog.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("address",address);
                params.put("phone",mobile);
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onSignupSuccess() {
        setResult(RESULT_OK, null);
        Toast.makeText(Signup.this,"Registration Complete!",Toast.LENGTH_SHORT).show();
        signupButton.setEnabled(true);

    }
    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Failed!", Toast.LENGTH_LONG).show();
        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameText.getText().toString();
        String address = addressText.getText().toString();
        String email = emailText.getText().toString();
        String mobile = mobileText.getText().toString();
        String password = passwordText.getText().toString();
        String reEnterPassword = reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameText.setError("at least 3 characters");
            valid = false;
        } else {
            nameText.setError(null);
        }

        if (address.isEmpty()) {
            addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            addressText.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {
            reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            reEnterPasswordText.setError(null);
        }

        return valid;
    }
}