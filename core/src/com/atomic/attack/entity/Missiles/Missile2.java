package com.atomic.attack.entity.Missiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Missile2 extends Missile {
    private static final TextureRegion reg = com.atomic.attack.Assets.instance.missile.missile2;

    public Missile2(Vector2 pos) {
        super(reg, pos, com.atomic.attack.UpgradeManager.instance.getCurrentMissileSpeed(), com.atomic.attack.UpgradeManager.instance.getCurrentMissileDamage());

    }

}
