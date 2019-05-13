package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.utils.Constants;

/**
 * Created by Skronak on 15/07/2017.
 */

public class SplashScreen implements Screen {

    private Texture texture=new Texture(Gdx.files.internal("sprites/badlogic.jpg"));
    private Image splashImage=new Image(texture);
    private TapDungeonGame game;
//    private LoadingScreen loadingScreen;
    private Camera camera = new OrthographicCamera(Constants.V_WIDTH, Constants.V_HEIGHT);
    private FitViewport viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
    private Stage stage = new Stage(viewport);

    public SplashScreen(TapDungeonGame game)
    {
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");
        this.game=game;
    }

    @Override
    public void show() {
        stage.addActor(splashImage);
        splashImage.setPosition(Constants.V_WIDTH/2-splashImage.getWidth()/2,Constants.V_HEIGHT/2-splashImage.getHeight()/2);
        splashImage.addAction(Actions.sequence(Actions.alpha(0)
                ,Actions.fadeIn(2.0f),Actions.delay(0.5f)));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

        // load LoadingScreen if animation is finished
        if (splashImage.getActions().size==0) {
            loadLoadingScreen();
        }
    }

    /**
     * loadLoadingScreen if animation is finished
     */
    private void loadLoadingScreen() {
        splashImage.addAction(Actions.sequence(Actions.fadeOut(2.0f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.setScreen(game.loadingScreen);
            }
        })));
   }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
    @Override
    public void pause() {
    }
    @Override
    public void resume() {
    }
    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        texture.dispose();
        stage.dispose();
    }
}
