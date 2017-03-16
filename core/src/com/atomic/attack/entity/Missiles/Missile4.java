package com.atomic.attack.entity.Missiles;

import com.badlogic.gdx.math.Vector2;

public class Missile4 extends Missile {

    public Missile4(Vector2 pos) {
        super(com.atomic.attack.Assets.instance.missile.missile4,pos, com.atomic.attack.UpgradeManager.instance.getCurrentMissileSpeed(), com.atomic.attack.UpgradeManager.instance.getCurrentMissileDamage());

    }

}
