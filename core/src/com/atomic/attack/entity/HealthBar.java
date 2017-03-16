package com.atomic.attack.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class HealthBar {

    float startingHP;
    float HP, widthOffset = 50, heightOffset = 20;

    protected TextureRegion reg1 = com.atomic.attack.Assets.instance.boss.healthBar1;
    protected TextureRegion reg2 = com.atomic.attack.Assets.instance.boss.healthBar2;

    public HealthBar(float HP) {
        this.startingHP = HP;
        this.HP = HP;

    }

    public void update(float HP) {
        this.HP = HP;
    }

    public void render(SpriteBatch sb) {

        sb.draw(reg1, com.atomic.attack.MainGame.WIDTH / 2 - widthOffset, com.atomic.attack.MainGame.HEIGHT - heightOffset, widthOffset * 2, heightOffset - 10);
        sb.draw(reg2, com.atomic.attack.MainGame.WIDTH / 2 + widthOffset, com.atomic.attack.MainGame.HEIGHT - heightOffset, Math.min(0, (-widthOffset * 2)  * (HP / startingHP)), heightOffset - 10);
    }

}
