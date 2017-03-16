package com.atomic.attack.entity.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class SpinnerTyrian extends com.atomic.attack.entity.FollowPathEnemy {

    private static final Array<TextureAtlas.AtlasRegion> regs = com.atomic.attack.Assets.instance.enemy.spinner;
    private Animation animation;
    private static final float frameDuration = 1/30f;

    private float timePassed = 0;

    public SpinnerTyrian(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(pos, entityManager, regs.first());
        animation = new Animation(frameDuration, regs);
        scoreVal = 5;
        HP = 4;

        maxLinearSpeed = 100;

    }

    public void updateMain(float dt) {
        timePassed += dt;
        reg = animation.getKeyFrame(timePassed, true);
    }

    @Override
    public void activate() {
        createPath(Path(this.pos, entityManager.player.getPos()), 20f, 0f);
        active = true;
    }

    private Array<Vector2> Path(Vector2 pos, Vector2 playerPos) {
        Array<Vector2> wayPoints = new Array<Vector2>(3);

        wayPoints.add(new Vector2(pos.x, pos.y));
        wayPoints.add(new Vector2(playerPos.x, playerPos.y + entityManager.mapCamera.getCamSpeed() * 20 * 60 * com.atomic.attack.screen.ScreenManager.getCurrentSreen().dt));
        wayPoints.add(new Vector2(com.atomic.attack.MainGame.WIDTH / 2, pos.y - com.atomic.attack.MainGame.HEIGHT * 2));

        return wayPoints;
    }

    @Override
    public void onDestruction() {
        entityManager.particles.add(new com.atomic.attack.particle.Exp3(new Vector2(pos.x + this.getMiddleX(), pos.y + this.getMiddleY()), entityManager));
        entityManager.removeEntity(this);
    }

}

