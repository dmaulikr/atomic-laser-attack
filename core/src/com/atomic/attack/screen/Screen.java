package com.atomic.attack.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Screen {
    protected boolean disposed = false;
    public float dt = 0;

    public abstract void create();

    public abstract void handleInput();

    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void resize(int width, int height);

    public abstract void dispose();

    public abstract void pause();

    public abstract void resume();


}
