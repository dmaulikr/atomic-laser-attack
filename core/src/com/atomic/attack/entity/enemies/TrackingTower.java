package com.atomic.attack.entity.enemies;


import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class TrackingTower extends com.atomic.attack.entity.StaticEnemy {

    private static final Array<TextureAtlas.AtlasRegion> regs = com.atomic.attack.Assets.instance.staticEnemy.trackingTower;
    private static final TextureRegion reg_d = com.atomic.attack.Assets.instance.staticEnemy.trackingTower_d;

    private Animation animation;
    final float frameDuration = 1/6f;
    private float lastFire = -0.5f;
    private final float projectileSpeed = 2f;
    final float fireRate = 1f;

    public TrackingTower(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(regs.first(), pos, entityManager);
        this.entityManager = entityManager;
        animation = new Animation(frameDuration, regs);
        HP = 10;
        scoreVal = 10;
    }

    @Override
    public void update(float dt) {

        if (lastFire > 0.5f) {
            reg = animation.getKeyFrame(lastFire, true);
        }
        else reg = regs.first();


        lastFire += dt;
        if(lastFire >=  fireRate) {
            entityManager.addEntity(new com.atomic.attack.entity.Projectiles.ProjectileRedBall(getPosMiddle(),
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
