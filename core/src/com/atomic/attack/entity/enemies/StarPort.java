package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.atomic.attack.entity.EntityManager;
import com.atomic.attack.entity.Projectiles.ProjectileRedBall;

public class StarPort extends com.atomic.attack.entity.StaticEnemy {

    private static final Array<TextureAtlas.AtlasRegion> regs = com.atomic.attack.Assets.instance.staticEnemy.starPort;
    private static final TextureRegion reg_d = com.atomic.attack.Assets.instance.staticEnemy.starPort_d;

    private Animation animation;
    private final float fireRate = 0.75f;
    private final float frameDuration = 1/(10 / fireRate);
    private float lastFire = -0.5f;
    private final float projectileSpeed = 2f;

    public StarPort(Vector2 pos, EntityManager entityManager) {
        super(regs.first(), pos, entityManager);
        this.entityManager = entityManager;
        animation = new Animation(frameDuration, regs);
        HP = 24;
        scoreVal = 20;
    }

    @Override
    public void update(float dt) {

        if (lastFire > fireRate / 2) {
            reg = animation.getKeyFrame(lastFire, true);
        }
        else reg = regs.first();


        lastFire += dt;
        if(lastFire >=  fireRate) {
            entityManager.addEntity(new ProjectileRedBall(getPosMiddle(),
                    entityManager.findVectorToPlayerMiddle(getPosMiddle()).nor().scl(projectileSpeed), entityManager.mapCamera));

            lastFire = 0;
        }
    }

    @Override
    public void onDestruction() {
        entityManager.particles.add(new com.atomic.attack.particle.Exp4(new Vector2(getPosMiddle()), entityManager));
        entityManager.addEntity(new com.atomic.attack.entity.DestroyedObject(reg_d, this.pos.cpy()));

        entityManager.removeEntity(this);
    }
}
