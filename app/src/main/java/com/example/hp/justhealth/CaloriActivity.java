package com.example.hp.justhealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class CaloriActivity extends AppCompatActivity {

    Toolbar toolbar;
    CardView cardCalori1, cardCalori2;

    Intent dayCalori, foodCalori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calori);

        // init views
        toolbar = (Toolbar) findViewById(R.id.calori_toolbar);
        cardCalori1 = (CardView) findViewById(R.id.cardcalori1);
        cardCalori2 = (CardView) findViewById(R.id.cardcalori2);

        toolbar.setTitle("Kalkulator Kalori");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // events
        cardCalori1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayCalori = new Intent(CaloriActivity.this, DayCaloriActivity.class);
                dayCalori.putExtra("JK", getIntent().getStringExtra("JK"));
                dayCalori.putExtra("TGL_LAHIR", getIntent().getStringExtra("TGL_LAHIR"));
                startActivity(dayCalori);
            }
        });

        cardCalori2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foodCalori = new Intent(CaloriActivity.this, FoodCaloriActivity.class);
                startActivity(foodCalori);
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
