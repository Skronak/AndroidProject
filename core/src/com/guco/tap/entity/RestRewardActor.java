package com.guco.tap.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import java.util.List;

/**
 * Created by Skronak on 15/08/2017.
 */

public class RestRewardActor extends Actor {
    private Animation openAnimation;
    private Array<TextureRegion> frames;
    private float deltatime;
    private int width;
    private int height;
    private List<ModuleElement> upgradeList;
    private float animSpeed;
    private TextureRegion currentFrame;
    private TextureRegion textureRegion;
    private boolean isTouched;

    public RestRewardActor () {
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/reward/chest1.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/reward/chest2.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/reward/chest3.png"))));
        openAnimation = new Animation(animSpeed, frames);
        openAnimation.setPlayMode(Animation.PlayMode.NORMAL);


    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        if (isTouched) {
            if (!openAnimation.isAnimationFinished(deltatime)) {
                super.draw(batch, parentAlpha);
                currentFrame = (TextureRegion) openAnimation.getKeyFrame(deltatime, true);
                batch.draw(currentFrame, getX(), getY(), width, height);
            }
        }
    }

    @Override
    public void act(float deltaTime)
    {
        super.act(deltaTime);
        deltatime += deltaTime;
    }

    public boolean isTouched() {
        return isTouched;
    }

    public void setTouched(boolean touched) {
        isTouched = touched;
    }
}
