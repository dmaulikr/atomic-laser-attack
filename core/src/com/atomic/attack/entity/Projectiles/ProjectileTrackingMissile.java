package com.atomic.attack.entity.Projectiles;

import com.badlogic.gdx.math.Vector2;

public class ProjectileTrackingMissile extends Projectile {
    public static final int damage = 2;
    private com.atomic.attack.entity.EntityManager entityManager;
    float maxSpeed, time, speed;
    static float destructionTime = 2f;

    public ProjectileTrackingMissile(Vector2 pos, Vector2 direction, com.atomic.attack.entity.EntityManager entityManager) {
        super(com.atomic.attack.Assets.instance.projectile.projectileTrackingMissile, pos, direction, damage);
        maxSpeed = Math.abs(direction.y);
        speed = maxSpeed / 2f;
        this.entityManager = entityManager;
    }

    @Override
    public void update(float dt) {
        Vector2 target = entityManager.findVectorToPlayerMiddle(pos);
        target.nor();
        target.scl(0.15f);
        direction.add(target);
        direction.nor();
        direction.scl(speed);
        pos.add(direction);
        angle = direction.angle() + 90;

        time += dt;

        if (time > destructionTime) {
            entityManager.particles.add(new com.atomic.attack.particle.Exp1(getPosMiddle(), entityManager).setScale(0.5f));
            entityManager.removeEntity(this);
        }

        speed = Math.min(maxSpeed * 3, speed + dt * 5);
    }
}
