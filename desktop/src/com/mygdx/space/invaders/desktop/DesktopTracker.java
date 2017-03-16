package com.mygdx.space.invaders.desktop;

import com.badlogic.gdx.Gdx;
import com.atomic.attack.Trackable;

public class DesktopTracker implements Trackable {

    @Override
    public void sendEvent(String key, String category, String action) {
        Gdx.app.debug(this.getClass().getSimpleName(), "Sending Event: " + category + action);
    }
}
