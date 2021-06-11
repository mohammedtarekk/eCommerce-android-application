package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // UI elements
    EditText usernameTXT;
    EditText passwordTXT;
    CheckBox rememberMeCB;
    TextView forgotPasswordButton;
    Button loginButton;
    TextView signUpButton;

    // Backend variables
    public static String FullName;
    public static int UserID;
    DPOperations database;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        initiateEnvVariables();

        // Check if already logged in
        boolean comingFromSignUp = getIntent().getBooleanExtra("comingFromSignUp", false);
        if(sharedPref.getBoolean("login",false) && !comingFromSignUp){
            UserID = sharedPref.getInt("userID", 0);
            Intent i = new Intent(MainActivity.this, HomePageActivity.class);
            FullName = sharedPref.getString("fullName","");
            i.putExtra("name", FullName);
            startActivity(i);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean loginStatus = login();
                if(loginStatus){
                    Intent i = new Intent(MainActivity.this, HomePageActivity.class);
                    i.putExtra("name", FullName);
                    startActivity(i);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RecoverPasswordActivity.class);
                startActivity(i);
            }
        });
    }

    private void initiateEnvVariables(){
        usernameTXT = (EditText) findViewById(R.id.in_username);
        passwordTXT = (EditText) findViewById(R.id.in_password);
        rememberMeCB = (CheckBox) findViewById(R.id.in_rememberMe);
        forgotPasswordButton = (TextView) findViewById(R.id.bt_forgotPass);
        loginButton = (Button) findViewById(R.id.bt_login);
        signUpButton = (TextView) findViewById(R.id.bt_signUp);
        database = new DPOperations(this);
        sharedPref = getSharedPreferences("remember me", MODE_PRIVATE);
    }

    private boolean login(){
        String username = usernameTXT.getText().toString();
        String password = passwordTXT.getText().toString();
        boolean rememberMe = rememberMeCB.isChecked();

        if(!username.equals("") && !password.equals("")){
            Cursor cursor = database.userLogin(username, password);
            if(cursor.getCount() <= 0)
                Toast.makeText(getApplicationContext(),"Username or password is invalid", Toast.LENGTH_LONG).show();
            else
            {
                UserID = cursor.getInt(0);
                FullName = cursor.getString(1);
                if(rememberMe)
                    stayLoggedIn(username, FullName, password, true);
                else
                    stayLoggedIn("", "", "", false);

                return true;
            }
        }
        else
            Toast.makeText(getApplicationContext(),"Please enter your username and password", Toast.LENGTH_LONG).show();

        return false;
    }

    private void stayLoggedIn(String username, String FullName, String password, boolean loginFlag){
        editor = sharedPref.edit();
        editor.putInt("userID", UserID);
        editor.putString("username", username);
        editor.putString("fullName", FullName);
        editor.putString("password", password);
        editor.putBoolean("login", loginFlag);
        editor.apply();
    }
}