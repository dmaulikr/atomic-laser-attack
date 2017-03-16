package com.mygdx.space.invaders.desktop;

import com.badlogic.gdx.Gdx;
import com.atomic.attack.AdHandler;

public class DesktopAdHandler implements AdHandler {
    @Override
    public void showAd() {
        Gdx.app.debug(this.getClass().getSimpleName(), "Showing Ad");
    }
}
