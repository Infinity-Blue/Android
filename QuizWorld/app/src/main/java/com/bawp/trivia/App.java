package com.bawp.trivia;
import android.app.Application;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class App extends Application  {

    AdView adView;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub

        super.onCreate();

        adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("cca-app-pub-3940256099942544/6300978111");
        // Request for Ads
        AdRequest adRequest = new AdRequest.Builder().build();
        // Load ads into Banner Ads
        adView.loadAd(adRequest);
    }

    public void loadAd(LinearLayout layAd) {

        // Locate the Banner Ad in activity xml
        if (adView.getParent() != null) {
            ViewGroup tempVg = (ViewGroup) adView.getParent();
            tempVg.removeView(adView);
        }

        layAd.addView(adView);

    }
}
