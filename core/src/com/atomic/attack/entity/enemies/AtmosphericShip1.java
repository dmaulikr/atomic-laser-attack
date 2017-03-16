package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.atomic.attack.entity.EntityManager;


public class AtmosphericShip1 extends com.atomic.attack.entity.FollowPathEnemy {

    private static final Array<TextureAtlas.AtlasRegion> regs = com.atomic.attack.Assets.instance.enemy.atmosphericShip1;
    final float predictionTime;
    final float pathOffset;
    Vector2 projectileDirection = new Vector2(0f, -10f);
    float lastFire = -0.5f;
    float fireRate = 0.15f;;
    float fireWaveDelay = 0.7f;
    int timer1 = 0;

    public AtmosphericShip1(Vector2 pos, EntityManager entityManager) {
        super(pos, entityManager, regs.first());
        scoreVal = 10;
        HP = 10;

        openPath = false;

        maxLinearSpeed = 100;
        predictionTime = 0.5f;
        pathOffset = 5f;

    }
    public void createPath() {
        createPath(Path(), pathOffset, predictionTime);
    }

    @Override
    public void activate() {
        createPath();
        active = true;
    }

    @Override
    public void updateMain(float dt) {
        //createPath();
        lastFire += dt;
        if(lastFire  >=  fireRate) {
            if (timer1 < 5) {
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
            reg = com.atomic.attack.Assets.instance.enemy.atmosphericShip1.get(1);
        }
        else if (linearVelocity.x < 0) {
            reg = com.atomic.attack.Assets.instance.enemy.atmosphericShip1.get(2);
        }
        else {
            reg = com.atomic.attack.Assets.instance.enemy.atmosphericShip1.first();
        }

    }

    private Array<Vector2> Path() {
        Array<Vector2> wayPoints = new Array<Vector2>(4);
        float ofs = 2 * entityManager.mapCamera.getCamSpeed();

        if (pos.x + getWidth() / 2 < entityManager.mapCamera.getPos().x) {
            wayPoints.add(new Vector2(pos.x, pos.y));
            wayPoints.add(new Vector2(pos.x + 50f * ofs, pos.y + 20 * ofs));
            wayPoints.add(new Vector2(pos.x, pos.y));
            wayPoints.add(new Vector2(pos.x - 50f * ofs, pos.y + 20 * ofs));
        }
        else {
            wayPoints.add(new Vector2(pos.x, pos.y));
            wayPoints.add(new Vector2(pos.x - 50f * ofs, pos.y + 20f * ofs));
            wayPoints.add(new Vector2(pos.x, pos.y));
            wayPoints.add(new Vector2(pos.x + 50f * ofs, pos.y + 20f * ofs));
        }


        return wayPoints;
    }


}
