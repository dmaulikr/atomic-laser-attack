package com.atomic.attack.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class DestroyedObject extends Entity {

    public DestroyedObject(TextureRegion reg, Vector2 pos) {
        super(reg);
        this.pos = pos;
    }

    @Override
    public void update(float dt) {

    }


}
