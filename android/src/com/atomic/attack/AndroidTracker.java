package com.atomic.attack;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AndroidTracker implements com.atomic.attack.Trackable {

    private final FirebaseAnalytics tracker;
    private final String TAG = "AndroidTracker";

    public AndroidTracker(FirebaseAnalytics analytics) {
        this.tracker = analytics;
    }

    public void sendEvent(String key, String category, String action) {
        Bundle bundle = new Bundle();
        bundle.putString(key, category);
        tracker.logEvent(action, bundle);

        Log.d(TAG, "Sending Event: " + action);
    }


}
