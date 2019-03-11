package com.guco.tap.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public abstract class AbstractGameEffect
        implements Disposable
{
    public float duration;
    public float startingDuration;
    protected Color color;
    public boolean isDone = false;
    protected float scale = 1;
    protected float rotation = 0.0F;
    public boolean renderBehind = false;

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < this.startingDuration / 2.0F) {
            this.color.a = (this.duration / (this.startingDuration / 2.0F));
        }
        if (this.duration < 0.0F)
        {
            this.isDone = true;
            this.color.a = 0.0F;
        }
    }

    public abstract void render(SpriteBatch paramSpriteBatch);

    public void render(SpriteBatch sb, float x, float y) {}

    public abstract void dispose();
}
