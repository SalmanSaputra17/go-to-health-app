package com.example.hp.justhealth;

import android.graphics.Color;
import android.media.Image;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.justhealth.Common.Common;
import com.example.hp.justhealth.Model.APIResponse;
import com.example.hp.justhealth.Retrofit.IMyAPI;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DayCaloriActivity extends AppCompatActivity {

    Animation fade;

    Toolbar toolbar;
    EditText txtBeratBadan, txtTinggiBadan;
    Spinner levelKegiatan;
    CardView cardResult;
    TextView txtResult;
    Button btnHitung, btnReset;

    IMyAPI mService;

    int sdk = android.os.Build.VERSION.SDK_INT;
    double berat_badan, tinggi_badan;
    String jenis_kelamin, tgl_lahir, levelKegiatanSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_calori);

        // init API
        mService = Common.getAPI();

        // init views
        fade = AnimationUtils.loadAnimation(this, R.anim.fade);

        toolbar = (Toolbar) findViewById(R.id.day_calori_toolbar);
        txtBeratBadan = (EditText) findViewById(R.id.textberatbadankalori1);
        txtTinggiBadan = (EditText) findViewById(R.id.texttinggibadankalori1);
        levelKegiatan = (Spinner) findViewById(R.id.spinnerkegiatan);
        cardResult = (CardView) findViewById(R.id.cardresultkalori1);
        txtResult = (TextView) findViewById(R.id.textresultkalori1);
        btnHitung = (Button) findViewById(R.id.btnhitungkalori1);
        btnReset = (Button) findViewById(R.id.btnresetdaykalori);

        toolbar.setTitle("Kalori per Hari");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // get intent data
        jenis_kelamin = Common.currentUser.getJk().toString();
        tgl_lahir = Common.currentUser.getTgl_lahir().toString();

        // level kegiatan adapter
        String[] levelKegiatanString = new String[]{"Level Kegiatan", "Tidak aktif", "Ringan", "Sedang", "Berat", "Sangat berat"};
        final List<String> levelKegiatanList = new ArrayList<>(Arrays.asList(levelKegiatanString));

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item_black, levelKegiatanList) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }

                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item_black);
        levelKegiatan.setAdapter(spinnerArrayAdapter);

        levelKegiatan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    levelKegiatanSelected = "";
                } else {
                    levelKegiatanSelected = (String) parent.getItemAtPosition(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // set animation alpha
        cardResult.setVisibility(View.GONE);

        // events
        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtBeratBadan.getText().toString().isEmpty() || txtTinggiBadan.getText().toString().isEmpty() || levelKegiatanSelected.isEmpty()) {
                    Toast.makeText(DayCaloriActivity.this, "Please, complete all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    berat_badan = Double.parseDouble(txtBeratBadan.getText().toString());
                    tinggi_badan = Double.parseDouble(txtTinggiBadan.getText().toString());
                    calculateCalori(jenis_kelamin, tgl_lahir, berat_badan, tinggi_badan, levelKegiatanSelected);

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

    private void calculateCalori(String jk, String tgl_lahir, double berat_badan, double tinggi_badan, String level_aktifitas) {
        mService.dayCalori(jk, tgl_lahir, berat_badan, tinggi_badan, level_aktifitas)
                .enqueue(new Callback<APIResponse>() {
                    @Override
                    public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                        APIResponse result = response.body();
                        if (result.isError()) {
                            Toast.makeText(DayCaloriActivity.this, result.getError_msg(), Toast.LENGTH_SHORT).show();
                        } else {
                            txtResult.setText(String.valueOf(result.getDay_calori()) + " Kkal");
                            cardResult.setVisibility(View.VISIBLE);
                            cardResult.startAnimation(fade);
                        }
                    }

                    @Override
                    public void onFailure(Call<APIResponse> call, Throwable t) {
                        Toast.makeText(DayCaloriActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void reset() {
        txtBeratBadan.setText("");
        txtTinggiBadan.setText("");
        levelKegiatan.setSelection(0);
        txtResult.setText("");
        cardResult.setVisibility(View.GONE);

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
