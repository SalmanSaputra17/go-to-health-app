package com.example.hp.justhealth;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.justhealth.Common.Common;
import com.example.hp.justhealth.Model.APIResponse;
import com.example.hp.justhealth.Retrofit.IMyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    EditText emailsignin, passwordsignin;
    Button btnsignin;
    TextView txtsignup;

    boolean isBackButtonPressed = false;

    IMyAPI mService;

    Intent toDashboard, signup;

    SharePrefManager sharePrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // init sharePrefManager
        sharePrefManager = new SharePrefManager(this);

        // init API
        mService = Common.getAPI();

        // init view
        emailsignin = (EditText) findViewById(R.id.textemailsignin);
        passwordsignin = (EditText) findViewById(R.id.textpasswordsignin);
        btnsignin = (Button) findViewById(R.id.btnsignin);
        txtsignup = (TextView) findViewById(R.id.textsignup);

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser(emailsignin.getText().toString(), passwordsignin.getText().toString());
            }
        });

        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(signup);
            }
        });

        if (sharePrefManager.getSpHasLogin()) {
            mService.getUserInfomartion(sharePrefManager.getSpEmail())
                    .enqueue(new Callback<APIResponse>() {
                        @Override
                        public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                            APIResponse result = response.body();
                            if (result.isError()) {
                               finish();
                            } else if (!result.isError()){
                                Common.currentUser = result.getUser();
                                startActivity(new Intent(getApplicationContext(), DashboardActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<APIResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void authenticateUser(String email, String password) {
        mService.loginUser(email, password)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse result = response.body();
                        if (result.isError()) {
                            Toast.makeText(SigninActivity.this, result.getError_msg(), Toast.LENGTH_SHORT).show();
                        } else if (!result.isError()) {
                            Common.currentUser = result.getUser();
                            sharePrefManager.saveSPString(SharePrefManager.SP_EMAIL, result.getUser().getEmail());
                            sharePrefManager.saveSPBoolean(SharePrefManager.SP_HAS_LOGIN, true);

                            toDashboard = new Intent(getApplicationContext(), DashboardActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(toDashboard);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(SigninActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

}
