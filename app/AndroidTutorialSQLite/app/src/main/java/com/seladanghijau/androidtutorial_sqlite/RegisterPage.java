package com.seladanghijau.androidtutorial_sqlite;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seladanghijau.androidtutorial_sqlite.db.MyDB;
import com.seladanghijau.androidtutorial_sqlite.model.User;

import java.util.List;

public class RegisterPage extends AppCompatActivity implements View.OnClickListener {
    EditText etName, etEmail, etPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // init view
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // init listener
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnRegister:
                String name, email, password;

                name = etName.getText().toString();
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                new Register(this, name, email, password).execute();
                finish();
                break;
        }
    }
}

class Register extends AsyncTask<Void, Void, Boolean> {
    private MyDB db;
    private ProgressDialog progressDialog;
    private String name, email, password;
    private Toast msgSuccess, msgFail;

    public Register(Context context, String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;

        db = Room.databaseBuilder(context, MyDB.class, "DB").build();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Registering...");

        msgSuccess = Toast.makeText(context, "Registered successfully.", Toast.LENGTH_LONG);
        msgFail = Toast.makeText(context, "Registration failed.", Toast.LENGTH_LONG);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Boolean registered) {
        if(registered)
            msgSuccess.show();
        else
            msgFail.show();

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        User newUser = new User(name, email, password);

        long newRowID = db.userDAO().insert(newUser);

        if(newRowID != 0)
            return true;
        else
            return false;
    }
}
