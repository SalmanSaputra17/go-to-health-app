package com.example.hp.gotohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;

public class CalorieActivity extends AppCompatActivity {
    Toolbar toolbar;
    CardView cardCalorie1, cardCalorie2;

    Intent dayCalorie, foodCalorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie);

        // init views
        toolbar = (Toolbar) findViewById(R.id.calori_toolbar);
        cardCalorie1 = (CardView) findViewById(R.id.cardcalori1);
        cardCalorie2 = (CardView) findViewById(R.id.cardcalori2);

        toolbar.setTitle("Kalkulator Kalori");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // events
        cardCalorie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayCalorie = new Intent(CalorieActivity.this, DayCalorieActivity.class);
                dayCalorie.putExtra("Gender", getIntent().getStringExtra("Gender"));
                dayCalorie.putExtra("DateOfBirth", getIntent().getStringExtra("DateOfBirth"));
                startActivity(dayCalorie);
            }
        });

        cardCalorie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodCalorie = new Intent(CalorieActivity.this, FoodCalorieActivity.class);
                startActivity(foodCalorie);
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
