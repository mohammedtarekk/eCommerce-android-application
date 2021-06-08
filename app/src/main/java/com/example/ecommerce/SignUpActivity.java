package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    EditText fullNameTXT;
    EditText userNameTXT;
    EditText passwordTXT;
    EditText jobTXT;
    Spinner genderTXT;
    TextView birthDateTXT;
    Button signUpButton;
    DPOperations database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initiateEnvVariables();

        signUpButton.setOnClickListener(v -> {
            String fullName = fullNameTXT.getText().toString();
            String username = userNameTXT.getText().toString();
            String password = passwordTXT.getText().toString();
            String job = jobTXT.getText().toString();
            String gender = genderTXT.getSelectedItem().toString();
            String birthDate = birthDateTXT.getText().toString();

            if(!fullName.equals("") && !username.equals("") && !password.equals("") && !job.equals("") && !gender.equals("") && !birthDate.equals("") && !birthDate.equals("Set Birthdate")){
                User user = new User(fullName, username, password, job, gender, birthDate);
                database.userSignUp(user);
                Toast.makeText(getApplicationContext(),"Successfully registered, back to login!", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                i.putExtra("comingFromSignUp", true);
                startActivity(i);
            }
            else
                Toast.makeText(getApplicationContext(),"Make sure you have entered all the data", Toast.LENGTH_LONG).show();
        });

        // show date picker
        birthDateTXT.setOnClickListener(v -> handleDatePicking());
    }

    private void initiateEnvVariables(){
        fullNameTXT = (EditText) findViewById(R.id.in_fullName);
        userNameTXT = (EditText) findViewById(R.id.in_usernameNew);
        passwordTXT = (EditText) findViewById(R.id.in_passwordNew);
        jobTXT = (EditText) findViewById(R.id.in_jobNew);
        genderTXT = (Spinner) findViewById(R.id.gender);
        birthDateTXT = (TextView) findViewById(R.id.BirthDate);
        signUpButton = (Button) findViewById(R.id.signUp_bt);
        database = new DPOperations(this);
    }

    private void handleDatePicking(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    monthOfYear++;
                    String date = ""+year+" / "+monthOfYear+" / "+dayOfMonth+"";
                    birthDateTXT.setText(date);
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}