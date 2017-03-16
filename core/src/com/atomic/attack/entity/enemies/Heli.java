package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Heli extends com.atomic.attack.entity.Enemy {
    static final float speedY = -1.5f;
    float timePassed = 0;
    private Animation animation;
    private static float frameduration = 1/20f;

    private static final Array<TextureAtlas.AtlasRegion> regs = com.atomic.attack.Assets.instance.enemy.heli;

    public Heli(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(regs.first(), pos, new Vector2(0f, speedY), entityManager);
        this.entityManager = entityManager;
        scoreVal = 5;
        HP = 6;
        hasShadow = true;
        animation = new Animation(frameduration, regs);
    }

    @Override
    public void update(float dt) {
        pos = pos.add(direction.cpy().scl(dt * 60));

        timePassed += dt;
        reg = animation.getKeyFrame(timePassed, true);

    }


}
