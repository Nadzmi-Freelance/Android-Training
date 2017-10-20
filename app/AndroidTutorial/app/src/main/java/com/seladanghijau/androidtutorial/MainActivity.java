package com.seladanghijau.androidtutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize views
        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvText = (TextView) findViewById(R.id.tvText);

        // initialize OnClickListener
        btnLogin.setOnClickListener(this);
        tvText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnLogin:
                String username, password;
                String message;

                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                message = "Username = " + username +
                        "\nPassword = " + password;

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvText:
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                message = "Username = " + username +
                        "\nPassword = " + password;

                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
