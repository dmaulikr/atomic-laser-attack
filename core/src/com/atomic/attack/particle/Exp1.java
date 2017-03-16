package com.atomic.attack.particle;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.atomic.attack.Assets;
import com.atomic.attack.entity.EntityManager;

public class Exp1 extends Particle {

    private static final float frameDuration = 1/20f;
    private static final Array<TextureAtlas.AtlasRegion> textures = Assets.instance.particle.exp1;
    private final Sound sound = Assets.instance.particle.exp4Sound;

    public Exp1(Vector2 pos, EntityManager entityManager) {
        super(frameDuration, textures, pos, MathUtils.random(0f, 360f), entityManager);
        scaleX = 1.5f;
        scaleY = 1.5f;
        sound.play(0.2f);
    }


}
