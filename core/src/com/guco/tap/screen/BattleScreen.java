package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.action.BlinkAction;
import com.guco.tap.action.CameraMoveToAction;
import com.guco.tap.action.ScaleLabelAction;
import com.guco.tap.actor.SpriterActor;
import com.guco.tap.actor.SpriterEnemyActor;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.input.TapInputProcessor;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;

import java.util.Random;

import static com.badlogic.gdx.Gdx.files;

public class BattleScreen extends AbstractScreen {

    private Random random;
    private int textAnimMinX;
    private Image backgroundImage, doorImage;
    float timeToCameraZoomTarget, cameraZoomTarget, cameraZoomOrigin, cameraZoomDuration;
    public Group layerBackground, layerEnemy, layerFrontObjects;
    public Label damageLabel;
    private int[] damageLabelPosition = {100, 80, 120, 70, 130};
    int gLPPointer;
    public InputMultiplexer inputMultiplexer;
    GameManager gameManager;

    public SpriterActor player;
    public SpriterActor effectActor;
    public SpriterEnemyActor currentEnemy;
    public SpriterEnemyActor enemyInShadow;

    public BattleScreen(TapDungeonGame tapDungeonGame) {
        super(tapDungeonGame);
        this.gameManager = tapDungeonGame.gameManager;
        this.hud = tapDungeonGame.hud;

        layerBackground = new Group();
        layerEnemy = new Group();
        layerFrontObjects = new Group();

        Texture backgroundTexture = new Texture(files.internal("sprites/badlogic.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(410, Constants.V_HEIGHT + 15);
        backgroundImage.setPosition(-90, -20);

        Texture doorTexture = new Texture(files.internal("sprites/background/dg_door.png"));
        doorImage = new Image(doorTexture);
        doorImage.setSize(backgroundImage.getWidth(), backgroundImage.getHeight());
        doorImage.setPosition(backgroundImage.getX(), backgroundImage.getY());
    }

    @Override
    public void show() {
        textAnimMinX = 100;
        random = new Random();

        player = gameManager.loadPlayer(spriteBatch);
        effectActor = gameManager.loadEffect(spriteBatch);

        hud.initializeHud();
        hud.update();

        gameManager.initialiseGame(spriteBatch);

        // Gestion des calques
        stage.addActor(layerBackground);
        stage.addActor(layerEnemy);
        stage.addActor(layerFrontObjects);

        currentEnemy = gameManager.getCurrentEnemy();
//        enemyInShadow = gameManager.getEnemyInShadow();

        // Ajout des objets dans les calques
        layerBackground.addActor(backgroundImage);
//        layerBackground.addActor(torchActor);
//        layerEnemy.addActor(enemyActorList.get(0));
        layerFrontObjects.addActor(doorImage);
        layerFrontObjects.addActor(currentEnemy);

        //      layerEnemy.addActor(enemyInShadow);
        layerFrontObjects.addActor(player);
        layerFrontObjects.addActor(effectActor);

        TapInputProcessor inputProcessor = new TapInputProcessor(gameManager);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hud.stage);
        inputMultiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        initStartScreen();
    }

    public void swapEnemy() {

//        waitingEnemyActors.get(0).clearActions();
//        waitingEnemyActors.get(1).clearActions();
//        waitingEnemyActors.get(0).setPosition(130, 220);
//        waitingEnemyActors.get(1).setPosition(190, 235);
//
//        waitingEnemyActors.get(1).setActiveAnimation("idle");
//
//        waitingEnemyActors.get(0).addAction(Actions.sequence(Actions.fadeOut(1f), Actions.moveTo(220, 235)));
//        waitingEnemyActors.get(1).addAction(Actions.parallel(Actions.moveTo(130, 220, 1f), Actions.color(Color.WHITE, 1f)));
//
//        // Change order of enemy on screen (0: current, 1: visible, 2: swap
//        Collections.swap(waitingEnemyActors, 0, 1);
//        if (waitingEnemyActors.size()>2) {
//            waitingEnemyActors.get(2).clearActions();
//            waitingEnemyActors.get(2).setPosition(220, 235);
//            waitingEnemyActors.get(2).getColor().a = 0f;
//            waitingEnemyActors.get(2).setActiveAnimation("idle");
//            waitingEnemyActors.get(2).addAction(Actions.parallel(Actions.moveTo(190, 235, 1f), fadeIn(3f), Actions.color(Color.BLACK)));
//
//            Collections.swap(waitingEnemyActors, 1, 2);
//        }
//
//        // first actor always on top
//        layerEnemy.swapActor(waitingEnemyActors.get(0), waitingEnemyActors.get(1));
    }

    public void initStartScreen() {
        final Label titleLabel = new Label("TAP DUNGEON", game.assetsManager.getSkin());
        titleLabel.setFontScale(2);
        titleLabel.setPosition(Constants.V_WIDTH / 2 - titleLabel.getWidth(), Constants.V_HEIGHT - Constants.V_HEIGHT / 6);
        final Label startLabel = new Label("TAP TO START", game.assetsManager.getSkin());
        startLabel.setFontScale(2);
        startLabel.setPosition(Constants.V_WIDTH / 2 - startLabel.getWidth(), Constants.V_HEIGHT / 6);
        startLabel.addAction(Actions.forever(new BlinkAction(3, 1)));

        hud.stage.addActor(titleLabel);
        hud.stage.addActor(startLabel);

        gameManager.currentState = GameState.PAUSE;
        camera.zoom -= 0.5;
        camera.translate(-70, 0);
        hud.setVisible(false);

        hud.stage.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                titleLabel.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        titleLabel.remove();
                    }
                })));
                startLabel.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        startLabel.remove();
                    }
                })));
                stage.addAction(Actions.sequence(Actions.parallel(
                        CameraMoveToAction.action(camera, 3f, camera.position.x + 70, camera.position.y, 0f, Interpolation.exp10In),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                zoomTo(1f, 3f);
                            }
                        })),
                        Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                gameManager.currentState = GameState.IN_GAME;
                            }
                        })));
                hud.stage.removeListener(this);
                hud.setVisible(true);
                return true;
            }
        });
    }

    public void changeBackround(TextureRegionDrawable backgroundDrawable) {
        backgroundImage.setDrawable(backgroundDrawable);
    }

    public void zoomTo(float newZoom, float duration) {
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

        stage.act();
        stage.draw();

//        spriteBatch.begin();
        debugMode(delta);

//        if (gameManager.currentState.equals(GameState.IN_GAME) || gameManager.currentState.equals(GameState.PAUSE)) {
//            spriterPlayer.update();
//            playerDrawer.draw(spriterPlayer);
//        } else {
//            hud.draw();
//        }

        hud.draw();
//        spriteBatch.end();
    }


    public void debugMode(float delta) {
        if (timeToCameraZoomTarget > 0) {
            timeToCameraZoomTarget -= delta;
            float progress = timeToCameraZoomTarget < 0 ? 1 : 1f - timeToCameraZoomTarget / cameraZoomDuration;
            camera.zoom = Interpolation.pow3Out.apply(cameraZoomOrigin, cameraZoomTarget, progress);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.B)) {
            gameManager.spriterPlayer.setAnimation("spec_1");
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            zoomTo(1, 3);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            zoomTo(2, 3);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-1f, 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(1f, 0f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gameManager.increaseAreaLevel();
        }
    }

    public void addDamageLabel(String damage) {
        damageLabel = new Label(damage, new Label.LabelStyle(gameManager.assetsManager.getFont(), Constants.NORMAL_LABEL_COLOR));
        damageLabel.setPosition(currentEnemy.getPosition().x + currentEnemy.getWidth() / 2, currentEnemy.getPosition().y + currentEnemy.getHeight() / 2);
        if (gLPPointer < damageLabelPosition.length - 1) {
            gLPPointer++;
        } else {
            gLPPointer = 0;
        }
        layerFrontObjects.addActor(damageLabel);
        damageLabel.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.parallel(
                        Actions.fadeIn(1f)
                ),
                Actions.fadeOut(2f),
                Actions.removeActor(damageLabel)
        ));
        damageLabel.addAction(Actions.parallel(
                Actions.moveTo(150 + random.nextInt(100 + textAnimMinX) - textAnimMinX, Constants.V_HEIGHT, 4f),
                ScaleLabelAction.action(damageLabel, 5f, 2f, Interpolation.linear)
        ));
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.debug("PlayScreen", "Resize occured w" + width + " h" + height);
        viewport.update(width, height);
        hud.resize(width, height);
    }

    @Override
    public void dispose() {
        Gdx.app.debug("PlayScreen", "dispose");
        spriteBatch.dispose();
        hud.dispose();
        Gdx.app.debug("PlayScreen", "saveData");
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************

    public Hud getHud() {
        return hud;
    }
}
