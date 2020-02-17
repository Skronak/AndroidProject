package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.action.ScaleLabelAction;
import com.guco.tap.actor.AnimatedActor;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.effect.TorchParticleSEffect;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.input.TapInputProcessor;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;

import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Gdx.files;

/**
 * Created by Skronak on 29/01/2017.
 */
public class AreaScreen extends AbstractScreen {

    private Random random;
    private int textAnimMinX;
    private com.guco.tap.utils.BitmapFontGenerator generator;
    public Image backgroundImage;
    private Image doorImage, torchImage;
    public Group layer0GraphicObject; // Background
    public Group layer1GraphicObject; // Objects
    public Group layer2GraphicObject; // Foreground
    public Label damageLabel;
    private int[] damageLabelPosition = {100,80,120,70,130};
    int gLPPointer;
    private AnimatedActor rewardActor;
    public InputMultiplexer inputMultiplexer;
    public TorchParticleSEffect torchParticleSEffect;
    Drawer<Sprite> playerDrawer,enemyDrawer;
    public SpriterPlayer spriterPlayer;
    SpriterPlayer boss;
    GameManager gameManager;
    // EnemyTemplateEntity present on screen
    public List<EnemyActor> enemyActorList;

    public AreaScreen(TapDungeonGame tapDungeonGame) {
        super(tapDungeonGame);
        gameManager = tapDungeonGame.gameManager;
        hud = tapDungeonGame.hud;
        layer0GraphicObject = new Group();
        layer1GraphicObject = new Group();
        layer2GraphicObject = new Group();
    }

    @Override
    public void show() {
        textAnimMinX =100;
        random = new Random();

        gameManager.initialiseGame();

        Texture backgroundTexture = new Texture(files.internal("sprites/background/dg_background.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundImage = new Image(backgroundTexture);

        Texture doorTexture= new Texture(files.internal("sprites/background/dg_door.png"));
        Image backgroundImage = new Image();
        doorImage = new Image(doorTexture);
        doorImage.setSize(backgroundImage.getWidth(),backgroundImage.getHeight());
        doorImage.setPosition(backgroundImage.getX(),backgroundImage.getY());

        stage.addActor(layer0GraphicObject);
        stage.addActor(layer1GraphicObject);
        stage.addActor(layer2GraphicObject);

        // Ajout des objets dans les calques
        layer0GraphicObject.addActor(backgroundImage);
        layer1GraphicObject.addActor(enemyActorList.get(2));
        layer1GraphicObject.addActor(enemyActorList.get(1));
        layer1GraphicObject.addActor(enemyActorList.get(0));
        layer2GraphicObject.addActor(doorImage);
        layer2GraphicObject.addActor(tapActor);
        hud.update();

        TapInputProcessor inputProcessor = new TapInputProcessor(gameManager);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hud.stage);
        inputMultiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        spriterPlayer =gameManager.loadPlayer();
        boss=gameManager.loadBoss();

        playerDrawer = gameManager.loadPlayerDrawer(spriteBatch);
        enemyDrawer = gameManager.loadBossDrawer(spriteBatch);
    }

    // member variables:
    float timeToCameraZoomTarget, cameraZoomTarget, cameraZoomOrigin, cameraZoomDuration;

    public void zoomTo (float newZoom, float duration){
        cameraZoomOrigin = ((OrthographicCamera)viewport.getCamera()).zoom;
        cameraZoomTarget = newZoom;
        timeToCameraZoomTarget = cameraZoomDuration = duration;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameManager.updateLogic(delta);
        hud.updateGoldLabel();
        debugMode(delta);
        stage.act();
        stage.draw();

        spriteBatch.begin();
        if (gameManager.currentState.equals(GameState.IN_GAME)||gameManager.currentState.equals(GameState.PAUSE)) {
            spriterPlayer.update();
            playerDrawer.draw(spriterPlayer);
        } else {
            hud.draw();
        }
        spriteBatch.end();
        hud.draw();
    }

    public void debugMode(float delta){
        if(Gdx.input.isKeyPressed(Input.Keys.B)) {
            gameManager.spriterPlayer.setAnimation("spec_1");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            zoomTo(1,3);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            zoomTo(2,3);        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            viewport.getCamera().translate(-1f,0f,0f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            viewport.getCamera().translate(1f,0f,0f);
        }
    }

    public void showDamageLabel(String damage) {
        damageLabel = new Label(damage,new Label.LabelStyle(gameManager.assetsManager.getFont(), Constants.NORMAL_LABEL_COLOR));
        damageLabel.setPosition(enemyActorList.get(0).getX()+enemyActorList.get(0).getWidth()/2,enemyActorList.get(0).getY()+enemyActorList.get(0).getHeight()/2);
        if (gLPPointer< damageLabelPosition.length-1){
            gLPPointer++;
        } else {
            gLPPointer=0;
        }
        layer2GraphicObject.addActor(damageLabel);
        damageLabel.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.parallel(
                    Actions.fadeIn(1f)
                ),
                Actions.fadeOut(2f),
                Actions.removeActor(damageLabel)
        ));
        damageLabel.addAction(Actions.parallel(
                Actions.moveTo(150+random.nextInt(100+textAnimMinX)-textAnimMinX,Constants.V_HEIGHT,4f),
                ScaleLabelAction.action(damageLabel,5f,2f,Interpolation.linear)
        ));
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.debug("PlayScreen", "Resize occured w"+width+" h"+height);
        viewport.update(width, height);
        hud.resize(width, height);
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
        Gdx.app.debug("PlayScreen","dispose");
        spriteBatch.dispose();
        hud.dispose();
        Gdx.app.debug("PlayScreen","saveData");
    }

    public Vector3 getMousePosInGameWorld() {
        return viewport.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************

    public GameManager getGameManager() {
        return gameManager;
    }
}
