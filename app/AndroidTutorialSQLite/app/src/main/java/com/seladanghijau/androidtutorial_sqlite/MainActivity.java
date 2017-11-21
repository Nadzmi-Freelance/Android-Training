package com.seladanghijau.androidtutorial_sqlite;

import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.seladanghijau.androidtutorial_sqlite.db.MyDB;
import com.seladanghijau.androidtutorial_sqlite.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etEmail, etPassword;
    Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        // init listener
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);

        // process
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("key", MODE_PRIVATE);

        if(!sharedPreferences.getString("id", "").isEmpty()) {
            startActivity(new Intent(this, Profile.class));
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btnLogin:
                String email, password;

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                new Login(this, email, password).execute();
                break;
            case R.id.btnRegister:
                startActivity(new Intent(this, RegisterPage.class));
                break;
        }
    }
}

class Login extends AsyncTask<Void, Void, User> {
    private MyDB db;
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor shEditor;
    private String email, password;
    private ProgressDialog progressDialog;
    private Toast msgSuccess, msgFail;

    public Login(Context context, String email, String password) {
        this.email = email;
        this.password = password;
        this.context = context;

        db = Room.databaseBuilder(context, MyDB.class, "DB").build();
        sharedPreferences = context.getSharedPreferences("key", MODE_PRIVATE);
        shEditor = sharedPreferences.edit();

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loging in...");
        progressDialog.setCancelable(false);

        msgSuccess = Toast.makeText(context, "Logged in", Toast.LENGTH_LONG);
        msgFail = Toast.makeText(context, "Failed to login", Toast.LENGTH_LONG);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(User user) {
        if(user != null) {
            Intent profilePage = new Intent(context, Profile.class);

            profilePage.putExtra("id", user.getId());
            profilePage.putExtra("email", user.getEmail());
            profilePage.putExtra("name", user.getName());
            profilePage.putExtra("password", user.getPassword());

            shEditor.putString("id", "" + user.getId());
            shEditor.putString("email", user.getEmail());
            shEditor.putString("name", user.getName());
            shEditor.putString("password", user.getPassword());
            shEditor.commit();

            context.startActivity(profilePage);
            msgSuccess.show();
        } else
            msgFail.show();

        if(progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected User doInBackground(Void... voids) {
        User user = db.userDAO().findByEmailAndPassword(email, password);

        return user;
    }
}
