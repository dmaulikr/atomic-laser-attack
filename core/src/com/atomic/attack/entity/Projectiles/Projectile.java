package com.atomic.attack.entity.Projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.atomic.attack.entity.Entity;

public class Projectile extends Entity {

    public int damage;

    public Projectile (TextureRegion reg, Vector2 pos, Vector2 direction, int damage) {
        super(reg);
        this.pos = pos.add(-getMiddleX(), -getMiddleY());
        this.direction = direction;
        this.damage = damage;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(reg, pos.x, pos.y, reg.getRegionWidth() / 2, reg.getRegionHeight() / 2,
                reg.getRegionWidth(), reg.getRegionHeight(), 1f, 1f, angle);
    }


    @Override
    public void update(float dt) {
        pos.add(direction.cpy().scl(dt * 60));
    }

}