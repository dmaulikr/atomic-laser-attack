package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class BigBullet extends com.atomic.attack.entity.Enemy {

    static final TextureRegion reg = com.atomic.attack.Assets.instance.enemy.bigBullet;
    static final float speedY = -2f;

    public BigBullet(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(reg, pos, new Vector2(0f, speedY), entityManager);
        this.entityManager = entityManager;
        scoreVal = 10;
        HP = 6;
        hasShadow = true;
    }

    @Override
    public void update(float dt) {
        pos = pos.add(direction.cpy().scl(dt * 60));
    }

    @Override
    public void onDestruction() {
        entityManager.particles.add(new com.atomic.attack.particle.Exp4(new Vector2(pos.x + this.getMiddleX(), pos.y + this.getMiddleY()), entityManager));

        for (int i = 0; i < 4; i++) {
            Vector2 vec = new Vector2(-6f, 0f).rotate(60 * i);
            entityManager.addEntity(new com.atomic.attack.entity.Projectiles.ProjectTriangle(getPosMiddle(), vec));
        }

        entityManager.removeEntity(this);
    }
}
