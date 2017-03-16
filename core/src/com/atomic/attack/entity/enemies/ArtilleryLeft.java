package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.math.Vector2;
import com.atomic.attack.Assets;

public class ArtilleryLeft extends ArtilleryRight {

    public ArtilleryLeft(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(Assets.instance.staticEnemy.artilleryLeft.first(), pos, entityManager);
    }

    @Override
    public void onDestruction() {
        entityManager.addEntity(new com.atomic.attack.entity.DestroyedObject(reg_d, this.pos.cpy().add(0f, 0f)));
        entityManager.particles.add(new com.atomic.attack.particle.Exp1(new Vector2(pos.x + this.getMiddleX(), pos.y + this.getMiddleY()), entityManager));
        entityManager.removeEntity(this);
    }

}
