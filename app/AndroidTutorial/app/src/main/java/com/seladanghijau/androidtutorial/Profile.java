package com.seladanghijau.androidtutorial;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    TextView tvID, tvName, tvEmail, tvPassword;
    Button btnLogout;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor shEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // init views
        tvID = (TextView) findViewById(R.id.tvID);
        tvName = (TextView) findViewById(R.id.tvName);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvPassword = (TextView) findViewById(R.id.tvPassword);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // init listener
        btnLogout.setOnClickListener(this);

        // process
        sharedPreferences = this.getSharedPreferences("key", MODE_PRIVATE);
        shEditor = sharedPreferences.edit();

        if(sharedPreferences.contains("user-info")) {
            try {
                JSONObject info = new JSONObject(sharedPreferences.getString("user-info", ""));

                tvID.setText(info.getString("id"));
                tvName.setText(info.getString("name"));
                tvEmail.setText(info.getString("email"));
                tvPassword.setText(info.getString("password"));
            } catch (Exception e) { e.printStackTrace(); }
        } else {
            try {
                JSONObject info = new JSONObject(getIntent().getStringExtra("info"));

                tvID.setText(info.getString("id"));
                tvName.setText(info.getString("name"));
                tvEmail.setText(info.getString("email"));
                tvPassword.setText(info.getString("password"));
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogout:
                shEditor.clear();
                shEditor.commit();

                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }
}
