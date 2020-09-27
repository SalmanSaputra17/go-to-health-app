package com.example.hp.gotohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.example.hp.gotohealth.Common.Common;
import com.example.hp.gotohealth.Retrofit.APIService;

public class HeartRateActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtNormalHeartRate, txtMaxHeartRate, txtExerciseHeartRate;
    APIService apiService;

    double normalHeartRate, maxHeartRate, exerciseHeartRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

        // init API
        apiService = Common.getAPI();

        // init views
        toolbar = (Toolbar) findViewById(R.id.heart_rate_toolbar);
        txtNormalHeartRate = (TextView) findViewById(R.id.txtdnnormal_result);
        txtMaxHeartRate = (TextView) findViewById(R.id.txtdnmax_result);
        txtExerciseHeartRate = (TextView) findViewById(R.id.txtdnlatihan_result);

        toolbar.setTitle("Denyut Nadi Anda");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // get intent data
        Intent intent = this.getIntent();
        normalHeartRate = intent.getDoubleExtra("normalHeartRate", 0.00);
        maxHeartRate = intent.getDoubleExtra("maxHeartRate", 0.00);
        exerciseHeartRate = intent.getDoubleExtra("exerciseHeartRate", 0.00);

        // set TextView
        txtNormalHeartRate.setText(String.valueOf(normalHeartRate) + " bpm");
        txtMaxHeartRate.setText(String.valueOf(maxHeartRate) + " bpm");
        txtExerciseHeartRate.setText(String.valueOf(exerciseHeartRate) + " bpm");

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
