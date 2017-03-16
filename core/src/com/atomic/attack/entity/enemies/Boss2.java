package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.atomic.attack.entity.Projectiles.ProjectileTrackingMissile;

public class Boss2 extends com.atomic.attack.entity.FollowPathEnemy implements com.atomic.attack.entity.DrawableHealthBar {

    private final int maxHP = 400;
    private final float predictionTime = 1f;
    private final float pathOffset = 10f;
    public com.atomic.attack.entity.HealthBar healthBar;
    private boolean destroyed = false;
    private boolean shooting = false;
    private boolean playerNearby = false;
    float lastFire = -1;
    float fireRate2 = 0.75f;
    final float fireRate = 0.1f;

    public Boss2(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(pos, entityManager, com.atomic.attack.Assets.instance.boss.boss2);
        scoreVal = 1000;
        HP = maxHP;
        openPath = false;
        maxLinearSpeed = 150;

        healthBar = new com.atomic.attack.entity.HealthBar(HP);
    }

    @Override
    public void activate() {
        createPath();
        active = true;

        entityManager.mapCamera.setCamSpeed(0.2f);
    }

    public void createPath() {
        createPath(Path(), pathOffset, predictionTime);
    }

    @Override
    public void updateMain(float dt) {
        lastFire += dt;
        healthBar.update(HP);
        createPath();

        if (!destroyed) {
            if(lastFire  >=  fireRate) {
                if (shooting) {
                    entityManager.addEntity(new com.atomic.attack.entity.Projectiles.ProjectileBoss1Rockets(pos.cpy().add(10f, 30f), new Vector2(0f, -7f)));
                    entityManager.addEntity(new com.atomic.attack.entity.Projectiles.ProjectileBoss1Rockets(pos.cpy().add(getWidth() - 10f, 30f), new Vector2(0f, -7f)));
                    lastFire = 0;
                }

                if (playerNearby && lastFire >= fireRate2) {
                    entityManager.addEntity(new ProjectileTrackingMissile(pos.cpy().add(10f, 10f), new Vector2(0f, -2f), entityManager));
                    entityManager.addEntity(new ProjectileTrackingMissile(pos.cpy().add(getWidth() - 10f, 10f), new Vector2(0f, -2f), entityManager));
                    lastFire = 0;
                }
            }
        }

    }

    private Array<Vector2> Path() {
        Array<Vector2> wayPoints = new Array<Vector2>(2);
        Vector2 camPos = new Vector2(entityManager.mapCamera.getPos());
        Vector2 playerPos = new Vector2(entityManager.player.getPosMiddle());
        if (playerPos.x > camPos.x + com.atomic.attack.MainGame.WIDTH / 3 || playerPos.x < camPos.x - com.atomic.attack.MainGame.WIDTH / 3) {       //If player in the middle
            shooting = true;
            playerNearby = false;
            if (playerPos.x < camPos.x) {
                wayPoints.add(new Vector2(camPos.x, camPos.y + 50));
            }
            else {
                wayPoints.add(new Vector2(camPos.x, camPos.y + 50));
            }
        }
        else {
            shooting = false;
            playerNearby = true;
            wayPoints.add(new Vector2(getPos().x, camPos.y + 50));
        }

        wayPoints.add(new Vector2(playerPos.x, camPos.y));


        return wayPoints;
    }

    @Override
    public com.atomic.attack.entity.HealthBar getHealtBar() {
        return healthBar;
    }

    @Override
    public boolean checkBottom(com.atomic.attack.camera.OrthoCamera camera) {
        return pos.y <= camera.getPos().y - reg.getRegionHeight() - com.atomic.attack.MainGame.HEIGHT / 2;
    }

    @Override
    public void onDestruction() {
        if (!destroyed) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    entityManager.particles.add(new com.atomic.attack.particle.Exp4(new Vector2(
                            MathUtils.random(pos.x, pos.x + getWidth()), MathUtils.random(pos.y, pos.y + getHeight())), entityManager));
                }
            }, 0, 0.1f, 60);

            final Boss2 thisRef = this;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    entityManager.removeEntity(thisRef);
                }
            }, 2f);

            entityManager.screenShake.shake(4f, 3f);
            destroyed = true;
            entityManager.levelWon();
        }
    }
}
