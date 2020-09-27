package com.example.hp.gotohealth;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hp.gotohealth.Common.Common;
import com.example.hp.gotohealth.Retrofit.APIService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();

    EditText username, dateOfBirth, email, password, confirmPassword;
    Spinner gender;
    Button btnSignUp;
    TextView txtSignIn;
    Intent toSignIn;

    APIService apiService;

    String stringGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // init API
        apiService = Common.getAPI();

        // init view
        username = (EditText) findViewById(R.id.textusernamesignup);
        gender = (Spinner) findViewById(R.id.textjksignup);
        dateOfBirth = (EditText) findViewById(R.id.texttglsignup);
        email = (EditText) findViewById(R.id.textemailsignup);
        password = (EditText) findViewById(R.id.textpasswordsignup);
        confirmPassword = (EditText) findViewById(R.id.textconfirmpasswordsignup);
        btnSignUp = (Button) findViewById(R.id.btnsignup);
        txtSignIn = (TextView) findViewById(R.id.textsignin);

        // gender adapter
        String[] genderString = new String[]{"Jenis Kelamin", "Laki-laki", "Perempuan"};

        final List<String> genderList = new ArrayList<>(Arrays.asList(genderString));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_black, genderList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }

                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        gender.setAdapter(spinnerArrayAdapter);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringGender = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // init pop-up datepicker
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateLabel();
            }
        };

        // shop pop-up datepicker
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewUser(username.getText().toString(), stringGender, dateOfBirth.getText().toString(),
                        email.getText().toString(), password.getText().toString(), confirmPassword.getText().toString());
            }
        });

    }

    private void createNewUser(String username, String gender, String dateOfBirth, String email, String password, String confirmPassword) {

    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateOfBirth.setText(sdf.format(myCalendar.getTime()));
    }

}
