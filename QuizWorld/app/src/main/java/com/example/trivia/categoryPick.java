package com.example.trivia;

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

import static com.example.trivia.quizAct.RESULT_CLOSE_ALL;

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

