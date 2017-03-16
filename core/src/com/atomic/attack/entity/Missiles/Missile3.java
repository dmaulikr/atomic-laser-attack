package com.atomic.attack.entity.Missiles;

import com.badlogic.gdx.math.Vector2;

public class Missile3 extends Missile {

    public Missile3(Vector2 pos) {
        super(com.atomic.attack.Assets.instance.missile.missile3, pos, com.atomic.attack.UpgradeManager.instance.getCurrentMissileSpeed(), com.atomic.attack.UpgradeManager.instance.getCurrentMissileDamage());

    }

}
