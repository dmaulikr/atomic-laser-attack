package com.atomic.attack.entity.Projectiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class ProjectileBoss1Rockets extends Projectile{

    public static final int damage = 1;
    private static final TextureRegion reg = com.atomic.attack.Assets.instance.projectile.projectileEnemyShip;

    public ProjectileBoss1Rockets(Vector2 pos, Vector2 direction) {
        super(reg, pos.add(-reg.getRegionWidth() / 2, -reg.getRegionHeight() / 2), direction, damage);
        angle = direction.angle() - 90;
    }

}
