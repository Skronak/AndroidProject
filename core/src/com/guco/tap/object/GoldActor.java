package com.guco.tap.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.guco.tap.Animation.AnimatedActor;

public class GoldActor extends AnimatedActor {

    public GoldActor(float posX, float posY) {
        super(20, 20);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/object/gold_coin_1.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/object/gold_coin_2.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/object/gold_coin_3.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/object/gold_coin_4.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/object/gold_coin_5.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/object/gold_coin_6.png"))));
        Animation idleAnimation = new Animation(0.1f,frames);
        setAnimation(idleAnimation);

        setPosition(posX, posY);
    }
}
