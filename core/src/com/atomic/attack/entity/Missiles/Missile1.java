package com.atomic.attack.entity.Missiles;

import com.badlogic.gdx.math.Vector2;

public class Missile1 extends Missile {
    public Missile1(Vector2 pos) {
        super(com.atomic.attack.Assets.instance.missile.missile1,pos, com.atomic.attack.UpgradeManager.instance.getCurrentMissileSpeed(), com.atomic.attack.UpgradeManager.instance.getCurrentMissileDamage());

    }

}
