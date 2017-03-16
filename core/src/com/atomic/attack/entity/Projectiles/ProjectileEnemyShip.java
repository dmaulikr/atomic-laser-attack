package com.atomic.attack.entity.Projectiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.atomic.attack.Assets;

public class ProjectileEnemyShip extends Projectile {
    public static final int damage = 1;
    private static final TextureRegion reg = Assets.instance.projectile.projectileEnemyShip;

    public ProjectileEnemyShip(Vector2 pos, Vector2 direction) {
        super(reg, pos, direction, damage);
        angle = direction.angle() - 90;
    }
}
