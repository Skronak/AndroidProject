package com.guco.tap.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.guco.tap.Animation.AnimatedBaseActor;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.utils.Constants;

public class CharacterAnimatedActor extends Group {

    private AnimatedBaseActor body;
    private AnimatedBaseActor weapon;
    private AnimatedBaseActor head;

    private final String ATK_ANIM="atk";
    private final String IDLE_ANIM="idle";
    private final String IDLE_BODY_ANIM_PREFIX="idle_b";
    private final String IDLE_HEAD_ANIM_PREFIX="idle_h";
    private final String IDLE_WEAP_ANIM_PREFIX="idle_w";
    private final String ATK_BODY_ANIM_PREFIX="atk_b";
    private final String ATK_HEAD_ANIM_PREFIX="atk_h";
    private final String ATK_WEAP_ANIM_PREFIX="atk_w";
    private int anim_width;
    private int anim_height;

    public CharacterAnimatedActor(int width, int height) {
        this.anim_width=width;
        this.anim_height=height;
        init();
    }

    public void init() {
        body = new AnimatedBaseActor();
        Animation idleAnimationB = initAnimation(IDLE_BODY_ANIM_PREFIX+GameInformation.INSTANCE.characterEquipedList.get(Constants.BODY_ID),3,0.5f);
        Animation atkAnimationB = initAnimation(ATK_BODY_ANIM_PREFIX+GameInformation.INSTANCE.characterEquipedList.get(Constants.BODY_ID),3,0.08f);

        body.storeAnimation(IDLE_ANIM, idleAnimationB);
        body.storeAnimation(ATK_ANIM, atkAnimationB);
        body.setSize(anim_width, anim_height);

        head = new AnimatedBaseActor();
        Animation idleAnimationH = initAnimation(IDLE_HEAD_ANIM_PREFIX+GameInformation.INSTANCE.characterEquipedList.get(Constants.HEAD_ID),3,0.5f);
        Animation atkAnimationH = initAnimation(ATK_HEAD_ANIM_PREFIX+GameInformation.INSTANCE.characterEquipedList.get(Constants.HEAD_ID),3,0.08f);

        head.storeAnimation(IDLE_ANIM, idleAnimationH);
        head.storeAnimation(ATK_ANIM, atkAnimationH);
        head.setSize(anim_width, anim_height);

        weapon = new AnimatedBaseActor();
        Animation idleAnimationW = initAnimation(IDLE_WEAP_ANIM_PREFIX+GameInformation.INSTANCE.characterEquipedList.get(Constants.WEAPON_ID),3,0.5f);
        Animation atkAnimationW = initAnimation(ATK_WEAP_ANIM_PREFIX+GameInformation.INSTANCE.characterEquipedList.get(Constants.WEAPON_ID),3,0.08f);

        weapon.storeAnimation(IDLE_ANIM, idleAnimationW);
        weapon.storeAnimation(ATK_ANIM, atkAnimationW);
        weapon.setSize(anim_width, anim_height);

        this.addActor(body);
        this.addActor(head);
        this.addActor(weapon);
    }

    public Animation initAnimation(String name, int nbSprites, float duration) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        try {
            for (int i=1;i<=nbSprites;i++) {
                frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/character/" + name +"_"+ i + ".png"))));
            }
        } catch (GdxRuntimeException exception) {
            Gdx.app.error(this.getClass().getSimpleName()+":"+exception.getStackTrace()[0].getLineNumber(),exception.getMessage());
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/error.png"))));
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

    public void playIdle(){
        head.setActiveAnimation(IDLE_ANIM);
        head.startAnimation();

        body.setActiveAnimation(IDLE_ANIM);
        body.startAnimation();

        weapon.setActiveAnimation(IDLE_ANIM);
        weapon.startAnimation();
    }

    public void switchBody(int id){
        GameInformation.INSTANCE.characterEquipedList.set(Constants.BODY_ID, id);
        Animation idleAnimationB = initAnimation(IDLE_BODY_ANIM_PREFIX+id,3,0.5f);
        body.storeAnimation(IDLE_ANIM, idleAnimationB);
        playIdle();
    }

    public void switchHead(int id){
        GameInformation.INSTANCE.characterEquipedList.set(Constants.HEAD_ID, id);
        Animation headAnimation = initAnimation(IDLE_HEAD_ANIM_PREFIX+id,4,0.5f);
        head.storeAnimation(IDLE_ANIM, headAnimation);
        playIdle();
    }

    public void switchWeapon(int id){
        GameInformation.INSTANCE.characterEquipedList.set(Constants.WEAPON_ID, id);
        Animation weapAnimation = initAnimation(IDLE_WEAP_ANIM_PREFIX+id,4,0.5f);
        weapon.storeAnimation(IDLE_ANIM, weapAnimation);
        playIdle();
    }
}
