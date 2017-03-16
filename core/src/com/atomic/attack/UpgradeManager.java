package com.atomic.attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class UpgradeManager {
    private Preferences prefs;

    public static final UpgradeManager instance = new UpgradeManager();

    private int missileType;
    private int thrusterType;
    private int difficulty;

    private UpgradeManager() {}

    public void init() {
        prefs = Gdx.app.getPreferences("PrefSave");
        if (!prefs.contains("score")) {
            prefs.putLong("score", 0);
            prefs.flush();
        }
        if (!prefs.contains("missileType")) {
            prefs.putInteger("missileType", 2);
            prefs.flush();
        }
        if (!prefs.contains("thrusterType")) {
            prefs.putInteger("thrusterType", 1);
            prefs.flush();
        }
        if (!prefs.contains("difficulty")) {
            prefs.putInteger("difficulty", 1);
            prefs.flush();
        }
    }

    public void loadAll() {
        MainGame.score = prefs.getLong("score");
        missileType = prefs.getInteger("missileType");
        thrusterType = prefs.getInteger("thrusterType");
        difficulty = prefs.getInteger("difficulty");

        prefs.flush();
    }

    public void saveAll() {
        prefs.putLong("score", MainGame.score);
        prefs.putInteger("missileType", 2);
        prefs.putInteger("thrusterType", 1);
        prefs.putInteger("difficulty", difficulty);

        prefs.flush();
    }

    public long getScore() {
        return prefs.getLong("score");
    }

    public int getCurrentMissile() {
        return missileType;
    }

    public void setThrusterType(int type) {
        this.thrusterType = type;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setMissileType(int type) {
        this.missileType = type;
    }

    public int getCurrentThrusterSpeed() {
        switch (thrusterType) {
            case 1: return 5;
            case 2: return 10;
            case 3: return 15;
            default: return 5;
        }
    }

    public int getCurrentMissileDamage() {
        switch (missileType) {
            case 1: return 1;
            case 2: return 2;
            case 3: return 2;
            case 4: return 8;
            case 5: return 20;
            default: return 1;
        }
    }

    public int getCurrentMissileSpeed() {
        switch (missileType) {
            case 1: return 5;
            case 2: return 10;
            case 3: return 10;
            case 4: return 10;
            case 5: return 5;
            default: return 5;
        }
    }

    public float getCurrentMissileFireRate() {
        switch (missileType) {
            case 1: return 0.5f;
            case 2: return 0.1f;
            case 3: return 0.1f;
            case 4: return 0.25f;
            case 5: return 0.5f;
            default: return 1f;
        }
    }
}
