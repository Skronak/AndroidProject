package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guco.tap.actor.TapActor;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.utils.Constants;

public abstract class AbstractScreen implements Screen {
    protected Viewport viewport;
    protected Hud hud;
    protected SpriteBatch spriteBatch;
    protected TapDungeonGame tapDungeonGame;
    public Stage stage;
    protected TapActor tapActor;
    protected OrthographicCamera camera;

    public AbstractScreen (TapDungeonGame tapDungeonGame){
        this.tapDungeonGame = tapDungeonGame;
        spriteBatch = new SpriteBatch();
        viewport = new StretchViewport(Constants.V_WIDTH, Constants.V_HEIGHT);
        viewport.apply(true);
        stage = new Stage(viewport, spriteBatch);
        camera = (OrthographicCamera) stage.getCamera();
        tapActor = new TapActor();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
    }

    /**
     * Affiche image la ou lecran est touche
     * @param positionX
     * @param positionY
     */
    public void showTapActor(int positionX, int positionY) {
        Vector3 position2World = stage.getCamera().unproject(new Vector3(positionX, positionY,0));
        tapActor.setPosition(position2World.x- ((int)tapActor.getWidth()/2),( (int) position2World.y-tapActor.getHeight()/2));//TODO a calculer autrepart
        tapActor.animate();
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
