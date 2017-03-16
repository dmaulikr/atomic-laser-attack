package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class EnemyShip1 extends com.atomic.attack.entity.Enemy {

    private float lastFire = -0.4f;
    final float shootingInterval = 0.25f;
    final float projectileSpeed = 6f;
    final float speed = 3f;
    private Vector2 dir;
    private Vector2 dirCopy;

    public EnemyShip1(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        this(com.atomic.attack.Assets.instance.enemy.enemyShip1, pos, entityManager);
    }

    public EnemyShip1(TextureRegion region, Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(region, pos, entityManager);
        this.entityManager = entityManager;
        scoreVal = 20;
        HP = 8;
        hasShadow = true;
    }

    @Override
    public void activate() {
        direction = entityManager.findVectorToPlayerMiddle(pos.cpy().add(getMiddleX(), getMiddleY()));
        direction.nor();
        direction.scl(speed);
        direction.add(new Vector2(0f, entityManager.mapCamera.getCamSpeed()));

        active = true;

        dir = entityManager.findVectorToPlayerMiddle(pos.cpy().add(getMiddleX(), getMiddleY()));
        dir.nor();
        dir.scl(6);

        dirCopy = dir.cpy();
    }

    @Override
    public void update(float dt) {

        pos.add(direction.cpy().scl(dt * 60));
        //pos.add(new Vector2(0f, camera.getCamSpeed()).scl(dt * 60));

        lastFire += dt;

        if(lastFire >=  shootingInterval) {
            dir = dirCopy.cpy();

            float x = dir.x;
            float y = dir.y;

            dir.set(y, -x);

            entityManager.addEntity(new com.atomic.attack.entity.Projectiles.ProjectileEnemyShip(pos.cpy().add(getMiddleX() + dir.x, getMiddleY() + dir.y), direction.cpy().scl(projectileSpeed)));

            dir.set(-dir.x, -dir.y);

            entityManager.addEntity(new com.atomic.attack.entity.Projectiles.ProjectileEnemyShip(pos.cpy().add(getMiddleX() + dir.x , getMiddleY() + dir.y), direction.cpy().scl(projectileSpeed)));

            lastFire = 0;
        }

        this.angle = direction.angle() - 90;
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(batchColor);

        sb.draw(reg, pos.x, pos.y, getMiddleX(), getMiddleY(),
                getWidth(), getHeight(), scaleX, scaleY, angle);

        sb.setColor(com.badlogic.gdx.graphics.Color.WHITE);
    }

}
