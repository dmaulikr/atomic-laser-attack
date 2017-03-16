package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.atomic.attack.entity.EntityManager;

public class Factory extends com.atomic.attack.entity.StaticEnemy {

    private static final Array<TextureAtlas.AtlasRegion> regs = com.atomic.attack.Assets.instance.destructable.factory;
    private static final TextureRegion reg_d = com.atomic.attack.Assets.instance.destructable.factory_d;

    float timePassed = 0;
    private Animation animation;
    private static float frameduration = 1/5f;

    public Factory(Vector2 pos, EntityManager entityManager) {
        super(regs.first(), pos, entityManager);
        HP = 18;
        scoreVal = 5;
        animation = new Animation(frameduration, regs);
        hasShadow = true;
    }

    @Override
    public void update(float dt) {
        timePassed += dt;
        reg = animation.getKeyFrame(timePassed, true);
    }

    @Override
    public void onDestruction() {
        entityManager.particles.add(new com.atomic.attack.particle.Exp4(new Vector2(getPosMiddle()), entityManager));
        entityManager.addEntity(new com.atomic.attack.entity.DestroyedObject(reg_d, this.pos.cpy()).enableShadow());

        entityManager.removeEntity(this);
    }
}
