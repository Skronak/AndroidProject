package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.manager.AreaManager;
import com.guco.tap.manager.AssetsManager;
import com.guco.tap.manager.GameInformationManager;
import com.guco.tap.manager.GameManager;
import com.guco.tap.manager.ItemManager;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.LargeMath;

/**
 * Created by Skronak on 15/07/2017.
 */

public class LoadingScreen implements Screen {

    private TapDungeonGame game;
    private Camera camera = new OrthographicCamera(Constants.V_WIDTH, Constants.V_HEIGHT);
    private FitViewport viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
    private Stage stage = new Stage(viewport);
    private Image background;
    private boolean started;

    public LoadingScreen(TapDungeonGame game){
        Gdx.app.debug(this.getClass().getSimpleName(), "Instantiate");

        this.game=game;
    }

    @Override
    public void show() {
        background = new Image(new Texture(Gdx.files.internal("sprites/badlogic.jpg")));
        stage.addActor(background);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        Gdx.app.log("rrrrr","renderrr");
        if (!started) {
            started=true;
            load();
            background.addAction(Actions.sequence(Actions.fadeOut(2.0f), Actions.run(new Runnable() {
                @Override
                public void run() {
                    game.setScreen(game.battleScreen);
                }
            })));
        }
        Gdx.app.debug("Loading",String.valueOf(game.assetsManager.getLoadValue()));
    }

    /**
     * load everything while game is intiating
     */
    public void load() {
        game.largeMath = new LargeMath();
        game.itemManager = new ItemManager(game);
        game.assetsManager = new AssetsManager();
        game.areaManager = new AreaManager(game);
        game.gameInformationManager = new GameInformationManager(game.assetsManager, game.itemManager);

        game.sb = new SpriteBatch();
        game.assetsManager.loadAsset();
        game.gameInformationManager.loadGameData();
        game.gameInformation = game.gameInformationManager.gameInformation;
        game.gameManager = new GameManager(game);

        game.hud = new Hud(game);
        game.battleScreen = new BattleScreen(game);
        game.gameManager.battleScreen = game.battleScreen;
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
        stage.dispose();
    }
}
