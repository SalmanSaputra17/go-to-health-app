package com.example.hp.justhealth;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.justhealth.Common.Common;
import com.example.hp.justhealth.Model.APIResponse;
import com.example.hp.justhealth.Retrofit.IMyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtProfil, txtFAQ, txtHapusAkun;

    SharePrefManager sharePrefManager;
    IMyAPI mService;

    Intent toProfil, toFAQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // init API
        mService = Common.getAPI();

        // init sharePrefManager
        sharePrefManager = new SharePrefManager(this);

        // init views
        toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        txtProfil = (TextView) findViewById(R.id.txtprofil);
        txtFAQ = (TextView) findViewById(R.id.txtfaq);
        txtHapusAkun = (TextView) findViewById(R.id.txthapusakun);

        toolbar.setTitle("Pengaturan");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toProfil = new Intent(SettingsActivity.this, ProfilActivity.class);
                startActivity(toProfil);
            }
        });

        txtFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toFAQ = new Intent(SettingsActivity.this, FAQsActivity.class);
                startActivity(toFAQ);
            }
        });

        txtHapusAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setTitle("Delete Account");
                builder.setMessage("Do you really want to delete your account ?");

                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mService.deleteUserAccount(Common.currentUser.getEmail())
                                .enqueue(new Callback<APIResponse>() {
                                    @Override
                                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                                        APIResponse result = response.body();
                                        if (result.isError()) {
                                            Toast.makeText(SettingsActivity.this, result.getError_msg(), Toast.LENGTH_SHORT).show();
                                        } else if (!result.isError()){
                                            Common.currentUser = null;
                                            sharePrefManager.saveSPString(SharePrefManager.SP_EMAIL, "");
                                            sharePrefManager.saveSPBoolean(SharePrefManager.SP_HAS_LOGIN, false);

                                            startActivity(new Intent(getApplicationContext(), SigninActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

                                            Toast.makeText(SettingsActivity.this, "Account has been deleted.", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<APIResponse> call, Throwable t) {
                                        Toast.makeText(SettingsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
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
