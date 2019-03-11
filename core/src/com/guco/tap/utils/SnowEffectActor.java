package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.guco.tap.screen.PlayScreen;

public class SnowEffectActor extends AbstractParticleEffectActor {
    private Image snow1;
    private Image snow2;
    private Image snow3;
    private Image fog;
    private Image background;
    private PlayScreen playScreen;

    public SnowEffectActor(PlayScreen screen){
        super();
        playScreen = screen;

        particleEffect.load(Gdx.files.internal("particles/snow.party"),Gdx.files.internal("particles"));
        particleEffect.getEmitters().first().setPosition(0, Constants.V_HEIGHT);
        particleEffect.scaleEffect(0.3f);

        snow1 = new Image(new Texture(Gdx.files.internal("sprites/weather/snow_0_0.png")));
        snow2 = new Image(new Texture(Gdx.files.internal("sprites/weather/snow_0_0.png")));
        snow3 = new Image(new Texture(Gdx.files.internal("sprites/weather/snow_0_0.png")));
        fog = new Image(new Texture(Gdx.files.internal("sprites/weather/fog02.png")));
        background = new Image(new Texture(Gdx.files.internal("sprites/weather/rockValley2_snow.png")));
        this.stop();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        snow1.setPosition(playScreen.characterActor.getX(),playScreen.characterActor.getY());
        snow2.setPosition(playScreen.characterActor.getX(),playScreen.characterActor.getY());
        snow3.setPosition(playScreen.characterActor.getX(),playScreen.characterActor.getY());
    }

    @Override
    public void start() {
        super.start();
        snow1.setVisible(true);
        snow2.setVisible(true);
        snow3.setVisible(true);
        fog.setVisible(true);
        background.setVisible(true);

        snow1.setSize(playScreen.characterActor.getWidth(), playScreen.characterActor.getHeight());
        snow1.setPosition(playScreen.characterActor.getX(),playScreen.characterActor.getY());
        snow1.getColor().a=0f;
        snow2.setSize(playScreen.characterActor.getWidth(), playScreen.characterActor.getHeight());
        snow2.setPosition(playScreen.characterActor.getX(),playScreen.characterActor.getY());
        snow2.getColor().a=0f;
        snow3.setSize(playScreen.characterActor.getWidth(), playScreen.characterActor.getHeight());
        snow3.setPosition(playScreen.characterActor.getX(),playScreen.characterActor.getY());
        snow3.getColor().a=0f;
        fog.setPosition(0,50);
        fog.setScale(2);
        fog.getColor().a=0f;
        background.setPosition(playScreen.getBackgroundImage().getX(), playScreen.getBackgroundImage().getY());
        background.setSize(playScreen.getBackgroundImage().getWidth(), playScreen.getBackgroundImage().getHeight());
        background.setScale(playScreen.getBackgroundImage().getScaleX(),playScreen.getBackgroundImage().getScaleY());
        background.getColor().a=0f;

        playScreen.getLayer1GraphicObject().addActor(background);
        playScreen.getLayer1GraphicObject().addActor(snow1);
        playScreen.getLayer1GraphicObject().addActor(snow2);
        playScreen.getLayer1GraphicObject().addActor(snow3);
        playScreen.getLayer1GraphicObject().addActor(fog);
        fog.addAction(Actions.sequence(Actions.delay(5f),Actions.fadeIn(20f),Actions.delay(10f), Actions.fadeOut(10f),Actions.removeActor(fog)));
        background.addAction(Actions.sequence(Actions.delay(5f),Actions.fadeIn(20f),Actions.delay(10), Actions.fadeOut(10f),Actions.removeActor(background)));
        snow1.addAction(Actions.sequence(Actions.delay(5f),Actions.fadeIn(5f),Actions.delay(30f), Actions.fadeOut(5f),Actions.removeActor(snow1)));
        snow2.addAction(Actions.sequence(Actions.delay(10f),Actions.fadeIn(5f),Actions.delay(25f), Actions.fadeOut(5f),Actions.removeActor(snow2)));
        snow3.addAction(Actions.sequence(Actions.delay(15f),Actions.fadeIn(5f),Actions.delay(5f), Actions.fadeOut(5f),Actions.removeActor(snow3)));
    }

    @Override
    public boolean isComplete() {
        if (snow3.getActions().size == 0 && particleEffect.isComplete()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void stop(){
        super.stop();
        snow1.clearActions();
        snow2.clearActions();
        snow3.clearActions();
        fog.clearActions();
        background.clearActions();
        snow1.setVisible(false);
        snow2.setVisible(false);
        snow3.setVisible(false);
        fog.setVisible(false);
        background.setVisible(false);
    }
}
