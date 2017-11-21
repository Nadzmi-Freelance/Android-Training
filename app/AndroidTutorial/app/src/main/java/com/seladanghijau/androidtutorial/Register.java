package com.seladanghijau.androidtutorial;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail, etName, etPassword;
    Button btnRegister;

    private RequestQueue requestQueue;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // initialize views
        etEmail = (EditText) findViewById(R.id.etEmail);
        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        // initialize listener
        btnRegister.setOnClickListener(this);

        // process
        requestQueue = Volley.newRequestQueue(this);

        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");

        etEmail.setText(email);
        etPassword.setText(password);
    }

    @Override
    public void onClick(View v) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        switch (v.getId()) {
            case R.id.btnRegister:
                final String email, name, password;
                String url;

                url = "https://test-ground.000webhostapp.com/register.php";

                email = etEmail.getText().toString();
                name = etName.getText().toString();
                password = etPassword.getText().toString();

                try {
                    StringRequest registerRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);

                                if(jsonResponse.getString("status").equalsIgnoreCase("1")) {
                                    Toast.makeText(getApplicationContext(), "Registration successful.", Toast.LENGTH_LONG).show();
                                    finish();
                                } else
                                    Toast.makeText(getApplicationContext(), "Failed to register.", Toast.LENGTH_LONG).show();
                            } catch (Exception e) { e.printStackTrace(); }

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "An error has occurred.", Toast.LENGTH_LONG).show();

                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params;

                            params = new HashMap<>();
                            params.put("email", email);
                            params.put("name", name);
                            params.put("password", password);

                            return params;
                        }
                    };

                    requestQueue.add(registerRequest);
                    progressDialog.show();
                } catch (Exception e) { e.printStackTrace(); }
                break;
        }
    }
}
