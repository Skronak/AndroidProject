package com.guco.tap.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

// Take your **animation** created for your sprite batch and call this:
public class SimpleAnimatedActor extends Image {

    private float stateTime = 0;
    Animation animation;

    public SimpleAnimatedActor(Animation animation) {
        super((Texture) animation.getKeyFrame(0));
        this.animation = animation;
    }

    @Override
    public void act(float delta)
    {
//        ((TextureRegionDrawable).getDrawable()).setRegion(animation.getKeyFrame(stateTime+=delta, true));
        super.act(delta);
    }
}
/* Render with:
    // if you change direction you will need logic for myAnimatedActor.remove and stage.addActor(myAnimatedActor);
    animationFrames = walkSheetArray[moveDirection];
    animation = new Animation(1f / 5f, animationFrames);
    myAnimatedActor = new AnimatedActor(animation);
    // stage.addActor(myAnimatedActor);
    myAnimatedActor.setPosition(posX, posY);
    stage.act(Gdx.graphics.getDeltaTime());
    stage.draw();
*/


