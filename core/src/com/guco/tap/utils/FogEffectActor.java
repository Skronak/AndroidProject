package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.guco.tap.screen.BattleScreen;

/**
 * Created by Skronak on 23/08/2017.
 */

public class FogEffectActor extends AbstractParticleEffectActor {

    private BattleScreen battleScreen;
    private Image fog;

    public FogEffectActor(BattleScreen screen){
        super();
        battleScreen = screen;

        particleEffect.load(Gdx.files.internal("particles/fog.party"),Gdx.files.internal("particles"));
        particleEffect.getEmitters().first().setPosition(Constants.V_WIDTH/2, Constants.V_HEIGHT/3);
        particleEffect.scaleEffect(0.5f);

        this.stop();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
