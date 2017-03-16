package com.atomic.attack.particle;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public abstract class Particle {

    private Animation animation;
    private float timePassed = 0;
    private Vector2 pos;
    private Array<TextureAtlas.AtlasRegion> textures;

    protected float scaleX = 1f;
    protected float scaleY = 1f;

    private float randomScale;

    private float angle;
    private com.atomic.attack.entity.EntityManager entityManager;

    public Particle(float frameDuration, Array<TextureAtlas.AtlasRegion> textures, Vector2 pos, float angle, com.atomic.attack.entity.EntityManager entityManager) {
        animation = new Animation(frameDuration, textures);
        this.pos = pos.add(-textures.first().getRegionWidth() / 2, -textures.first().getRegionHeight() / 2);
        this.textures = textures;
        this.angle = angle;
        randomScale = MathUtils.random(-0.1f, 0.1f);
        this.entityManager = entityManager;
    }

    public void render(SpriteBatch sb) {
        timePassed += entityManager.getScreen().dt;

        sb.draw(animation.getKeyFrame(timePassed, false), pos.x, pos.y,
                getWidth() / 2, getHeight() / 2, getWidth(), getHeight(),
                scaleX + randomScale, scaleY + randomScale, angle);

        //float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation

    }

    public boolean isAnimationFinished() {
        if (animation.isAnimationFinished(timePassed)) {
            return true;
        }
        else return false;
    }

    private float getWidth() {
        return textures.first().getRegionWidth();
    }

    private float getHeight() {
        return textures.first().getRegionHeight();
    }

    public Particle setScale(float scale) {
        this.scaleY = scale;
        this.scaleX = scale;
        return this;
    }
}
