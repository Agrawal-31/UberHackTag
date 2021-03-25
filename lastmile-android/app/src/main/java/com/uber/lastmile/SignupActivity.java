package com.uber.lastmile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.uber.lastmile.utils.SharedPrefManager;
import com.uber.lastmile.utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.uber.lastmile.constants.constants.LOGIN_URL;
import static com.uber.lastmile.constants.constants.SIGNUP_URL;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private int mStatusCode;

    @BindView(R.id.input_name) EditText _nameText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_signup) Button _signupButton;
    @BindView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own signup logic here.
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", email);
            jsonBody.put("password", password);
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNUP_URL,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                //converting response to json object
                                JSONObject obj = new JSONObject(response);

                                //if no error in response
                                if (mStatusCode == HttpURLConnection.HTTP_OK) {
                                    Toast.makeText(getApplicationContext(), "Signed Up! Please login to continue", Toast.LENGTH_SHORT).show();

                                    //storing the user in shared preferences
                                    //SharedPrefManager.getInstance(getApplicationContext()).userLogin(obj.getString("token"));
                                    //starting the profile activity
                                    onSignupSuccess();
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                } else {
                                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    onSignupFailed();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                            Log.e("VOLLEY",error.getMessage());
                        }
                    }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        mStatusCode = response.statusCode;
                        // can get more details such as response.headers
                    }
                    return super.parseNetworkResponse(response);
                }
            };

            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }catch (Exception e){
            Log.e("VOLLEY", e.toString());
        }
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            _passwordText.setError("between 4 and 20 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}