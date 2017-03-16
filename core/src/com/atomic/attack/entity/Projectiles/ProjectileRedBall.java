package com.atomic.attack.entity.Projectiles;


import com.badlogic.gdx.math.Vector2;

public class ProjectileRedBall extends Projectile {
    public static final int damage = 1;
    private boolean trackable = false;
    private com.atomic.attack.camera.OrthoCamera camera;

    public ProjectileRedBall(Vector2 pos, Vector2 direction) {
        super(com.atomic.attack.Assets.instance.projectile.projectileRedBall2, pos, direction, damage);
    }

    public ProjectileRedBall(Vector2 pos, Vector2 direction, com.atomic.attack.camera.OrthoCamera camera) {
        this(pos, direction);
        trackable = true;
        this.camera = camera;
    }

    @Override
    public void update(float dt) {
        pos.add(direction.cpy().scl(dt * 60));

       if(trackable) pos.add(0, camera.getCamSpeed() * 60 * dt);
    }

}
