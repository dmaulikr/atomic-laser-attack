package com.atomic.attack.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;


public class TransitionScreen extends Screen {
    public static final String TAG = TransitionScreen.class.getSimpleName();

    private Screen oldScreen, newScreen;

    private float time = 0;
    private float inTransitionTime = 0.15f;
    private float outTransitionTime = 0.15f;
    private Interpolation interpolation = Interpolation.linear;

    boolean fadingIn = false;
    boolean fadingOut = false;
    boolean blackFrame = false;
    boolean showAds;

    private Sprite black;

    public TransitionScreen(Screen oldScreen, Screen newScreen, float inTime, float outTime, Interpolation interpolation, boolean showAds) {
        this.oldScreen = oldScreen;
        this.newScreen = newScreen;
        this.inTransitionTime = inTime;
        this.outTransitionTime = outTime;
        this.interpolation = interpolation;
        this.showAds = showAds;
    }

    @Override
    public void create() {
        com.atomic.attack.Assets.instance.loadBlack(new AssetManager());
        black = com.atomic.attack.Assets.instance.black.black;
        fadingOut = true;
    }

    @Override
    public void handleInput() {

        if (fadingOut) {
            oldScreen.handleInput();
        }

        if (fadingIn) {
            newScreen.handleInput();
        }
    }

    @Override
    public void update(float dt) {
        time += Math.min(dt, 1/40f);

        if (fadingOut) {
            oldScreen.update(dt);
        }

        if (fadingIn) {
            newScreen.update(dt);
        }

    }

    @Override
    public void render(SpriteBatch sb) {



        if(fadingOut) {
            time = Math.min(time, outTransitionTime);

            oldScreen.render(sb);

            float transTime = time / outTransitionTime;
            float applied = interpolation.apply(transTime);
            Color color = sb.getColor();
            sb.setColor(new Color(1, 1, 1, applied));

            sb.begin();
            sb.draw(black, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            sb.end();

            sb.setColor(color);

            if (blackFrame) {
                oldScreen.dispose();
                Gdx.app.debug(TAG, oldScreen.getClass().getSimpleName() + " disposed");
                fadingOut = false;
                fadingIn = true;

                newScreen.create();
                time = 0;

                if (showAds) {
                    newScreen.pause();
                    com.atomic.attack.MainGame.adHandler.showAd();
                    showAds = false;
                }

                blackFrame = false;
            }

            if (time == outTransitionTime) {
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                blackFrame = true;
            }

        }

        if (fadingIn) {
            time = Math.min(time, inTransitionTime);
            newScreen.render(sb);

            float transTime = time / inTransitionTime;
            float applied = 1 - interpolation.apply(transTime);
            Color color = sb.getColor();
            sb.setColor(new Color(1, 1, 1, applied));

            sb.begin();
            sb.draw(black, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            sb.end();

            sb.setColor(color);


            if (time == inTransitionTime) {
                ScreenManager.setScreen(newScreen, false);
            }
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {
        if(fadingOut) {
            oldScreen.pause();
        }
        else {
            newScreen.pause();
        }
        //MusicManager.instance.pauseMusic();
        Gdx.app.debug(TAG, "Paused");

    }

    @Override
    public void resume() {
        if (!oldScreen.disposed) {
            oldScreen.dispose();
        }
        ScreenManager.setScreen(newScreen, true);
    }
}
