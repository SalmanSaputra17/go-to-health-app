package com.example.hp.justhealth;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.justhealth.Common.Common;
import com.example.hp.justhealth.Model.APIResponse;
import com.example.hp.justhealth.Retrofit.IMyAPI;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    EditText username, tgl_lahir, email, password;
    Spinner jk;
    Button btnsignup;
    TextView txtsignin;
    Intent signin;

    IMyAPI mService;

    String stringJk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // init API
        mService = Common.getAPI();

        // init view
        username = (EditText) findViewById(R.id.textusernamesignup);
        jk = (Spinner) findViewById(R.id.textjksignup);
        tgl_lahir = (EditText) findViewById(R.id.texttglsignup);
        email = (EditText) findViewById(R.id.textemailsignup);
        password = (EditText) findViewById(R.id.textpasswordsignup);
        btnsignup = (Button) findViewById(R.id.btnsignup);
        txtsignin = (TextView) findViewById(R.id.textsignin);

        // jk adapter
        String[] jkString = new String[]{"Jenis Kelamin", "Laki-laki", "Perempuan"};

        final List<String> jkList = new ArrayList<>(Arrays.asList(jkString));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_black, jkList) {
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
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }

                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        jk.setAdapter(spinnerArrayAdapter);

        jk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringJk = (String) parent.getItemAtPosition(position);
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

        // memunculkan pop-up datepicker ketika textfield di klik
        tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignupActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        txtsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatNewUser(username.getText().toString(), stringJk, tgl_lahir.getText().toString(),
                        email.getText().toString(), password.getText().toString());
            }
        });

    }

    private void creatNewUser(String username, String jk, String tgl_lahir, String email, String password) {
        mService.registerUser(username, jk, tgl_lahir, email, password)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse result = response.body();
                        if (result.isError()) {
                            Toast.makeText(SignupActivity.this, result.getError_msg(), Toast.LENGTH_SHORT).show();
                        } else if (!result.isError()) {
                            Toast.makeText(SignupActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), SigninActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(SignupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        tgl_lahir.setText(sdf.format(myCalendar.getTime()));
    }

}
