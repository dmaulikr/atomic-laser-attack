package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.atomic.attack.MainGame;
import com.atomic.attack.entity.EntityManager;
import com.atomic.attack.entity.HealthBar;
import com.atomic.attack.entity.Projectiles.ProjectileBoss1Rockets;
import com.atomic.attack.entity.Projectiles.ProjectileRedBall;

public class Boss1 extends com.atomic.attack.entity.FollowPathEnemy implements com.atomic.attack.entity.DrawableHealthBar {
    static final TextureRegion reg = com.atomic.attack.Assets.instance.boss.boss1;

    final float predictionTime;
    final float pathOffset;
    float lastFire = -1;
    float lastFire2 = 0;
    float timer1 = 0;
    final float fireRate = 0.1f;

    private final int maxHP = 300;

    private boolean destroyed = false;

    private com.atomic.attack.camera.OrthoCamera statCamera;
    public HealthBar healthBar;

    public Boss1(Vector2 pos, EntityManager entityManager) {
        super(pos, entityManager, reg);
        angle = 180;
        scoreVal = 1000;
        HP = maxHP;

        openPath = false;

        maxLinearSpeed = 150;
        predictionTime = 1f;
        pathOffset = 10f;

        statCamera = entityManager.statCamera;
        healthBar = new HealthBar(HP);

    }



    public void createPath() {
        createPath(Path(), pathOffset, predictionTime);
    }

    @Override
    public void activate() {
        createPath();
        active = true;

        entityManager.mapCamera.setCamSpeed(0.2f);
    }

    private Array<Vector2> Path() {
        Array<Vector2> wayPoints = new Array<Vector2>(3);

        wayPoints.add(new Vector2(entityManager.mapCamera.getPos().x - MainGame.WIDTH / 2, entityManager.mapCamera.getPos().y + MainGame.HEIGHT / 3 - getHeight()));
        wayPoints.add(new Vector2(entityManager.mapCamera.getPos().x , entityManager.mapCamera.getPos().y + getHeight() - 20f));
        wayPoints.add(new Vector2(entityManager.mapCamera.getPos().x + MainGame.WIDTH / 2 - getWidth(), entityManager.mapCamera.getPos().y + MainGame.HEIGHT / 3 - getHeight()));


        return wayPoints;
    }

    public void updateMain(float dt) {
        healthBar.update(HP);

        createPath();

        if (!destroyed) {
            lastFire += dt;
            lastFire2 += dt;
            if(lastFire  >=  fireRate) {
                if (timer1 < 7) {
                    entityManager.addEntity(new ProjectileBoss1Rockets(pos.cpy().add(21f, 8f), new Vector2(0f, -7f)));
                    entityManager.addEntity(new ProjectileBoss1Rockets(pos.cpy().add(getWidth() - 14f, 8f), new Vector2(0f, -7f)));

                    lastFire = 0;
                    timer1 += 1;
                }
                else {
                    timer1 = 0;
                    lastFire = -fireRate * 3;
                }
            }
            if (this.HP < maxHP / 2) {
                if (lastFire2 >= fireRate * 5) {
                    entityManager.addEntity(new ProjectileRedBall(getPosMiddle().add(0f, -6f), entityManager.findVectorToPlayerMiddle(this.getPosMiddle()).nor().scl(3f).rotate(MathUtils.random(-40, 40))));
                    lastFire2 = 0;
                }
            }
        }
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

            final Boss1 thisRef = this;

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

    @Override
    public HealthBar getHealtBar() {
        return healthBar;
    }
}
