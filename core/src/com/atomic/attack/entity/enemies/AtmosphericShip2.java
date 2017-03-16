package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class AtmosphericShip2 extends com.atomic.attack.entity.FollowPathEnemy {

    private static final Array<TextureAtlas.AtlasRegion> regs = com.atomic.attack.Assets.instance.enemy.atmosphericShip2;
    final float predictionTime;
    final float pathOffset;
    Vector2 projectileDirection = new Vector2(0f, -8f);
    float lastFire = -1;
    float fireRate = 0.15f;
    float fireWaveDelay = 0.7f;
    int timer1 = 0;

    public AtmosphericShip2(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(pos, entityManager, regs.first());
        scoreVal = 10;
        HP = 10;

        openPath = true;

        maxLinearAcceleration = 4000;
        maxLinearSpeed = entityManager.mapCamera.getCamSpeed() * 60 - 10;
        predictionTime = 1f;
        pathOffset = 20f;

    }

    @Override
    public void activate() {
        createPath(Path(), pathOffset, predictionTime);
        active = true;
    }

    @Override
    public void updateMain(float dt) {
        lastFire += dt;
        if(lastFire  >=  fireRate) {
            if (timer1 < 4) {
                entityManager.addEntity(new com.atomic.attack.entity.Projectiles.ProjectileEnemyShip(new Vector2(pos.x + getMiddleX(), pos.y), projectileDirection));

                lastFire = 0;
                timer1 += 1;
            }
            else {
                timer1 = 0;
                lastFire = -fireWaveDelay;
            }

        }

        if (linearVelocity.x > 0) {
            reg = com.atomic.attack.Assets.instance.enemy.atmosphericShip2.get(1);
        }
        else if (linearVelocity.x < 0) {
            reg = com.atomic.attack.Assets.instance.enemy.atmosphericShip2.get(2);
        }
        else {
            reg = com.atomic.attack.Assets.instance.enemy.atmosphericShip2.first();
        }

    }

    private Array<Vector2> Path() {
        Array<Vector2> wayPoints = new Array<Vector2>(3);

        Vector2 tempPos = pos.cpy();

        wayPoints.add(new Vector2(tempPos.x, tempPos.y));
        wayPoints.add(new Vector2(tempPos.x, tempPos.y + 150));
        wayPoints.add(new Vector2(tempPos.x, tempPos.y - 400));



        return wayPoints;
    }


}
