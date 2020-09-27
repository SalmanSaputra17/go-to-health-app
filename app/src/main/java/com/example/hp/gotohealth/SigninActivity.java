package com.example.hp.gotohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gotohealth.Common.Common;
import com.example.hp.gotohealth.Retrofit.APIService;

public class SigninActivity extends AppCompatActivity {

    EditText email, password;
    Button btnSignIn;
    TextView txtSignUp;

    boolean isBackButtonPressed = false;

    APIService apiService;

    Intent toDashboard, toSignUp;

    SharePrefManager sharePrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // init sharePrefManager
        sharePrefManager = new SharePrefManager(this);

        // init API
        apiService = Common.getAPI();

        // init view
        email = (EditText) findViewById(R.id.textemailsignin);
        password = (EditText) findViewById(R.id.textpasswordsignin);
        btnSignIn = (Button) findViewById(R.id.btnsignin);
        txtSignUp = (TextView) findViewById(R.id.textsignup);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser(email.getText().toString(), password.getText().toString());
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSignUp = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(toSignUp);
            }
        });

        if (sharePrefManager.getSpHasLogin()) {

        }

    }

    private void authenticateUser(String email, String password) {

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
