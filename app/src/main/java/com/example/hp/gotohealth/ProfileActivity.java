package com.example.hp.gotohealth;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtUsername, txtEmail, txtGender, txtDateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // init views
        toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        txtUsername = (TextView) findViewById(R.id.usernametext);
        txtEmail = (TextView) findViewById(R.id.emailtext);
        txtGender = (TextView) findViewById(R.id.jktext);
        txtDateOfBirth = (TextView) findViewById(R.id.tgllahirtext);

        toolbar.setTitle("Profil");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // events
        // txtUsername.setText(Common.currentUser.getUsername().toString());
        // txtEmail.setText(Common.currentUser.getEmail().toString());
        // txtGender.setText(Common.currentUser.getJk().toString());
        // txtDateOfBirth.setText(Common.currentUser.getTgl_lahir().toString());
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
