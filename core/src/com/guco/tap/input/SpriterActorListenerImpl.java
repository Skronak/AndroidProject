package com.guco.tap.input;

import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.actor.SpriterActor;

public class SpriterActorListenerImpl implements SpriterPlayer.PlayerListener {

    public static final int ANIMATION_END_MARGIN = 3;
    private final SpriterActor spriterActor;

    public SpriterActorListenerImpl(SpriterActor spriterActor) {
        this.spriterActor = spriterActor;
    }

    @Override
    public void animationFinished(Animation animation) {
        spriterActor.spriterPlayer.animationFinished = true;
        spriterActor.spriterPlayer.setAnimation("idle");

        if (animation.name.equals("atk")) {
            spriterActor.isAttacking = false;
        }
    }

    @Override
    public void animationChanged(Animation oldAnim, Animation newAnim) {
    }

    @Override
    public void preProcess(SpriterPlayer spriterPlayer) {
    }

    @Override
    public void postProcess(SpriterPlayer spriterPlayer) {
    }

    @Override
    public void mainlineKeyChanged(Mainline.Key prevKey, Mainline.Key newKey) {
        if (spriterActor.spriterPlayer.getAnimation().name.equals("atk")) {
            if (newKey.time >= (spriterActor.spriterPlayer.getAnimation().length) / ANIMATION_END_MARGIN) {
                spriterActor.isAttacking = false;
            }
        }
    }
}