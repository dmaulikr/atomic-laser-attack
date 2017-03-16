package com.atomic.attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

public class GameFont {
    public BitmapFont font;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public GameFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("dpcomic.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.shadowColor = Color.BLACK;
        parameter.shadowOffsetX = 2;
        parameter.shadowOffsetY = 2;
        parameter.minFilter = Texture.TextureFilter.Nearest;
        parameter.magFilter = Texture.TextureFilter.Nearest;

        font = generator.generateFont(parameter);
    }

    public GameFont() {
        this(32);
    }

    public void draw(SpriteBatch sb, String text, Vector2 pos) {
        font.draw(sb, text, pos.x, pos.y);

    }

    public float getHeight() {
        return parameter.size;
    }

}
