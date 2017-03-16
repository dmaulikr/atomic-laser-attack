package com.atomic.attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener{

    public static final String TAG = Assets.class.getSimpleName();
    public static final Assets instance = new Assets();
    public AssetManager assetManager;

    public AssetPlayer player;
    public AssetEnemy enemy;
    public AssetBoss boss;
    public AssetMissile missile;
    public AssetProjectile projectile;
    public AssetParticle particle;
    public AssetLevel1 assetLevel1;
    public AssetStaticEnemy staticEnemy;
    public AssetDestructable destructable;

    public AssetMainMenu mainMenu;

    public AssetBlack black;

    private Assets() {}

    public void loadBlack(AssetManager assetMan) {
        Gdx.app.debug(TAG, "Loading black texture");
        if (this.assetManager == null) {
            this.assetManager = assetMan;
        }
        assetManager.setErrorListener(this);
        assetManager.load(Constants.BLACK_TEXTURE, Texture.class);
        assetManager.finishLoading();

        Texture tex = assetManager.get(Constants.BLACK_TEXTURE);
        black = new AssetBlack(tex);
    }

    public void loadMenu(AssetManager assetMan) {
        Gdx.app.debug(TAG, "Loading UI assets");
        if (this.assetManager == null) {
            this.assetManager = assetMan;
        }
        assetManager.setErrorListener(this);
        assetManager.load(Constants.MAIN_MENU_UI, TextureAtlas.class);
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "Loaded: " + a);
        }

        TextureAtlas atlas = assetManager.get(Constants.MAIN_MENU_UI);

        mainMenu = new AssetMainMenu(atlas);
    }

    public void loadGame(AssetManager assetMan) {
        loadGame(assetMan, false);
    }

    public void loadGame(AssetManager assetMan, boolean forceLoad) {
        Gdx.app.debug(TAG, "Loading game assets");
        if (this.assetManager == null || forceLoad) {
            this.assetManager = assetMan;
        }
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS_OJBECTS, TextureAtlas.class);
        assetManager.load(Constants.TEXTURE_ATLAS_PARTICLES, TextureAtlas.class);
        assetManager.finishLoading();

        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
        for (String a : assetManager.getAssetNames()) {
            Gdx.app.debug(TAG, "Loaded: " + a);
        }

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OJBECTS);
        TextureAtlas particles = assetManager.get(Constants.TEXTURE_ATLAS_PARTICLES);

        for (Texture t : atlas.getTextures()) {
            t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }

        for (Texture t : particles.getTextures()) {
            t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }

        player = new AssetPlayer(atlas);
        enemy = new AssetEnemy(atlas);
        boss = new AssetBoss(atlas);
        missile = new AssetMissile(atlas);
        projectile = new AssetProjectile(atlas);
        particle = new AssetParticle(particles);
        assetLevel1 = new AssetLevel1(atlas);
        staticEnemy = new AssetStaticEnemy(atlas);
        destructable = new AssetDestructable(atlas);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset.fileName, throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class AssetBlack {
        public final Sprite black;
        public final Texture blackTex;
        public AssetBlack(Texture tex) {
            blackTex = tex;
            black = new Sprite(blackTex);
        }
    }

    public class AssetMainMenu {
        public final TextureAtlas mainMenu;
        public AssetMainMenu(TextureAtlas atlas) {
            mainMenu = atlas;
        }
    }

    public class AssetPlayer {
        public final TextureAtlas.AtlasRegion player;
        public final TextureAtlas.AtlasRegion healthBarPlayer;
        public final Sound playerLaser;
        public AssetPlayer(TextureAtlas atlas) {
            player = atlas.findRegion("player");
            healthBarPlayer = atlas.findRegion("healthBarPlayer");
            playerLaser = Gdx.audio.newSound(Gdx.files.internal(Constants.AUDIO + "laser.wav"));
        }
    }

    public class AssetEnemy {
        public final Array<TextureAtlas.AtlasRegion> spinner;
        public final TextureAtlas.AtlasRegion enemyShip1;
        public final TextureAtlas.AtlasRegion enemyShip2;
        public final Array<TextureAtlas.AtlasRegion> atmosphericShip1;
        public final Array<TextureAtlas.AtlasRegion> atmosphericShip2;
        public final TextureAtlas.AtlasRegion bigBullet;
        public final Array<TextureAtlas.AtlasRegion> heli;
        public AssetEnemy(TextureAtlas atlas) {
            spinner = atlas.findRegions("spinner");
            enemyShip1 = atlas.findRegion("enemyShip1");
            enemyShip2 = atlas.findRegion("enemyShip2");
            atmosphericShip1 = atlas.findRegions("atmosphericShip1");
            atmosphericShip2 = atlas.findRegions("atmosphericShip2");
            bigBullet = atlas.findRegion("bigBullet");
            heli = atlas.findRegions("heli");
        }
    }

    public class AssetBoss {
        public final TextureAtlas.AtlasRegion boss1;
        public final TextureAtlas.AtlasRegion healthBar1;
        public final TextureAtlas.AtlasRegion healthBar2;
        public final TextureAtlas.AtlasRegion boss2;
        public AssetBoss(TextureAtlas atlas) {
            boss1 = atlas.findRegion("boss1");
            healthBar1 = atlas.findRegion("healthBar1");
            healthBar2 = atlas.findRegion("healthBar2");
            boss2 = atlas.findRegion("bigFighter");
        }
    }

    public class AssetMissile {
        public final TextureAtlas.AtlasRegion missile1;
        public final TextureAtlas.AtlasRegion missile2;
        public final TextureAtlas.AtlasRegion missile3;
        public final TextureAtlas.AtlasRegion missile4;
        public final TextureAtlas.AtlasRegion missile5;
        public AssetMissile(TextureAtlas atlas) {
            missile1 = atlas.findRegion("missile1");
            missile2 = atlas.findRegion("missile2");
            missile3 = atlas.findRegion("missile3");
            missile4 = atlas.findRegion("missile4");
            missile5 = atlas.findRegion("missile5");
        }
    }

    public class AssetProjectile {
        public final TextureAtlas.AtlasRegion projectile;
        public final TextureAtlas.AtlasRegion projectileRedBall;
        public final TextureAtlas.AtlasRegion projectileEnemyShip;
        public final TextureAtlas.AtlasRegion projectileArtillery;
        public final TextureAtlas.AtlasRegion projectileTriangle;
        public final TextureAtlas.AtlasRegion projectileRedBall2;
        public final TextureAtlas.AtlasRegion projectileTrackingMissile;
        public AssetProjectile(TextureAtlas atlas) {
            projectile = atlas.findRegion("projectile_boss1");
            projectileRedBall = atlas.findRegion("projectileRedBall");
            projectileEnemyShip = atlas.findRegion("projectileEnemyShip");
            projectileArtillery = atlas.findRegion("artilleryProjectile");
            projectileTriangle = atlas.findRegion("projectileTriangle");
            projectileRedBall2 = atlas.findRegion("projectileRedBall2");
            projectileTrackingMissile = atlas.findRegion("projectileTrackingMissile");
        }
    }

    public class AssetLevel1 {
        public final TextureAtlas.AtlasRegion staticBackground;
        public final TextureAtlas.AtlasRegion tower1;
        public AssetLevel1(TextureAtlas atlas) {
            staticBackground = atlas.findRegion("background1");
            tower1 = atlas.findRegion("tower1");
        }
    }

    public class AssetParticle {
        public final Array<TextureAtlas.AtlasRegion> exp1;
        public final Array<TextureAtlas.AtlasRegion> exp2;
        public final Array<TextureAtlas.AtlasRegion> exp3;
        public final Array<TextureAtlas.AtlasRegion> exp4;
        public final Array<TextureAtlas.AtlasRegion> exp5;
        public final Sound exp4Sound;
        public final Sound exp3Sound;
        public final Sound exp2Sound;
        public AssetParticle(TextureAtlas atlas) {
            exp1 = atlas.findRegions("exp1");
            exp2 = atlas.findRegions("exp2");
            exp3 = atlas.findRegions("exp3");
            exp4 = atlas.findRegions("exp4");
            exp5 = atlas.findRegions("exp5");
            exp4Sound = Gdx.audio.newSound(Gdx.files.internal(Constants.AUDIO + "exp4sound.wav"));
            exp3Sound = Gdx.audio.newSound(Gdx.files.internal(Constants.AUDIO + "exp3sound.wav"));
            exp2Sound = Gdx.audio.newSound(Gdx.files.internal(Constants.AUDIO + "exp2sound.wav"));
        }
    }

    public class AssetStaticEnemy {
        public final Array<TextureAtlas.AtlasRegion> artilleryRight;
        public final Array<TextureAtlas.AtlasRegion> artilleryLeft;
        public final TextureAtlas.AtlasRegion artilleryRight_d;
        public final Array<TextureAtlas.AtlasRegion> trackingTower;
        public final TextureAtlas.AtlasRegion trackingTower_d;
        public final Array<TextureAtlas.AtlasRegion> starPort;
        public final TextureAtlas.AtlasRegion starPort_d;
        public AssetStaticEnemy(TextureAtlas atlas) {
            artilleryRight = atlas.findRegions("artilleryRight");
            artilleryLeft = atlas.findRegions("artilleryLeft");
            artilleryRight_d = atlas.findRegion("artilleryRight_d");
            trackingTower = atlas.findRegions("trackingTower");
            trackingTower_d = atlas.findRegion("trackingTower_d");
            starPort = atlas.findRegions("starport");
            starPort_d = atlas.findRegion("starport_d");
        }
    }

    public class AssetDestructable {
        public final Array<TextureAtlas.AtlasRegion> factory;
        public final TextureAtlas.AtlasRegion factory_d;
        public final TextureAtlas.AtlasRegion field;
        public final TextureAtlas.AtlasRegion field_d;
        public AssetDestructable(TextureAtlas atlas) {
            factory = atlas.findRegions("factory");
            factory_d = atlas.findRegion("factory_d");
            field = atlas.findRegion("field");
            field_d = atlas.findRegion("field_d");
        }
    }
}
