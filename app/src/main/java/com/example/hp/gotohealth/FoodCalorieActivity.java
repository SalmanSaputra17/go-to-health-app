package com.example.hp.gotohealth;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.gotohealth.Common.Common;
import com.example.hp.gotohealth.Retrofit.APIService;

import java.util.ArrayList;

public class FoodCalorieActivity extends AppCompatActivity {

    Animation fade;

    Toolbar toolbar;
    LinearLayout container;
    Button btnCalculate, btnAdd, btnReset;
    CardView cardResult;
    TextView txtResult;
    AutoCompleteTextView txtFoodFirst, txtAdd;

    APIService apiService;
    ArrayList<String> inputFoods = new ArrayList<>();

    int sum, sdk = android.os.Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_calorie);

        // init API
        apiService = Common.getAPI();

        // init views
        fade = AnimationUtils.loadAnimation(this, R.anim.fade);

        toolbar = (Toolbar) findViewById(R.id.food_calori_toolbar);
        container = (LinearLayout) findViewById(R.id.container);
        btnCalculate = (Button) findViewById(R.id.btnhitungkalori2);
        btnAdd = (Button) findViewById(R.id.add);
        btnReset = (Button) findViewById(R.id.btnresetfoodkalori);
        cardResult = (CardView) findViewById(R.id.cardresultkalori2);
        txtResult = (TextView) findViewById(R.id.textresultkalori2);
        txtFoodFirst = (AutoCompleteTextView) findViewById(R.id.txtmakanan1);

        toolbar.setTitle("Jumlah Kalori Makanan");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // events
        cardResult.setVisibility(View.GONE);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.row_input, null);
                txtAdd = (AutoCompleteTextView) addView.findViewById(R.id.txtadd);

                container.addView(addView);
            }
        });

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAllFoods();
                if (inputFoods.size() > 0) {
                    getTotal(inputFoods);
                    btnCalculate.setEnabled(false);
                    btnAdd.setEnabled(false);

                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        btnCalculate.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                        btnAdd.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                    } else {
                        btnCalculate.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                        btnAdd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                    }

                } else {
                    Toast.makeText(FoodCalorieActivity.this, "Please complete all the fields !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                btnCalculate.setEnabled(true);
                btnAdd.setEnabled(true);
            }
        });

    }

    private void insertAllFoods() {
        String txtMakanan1Values = txtFoodFirst.getText().toString().toLowerCase();

        if (!txtMakanan1Values.isEmpty()) {
            inputFoods.add(txtMakanan1Values);
        }

        int childCount = container.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View thisChild = container.getChildAt(i);
            AutoCompleteTextView txtAdd = (AutoCompleteTextView) thisChild.findViewById(R.id.txtadd);
            String txtAddValues = txtAdd.getText().toString().toLowerCase();

            if (!txtAddValues.isEmpty()) {
                inputFoods.add(txtAddValues);
            }
        }
    }

    private void getTotal(final ArrayList<String> inputFoods) {

    }

    private void reset() {
        inputFoods.clear();
        txtFoodFirst.setText("");

        int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View thisChild = container.getChildAt(i);
            AutoCompleteTextView txtAdd = (AutoCompleteTextView) thisChild.findViewById(R.id.txtadd);
            txtAdd.setText("");
        }

        txtResult.setText("");
        cardResult.setVisibility(View.GONE);

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            btnCalculate.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtngradient));
            btnAdd.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtncircle));
        } else {
            btnCalculate.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtngradient));
            btnAdd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtncircle));
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
