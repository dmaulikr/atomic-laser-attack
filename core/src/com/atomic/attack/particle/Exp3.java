package com.atomic.attack.particle;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Exp3 extends Particle {

    private static final float frameDuration = 1/20f;
    private static final Array<TextureAtlas.AtlasRegion> textures = com.atomic.attack.Assets.instance.particle.exp3;
    private final Sound sound = com.atomic.attack.Assets.instance.particle.exp4Sound;

    public Exp3(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        super(frameDuration, textures, pos, MathUtils.random(-45f, 45f), entityManager);
        scaleX = 1.2f;
        scaleY = 1.2f;
        sound.play(0.15f);
    }

}
