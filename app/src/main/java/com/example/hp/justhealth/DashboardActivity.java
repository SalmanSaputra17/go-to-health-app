package com.example.hp.justhealth;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.justhealth.Common.Common;
import com.example.hp.justhealth.Model.APIResponse;
import com.example.hp.justhealth.Retrofit.IMyAPI;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textName, textEmail;
    Button btnLogout;
    CardView cardBeratBadan, cardKalori, cardHeartRate, cardSettings;

    IMyAPI mService;

    Intent checkBB, caloriPage, heartRatePage, settingPage;

    String username, email, tgl_lahir;
    boolean isBackButtonPressed = false;
    SharePrefManager sharePrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // init API
        mService = Common.getAPI();

        // init sharePrefManager
        sharePrefManager = new SharePrefManager(this);

        // init views
        toolbar = (Toolbar) findViewById(R.id.dashboard_toolbar);
        textName = (TextView) findViewById(R.id.textname);
        textEmail = (TextView) findViewById(R.id.textemail);
        btnLogout = (Button) findViewById(R.id.btnlogout);
        cardBeratBadan = (CardView) findViewById(R.id.cardberatbadan);
        cardKalori = (CardView) findViewById(R.id.cardkalori);
        cardHeartRate = (CardView) findViewById(R.id.cardheartrate);
        cardSettings = (CardView) findViewById(R.id.cardsettings);

        toolbar.setTitle("Dashboard");
        setSupportActionBar(toolbar);

        // get intent data
        username = Common.currentUser.getUsername();
        email = Common.currentUser.getEmail();
        tgl_lahir = Common.currentUser.getTgl_lahir();

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

                        startActivity(new Intent(getApplicationContext(), SigninActivity.class)
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

        cardBeratBadan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBB = new Intent(DashboardActivity.this, CheckBBActivity.class);
                startActivity(checkBB);
            }
        });

        cardKalori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caloriPage = new Intent(DashboardActivity.this, CaloriActivity.class);
                startActivity(caloriPage);
            }
        });

        cardHeartRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDN(tgl_lahir);
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

    private void getDN(String tgl_lahir) {
        mService.DN(tgl_lahir)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse result = response.body();
                        if (result.isError()) {
                            Toast.makeText(DashboardActivity.this, result.getError_msg(), Toast.LENGTH_SHORT).show();
                        } else if (!result.isError()) {
                            heartRatePage = new Intent(DashboardActivity.this, HeartRateActivity.class);
                            heartRatePage.putExtra("dn_normal", result.getDn_normal());
                            heartRatePage.putExtra("dn_max", result.getDn_max());
                            heartRatePage.putExtra("dn_latihan", result.getDn_latihan());

                            startActivity(heartRatePage);
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {

                    }
                });
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
            case R.id.server_config :
                Toast.makeText(this, "Server Configuration", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about_us:
                Toast.makeText(this, "About us", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
