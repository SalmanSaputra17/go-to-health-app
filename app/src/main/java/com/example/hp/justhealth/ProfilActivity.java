package com.example.hp.justhealth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.justhealth.Common.Common;

public class ProfilActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtUsername, txtEmail, txtJK, txtTglLahir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        // init views
        toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        txtUsername = (TextView) findViewById(R.id.usernametext);
        txtEmail = (TextView) findViewById(R.id.emailtext);
        txtJK = (TextView) findViewById(R.id.jktext);
        txtTglLahir = (TextView) findViewById(R.id.tgllahirtext);

        toolbar.setTitle("Profil");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // events
        txtUsername.setText(Common.currentUser.getUsername().toString());
        txtEmail.setText(Common.currentUser.getEmail().toString());
        txtJK.setText(Common.currentUser.getJk().toString());
        txtTglLahir.setText(Common.currentUser.getTgl_lahir().toString());

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
