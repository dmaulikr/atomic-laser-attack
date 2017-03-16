package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

public class ArtilleryRight extends com.atomic.attack.entity.StaticEnemy {
    protected static final TextureRegion reg_d = com.atomic.attack.Assets.instance.staticEnemy.artilleryRight_d;
    private float lastFire = 0;
    final Vector2 projectileVector = new Vector2(-4f, -2f);
    boolean left = false;
    float time = 0.25f;

    public ArtilleryRight(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(com.atomic.attack.Assets.instance.staticEnemy.artilleryRight.first(), pos, entityManager);
        this.entityManager = entityManager;
        HP = 40;
        scoreVal = 40;
    }

    public ArtilleryRight(TextureRegion reg, Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(reg, pos, entityManager);
        HP = 40;
        scoreVal = 30;
        this.projectileVector.x = -this.projectileVector.x;
        this.entityManager = entityManager;
        left = true;
    }

    @Override
    public void update(float dt) {

        lastFire += dt;
        if(lastFire >=  1) {
            if (left) {
                entityManager.addEntity(new com.atomic.attack.entity.Projectiles.ProjectileArtillery(pos.cpy().add(super.getWidth(), super.getMiddleY()), projectileVector));
                lastFire = 0;
                reg = com.atomic.attack.Assets.instance.staticEnemy.artilleryLeft.get(1);

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        reg = com.atomic.attack.Assets.instance.staticEnemy.artilleryLeft.first();
                    }
                }, time * dt * 60);

            }
            else {
                entityManager.addEntity(new com.atomic.attack.entity.Projectiles.ProjectileArtillery(pos.cpy().add(0f, super.getMiddleY()), projectileVector));
                lastFire = 0;
                reg = com.atomic.attack.Assets.instance.staticEnemy.artilleryRight.get(1);

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        reg = com.atomic.attack.Assets.instance.staticEnemy.artilleryRight.first();
                    }
                }, time * dt * 60);
            }


        }
    }

    @Override
    public void onDestruction() {
        entityManager.addEntity(new com.atomic.attack.entity.DestroyedObject(reg_d, this.pos.cpy().add(10f, 0f)));
        entityManager.particles.add(new com.atomic.attack.particle.Exp1(new Vector2(pos.x + this.getMiddleX(), pos.y + this.getMiddleY()), entityManager));
        entityManager.removeEntity(this);
    }
}
