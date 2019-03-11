package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.guco.tap.screen.PlayScreen;

/**
 * Created by Skronak on 23/08/2017.
 */

public class RainEffectActor extends AbstractParticleEffectActor {

    private PlayScreen playScreen;

    public RainEffectActor(PlayScreen screen){
        super();
        playScreen = screen;

        particleEffect.load(Gdx.files.internal("particles/rain.party"),Gdx.files.internal("particles"));
        particleEffect.getEmitters().first().setPosition(0, Constants.V_HEIGHT);
        particleEffect.scaleEffect(0.3f);

        this.stop();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
