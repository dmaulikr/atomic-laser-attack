package com.atomic.attack.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.atomic.attack.MainGame;

public class OrthoCamera extends OrthographicCamera {
    private float camSpeed;

    Viewport viewport;
    Vector2 pos = new Vector2();

    public OrthoCamera(float camSpeed) {
        this();
        this.camSpeed = camSpeed;
    }

    public OrthoCamera() {
        viewport = new StretchViewport(MainGame.WIDTH, MainGame.HEIGHT, this);
        viewport.apply();
        camSpeed = 0f;
    }

    public void setPosition(float x, float y) {
        position.set(x, y, 0f);
        pos.set(x, y);
    }

    public Vector2 getPos() {
        return pos;
    }

    public void updateY(float dt) {
        Vector2 camPos = this.getPos();
        camPos.add(0f, camSpeed * dt * 60f);
        this.translate(camPos);
    }

    public void updateX(float STARTING_POSITION_X, Vector2 playerPos, float amount) {
        Vector2 camPos = this.getPos();
        playerPos.x -= MainGame.WIDTH / 2f;
        float a =STARTING_POSITION_X + (playerPos.x / amount);
        a = a * 1000;
        float b = (int)a;
        this.setPosition(b / 1000, camPos.y);

    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public float getCamSpeed() {
        return camSpeed;
    }

    public void setCamSpeed(float camSpeed) {
        this.camSpeed = camSpeed;
    }

    public Vector2 unprojectCoordinates(float x, float y) {
        Vector3 rawtouch = new Vector3(x, y,0);
        unproject(rawtouch);
        return new Vector2(rawtouch.x, rawtouch.y);
    }
}
