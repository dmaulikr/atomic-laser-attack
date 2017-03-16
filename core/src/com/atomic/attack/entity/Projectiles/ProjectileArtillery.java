package com.atomic.attack.entity.Projectiles;

import com.badlogic.gdx.math.Vector2;

public class ProjectileArtillery extends Projectile {
    public static final int damage = 3;

    public ProjectileArtillery(Vector2 pos, Vector2 direction) {
        super(com.atomic.attack.Assets.instance.projectile.projectileArtillery, pos, direction, damage);
        this.angle = direction.angle() + 90;
    }

    @Override
    public void update(float dt) {
        pos.add(direction.cpy().scl(dt * 60));

    }

}
