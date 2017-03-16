package com.atomic.attack.entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

public abstract class Enemy extends Entity {

    protected int HP = 0;           //To be changed in subclasses
    protected int scoreVal = 0;
    public boolean active = false;
    protected com.atomic.attack.entity.EntityManager entityManager;
    protected com.badlogic.gdx.graphics.Color batchColor = com.badlogic.gdx.graphics.Color.WHITE;

    public Enemy(TextureRegion reg, Vector2 pos, Vector2 direction, com.atomic.attack.entity.EntityManager entityManager) {
        super(reg);
        this.pos = pos.add(-getMiddleX(), -getMiddleY());
        this.direction = direction;
        this.entityManager = entityManager;
        hasShadow = true;
    }

    public Enemy(TextureRegion reg, Vector2 pos, com.atomic.attack.entity.EntityManager entityManager) {
        this(reg, pos, new Vector2(0f, 0f), entityManager);
        hasShadow = false;
    }

    public void removeHP(int HP) {
        this.HP -= HP;
    }

    public boolean isDead() {
        if (HP <= 0) return true;
        else return false;
    }

    @Override
    public void render(SpriteBatch sb) {
        //sb.setColor(batchColor);
        sb.draw(reg, pos.x, pos.y, getMiddleX(), getMiddleY(),
                getWidth(), getHeight(), scaleX, scaleY, angle);
        //sb.setColor(com.badlogic.gdx.graphics.Color.WHITE);
    }

    public void activate() {
        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public int getScoreVal() {
        return scoreVal;
    }

    public void onDestruction() {
        entityManager.particles.add(new com.atomic.attack.particle.Exp4(new Vector2(pos.x + this.getMiddleX(), pos.y + this.getMiddleY()), entityManager));
        entityManager.removeEntity(this);
    }

    public void setColor(com.badlogic.gdx.graphics.Color color, float time) {
        //this.batchColor = color;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                batchColor = com.badlogic.gdx.graphics.Color.WHITE;
            }
        }, time);

    }
}
