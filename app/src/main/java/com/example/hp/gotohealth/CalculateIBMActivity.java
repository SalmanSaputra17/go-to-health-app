package com.example.hp.gotohealth;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gotohealth.Common.Common;
import com.example.hp.gotohealth.Retrofit.APIService;

public class CalculateIBMActivity extends AppCompatActivity {
    Animation fade;

    Toolbar toolbar;
    EditText txtBodyMass, txtBodyHeight;
    TextView txtResult, txtInformation;
    CardView cardResult;
    Button btnCalculate, btnReset;

    APIService apiService;

    int sdk = android.os.Build.VERSION.SDK_INT;
    double weight, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate_ibm);

        // init API
        apiService = Common.getAPI();

        // init views
        fade = AnimationUtils.loadAnimation(this, R.anim.fade);

        toolbar = (Toolbar) findViewById(R.id.check_bb_toolbar);
        txtBodyMass = (EditText) findViewById(R.id.textberatbadan);
        txtBodyHeight = (EditText) findViewById(R.id.texttinggibadan);
        txtResult = (TextView) findViewById(R.id.textresult);
        txtInformation = (TextView) findViewById(R.id.textketerangan);
        cardResult = (CardView) findViewById(R.id.cardresult);
        btnCalculate = (Button) findViewById(R.id.btnhitungbb);
        btnReset = (Button) findViewById(R.id.btnresetbb);

        toolbar.setTitle("Kalkulator BMI");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cardResult.setVisibility(View.GONE);

        // events
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtBodyMass.getText().toString().isEmpty() || txtBodyHeight.getText().toString().isEmpty()) {
                    Toast.makeText(CalculateIBMActivity.this, "Silahkan lengkapi semua bidang!", Toast.LENGTH_SHORT).show();
                } else {
                    weight = Double.parseDouble(txtBodyMass.getText().toString());
                    height = Double.parseDouble(txtBodyHeight.getText().toString());

                    calculateIBM(weight, height);

                    btnCalculate.setEnabled(false);

                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        btnCalculate.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                    } else {
                        btnCalculate.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                    }
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                btnCalculate.setEnabled(true);
            }
        });

    }

    private void calculateIBM(double weight, double height) {

    }

    private void reset() {
        txtBodyMass.setText("");
        txtBodyHeight.setText("");
        txtResult.setText("");
        txtInformation.setText("");
        cardResult.setVisibility(View.GONE);

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            btnCalculate.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtngradient));
        } else {
            btnCalculate.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtngradient));
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
