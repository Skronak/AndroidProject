package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.input.CameraDragProcessor;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.ScrollingBackground;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

/**
 * Created by Skronak on 29/01/2017.
 */
public class LevelScreen extends AbstractScreen {

    private GameManager gameManager;
    private ScrollingBackground scrollingBackground;
    private Hud hud;

    public LevelScreen(TapDungeonGame tapDungeonGame) {
        super(tapDungeonGame);
        this.gameManager=tapDungeonGame.gameManager;
        this.hud = tapDungeonGame.hud;
    }

    @Override
    public void show() {
        stage.getRoot().getColor().a = 0;
        stage.getRoot().addAction(fadeIn(0.5f));

        //Input
        Image image1 =new Image(new Texture(Gdx.files.internal("sprites/background/levelSelect1.png")));
        Image image2 =new Image(new Texture(Gdx.files.internal("sprites/background/levelSelect1.png")));
        image2.setPosition(image1.getWidth(),0);
        Image image3 =new Image(new Texture(Gdx.files.internal("sprites/background/levelSelect1.png")));
        image3.setPosition(image2.getX()+image2.getWidth(),0);
        Image image4 =new Image(new Texture(Gdx.files.internal("sprites/background/levelSelect1.png")));
        image4.setPosition(image3.getX()+image3.getWidth(),0);

        scrollingBackground = new ScrollingBackground("sprites/background/cl.png");

        stage.addActor(image1);
        stage.addActor(image2);
        stage.addActor(image3);
        stage.addActor(image4);
        stage.addActor(scrollingBackground);

        CameraDragProcessor cameraDragProcessor = new CameraDragProcessor(this);
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(cameraDragProcessor);
        inputMultiplexer.addProcessor(hud.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        stage.act();
        stage.draw();
        hud.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.B)) {
            gameManager.spriterPlayer.setAnimation("spec_1");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.zoom+=0.1f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.zoom -= 0.1f;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-1f,0f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(1f,0f);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
        Gdx.app.log("PlayScreen","dispose");
        spriteBatch.dispose();
    }
}
