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

import static com.example.trivia.quizAct.RESULT_CLOSE_ALL;

public class MainActivity extends AppCompatActivity {

    private Button next;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        apptitle= findViewById(R.id.apptitle);
        apptitle.setTypeface(ResourcesCompat.getFont(this, R.font.lemon));

        //initialize mobile Ad format
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        next= findViewById(R.id.playButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent= new Intent(MainActivity.this, categoryPick.class);
                startActivityForResult(myIntent, 1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        MainActivity.this.finish();
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
