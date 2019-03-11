package com.guco.tap.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.guco.tap.Animation.AnimatedBaseActor;

public class CharacterActor extends Group {

    private AnimatedBaseActor body;
    private AnimatedBaseActor weapon;
    private AnimatedBaseActor head;

    private final String ATK_ANIM="atk";
    private final String IDLE_ANIM="idle";
    private final int ANIM_WIDTH = 270;
    private final int ANIM_HEIGHT = 200;

    public CharacterActor(){
        init();
    }

    public void init(){
        body = new AnimatedBaseActor();
        Animation idleAnimationB = initAnimation("body1_idle",3,0.5f);
        Animation atkAnimationB = initAnimation("body1_atk",3,0.08f);

        body.storeAnimation(IDLE_ANIM, idleAnimationB);
        body.storeAnimation(ATK_ANIM, atkAnimationB);
        body.setSize(ANIM_WIDTH, ANIM_HEIGHT);

        head = new AnimatedBaseActor();
        Animation idleAnimationH = initAnimation("body1_idle",3,0.5f);
        Animation atkAnimationH = initAnimation("head1_atk",3,0.08f);

        head.storeAnimation(IDLE_ANIM, idleAnimationH);
        head.storeAnimation(ATK_ANIM, atkAnimationH);
        head.setSize(ANIM_WIDTH, ANIM_HEIGHT);

        weapon = new AnimatedBaseActor();
        Animation idleAnimationW = initAnimation("body1_idle",3,0.5f);
        Animation atkAnimationW = initAnimation("sword2_atk",3,0.08f);
        weapon.storeAnimation(IDLE_ANIM, idleAnimationW);
        weapon.storeAnimation(ATK_ANIM, atkAnimationW);
        weapon.setSize(ANIM_WIDTH, ANIM_HEIGHT);

        this.addActor(weapon);
        this.addActor(body);
        this.addActor(head);

    }

    public Animation initAnimation(String name, int nbSprites, float duration) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        try {
            for (int i=1;i<=nbSprites;i++) {
                frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/character/" + name +"_"+ i + ".png"))));
            }
        } catch (GdxRuntimeException exception) {
            Gdx.app.error("CharacterActor",exception.getMessage());
        }
        Animation animation  = new Animation(duration, frames);
        animation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        return animation;
    }

    public void playAtk(){
        head.setActiveAnimation(ATK_ANIM);
        head.startAnimation();

        body.setActiveAnimation(ATK_ANIM);
        body.startAnimation();

        weapon.setActiveAnimation(ATK_ANIM);
        weapon.startAnimation();
    }

    public void switchBody(int id){
    }

    public void switchHead(int id){
    }

    public void switchRightArm(int id){
    }
}
