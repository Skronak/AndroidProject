package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.LibGdxDrawer;
import com.brashmonkey.spriter.LibGdxLoader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.PlayerListenerImpl;
import com.brashmonkey.spriter.SCMLReader;
import com.guco.tap.Animation.AnimatedActor;
import com.guco.tap.Animation.TapActor;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.actor.CharacterAnimatedActor;
import com.guco.tap.effect.TorchParticleSEffect;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.input.CustomInputProcessor;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.FlamEffectActor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Gdx.files;

/**
 * Created by Skronak on 29/01/2017.
 */
public class PlayScreen implements Screen {

    // common batch for screen/hud
    private SpriteBatch spriteBatch;
    private GameManager gameManager;
    private Random random;
    public Stage stage;
    private long lastTouch;
    private int consecutivTouch; // touche consecutives
    public OrthographicCamera camera;
    private Viewport viewport;
    private Hud hud;
    private int textAnimMinX;
    private com.guco.tap.utils.BitmapFontGenerator generator;
    private Image backgroundImage;
    private Image doorImage;
    private Group layer0GraphicObject = new Group(); // Background
    public Group layer1GraphicObject = new Group(); // Objects
    private Group layer2GraphicObject = new Group(); // Foreground
    private Label goldLabel;
    private int[] goldLabelPosition = {100,80,120,70,130};
    int gLPPointer;
    private TapActor tapActor;
    private AnimatedActor rewardActor;
    public InputMultiplexer inputMultiplexer;
    public TorchParticleSEffect torchParticleSEffect;
    Drawer<Sprite> drawer;
    LibGdxLoader loader;
    Player player;
    ShapeRenderer renderer;

    public CharacterAnimatedActor characterActor;
    // Enemy present on screen
    public List<EnemyActor> enemyActorList;
    ShaderProgram blurShader;
    Texture backgroundTexture;

    @Override
    public void show() {
        lastTouch = 0l;
        textAnimMinX =100;
        consecutivTouch=0;

        spriteBatch = new SpriteBatch();
        random = new Random();
        gameManager = new GameManager(this);
        camera = new OrthographicCamera(Constants.V_WIDTH, Constants.V_HEIGHT);
        viewport = new StretchViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
        stage = new Stage(viewport, spriteBatch);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        hud = new Hud(spriteBatch, gameManager, this);

        gameManager.initialiseGame();

        //tapActor
        tapActor = new TapActor();

        backgroundTexture = new Texture(files.internal("sprites/background/dg_background.png"));
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

        characterActor = gameManager.initializeCharacter();
        characterActor.setPosition(-25,220);

        // Gestion des calques
        stage.addActor(layer0GraphicObject);
        stage.addActor(layer1GraphicObject);
        stage.addActor(layer2GraphicObject);

        // Ajout des objets dans les calques
        layer0GraphicObject.addActor(backgroundImage);
        layer0GraphicObject.addActor(doorImage);
        layer1GraphicObject.addActor(enemyActorList.get(2));
        layer1GraphicObject.addActor(enemyActorList.get(1));
        layer1GraphicObject.addActor(enemyActorList.get(0));
        //layer2GraphicObject.addActor(characterActor);
        layer2GraphicObject.addActor(doorImage);
        hud.update();

        if (GameInformation.INSTANCE.isFirstPlay()) {
            displayTutorial();
        }

        CustomInputProcessor inputProcessor = new CustomInputProcessor(this);
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(hud.getStage());
        inputMultiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        torchParticleSEffect=new TorchParticleSEffect(200,200);
        FlamEffectActor flamEffectActor = new FlamEffectActor(150,350);
        flamEffectActor.start();
        stage.addActor(flamEffectActor);

        FileHandle handle = Gdx.files.internal("spriter/animation2.scml");
        Data data = new SCMLReader(handle.read()).getData();
        renderer = new ShapeRenderer();
        loader = new LibGdxLoader(data);
        loader.load(handle.file());
        drawer = new LibGdxDrawer(loader, spriteBatch, renderer);
        player = new Player(data.getEntity(0));
        player.setPosition(85,220);
        player.setScale(0.4f);
        player.setAnimation("withtag");
        player.addListener(new PlayerListenerImpl(player,this));
    }

    // member variables:
    float timeToCameraZoomTarget, cameraZoomTarget, cameraZoomOrigin, cameraZoomDuration;

    private void zoomTo (float newZoom, float duration){
        cameraZoomOrigin = camera.zoom;
        cameraZoomTarget = newZoom;
        timeToCameraZoomTarget = cameraZoomDuration = duration;
    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameManager.updateLogic(delta);

        hud.updateGoldLabel();
        stage.act();
        stage.draw();
        spriteBatch.setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.draw();

        player.update();

        spriteBatch.begin();
        drawer.draw(player);
        spriteBatch.end();

        //DEBUG
        //todo bloquer rezoom si deja zoom max, pareil max dezoom
        if (timeToCameraZoomTarget > 0) {
            timeToCameraZoomTarget -= delta;
            float progress = timeToCameraZoomTarget < 0 ? 1 : 1f - timeToCameraZoomTarget / cameraZoomDuration;
            camera.zoom = Interpolation.pow3Out.apply(cameraZoomOrigin, cameraZoomTarget, progress);
            // reajust y position
            if (cameraZoomTarget == 2) {
                camera.position.y = Interpolation.pow3Out.apply(285, 0, progress);
            } else {
                camera.position.y = Interpolation.pow3Out.apply(0, 285, progress);
            }
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

        spriteBatch.begin();
        torchParticleSEffect.update();
        torchParticleSEffect.render(spriteBatch);
        spriteBatch.end();

    }

    /**
     * Affiche image la ou lecran est touche
     * @param positionX
     * @param positionY
     */
    public void processPointerHitAnimation(int positionX, int positionY) {
        tapActor.clearActions();
        tapActor.setDeltatime(0);
        Vector3 position2World = camera.unproject(new Vector3(positionX, positionY,0));
        tapActor.setColor(Color.WHITE);
        tapActor.setPosition(position2World.x- ((int)tapActor.getWidth()/2),( (int) position2World.y-tapActor.getHeight()/2));//TODO a calculer autrepart
        tapActor.addAction(Actions.sequence(
                Actions.show(),
                Actions.fadeIn(0.5f),
                Actions.fadeOut(0.2f),
                Actions.hide()
        ));
    }

    /**
     * Animation du jeu au touche
     */
    public void processHit() {
        player.setAnimation("atk");
        characterActor.playAtk();
        gameManager.hurtEnemy();

        goldLabel = new Label(gameManager.largeMath.getDisplayValue(GameInformation.INSTANCE.getGenGoldActive(), GameInformation.INSTANCE.getGenCurrencyActive()),new Label.LabelStyle(AssetManager.INSTANCE.getFont(), Constants.NORMAL_LABEL_COLOR));
        goldLabel.setPosition(150,enemyActorList.get(0).getY()+enemyActorList.get(0).getHeight()/2);

        if (gLPPointer<goldLabelPosition.length-1){
            gLPPointer++;
        } else {
            gLPPointer=0;
        }
        layer2GraphicObject.addActor(goldLabel);
        goldLabel.addAction(Actions.sequence(
                Actions.fadeIn(1f),
                Actions.fadeOut(2f),
                Actions.removeActor(goldLabel)
        ));
        goldLabel.addAction(Actions.moveTo(150+random.nextInt(100+textAnimMinX)-textAnimMinX,Constants.V_HEIGHT,3f));

        // Augmente vitesse en fonction delai avec derniere touche
        if (System.currentTimeMillis()-lastTouch >= 500f) {
            //stationEntity.beamActor.decreaseSpeed(0.08f);
            consecutivTouch=0;
        } else {
            consecutivTouch++;
        }
        lastTouch=System.currentTimeMillis();

        if (consecutivTouch >= 10) {
        }
    }

    /**
     * Animation du jeu au touche critique
     * @param value: valeur du critique
     */
    public void processCriticalHit(float value) {
        hud.animateCritical();

        goldLabel.setText("CRITICAL "+String.valueOf(value));
        goldLabel.setColor(Constants.CRITICAL_LABEL_COLOR);
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
        Gdx.app.debug("PlayScreen","saveInformation");
        gameManager.largeMath.formatGameInformation();
        GameInformation.INSTANCE.saveInformation();
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

   // public StationEntity getStationEntity() {
   //     return stationEntity;
   // }

    public Image getBackgroundImage() {
        return backgroundImage;
    }


    public Group getLayer2GraphicObject() {
        return layer2GraphicObject;
    }
}
