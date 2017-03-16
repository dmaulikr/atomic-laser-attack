package com.atomic.attack.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected TextureRegion reg;
    protected Vector2 pos, direction;
    public boolean active = true;
    //protected OrthoCamera camera;
    private Polygon collisionPolygon;

    protected float angle = 0f;
    protected float scaleX = 1f;
    protected float scaleY = 1f;

    protected boolean hasShadow = false;

    public Entity (TextureRegion reg) {
        this.reg = reg;
        collisionPolygon = new Polygon(new float[]{0, 0, getWidth() * scaleX, 0, getWidth() * scaleX, getHeight() * scaleY, 0, getHeight() * scaleY});
    }

    public abstract void update(float dt);

    public void render(SpriteBatch sb) {
        sb.draw(reg, pos.x, pos.y, getMiddleX(), getMiddleY(),
                getWidth(), getHeight(), scaleX, scaleY, angle);
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public Polygon getCollisionPolygon() {
        return collisionPolygon;
    }

    public void updateCollisionPolygon() {
        collisionPolygon.setPosition(this.getPos().x, this.getPos().y);
        collisionPolygon.setOrigin(getMiddleX(), getMiddleY());
        collisionPolygon.setRotation(this.angle);
    }

    public float getMiddleX() {
        return reg.getRegionWidth() / 2;
    }

    public float getMiddleY() {
        return reg.getRegionHeight() / 2;
    }

    public float getWidth() {
        return reg.getRegionWidth();
    }

    public float getHeight() {
        return reg.getRegionHeight();
    }

    public boolean checkBottom(com.atomic.attack.camera.OrthoCamera camera) {
        return pos.y <= camera.getPos().y - reg.getRegionHeight() - com.atomic.attack.MainGame.HEIGHT / 2;
    }

    public boolean checkTop(com.atomic.attack.camera.OrthoCamera camera) {
        return pos.y >= camera.getPos().y + com.atomic.attack.MainGame.HEIGHT / 2;
    }

    public boolean checkBottomAndSides(com.atomic.attack.camera.OrthoCamera camera) {
        return pos.y <= camera.getPos().y - getHeight() - com.atomic.attack.MainGame.HEIGHT / 2 || pos.y >= camera.getPos().y + com.atomic.attack.MainGame.HEIGHT + getHeight() || pos.x < camera.getPos().x - com.atomic.attack.MainGame.WIDTH / 2 - getWidth() || pos.x > camera.getPos().x + com.atomic.attack.MainGame.WIDTH / 2;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public boolean isActive() {
        return active;
    }

    public Vector2 getPosMiddle() {
        return new Vector2(pos.x + reg.getRegionWidth() / 2, pos.y + reg.getRegionHeight() / 2);
    }

    public void renderShadow(SpriteBatch sb) {
        if (hasShadow) {
            Color tempCol = sb.getColor();
            sb.setColor(0f, 0f, 0f, 0.3f);
            float posMod = 4f;
            sb.draw(reg, pos.x + posMod, pos.y - posMod, getMiddleX(), getMiddleY(),
                    getWidth(), getHeight(), scaleX, scaleY, angle);
            sb.setColor(tempCol);
        }
    }

    public Entity enableShadow() {
        hasShadow = true;
        return this;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

}
