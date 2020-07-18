package com.example.hp.justhealth;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.balysv.materialmenu.MaterialMenu;
import com.balysv.materialmenu.MaterialMenuDrawable;
import com.example.hp.justhealth.Common.Common;
import com.example.hp.justhealth.Model.APIResponse;
import com.example.hp.justhealth.Retrofit.IMyAPI;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FoodCaloriActivity extends AppCompatActivity {

    Animation fade;

    Toolbar toolbar;
    LinearLayout container;
    Button btnHitung, btnAdd, btnReset;
    CardView cardResult;
    TextView txtResult;
    AutoCompleteTextView txtMakanan1, txtAdd;

    IMyAPI mService;
    ArrayList<String> inputFoods = new ArrayList<>();

    int sum, sdk = android.os.Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_calori);

        // init API
        mService = Common.getAPI();

        // init views
        fade = AnimationUtils.loadAnimation(this, R.anim.fade);

        toolbar = (Toolbar) findViewById(R.id.food_calori_toolbar);
        container = (LinearLayout) findViewById(R.id.container);
        btnHitung = (Button) findViewById(R.id.btnhitungkalori2);
        btnAdd = (Button) findViewById(R.id.add);
        btnReset = (Button) findViewById(R.id.btnresetfoodkalori);
        cardResult = (CardView) findViewById(R.id.cardresultkalori2);
        txtResult = (TextView) findViewById(R.id.textresultkalori2);
        txtMakanan1 = (AutoCompleteTextView) findViewById(R.id.txtmakanan1);

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

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertAllFoods();
                if (inputFoods.size() > 0) {
                    getTotal(inputFoods);
                    btnHitung.setEnabled(false);
                    btnAdd.setEnabled(false);

                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        btnHitung.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                        btnAdd.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                    } else {
                        btnHitung.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                        btnAdd.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtndisabled));
                    }

                } else {
                    Toast.makeText(FoodCaloriActivity.this, "Please complete all the fields !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                btnHitung.setEnabled(true);
                btnAdd.setEnabled(true);
            }
        });

    }

    private void insertAllFoods() {
        String txtMakanan1Values = txtMakanan1.getText().toString().toLowerCase();
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
        final int arrSize = inputFoods.size();
        for (int i = 0; i < arrSize; i++) {
            final int index = i;
            mService.foodCalori(inputFoods.get(i))
            .enqueue(new Callback<APIResponse>() {
                @Override
                public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                    APIResponse result = response.body();
                    if (result.isError()) {
                        Toast.makeText(FoodCaloriActivity.this, result.getError_msg(), Toast.LENGTH_SHORT).show();
                    } else if (!result.isError()) {
                        sum += result.getFood_calori();

                        if (index == arrSize - 1) {
                            txtResult.setText(String.valueOf(sum) + " Kkal");
                            cardResult.setVisibility(View.VISIBLE);
                            cardResult.startAnimation(fade);

                            sum = 0;
                        }
                    }
                }

                @Override
                public void onFailure(Call<APIResponse> call, Throwable t) {
                    Toast.makeText(FoodCaloriActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void reset() {
        inputFoods.clear();
        txtMakanan1.setText("");

        int childCount = container.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View thisChild = container.getChildAt(i);
            AutoCompleteTextView txtAdd = (AutoCompleteTextView) thisChild.findViewById(R.id.txtadd);
            txtAdd.setText("");
        }

        txtResult.setText("");
        cardResult.setVisibility(View.GONE);

        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            btnHitung.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtngradient));
            btnAdd.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtncircle));
        } else {
            btnHitung.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bgbtngradient));
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
