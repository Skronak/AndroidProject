package com.guco.tap.Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;

/**
 * Created by Skronak on 09/02/2017.
 */
public class AnimatedBaseActor extends BaseActor
{
    private float elapsedTime;
    private Animation<TextureRegion> activeAnim;
    private String activeName;
    private HashMap<String,Animation> animationStorage;

    private boolean pauseAnim;

    public AnimatedBaseActor()
    {
        super();
        elapsedTime = 0;
        activeAnim = null;
        activeName = null;
        animationStorage = new HashMap<String,Animation>();
        pauseAnim = false;
    }

    public void storeAnimation(String name, Animation anim)
    {
        animationStorage.put(name, anim);
        if (activeName == null)
            setActiveAnimation(name);
    }

    public void storeAnimation(String name, Texture tex)
    {
        TextureRegion reg = new TextureRegion(tex);
        TextureRegion[] frames = { reg };
        Animation<TextureRegion> anim = new Animation<TextureRegion>(1.0f, frames);
        storeAnimation(name, anim);
    }

    public void setActiveAnimation(String name)
    {
        if ( !animationStorage.containsKey(name) )
        {
            Gdx.app.log("AnimatedBaseActor","No animation: " + name);
            return;
        }

        // if this animation is already playing; no need to change...
//        if ( name.equals(activeName) )
//            return;

        activeName = name;
        activeAnim = animationStorage.get(name);
        elapsedTime = 0;

        // if width or height not set, then set them...
        if ( getWidth() == 0 || getHeight() == 0 )
        {
            Texture tex = activeAnim.getKeyFrame(0).getTexture();
            setWidth( tex.getWidth() );
            setHeight( tex.getHeight() );
        }
    }

    public String getAnimationName()
    {
        return activeName;
    }

    public Animation getCurrentAnimation(){
        return activeAnim;
    }

    public void pauseAnimation()
    {  pauseAnim = true;  }

    public void startAnimation()
    {  pauseAnim = false;  }

    public void restartAnimation(){
        if (animationStorage.get(getAnimationName()).isAnimationFinished(elapsedTime)) {
            elapsedTime = 0f;
        }
        startAnimation();
    }

    public void setAnimationFrame(int n)
    {  elapsedTime = n * activeAnim.getFrameDuration();  }

    public void act(float dt)
    {
        super.act( dt );
        if (!pauseAnim)
            elapsedTime += dt;
    }

    public void draw(Batch batch, float parentAlpha)
    {
        region.setRegion( activeAnim.getKeyFrame(elapsedTime) );
        super.draw(batch, parentAlpha);
        if (!activeName.equals("death") && animationStorage.get(getAnimationName()).isAnimationFinished(elapsedTime)) {
            setActiveAnimation("idle");
        }
    }

    public void copy(AnimatedBaseActor original)
    {
        super.copy(original);
        this.elapsedTime = 0;
        this.animationStorage = original.animationStorage; // sharing a reference
        this.activeName = new String(original.activeName);
        this.activeAnim = this.animationStorage.get( this.activeName );
    }

    public AnimatedBaseActor clone()
    {
        AnimatedBaseActor newbie = new AnimatedBaseActor();
        newbie.copy( this );
        return newbie;
    }
}