package com.example.hp.gotohealth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gotohealth.Common.Common;
import com.example.hp.gotohealth.Retrofit.APIService;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textName, textEmail;
    Button btnLogout;
    CardView cardBodyMass, cardCalorie, cardHeartRate, cardSettings;

    APIService apiService;

    Intent calculateIBMPage, caloriePage, heartRatePage, settingPage;

    String username, email, dateOfBirth;
    boolean isBackButtonPressed = false;

    SharePrefManager sharePrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // init API
        apiService = Common.getAPI();

        // init sharePrefManager
        sharePrefManager = new SharePrefManager(this);

        // init views
        toolbar = (Toolbar) findViewById(R.id.dashboard_toolbar);
        textName = (TextView) findViewById(R.id.textname);
        textEmail = (TextView) findViewById(R.id.textemail);

        btnLogout = (Button) findViewById(R.id.btnlogout);

        cardBodyMass = (CardView) findViewById(R.id.cardberatbadan);
        cardCalorie = (CardView) findViewById(R.id.cardkalori);
        cardHeartRate = (CardView) findViewById(R.id.cardheartrate);
        cardSettings = (CardView) findViewById(R.id.cardsettings);

        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        // get intent data
        // username = Common.currentUser.getUsername();
        // email = Common.currentUser.getEmail();
        // dateOfBirth = Common.currentUser.getTgl_lahir();

        textName.setText(username);
        textEmail.setText(email);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
                builder.setTitle("Sign-out");
                builder.setMessage("Do you really want to sign-out ?");

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Common.currentUser = null;
                        sharePrefManager.saveSPString(SharePrefManager.SP_EMAIL, "");
                        sharePrefManager.saveSPBoolean(SharePrefManager.SP_HAS_LOGIN, false);

                        startActivity(new Intent(getApplicationContext(), SignInActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                });

                builder.setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
            }
        });

        cardBodyMass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateIBMPage = new Intent(DashboardActivity.this, CalculateIBMActivity.class);
                startActivity(calculateIBMPage);
            }
        });

        cardCalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caloriePage = new Intent(DashboardActivity.this, CalorieActivity.class);
                startActivity(caloriePage);
            }
        });

        cardSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingPage = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(settingPage);
            }
        });
    }

    private void getHeartRate(String dateOfBirth) {

    }

    @Override
    public void onBackPressed() {
        if (isBackButtonPressed) {
            super.onBackPressed();
            return;
        }

        isBackButtonPressed = true;
        Toast.makeText(this, "Please press Back once again to exit !", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        isBackButtonPressed = false;
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us:
                Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
