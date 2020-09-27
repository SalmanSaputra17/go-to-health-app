package com.example.hp.gotohealth;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gotohealth.Common.Common;
import com.example.hp.gotohealth.Retrofit.APIService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayCalorieActivity extends AppCompatActivity {

    Animation fade;

    Toolbar toolbar;
    EditText txtBodyMass, txtBodyHeight;
    Spinner activityLevel;
    CardView cardResult;
    TextView txtResult;
    Button btnCalculate, btnReset;

    APIService apiService;

    int sdk = android.os.Build.VERSION.SDK_INT;
    double weight, height;
    String gender, dateOfBirth, selectedActivityLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_calorie);

        // init API
        apiService = Common.getAPI();

        // init views
        fade = AnimationUtils.loadAnimation(this, R.anim.fade);

        toolbar = (Toolbar) findViewById(R.id.day_calori_toolbar);
        txtBodyMass = (EditText) findViewById(R.id.textberatbadankalori1);
        txtBodyHeight = (EditText) findViewById(R.id.texttinggibadankalori1);
        activityLevel = (Spinner) findViewById(R.id.spinnerkegiatan);
        cardResult = (CardView) findViewById(R.id.cardresultkalori1);
        txtResult = (TextView) findViewById(R.id.textresultkalori1);
        btnCalculate = (Button) findViewById(R.id.btnhitungkalori1);
        btnReset = (Button) findViewById(R.id.btnresetdaykalori);

        toolbar.setTitle("Kalori per Hari");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // get intent data
        // gender = Common.currentUser.getGender().toString();
        // dateOfBirth = Common.currentUser.getDateOfBirth().toString();

        // level kegiatan adapter
        String[] levelKegiatanString = new String[]{"Level Kegiatan", "Tidak aktif", "Ringan", "Sedang", "Berat", "Sangat berat"};
        final List<String> levelKegiatanList = new ArrayList<>(Arrays.asList(levelKegiatanString));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_black, levelKegiatanList) {
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
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }

                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        activityLevel.setAdapter(spinnerArrayAdapter);

        activityLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    selectedActivityLevel = "";
                } else {
                    selectedActivityLevel = (String) parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // set animation alpha
        cardResult.setVisibility(View.GONE);

        // events
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtBodyMass.getText().toString().isEmpty() || txtBodyHeight.getText().toString().isEmpty() || selectedActivityLevel.isEmpty()) {
                    Toast.makeText(DayCalorieActivity.this, "Please, complete all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    weight = Double.parseDouble(txtBodyMass.getText().toString());
                    height = Double.parseDouble(txtBodyHeight.getText().toString());
                    calculateCalorie(gender, dateOfBirth, weight, height, selectedActivityLevel);

                    btnCalculate.setEnabled(false);

                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        btnCalculate.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                    } else {
                        btnCalculate.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                    }
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                btnCalculate.setEnabled(true);
            }
        });

    }

    private void calculateCalorie(String gender, String dateOfBirth, double weight, double height, String activityLevel) {

    }

    private void reset() {
        txtBodyMass.setText("");
        txtBodyHeight.setText("");
        activityLevel.setSelection(0);
        txtResult.setText("");
        cardResult.setVisibility(View.GONE);

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            btnCalculate.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtngradient));
        } else {
            btnCalculate.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtngradient));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
