package com.example.hp.justhealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hp.justhealth.Common.Common;
import com.example.hp.justhealth.Retrofit.IMyAPI;

public class HeartRateActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtDNNormal, txtDNMax, txtDNLatihan;
    IMyAPI mService;

    String dn_normal, dn_latihan;
    double dn_max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_rate);

        // init API
        mService = Common.getAPI();

        // init views
        toolbar = (Toolbar) findViewById(R.id.heart_rate_toolbar);
        txtDNNormal = (TextView) findViewById(R.id.txtdnnormal_result);
        txtDNMax = (TextView) findViewById(R.id.txtdnmax_result);
        txtDNLatihan = (TextView) findViewById(R.id.txtdnlatihan_result);

        toolbar.setTitle("Denyut Nadi Anda");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // get intent data
        Intent intent = this.getIntent();
        dn_normal = intent.getStringExtra("dn_normal");
        dn_max = intent.getDoubleExtra("dn_max", 0.00);
        dn_latihan = intent.getStringExtra("dn_latihan");

        // set TextView
        txtDNNormal.setText(dn_normal);
        txtDNMax.setText(String.valueOf(dn_max) + " bpm");
        txtDNLatihan.setText(dn_latihan);

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
