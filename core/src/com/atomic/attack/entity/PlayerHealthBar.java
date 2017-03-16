package com.atomic.attack.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.atomic.attack.Assets;
import com.atomic.attack.MainGame;

public class PlayerHealthBar extends HealthBar {

    private final float posMod = 10;

    public PlayerHealthBar(float HP) {
        super(HP);
        reg1 = Assets.instance.boss.healthBar1;
        reg2 = Assets.instance.player.healthBarPlayer;
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.draw(reg1, MainGame.WIDTH - widthOffset * 2 - posMod, MainGame.HEIGHT - heightOffset, widthOffset * 2, heightOffset - posMod);
        sb.draw(reg2, MainGame.WIDTH - posMod , MainGame.HEIGHT - heightOffset, Math.min(0, (-widthOffset * 2)  * (HP / startingHP)), heightOffset - posMod);
    }
}
