package com.atomic.attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

public class MusicManager implements Disposable{
    public static final MusicManager instance = new MusicManager();

    private com.badlogic.gdx.audio.Music music;
    private final String TAG = this.getClass().getSimpleName();
    private boolean fading = false;
    private float fadeTimer, fadeTime;

    private MusicManager() {}

    public void setMusic(com.badlogic.gdx.audio.Music music) {
        if (this.music != null) {
            dispose();
        }
        this.music = music;
    }

    public void startMusic() {
        music.play();
        Gdx.app.debug(TAG, "Music started");
    }

    public void resumeMusic(float position) {
        music.setPosition(position);
        music.play();
        Gdx.app.debug(TAG, "Music Resumed");
    }

    public float pauseMusic() {
        this.music.pause();
        Gdx.app.debug(TAG, "Music Paused");
        return music.getPosition();
    }

    public void stopMusic() {
        Gdx.app.debug(TAG, "Music Stopped");
        this.music.stop();
    }

    @Override
    public void dispose() {
        if (music != null) {
            stopMusic();
            music.dispose();
        }
    }

    public boolean isPlaying() {
        if (music != null) {
            return music.isPlaying();
        }
        else return false;
    }

    public void fade(float time) {
        fading = true;
        fadeTimer = time;
        fadeTime = time;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                fading = false;
            }
        }, time);
    }

    public void update(float dt) {
        if (music != null && music.isPlaying() && fading) {
            fadeTimer -= dt;
            fadeTimer = Math.max(fadeTimer, 0f);
            music.setVolume(fadeTimer / fadeTime);
        }
    }
}
