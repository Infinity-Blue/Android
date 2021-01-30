package com.reveinfini.quizworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import static com.reveinfini.quizworld.quizAct.RESULT_CLOSE_ALL;

public class categoryPick<category> extends AppCompatActivity implements View.OnClickListener {

    private Button topic_1;
    private Button topic_2;
    private Button topic_3;
    private Button topic_4;
    private Button topic_5;
    private Button topic_6;
    private Button topic_7;
    private Button topic_8;
    private Button topic_9;
    private Button topic_10;
    private Button topic_11;
    private Button topic_12;
    private Button topic_13;
    private Button topic_14;
    private Button topic_15;
    private Button topic_16;
    private Button topic_17;

    int category;
    ConstraintLayout constLay;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_category_pick);

        //initialize mobile Ad format
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        topic_1 = findViewById(R.id.topic1);
        topic_2 = findViewById(R.id.topic2);
        topic_3 = findViewById(R.id.topic3);
        topic_4 = findViewById(R.id.topic4);
        topic_5 = findViewById(R.id.topic5);
        topic_6 = findViewById(R.id.topic6);
        topic_7 = findViewById(R.id.topic7);
        topic_8 = findViewById(R.id.topic8);
        topic_9 = findViewById(R.id.topic9);
        topic_10 = findViewById(R.id.topic10);
        topic_11 = findViewById(R.id.topic11);
        topic_12 = findViewById(R.id.topic12);
        topic_13 = findViewById(R.id.topic13);
        topic_14 = findViewById(R.id.topic14);
        topic_15 = findViewById(R.id.topic15);
        topic_16 = findViewById(R.id.topic16);
        topic_17 = findViewById(R.id.topic17);

        topic_1.setOnClickListener(this);
        topic_2.setOnClickListener(this);
        topic_3.setOnClickListener(this);
        topic_4.setOnClickListener(this);
        topic_5.setOnClickListener(this);
        topic_6.setOnClickListener(this);
        topic_7.setOnClickListener(this);
        topic_8.setOnClickListener(this);
        topic_9.setOnClickListener(this);
        topic_10.setOnClickListener(this);
        topic_11.setOnClickListener(this);
        topic_12.setOnClickListener(this);
        topic_13.setOnClickListener(this);
        topic_14.setOnClickListener(this);
        topic_15.setOnClickListener(this);
        topic_16.setOnClickListener(this);
        topic_17.setOnClickListener(this);

    }


        public void onClick (View v){
            switch (v.getId()) {
                case R.id.topic1:
                    category=9;
                    break;
                case R.id.topic2:
                    category=11;
                    break;
                case R.id.topic3:
                    category=12;
                    break;
                case R.id.topic4:
                    category=23;
                    break;
                case R.id.topic5:
                    category=10;
                    break;
                case R.id.topic6:
                    category=21;
                    break;
                case R.id.topic7:
                    category=18;
                    break;
                case R.id.topic8:
                    category=20;
                    break;
                case R.id.topic9:
                    category=15;
                    break;
                case R.id.topic10:
                    category=17;
                    break;
                case R.id.topic11:
                    category=24;
                    break;
                case R.id.topic12:
                    category=13;
                    break;
                case R.id.topic13:
                    category=19;
                    break;
                case R.id.topic14:
                    category=22;
                    break;
                case R.id.topic15:
                    category=26;
                    break;
                case R.id.topic16:
                    category=10;
                    break;
                case R.id.topic17:
                    category=32;
                    break;
            }

            Intent intent= new Intent(categoryPick.this, quizAct.class);
            intent.putExtra("category", category);
            startActivityForResult(intent,1);
        }

    @Override
    public void onBackPressed() {
        Intent new_intent = new Intent(this, MainActivity.class);
        new_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(new_intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(resultCode)
        {
            case RESULT_CLOSE_ALL:
                setResult(RESULT_CLOSE_ALL);
                finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

