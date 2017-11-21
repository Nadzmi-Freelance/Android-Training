package com.seladanghijau.androidtutorial_sqlite;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.seladanghijau.androidtutorial_sqlite.db.MyDB;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    TextView tvId, tvName, tvEmail, tvPassword;
    Button btnLogout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor shEditor;
    private String id, email, name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // init views
        tvId = findViewById(R.id.tvId);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPassword = findViewById(R.id.tvPassword);
        btnLogout = findViewById(R.id.btnLogout);

        // init listener
        btnLogout.setOnClickListener(this);

        // process
        sharedPreferences = getApplication().getSharedPreferences("key", MODE_PRIVATE);
        shEditor = sharedPreferences.edit();

        if(!sharedPreferences.getString("id", "").isEmpty()) {
            id = sharedPreferences.getString("id", "");
            email = sharedPreferences.getString("email", "");
            name = sharedPreferences.getString("name", "");
            password = sharedPreferences.getString("password", "");
        } else {
            id = getIntent().getStringExtra("id");
            email = getIntent().getStringExtra("email");
            name = getIntent().getStringExtra("name");
            password = getIntent().getStringExtra("password");
        }

        tvId.setText("ID: " + id);
        tvName.setText("Email: " + email);
        tvEmail.setText("Name: " + name);
        tvPassword.setText("Password: " + password);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnLogout:
                shEditor.clear();
                shEditor.commit();

                finish();
                break;
        }
    }
}
