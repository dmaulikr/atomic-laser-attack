package com.atomic.attack.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;

public class ScreenManager {
    private final static String TAG = "ScreenManager";
    private static Screen currentSreen;

    public static void setScreen(Screen screen) {
        setScreen(screen, true);
    }

    public static void setScreen(Screen screen, boolean useCreateMethod) {
        disposeScreen(currentSreen);
        //Gdx.app.debug(TAG, "Screen Launched: " + screen.getClass().getSimpleName());

        currentSreen = screen;

        if (useCreateMethod) {
            screen.create();
        }
    }


    public static void transitionScreen(Screen screen) {
        transitionScreen(screen, 0.15f, 0.15f, Interpolation.linear);
    }

    public static void transitionScreen(Screen screen, float inTime, float outTime) {
       transitionScreen(screen, inTime, outTime, Interpolation.linear);

    }

    public static void transitionScreen(Screen screen, float inTime, float outTime, Interpolation interpolation) {
        transitionScreen(screen, inTime, outTime, Interpolation.linear, false);
    }

    public static void transitionScreen(Screen screen, float inTime, float outTime, Interpolation interpolation, boolean showAds) {
        Gdx.app.debug(TAG, "Screen Transition from " + currentSreen.getClass().getSimpleName() + " to " + screen.getClass().getSimpleName());
        currentSreen = new TransitionScreen(currentSreen, screen, inTime, outTime, interpolation, showAds);
        currentSreen.create();
    }

    public static void disposeScreen(Screen screen) {
        if(screen != null && screen.disposed == false) {
            Gdx.app.debug(TAG, "Screen Disposed: " + screen.getClass().getSimpleName());
            screen.dispose();
        }
        else if (screen != null) {
            Gdx.app.error(TAG, "Screen NOT Disposed: " + screen.getClass().getSimpleName());
        }
    }

    public static Screen getCurrentSreen() {
        return currentSreen;
    }



}
