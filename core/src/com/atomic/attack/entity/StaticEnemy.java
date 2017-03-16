package com.atomic.attack.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public abstract class StaticEnemy extends Enemy {

    public StaticEnemy(TextureRegion reg, Vector2 pos, EntityManager entityManager) {
        super(reg, pos, entityManager);
    }



}
