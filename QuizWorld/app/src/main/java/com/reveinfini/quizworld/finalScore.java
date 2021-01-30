package com.reveinfini.quizworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import androidx.appcompat.app.AppCompatActivity;

public class finalScore extends AppCompatActivity {

    int score;
    int correctNbr;
    private TextView result;
    private TextView scoreView;
    private AdView mAdView;
    private ImageButton category;
    private ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_final_score);

        //initialize mobile Ad format
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        result=findViewById(R.id.resultView);
        scoreView=findViewById(R.id.score);
        category=findViewById(R.id.catButton);
        home=findViewById(R.id.homeButton);

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), categoryPick.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(v.getContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        score = bundle.getInt("score");
        correctNbr = bundle.getInt("correctNbr");
        result.setText("You've got "+correctNbr+" answers Correct \n" + "out of 10");
        scoreView.setText(String.valueOf(score));

    }


    @Override
    public void onBackPressed() {
        Intent new_intent = new Intent(this, categoryPick.class);
        new_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(new_intent);
    }
}
