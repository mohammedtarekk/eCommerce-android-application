package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RecoverPasswordActivity extends AppCompatActivity {

    EditText usernameTXT;
    Button recoverButton;
    TextView recoveredPassword;
    DPOperations database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        getSupportActionBar().hide();
        initiateEnvVariables();

        recoverButton.setOnClickListener(v -> {
            String username = usernameTXT.getText().toString();
            recoveredPassword.setText("");
            if(!username.equals("")){
                Cursor cursor = database.recoverPassword(username);
                if(cursor.getCount() <= 0)
                    Toast.makeText(getApplicationContext(),"Username is invalid", Toast.LENGTH_LONG).show();
                else{
                    String password = "Your password is: " + cursor.getString(0);
                    recoveredPassword.setText(password);
                }
            }
            else
                Toast.makeText(getApplicationContext(),"Please enter your username", Toast.LENGTH_LONG).show();
        });

    }

    private void initiateEnvVariables(){
        usernameTXT = (EditText) findViewById(R.id.in_usernameRec);
        recoverButton = (Button) findViewById(R.id.recover_bt);
        recoveredPassword = (TextView) findViewById(R.id.RecoverdPassword);
        database = new DPOperations(this);
    }
}