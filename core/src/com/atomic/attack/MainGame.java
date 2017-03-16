package com.atomic.attack;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.atomic.attack.screen.ScreenManager;
import com.atomic.attack.screen.menus.MainMenu;


public class MainGame extends ApplicationAdapter {
    public static final String TAG = MainGame.class.getSimpleName();
    public static final float SCALE = 0.5f;

    public static long score = 0;

    public static final boolean debug = false;

    public static float WIDTH = 854 * SCALE, HEIGHT;       //Height will be changed in create()

	SpriteBatch batch;

    private FPSLogger fpslogger;
    RawFPSCounter rawFPScounter;

    public static com.atomic.attack.Trackable tracker;
    public static com.atomic.attack.AdHandler adHandler;


    public MainGame(com.atomic.attack.Trackable tracker, com.atomic.attack.AdHandler adHandler) {
        this.tracker = tracker;
        this.adHandler = adHandler;
    }

    @Override
	public void create () {
        float ratio = (float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth();     //Manages differet aspect ratios
        if (ratio < 0.5f) ratio = 0.5f;
        if (ratio > 0.6f) ratio = 0.6f;
        System.out.println("Ratio: " + ratio);
        HEIGHT = WIDTH * ratio;

        Gdx.input.setCatchBackKey(true);
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        Gdx.app.debug(TAG, "Window Height: " + HEIGHT + ", " + "Window Width: " + WIDTH);

        UpgradeManager.instance.init();
        UpgradeManager.instance.loadAll();
        UpgradeManager.instance.setThrusterType(2);
        UpgradeManager.instance.setMissileType(2);


        batch = new SpriteBatch();

        //fpslogger = new FPSLogger();

        //rawFPScounter = new RawFPSCounter();

        Gdx.app.debug(TAG, "Loading Menu assets");
        Assets.instance.assetManager = new AssetManager();
        Assets.instance.loadMenu(new AssetManager());
        ScreenManager.setScreen(new MainMenu());


    }

	@Override
	public void render () {
        //rawFPScounter.startRawFPS();
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 1/40f);
        dt = 1/60f;     //To be commented if delta time is suppose to be used

       // fpslogger.log();

        Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        com.atomic.attack.MusicManager.instance.update(dt);

        if(ScreenManager.getCurrentSreen() != null) {
            ScreenManager.getCurrentSreen().handleInput();
        }

        if(ScreenManager.getCurrentSreen() != null) {
            ScreenManager.getCurrentSreen().update(dt);
        }

        if(ScreenManager.getCurrentSreen() != null) {
            ScreenManager.getCurrentSreen().render(batch);
        }



        //rawFPScounter.endRawFps();

	}


    @Override
    public void resize(int width, int height) {
        if(ScreenManager.getCurrentSreen() != null) {
            ScreenManager.getCurrentSreen().resize(width, height);
        }
    }

    @Override
    public void dispose() {
        if(ScreenManager.getCurrentSreen() != null) {
            ScreenManager.getCurrentSreen().dispose();
        }

    }

    @Override
    public void pause() {
        UpgradeManager.instance.saveAll();

        if(ScreenManager.getCurrentSreen() != null) {
            ScreenManager.getCurrentSreen().pause();
        }
    }

    @Override
    public void resume() {
        if(ScreenManager.getCurrentSreen() != null) {
            ScreenManager.getCurrentSreen().resume();
        }

    }


}
