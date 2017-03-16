package com.atomic.attack.entity.Missiles;

import com.badlogic.gdx.math.Vector2;

public class Missile5 extends Missile {

    public Missile5(Vector2 pos) {
        super(com.atomic.attack.Assets.instance.missile.missile5,pos, com.atomic.attack.UpgradeManager.instance.getCurrentMissileSpeed(), com.atomic.attack.UpgradeManager.instance.getCurrentMissileDamage());

    }

}
