package com.guco.tap.input;

import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.actor.SpriterActor;

public class EffectActorListenerImpl implements SpriterPlayer.PlayerListener {

    SpriterActor spriterActorParent;

    public EffectActorListenerImpl(SpriterActor spriterActorParent) {
        this.spriterActorParent = spriterActorParent;
    }

    @Override
    public void animationFinished(Animation animation) {
        spriterActorParent.setVisible(false);
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
    }
}
