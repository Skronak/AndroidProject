package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.utils.Constants;

import java.util.Random;

public abstract class AbstractScreen implements Screen {
    protected Viewport viewport;
    protected Hud hud;
    protected SpriteBatch spriteBatch;
    protected TapDungeonGame tapDungeonGame;

    public Stage stage;
    public OrthographicCamera camera;

    public AbstractScreen (TapDungeonGame tapDungeonGame){
        this.tapDungeonGame = tapDungeonGame;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.V_WIDTH, Constants.V_HEIGHT);
        viewport = new StretchViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
        stage = new Stage(viewport, spriteBatch);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    public void addPlayer(){

    }

    public void addEnemy(){

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
