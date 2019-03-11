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
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;

public class PlayerActor extends Actor {
    Drawer<Sprite> drawer;
    LibGdxLoader loader;
    Player player;
    ShapeRenderer renderer;

    public PlayerActor(SpriteBatch spriteBatch) {
        FileHandle handle = Gdx.files.internal("spriter/animation.scml");
        Data data = new SCMLReader(handle.read()).getData();
        ShapeRenderer renderer = new ShapeRenderer();
        loader = new LibGdxLoader(data);
        loader.load(handle.file());
        drawer = new LibGdxDrawer(loader, spriteBatch, renderer);
        player = new Player(data.getEntity(0));
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        player.update();
        drawer.draw(player);
    }

    public void setPosition(int posX, int posY) {
        player.setPosition(posX, posY);
    }

    public void setScale(float scale) {
        player.setScale(scale);
    }

}
