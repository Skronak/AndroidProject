package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.guco.tap.screen.BattleScreen;

/**
 * Created by Skronak on 23/08/2017.
 */

public class RainEffectActor extends AbstractParticleEffectActor {

    private BattleScreen battleScreen;

    public RainEffectActor(BattleScreen screen){
        super();
        battleScreen = screen;

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
