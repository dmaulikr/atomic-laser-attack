package com.atomic.attack;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.space.invaders.R;

public class AndroidLauncher extends AndroidApplication implements AdHandler {
    private static FirebaseAnalytics analytics;
    private static com.atomic.attack.Trackable tracker;
    private static final String TAG = "AndroidLauncher";
    private static InterstitialAd IntAd;
    //private static boolean adShowing = false;

    /*
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (IntAd.isLoaded())  {
                        adShowing = true;
                        IntAd.show();
                    }
                    else {
                        adShowing = false;
                        Log.d(TAG, "Couldn't display Ad: Ad not loaded");
                    }
                    break;
            }
        }
    };
    */

    public void showOrLoadInterstital() {
        try {
            runOnUiThread(new Runnable() {
                public void run() {
                    if (IntAd.isLoaded()) {
                        IntAd.show();
                    }
                    else {
                        Log.d(TAG, "Couldn't display Ad: Ad not loaded");
                    }
                }
            });
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
	protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        analytics = FirebaseAnalytics.getInstance(this);
        tracker = new AndroidTracker(analytics);
        IntAd = new InterstitialAd(this);
        IntAd.setAdUnitId(getString(R.string.ad_ID));

        IntAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                //adShowing = false;
            }
        });

        requestNewInterstitial();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;
        config.useAccelerometer = false;
        config.useCompass = false;
        config.useGyroscope = false;
        //config.useWakelock = true;

        RelativeLayout layout = new RelativeLayout(this);
        View gameView = initializeForView(new MainGame(tracker, this), config);
        layout.addView(gameView);


        setContentView(layout);


    }


    @Override
    public void showAd() {
        showOrLoadInterstital();
        //handler.sendEmptyMessage(1);
    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("C8BDFF638FD2DE43A0716B02D65F2C07")
                .addTestDevice("5CE9A9AF579A5B3AF4A63D4D1AAFFCD8")
                .addTestDevice("BE382AA02290308C4D7DF39CCB43687E")
                .build();

        IntAd.loadAd(adRequest);
    }

}

