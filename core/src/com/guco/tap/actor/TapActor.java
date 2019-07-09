package com.guco.tap.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.Gdx.files;

public class TapActor extends AnimatedActor {

    public TapActor() {
        super(20,20);
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture(files.internal("sprites/tap/tap1.png"))));
        frames.add(new TextureRegion(new Texture(files.internal("sprites/tap/tap2.png"))));
        frames.add(new TextureRegion(new Texture(files.internal("sprites/tap/tap3.png"))));
        frames.add(new TextureRegion(new Texture(files.internal("sprites/tap/tap4.png"))));
        frames.add(new TextureRegion(new Texture(files.internal("sprites/tap/tap5.png"))));
        frames.add(new TextureRegion(new Texture(files.internal("sprites/tap/tap6.png"))));
        frames.add(new TextureRegion(new Texture(files.internal("sprites/tap/tap7.png"))));
        animation = new Animation(0.2f, frames, Animation.PlayMode.NORMAL);

        setVisible(false);
    }

}
