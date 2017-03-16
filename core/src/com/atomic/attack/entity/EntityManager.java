package com.atomic.attack.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.atomic.attack.camera.ScreenShake;
import com.atomic.attack.entity.Missiles.Missile;
import com.atomic.attack.entity.Missiles.Missile2;
import com.atomic.attack.entity.Missiles.Missile3;
import com.atomic.attack.entity.Missiles.Missile4;
import com.atomic.attack.entity.Missiles.Missile5;
import com.atomic.attack.particle.Particle;
import com.atomic.attack.screen.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private static final String TAG = com.atomic.attack.Assets.class.getSimpleName();
    private final List<com.atomic.attack.entity.Entity> entities = new ArrayList<com.atomic.attack.entity.Entity>();
    public final List<Particle> particles = new ArrayList<Particle>();
    public final Player player;
    public com.atomic.attack.camera.OrthoCamera mapCamera;
    public com.atomic.attack.camera.OrthoCamera statCamera;
    private GameScreen screen;

    public ScreenShake screenShake;

    private boolean checkingCollisions = true;

    private final float playerHitCooldownTime = 0.1f; //Seconds

    public EntityManager(com.atomic.attack.camera.OrthoCamera mapCamera, com.atomic.attack.camera.OrthoCamera statCamera, GameScreen screen, Vector2 playerPos, ScreenShake screenShake) {
        player = new Player(playerPos, this, mapCamera);
        this.mapCamera = mapCamera;
        this.screen = screen;
        this.statCamera = statCamera;
        this.screenShake = screenShake;
    }

    public void update(float dt) {
        for(int i = 0; i < entities.size(); i++) {
            com.atomic.attack.entity.Entity e = entities.get(i);
            if (e.isActive()) {
                e.update(dt);
                if (e.checkBottom(mapCamera)) {
                    entities.remove(e); //Kai pasiekia apacia
                }
            }
        }

        //Gdx.app.debug(TAG, "Entities active: " + entities.size);
        for(Missile m : getMissiles()) {
            if (m.checkTop(mapCamera)) {
                entities.remove(m);
            }
        }
        for(Enemy e : getEnemies()) {
            if (!e.checkTop(mapCamera) && !e.active) {
                e.activate();
            }

        }
        player.update(dt);
        checkCollilsions();
    }

    public void render(SpriteBatch sb) {
        for(com.atomic.attack.entity.Entity e : entities) {
            if (e instanceof DestroyedObject || e instanceof StaticEnemy) {
                if (e.isActive()) {
                    e.render(sb);
                }
            }
        }
        for(com.atomic.attack.entity.Entity e : entities) {
            if (!(e instanceof DestroyedObject) && !(e instanceof StaticEnemy)) {
                if (e.isActive()) {
                    e.render(sb);
                }
            }
        }
        player.render(sb);

        for(int i = 0; i < particles.size(); i++) {
            Particle p = particles.get(i);
            p.render(sb);
            if (p.isAnimationFinished()) {
                particles.remove(p);
            }
        }

    }

    public void statRender(SpriteBatch sb) {
        for(Enemy e : getEnemies()) {
            if (e instanceof DrawableHealthBar) {
                if (e.isActive()) {
                    com.atomic.attack.entity.HealthBar hb = ((DrawableHealthBar) e).getHealtBar();
                    hb.render(sb);
                }
            }
        }

        player.getHealtBar().render(sb);

    }

    public void renderEntityShadow(SpriteBatch sb) {
        for(com.atomic.attack.entity.Entity e : entities) {
            if (e.isActive()) {
                e.renderShadow(sb);
            }
        }
        player.renderShadow(sb);
    }

    private boolean checkDeath(Enemy e) {
        if (e.isDead()) {
            e.onDestruction();
            com.atomic.attack.MainGame.score += e.getScoreVal();

            return true;
        }
        return false;
    }

    private void checkCollilsions() {
        if (checkingCollisions) {
            for(Enemy e : getEnemies()) {
                if (e.isActive()) {
                    for(Missile m : getMissiles()) {
                        if(Intersector.overlapConvexPolygons(e.getCollisionPolygon(), m.getCollisionPolygon()) && e.isActive()) {         //If Missile collides with Enemy
                            e.removeHP(m.damage);
                            //e.setColor(com.badlogic.gdx.graphics.Color.SCARLET, Constants.HIT_BLINK_TIME);
                            m.onDestruction(this, e);
                            entities.remove(m); //Removes Missile

                            if (checkDeath(e)) break;
                        }
                    }
                    if (!(e instanceof StaticEnemy)) {
                        if (Intersector.overlapConvexPolygons(e.getCollisionPolygon(), player.getCollisionPolygon())) {
                            playerHitByEnemy(e);
                        }
                    }
                }
            }
            for(com.atomic.attack.entity.Projectiles.Projectile p : getProjectiles()) {
                if (Intersector.overlapConvexPolygons(p.getCollisionPolygon(), player.getCollisionPolygon())) {
                    playerHitByProjectile(p);
                }
            }
        }
    }

    private void playerHitByProjectile(com.atomic.attack.entity.Projectiles.Projectile p) {
        player.getHit(p.damage);
        if (player.isDead()) playerDied();
        entities.remove(p);
    }

    public void removeEntity(com.atomic.attack.entity.Entity e) {
        entities.remove(e);
    }

    private void playerHitByEnemy(Enemy e) {
        Vector2 hitDirection = findVectorToPlayerMiddle(e.getPosMiddle());
        hitDirection.nor();
        player.direction.add(hitDirection.cpy().scl(com.atomic.attack.UpgradeManager.instance.getCurrentThrusterSpeed()));

        if (!player.onCooldown) {
            Gdx.input.vibrate(50);

            player.getHit(1);
            e.removeHP(2);
            checkDeath(e);

            if (player.isDead()) {
                playerDied();
            }

            player.onCooldown = true;

            Timer.schedule(new Timer.Task() {
                public void run() {
                    player.onCooldown = false;
                }
            }, playerHitCooldownTime);
        }

    }

    public void playerDied() {
        if (checkingCollisions) {
            com.atomic.attack.MainGame.tracker.sendEvent(com.atomic.attack.Constants.PARAM_LEVEL_ID, screen.level_Name, com.atomic.attack.Constants.EVENT_PLAYERDEATH + "_" + screen.getClass().getSimpleName());
            changeScreenDelta(0.5f);
            checkingCollisions = false;
            screen.screenShake.shake(5f, 3f);

            final EntityManager em = this;

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    particles.add(new com.atomic.attack.particle.Exp4(new Vector2(
                            MathUtils.random(player.pos.x, player.pos.x + player.getWidth()), MathUtils.random(player.pos.y, player.pos.y + player.getHeight())), em));
                }
            }, 0, 0.15f, 20);

            boolean showAds = MathUtils.randomBoolean(1 / 3f);
            com.atomic.attack.MusicManager.instance.fade(3);
            com.atomic.attack.screen.ScreenManager.transitionScreen(new com.atomic.attack.screen.menus.LevelSelectMenu(), 0.3f, 3f, Interpolation.exp5In, showAds);

        }
    }

    public void changeScreenDelta(float modifier) {
        screen.deltaTimeScale = modifier;
    }

    public void levelWon() {
        if (checkingCollisions) {
            changeScreenDelta(0.5f);
            com.atomic.attack.MainGame.tracker.sendEvent(com.atomic.attack.Constants.PARAM_LEVEL_ID, screen.getClass().getSimpleName(), com.atomic.attack.Constants.EVENT_LEVEL_WON + "_" + screen.getClass().getSimpleName());
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    com.atomic.attack.screen.ScreenManager.transitionScreen(new com.atomic.attack.screen.menus.LevelSelectMenu(), 0.3f, 3f, Interpolation.exp5In, true);   //Shows ads
                }
            }, 3);
            com.atomic.attack.MusicManager.instance.fade(6);
            checkingCollisions = false;
        }
    }

    private Array<Enemy> getEnemies() {
        Array<Enemy> ret = new Array<Enemy>();
        for(com.atomic.attack.entity.Entity e : entities) {
            if(e instanceof Enemy) {
                ret.add((Enemy) e);
            }
        }
        return ret;
    }

    private Array<Missile> getMissiles() {
        Array<Missile> ret = new Array<Missile>();
        for(com.atomic.attack.entity.Entity e : entities) {
            if(e instanceof Missile) {
                ret.add((Missile)e);
            }
        }
        return ret;
    }

    private Array<com.atomic.attack.entity.Projectiles.Projectile> getProjectiles() {
        Array<com.atomic.attack.entity.Projectiles.Projectile> ret = new Array<com.atomic.attack.entity.Projectiles.Projectile>();
        for(com.atomic.attack.entity.Entity e : entities) {
            if(e instanceof com.atomic.attack.entity.Projectiles.Projectile) {
                ret.add((com.atomic.attack.entity.Projectiles.Projectile)e);
            }
        }
        return ret;
    }


    public Vector2 findVectorToPlayerMiddle(Vector2 pos) {
        Vector2 playerPos = player.getPosMiddle();
        float a = pos.x - playerPos.x;
        float b = pos.y - playerPos.y;

        return new Vector2(-1 * a, -1 * b);
    }

    public Vector2 findVectorToPlayerPos(Vector2 pos) {
        Vector2 playerPos = player.getPos();
        float a = pos.x - playerPos.x;
        float b = pos.y - playerPos.y;

        return new Vector2(-1 * a, -1 * b);
    }

    public void addMissile() {
        switch(com.atomic.attack.UpgradeManager.instance.getCurrentMissile()) {
            case 1: addEntity(new com.atomic.attack.entity.Missiles.Missile1(new Vector2(player.pos.x, player.pos.y + player.getHeight() + player.mouseOffset)));
                    addEntity(new com.atomic.attack.entity.Missiles.Missile1(new Vector2(player.pos.x + player.getWidth() - com.atomic.attack.Assets.instance.missile.missile1.getRegionWidth(),
                            player.pos.y + player.getHeight() + player.mouseOffset)));
                break;

            case 2: addEntity(new Missile2(new Vector2(player.pos.x,
                    player.pos.y + player.getHeight() + player.mouseOffset)));
                addEntity(new Missile2(new Vector2(player.pos.x + player.getWidth() - com.atomic.attack.Assets.instance.missile.missile2.getRegionWidth(),
                        player.pos.y + player.getHeight() + player.mouseOffset)));
                break;

            case 3: addEntity(new Missile3(new Vector2(player.pos.x,
                    player.pos.y + player.getHeight() + player.mouseOffset)));
                addEntity(new Missile3(new Vector2(player.pos.x + player.getWidth() - com.atomic.attack.Assets.instance.missile.missile3.getRegionWidth(),
                        player.pos.y + player.getHeight() + player.mouseOffset)));
                break;

            case 4: addEntity(new Missile4(new Vector2(player.pos.x,
                    player.pos.y + player.getHeight() + player.mouseOffset)));
                addEntity(new Missile4(new Vector2(player.pos.x + player.getWidth() - com.atomic.attack.Assets.instance.missile.missile4.getRegionWidth(),
                        player.pos.y + player.getHeight() + player.mouseOffset)));
                addEntity(new Missile4(new Vector2(player.pos.x + player.getWidth() / 2 - com.atomic.attack.Assets.instance.missile.missile4.getRegionWidth() / 2,
                        player.pos.y + player.getHeight() + player.mouseOffset + 5f)));
                break;

            case 5: addEntity(new Missile5(new Vector2(player.pos.x,
                    player.pos.y + player.getHeight() + player.mouseOffset)));
                addEntity(new Missile5(new Vector2(player.pos.x + player.getWidth() - com.atomic.attack.Assets.instance.missile.missile5.getRegionWidth(),
                        player.pos.y + player.getHeight() + player.mouseOffset)));
                break;

        }
    }

    public void addEntities(Array<com.atomic.attack.entity.Entity> entities) {
        for (com.atomic.attack.entity.Entity e : entities) {
            addEntity(e);
        }
    }

    public void addEntity(com.atomic.attack.entity.Entity entity) {
        entities.add(entity);
    }

    public void renderCollisionPolygons(ShapeRenderer shapeRenderer) {
        for (com.atomic.attack.entity.Entity e : entities) {
            shapeRenderer.polygon(e.getCollisionPolygon().getTransformedVertices());
        }
        shapeRenderer.polygon(player.getCollisionPolygon().getTransformedVertices());
    }

    public void updateCollisionPolygons() {
        for (com.atomic.attack.entity.Entity e : entities) {
            e.updateCollisionPolygon();
        }
    }

    public com.atomic.attack.screen.Screen getScreen() {
        return screen;
    }

    public void dispose() {
        for (com.atomic.attack.entity.Entity e : entities) {
            if (e instanceof Disposable) {
                ((Disposable) e).dispose();
            }
        }

        for (Particle p : particles) {
            if (p instanceof Disposable) {
                ((Disposable) p).dispose();
            }
        }

    }
}
