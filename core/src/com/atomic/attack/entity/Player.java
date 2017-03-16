package com.atomic.attack.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;

public class Player extends Entity implements DrawableHealthBar, Disposable{

    private static final TextureRegion reg = com.atomic.attack.Assets.instance.player.player;
    private final Sound laserSound = com.atomic.attack.Assets.instance.player.playerLaser;
    private final Sound hitSound = com.atomic.attack.Assets.instance.particle.exp3Sound;
    private static String TAG = Player.class.getSimpleName();

    private final EntityManager entityManager;
    private final com.atomic.attack.camera.OrthoCamera camera;

    final int maxHP = 30 - 10 * (com.atomic.attack.UpgradeManager.instance.getDifficulty());
    public int HP = maxHP;
    private float lastFire = 0;

    public boolean onCooldown = false;
    public final float mouseOffset = -20f;


    private PlayerHealthBar hb;

    public Player (Vector2 pos, EntityManager entityManager, com.atomic.attack.camera.OrthoCamera camera) {
        super(reg);
        Gdx.app.debug(TAG, "Total HP: " + String.valueOf(maxHP));
        this.pos = pos;
        this.entityManager = entityManager;
        this.camera = camera;
        direction = new Vector2(0f, 0f);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (!isDead()) {
                    if (com.atomic.attack.UpgradeManager.instance.getDifficulty() == 0) {
                        HP = Math.min(maxHP, HP + 2);
                    }
                    else {
                        HP = Math.min(maxHP, HP + 1);
                    }
                }
            }
        }, 0f, 3f);

        hb = new PlayerHealthBar(maxHP);

    }

    @Override
    public void update(float dt) {

        //Used when player is influenced by outside forces
        if (!direction.isZero()) {
            pos.add(direction);
            direction.set(0f, 0f);
            //Interpolation.linear.apply(direction.x, 0f, 0.2f)
        }

        pos.add(0f, camera.getCamSpeed() * dt * 60f);

        //Missile fire system
        if (!this.isDead()) {
            lastFire += (dt);
            if(lastFire  >=  com.atomic.attack.UpgradeManager.instance.getCurrentMissileFireRate()) {
                entityManager.addMissile();

                long id = laserSound.play(0.03f);

                lastFire = 0;
            }
        }

        hb.update(HP);

        Vector3 projection = camera.project(new Vector3(getPosMiddle(), 0));
        if (projection.x > Gdx.graphics.getWidth() || projection.x < 0 || projection.y > Gdx.graphics.getHeight() || projection.y < 0) {
            Vector2 input = new Vector2(projection.x, projection.y);
            float a = Gdx.graphics.getWidth() / 2 - input.x;
            float b = Gdx.graphics.getHeight() / 2 - input.y;
            Vector2 out = new Vector2(a, b);
            out.nor();
            out.scl(2f);

            pos.add(out);
        }


        updateCollisionPolygon();
    }

    public void getHit(int damage) {
        this.HP -= damage;
        entityManager.screenShake.shake(2f, 0.3f);
        Gdx.input.vibrate(30);
        hitSound.play(0.3f);

    }

    public boolean isDead() {
        return (HP <= 0);
    }

    public void renderShadow(SpriteBatch sb) {
            Color tempCol = sb.getColor();
            sb.setColor(0f, 0f, 0f, 0.3f);
            float posMod = 5f;
            sb.draw(reg, pos.x + posMod, pos.y - posMod, getMiddleX(), getMiddleY(),
                    getWidth(), getHeight(), scaleX, scaleY, angle);
            sb.setColor(tempCol);

    }

    @Override
    public com.atomic.attack.entity.HealthBar getHealtBar() {
        return hb;
    }

    @Override
    public void dispose() {
        laserSound.dispose();
    }
}
