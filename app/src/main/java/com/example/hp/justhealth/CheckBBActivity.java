package com.example.hp.justhealth;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.justhealth.Common.Common;
import com.example.hp.justhealth.Model.APIResponse;
import com.example.hp.justhealth.Retrofit.IMyAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckBBActivity extends AppCompatActivity {

    Animation fade;

    Toolbar toolbar;
    EditText txtBeratBadan, txtTinggiBadan;
    TextView txtresult, txtketerangan;
    CardView cardresult;
    Button btnHitung, btnReset;

    IMyAPI mService;
    int sdk = android.os.Build.VERSION.SDK_INT;
    double berat_badan, tinggi_badan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bb);

        // init API
        mService = Common.getAPI();

        // init views
        fade = AnimationUtils.loadAnimation(this, R.anim.fade);

        toolbar = (Toolbar) findViewById(R.id.check_bb_toolbar);
        txtBeratBadan = (EditText) findViewById(R.id.textberatbadan);
        txtTinggiBadan = (EditText) findViewById(R.id.texttinggibadan);
        txtresult = (TextView) findViewById(R.id.textresult);
        txtketerangan = (TextView) findViewById(R.id.textketerangan);
        cardresult = (CardView) findViewById(R.id.cardresult);
        btnHitung = (Button) findViewById(R.id.btnhitungbb);
        btnReset = (Button) findViewById(R.id.btnresetbb);

        toolbar.setTitle("Kalkulator BMI");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cardresult.setVisibility(View.GONE);

        // events
        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtBeratBadan.getText().toString().isEmpty() || txtTinggiBadan.getText().toString().isEmpty()) {
                    Toast.makeText(CheckBBActivity.this, "Please, complete all the fields !", Toast.LENGTH_SHORT).show();
                } else {
                    berat_badan = Double.parseDouble(txtBeratBadan.getText().toString());
                    tinggi_badan = Double.parseDouble(txtTinggiBadan.getText().toString());
                    hitungIMT(berat_badan, tinggi_badan);

                    btnHitung.setEnabled(false);

                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        btnHitung.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                    } else {
                        btnHitung.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                    }
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                btnHitung.setEnabled(true);
            }
        });

    }

    private void hitungIMT(double berat_badan, double tinggi_badan) {
        mService.checkBBUser(berat_badan, tinggi_badan)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse result = response.body();
                        if (result.isError()){
                            Toast.makeText(CheckBBActivity.this, result.getError_msg(), Toast.LENGTH_SHORT).show();
                        } else if (!result.isError()){
                            txtresult.setText(String.valueOf(result.getBB_ideal()) + " Kg");
                            txtketerangan.setText(result.getKategori());

                            cardresult.setVisibility(View.VISIBLE);
                            cardresult.startAnimation(fade);
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(CheckBBActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void reset() {
        txtTinggiBadan.setText("");
        txtBeratBadan.setText("");
        txtresult.setText("");
        txtketerangan.setText("");
        cardresult.setVisibility(View.GONE);

        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            btnHitung.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtngradient));
        } else {
            btnHitung.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtngradient));
        }
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
