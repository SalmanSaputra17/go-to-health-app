package com.example.hp.gotohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.hp.gotohealth.Common.Common;
import com.example.hp.gotohealth.Retrofit.APIService;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtProfile, txtFAQ, txtDeleteAccount;

    SharePrefManager sharePrefManager;
    APIService apiService;

    Intent toProfile, toFAQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // init API
        apiService = Common.getAPI();

        // init sharePrefManager
        sharePrefManager = new SharePrefManager(this);

        // init views
        toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        txtProfile = (TextView) findViewById(R.id.txtprofil);
        txtFAQ = (TextView) findViewById(R.id.txtfaq);
        txtDeleteAccount = (TextView) findViewById(R.id.txthapusakun);

        toolbar.setTitle("Pengaturan");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toProfile = new Intent(SettingsActivity.this, ProfileActivity.class);
                startActivity(toProfile);
            }
        });

        txtFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFAQ = new Intent(SettingsActivity.this, FAQsActivity.class);
                startActivity(toFAQ);
            }
        });
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
