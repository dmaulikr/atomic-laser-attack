package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.atomic.attack.entity.DestroyedObject;
import com.atomic.attack.entity.EntityManager;

public class Field extends com.atomic.attack.entity.StaticEnemy {
    private static final TextureRegion reg = com.atomic.attack.Assets.instance.destructable.field;
    private static final TextureRegion reg_d = com.atomic.attack.Assets.instance.destructable.field_d;

    public Field(Vector2 pos, EntityManager entityManager) {
        super(reg, pos, entityManager);
        HP = 20;
        scoreVal = 5;
        hasShadow = false;
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void onDestruction() {
        entityManager.particles.add(new com.atomic.attack.particle.Exp4(new Vector2(getPosMiddle()), entityManager));
        entityManager.addEntity(new DestroyedObject(reg_d, this.pos.cpy()));

        entityManager.removeEntity(this);
    }
}
