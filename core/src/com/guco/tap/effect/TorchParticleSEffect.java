package com.guco.tap.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TorchParticleSEffect
        extends AbstractGameEffect
{
    private float x;
    private float y;
    private float vY;
    private TextureRegion img;
    public static boolean renderGreen = false;
    private float scale=3f;
    TextureRegion TORCH_FIRE_1,TORCH_FIRE_2,TORCH_FIRE_3;


    public TorchParticleSEffect(float x, float y)
    {
        TORCH_FIRE_1=new TextureRegion(new Texture(Gdx.files.internal("particles/TORCH0.png")));
        TORCH_FIRE_2=new TextureRegion(new Texture(Gdx.files.internal("particles/TORCH1.png")));
        TORCH_FIRE_3=new TextureRegion(new Texture(Gdx.files.internal("particles/TORCH2.png")));
        this.duration = MathUtils.random(1.5F, 3.0F);
        this.startingDuration = this.duration;
        this.img = getImg();
        this.x = (x - this.img.getRegionWidth() / 2 + MathUtils.random(-3.0F, 3.0F) * scale);
        this.y = (y - this.img.getRegionHeight() / 2);
        this.scale = (scale * MathUtils.random(0.5F, 1.0F));
        this.vY = (MathUtils.random(1.0F, 10.0F) * scale);
        this.vY *= this.vY;
        this.rotation = MathUtils.random(-20.0F, 20.0F);
        if (!renderGreen) {
            this.color = new Color(MathUtils.random(0.6F, 1.0F), MathUtils.random(0.3F, 0.6F), MathUtils.random(0.0F, 0.3F), 0.01F);
        } else {
            this.color = new Color(MathUtils.random(0.1F, 0.3F), MathUtils.random(0.5F, 0.9F), MathUtils.random(0.1F, 0.3F), 0.01F);
        }
        this.renderBehind = true;
    }

    private TextureRegion getImg()
    {
        switch (MathUtils.random(0, 2))
        {
            case 0:
                return TORCH_FIRE_1;
            case 1:
                return TORCH_FIRE_2;
        }
        return TORCH_FIRE_3;
    }

    public void update()
    {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
        this.color.a = Interpolation.fade.apply(0.0F, 0.75F, this.duration / this.startingDuration);
        this.y += this.vY * Gdx.graphics.getDeltaTime();
    }

    public void render(SpriteBatch sb)
    {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(this.img, this.x, this.y, this.img.getRegionWidth() / 2.0F, this.img.getRegionHeight()/ 2.0F, this.img.getRegionWidth(), this.img.getRegionHeight(), this.scale, this.scale, this.rotation);

        sb.setBlendFunction(770, 771);
    }

    public void dispose() {}
}