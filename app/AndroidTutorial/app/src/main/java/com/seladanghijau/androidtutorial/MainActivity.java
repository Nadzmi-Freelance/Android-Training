package com.seladanghijau.androidtutorial;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEemail, etPassword;
    Button btnLogin;

    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor shEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize views
        etEemail = (EditText) findViewById(R.id.etEemail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        // initialize OnClickListener
        btnLogin.setOnClickListener(this);

        // process
        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getApplication().getSharedPreferences("key", MODE_PRIVATE);

        if(!sharedPreferences.getString("user-info", "").isEmpty()) {
            startActivity(new Intent(this, Profile.class));
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogin:
                final String email, password;
                String url;

                email = etEemail.getText().toString();
                password = etPassword.getText().toString();

                url = getString(R.string.login_url);

                try {
                    StringRequest loginRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if(!response.isEmpty()) {
                                    shEditor = sharedPreferences.edit();

                                    shEditor.putString("user-info", response);
                                    shEditor.commit();

                                    Intent profile = new Intent(getApplicationContext(), Profile.class);
                                    profile.putExtra("info", response);

                                    startActivity(profile);
                                    finish();
                                } else
                                    Toast.makeText(getApplicationContext(), "No record found", Toast.LENGTH_LONG).show();
                            } catch (Exception e) { e.printStackTrace(); }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_LONG).show();
                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();

                            params.put("email", email);
                            params.put("password", password);

                            return params;
                        }
                    };

                    requestQueue.add(loginRequest);
                } catch (Exception e) { e.printStackTrace(); }
                break;
        }
    }
}
