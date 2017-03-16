package com.atomic.attack.particle;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.atomic.attack.Assets;
import com.atomic.attack.entity.EntityManager;

public class Exp2 extends Particle {

    private static final float frameDuration = 1/30f;
    private static final Array<TextureAtlas.AtlasRegion> textures = Assets.instance.particle.exp2;
    private final Sound sound = Assets.instance.particle.exp2Sound;

    public Exp2(Vector2 pos, EntityManager entityManager) {
        super(frameDuration, textures, pos, MathUtils.random(0f, 360f), entityManager);
        sound.play(0.08f);
    }

}
