package com.guco.tap.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.LibGdxDrawer;
import com.brashmonkey.spriter.LibGdxLoader;
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.SpriterPlayer;

public class PlayerActor extends Actor {
    Drawer<Sprite> drawer;
    LibGdxLoader loader;
    public SpriterPlayer spriterPlayer;

    public PlayerActor(SpriteBatch spriteBatch, String animationPath) {
        FileHandle handle = Gdx.files.internal(animationPath);
        Data data = new SCMLReader(handle.read()).getData();
        ShapeRenderer renderer = new ShapeRenderer();
        loader = new LibGdxLoader(data);
        loader.load(handle.file());
        drawer = new LibGdxDrawer(loader, spriteBatch, renderer);
        spriterPlayer = new SpriterPlayer(data.getEntity(0));
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        spriterPlayer.update();
        drawer.draw(spriterPlayer);
    }

    public void setPosition(int posX, int posY) {
        spriterPlayer.setPosition(posX, posY);
    }

    public void setScale(float scale) {
        spriterPlayer.setScale(scale);
    }

    public void setAnimation(String animation) {
        spriterPlayer.setAnimation(animation);
    }
 }
