package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ParticleEffectActor extends Actor {
    public ParticleEffect particleEffect;

    public ParticleEffectActor(){
        super();
    }

    public ParticleEffectActor(ParticleEffect particleEffect) {
        super();
        this.particleEffect = particleEffect;
    }

    @Override
    public void draw (Batch spriteBatch, float delta) {
        particleEffect.draw(spriteBatch, Gdx.graphics.getDeltaTime());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        particleEffect.update(delta);
    }

    public void start() {
        particleEffect.start();
    }

    public void allowCompletion() {
        particleEffect.allowCompletion();
    }

    public void stop(){
        particleEffect.getEmitters().get(0).allowCompletion();
    }

}