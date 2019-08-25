package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
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
import com.guco.tap.actor.TapActor;
import com.guco.tap.actor.TorchActor;
import com.guco.tap.effect.TorchParticleSEffect;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.input.TapInputProcessor;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Gdx.files;

/**
 * Created by Skronak on 29/01/2017.
 */
public class PlayScreen extends AbstractScreen {

    private Random random;
    private int textAnimMinX;
    private com.guco.tap.utils.BitmapFontGenerator generator;
    private Image backgroundImage;
    private Image doorImage, torchImage;
    public Group layer0GraphicObject; // Background
    public Group layer1GraphicObject; // Objects
    public Group layer2GraphicObject; // Foreground
    public Label damageLabel;
    private int[] damageLabelPosition = {100,80,120,70,130};
    int gLPPointer;
    private TapActor tapActor;
    private AnimatedActor rewardActor;
    public InputMultiplexer inputMultiplexer;
    public TorchParticleSEffect torchParticleSEffect;
    Drawer<Sprite> playerDrawer,enemyDrawer;
    public SpriterPlayer spriterPlayer;
    SpriterPlayer boss;
    GameManager gameManager;
    // Enemy present on screen
    public List<EnemyActor> enemyActorList;


    /**
     * Constructor
     * @param tapDungeonGame
     */
    public PlayScreen(TapDungeonGame tapDungeonGame) {
        super(tapDungeonGame);
        this.gameManager=tapDungeonGame.gameManager;
        this.hud = tapDungeonGame.hud;
        layer0GraphicObject = new Group();
        layer1GraphicObject = new Group();
        layer2GraphicObject = new Group();
    }

    @Override
    public void show() {
        textAnimMinX =100;
        random = new Random();


        gameManager.initialiseGame();

        //tapActor
        tapActor = new TapActor();

        Texture backgroundTexture = new Texture(files.internal("sprites/background/dg_background.png"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundImage = new Image(backgroundTexture);

        backgroundImage.setSize(410,Constants.V_HEIGHT+15);
        backgroundImage.setPosition(-90,-20);

        Texture doorTexture= new Texture(files.internal("sprites/background/dg_door.png"));
        doorImage = new Image(doorTexture);
        doorImage.setSize(backgroundImage.getWidth(),backgroundImage.getHeight());
        doorImage.setPosition(backgroundImage.getX(),backgroundImage.getY());

        EnemyActor enemyActor = gameManager.enemyActorQueue.get(0);
        enemyActor.setPosition(130,220);

        EnemyActor nextEnemyActor = gameManager.enemyActorQueue.get(1);
        nextEnemyActor.setPosition(190,235);
        nextEnemyActor.setColor(Color.BLACK);

        EnemyActor hiddenEnemyActor = gameManager.enemyActorQueue.get(2);
        hiddenEnemyActor.setPosition(220,235);
        hiddenEnemyActor.getColor().a=0f;

        enemyActorList = new ArrayList<EnemyActor>();
        enemyActorList.add(enemyActor);
        enemyActorList.add(nextEnemyActor);
        enemyActorList.add(hiddenEnemyActor);

        // Gestion des calques
        stage.addActor(layer0GraphicObject);
        stage.addActor(layer1GraphicObject);
        stage.addActor(layer2GraphicObject);

        // Init torch
        TorchActor torchActor = new TorchActor(gameManager.ressourceManager);
        torchActor.start();

        // Ajout des objets dans les calques
        layer0GraphicObject.addActor(backgroundImage);
        layer0GraphicObject.addActor(torchActor);
        layer1GraphicObject.addActor(enemyActorList.get(2));
        layer1GraphicObject.addActor(enemyActorList.get(1));
        layer1GraphicObject.addActor(enemyActorList.get(0));
        layer2GraphicObject.addActor(doorImage);
        layer2GraphicObject.addActor(tapActor);
        hud.update();

        //if (gameManager.isFirstPlay()) {
        //    displayTutorial();
        //}

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
        cameraZoomOrigin = camera.zoom;
        cameraZoomTarget = newZoom;
        timeToCameraZoomTarget = cameraZoomDuration = duration;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        gameManager.updateLogic(delta);
        hud.updateGoldLabel();
        //boss.update();
        debugMode(delta);
        stage.act();
        stage.draw();

        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        if (gameManager.currentState.equals(GameState.IN_GAME)) {
            spriterPlayer.update();
            playerDrawer.draw(spriterPlayer);
        }
        spriteBatch.end();
        hud.draw();
    }

    /**
     * Affiche image la ou lecran est touche
     * @param positionX
     * @param positionY
     */
    public void showTapActor(int positionX, int positionY) {
        Vector3 position2World = camera.unproject(new Vector3(positionX, positionY,0));
        tapActor.setPosition(position2World.x- ((int)tapActor.getWidth()/2),( (int) position2World.y-tapActor.getHeight()/2));//TODO a calculer autrepart

        tapActor.animate();
    }

    public void debugMode(float delta){
        if (timeToCameraZoomTarget > 0){
            timeToCameraZoomTarget -= delta;
            float progress = timeToCameraZoomTarget < 0 ? 1 : 1f - timeToCameraZoomTarget / cameraZoomDuration;
            camera.zoom = Interpolation.pow3Out.apply(cameraZoomOrigin, cameraZoomTarget, progress);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.B)) {
            gameManager.spriterPlayer.setAnimation("spec_1");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            zoomTo(1,3);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            zoomTo(2,3);        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-1f,0f);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(1f,0f);
        }

    }

    public void showDamageLabel(String damage) {
        damageLabel = new Label(damage,new Label.LabelStyle(gameManager.ressourceManager.getFont(), Constants.NORMAL_LABEL_COLOR));
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

    /**
     * Animation du jeu au touche critique
     * @param value: valeur du critique
     */
    public void processCriticalHit(float value) {
        hud.animateCritical();

        damageLabel.setText("CRITICAL "+String.valueOf(value));
        damageLabel.setColor(Constants.CRITICAL_LABEL_COLOR);
    }

    /**
     * Animation du jeu au touche normal
     */
    public void processNormalHit() {
    }

    //TODO: a terminer
    //afficher tuto en surimpression
    private void displayTutorial(){
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
        return camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************

    public GameManager getGameManager() {
        return gameManager;
    }

    public Hud getHud() {
        return hud;
    }

    public Group getLayer1GraphicObject() {
        return layer1GraphicObject;
    }

    public Image getBackgroundImage() {
        return backgroundImage;
    }


    public Group getLayer2GraphicObject() {
        return layer2GraphicObject;
    }
}
