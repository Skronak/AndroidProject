package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.guco.tap.screen.PlayScreen;

/**
 * Created by Skronak on 23/08/2017.
 */

public class FlamEffectActor extends AbstractParticleEffectActor {
    public FlamEffectActor(float x, float y){
        super();
        particleEffect.load(Gdx.files.internal("particles/torch.party"),Gdx.files.internal("particles"));
        particleEffect.getEmitters().first().setPosition(x, y);
        particleEffect.scaleEffect(0.5f);
        this.stop();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
