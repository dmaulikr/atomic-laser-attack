package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EnemyShip2 extends EnemyShip1{
    private static final TextureRegion reg = com.atomic.attack.Assets.instance.enemy.enemyShip2;

    public EnemyShip2(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(reg, pos, entityManager);
    }
}