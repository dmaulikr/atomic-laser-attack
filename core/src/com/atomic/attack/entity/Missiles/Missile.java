package com.atomic.attack.entity.Missiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.atomic.attack.entity.Entity;

public class Missile extends Entity {

    public int damage;

    public Missile(TextureRegion reg, Vector2 pos, float speed, int damage) {
        super(reg);
        this.pos = pos;
        this.direction = new Vector2(0f, speed);
        this.damage = damage;
    }

    @Override
    public void update(float dt) {
        pos.add(direction.cpy().scl(dt * 60f));
    }

    public void onDestruction(com.atomic.attack.entity.EntityManager entityManager, com.atomic.attack.entity.Enemy e) {
        Vector2 base = getExplosionVector();
        base.add(randomExplosionVector(e));
        entityManager.particles.add(new com.atomic.attack.particle.Exp2(base, entityManager));
    }

    public Vector2 randomExplosionVector(com.atomic.attack.entity.Enemy e) {
        return new Vector2(MathUtils.random(-5f, 5f), MathUtils.random(-5f, (e.getHeight() - e.getHeight() / 3)));
    }

    public Vector2 getExplosionVector() {
        return new Vector2(pos.x + reg.getRegionWidth() / 2, pos.y + reg.getRegionHeight());
    }

}
