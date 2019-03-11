package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Generic actor containing a particle emitter
 */
abstract class AbstractParticleEffectActor extends Actor {
    public ParticleEffect particleEffect;
    float PARTICLE_SYSTEM_STEP = 1/60f;
    float PARTICLE_SYSTEM_MAX_STEP = 0.25f;

    public AbstractParticleEffectActor() {
        super();
        this.particleEffect =  new ParticleEffect();
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

    public void disableCompletion(){
    }

    public boolean isComplete(){
        return particleEffect.isComplete();
    }

    public void stop(){
        particleEffect.getEmitters().get(0).allowCompletion();
    }

}