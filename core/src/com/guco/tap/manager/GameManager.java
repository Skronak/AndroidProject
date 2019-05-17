package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.LibGdxDrawer;
import com.brashmonkey.spriter.LibGdxLoader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.guco.tap.action.CameraMoveToAction;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.listener.PlayerListenerImpl;
import com.guco.tap.object.GoldActor;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;
import com.guco.tap.utils.LargeMath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

/**
 * Created by Skronak on 30/07/2017.
 *
 * Classe de gestion globale du jeu
 * gerant le lien entre PlayScreen et GameInformation
 */
public class GameManager {

    public PlayScreen playScreen;

    public LargeMath largeMath;

    //public WeatherManager weatherManager;

    public ModuleManager moduleManager;

    public AchievementManager achievementManager;

    public RessourceManager ressourceManager;

    public float autoSaveTimer,weatherTimer, increaseGoldTimer, logicTimer;

    public ArrayList<Integer> newModuleIdList;

    // Enemy present on this floor
    public ArrayList<EnemyActor> enemyActorQueue;

    // Etat du jeu
    public GameState currentState;

    private EnemyActor currentEnemyActor;

    Random rand = new Random();

    public int nbMandatoryFight;

    public Data data;

    public Player player;

    TapDungeonGame game;

    public AssetManager assetManager;

    public GameInformationManager gameInformationManager;

    public GameInformation gameInformation;

    public GameManager(TapDungeonGame game) {
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");
        this.gameInformationManager = game.gameInformationManager;
        currentState = GameState.IN_GAME;
        this.assetManager=game.assetManager;
        this.gameInformation = game.gameInformation;
        largeMath = new LargeMath(gameInformation);
        newModuleIdList = new ArrayList<Integer>();
        //weatherManager = new WeatherManager(playScreen);
        moduleManager = new ModuleManager(this);
        achievementManager = new AchievementManager(this);
        ressourceManager = new RessourceManager(this);
        autoSaveTimer = 0f;
        increaseGoldTimer = 0f;
        weatherTimer = 0f;
        logicTimer = 0f;
        enemyActorQueue = new ArrayList<EnemyActor>();
    }

    public void initialiseGame() {
        gameInformation.currentEnemyIdx=0;
        //moduleManager.initialize(playScreen.getHud().getModuleMenu());
        initEnemyQueue();
        moduleManager.initialize(playScreen.getHud().getModuleMenu());

        currentEnemyActor = enemyActorQueue.get(gameInformation.currentEnemyIdx);
        playScreen.getHud().initEnemyInformation(currentEnemyActor);
        nbMandatoryFight=5; // from floor information
        playScreen.getHud().postInitMenu();
    }

    public Player loadPlayer(){
        int weaponMap=0;
        int headMap=1;
        int bodyMap=2;
        player = new Player(data.getEntity(0));
        player.setPosition(85,220);
        player.setScale(0.37f);
        player.speed=15;
        player.setAnimation("idle");
        player.addListener(new PlayerListenerImpl(player,playScreen));
        ;
        player.characterMaps[weaponMap]= player.getEntity().getCharacterMap(assetManager.weaponList.get(gameInformation.equipedWeapon).mapName);
        player.characterMaps[headMap]= player.getEntity().getCharacterMap(assetManager.helmList.get(gameInformation.equipedHead).mapName);
        player.characterMaps[bodyMap]= player.getEntity().getCharacterMap(assetManager.bodyList.get(gameInformation.equipedBody).mapName);
        return player;
    }

    public Player loadBoss(){
        FileHandle handle = Gdx.files.internal("spriter/boss/dragon.scml");
        Data data = new SCMLReader(handle.read()).getData();
        LibGdxLoader loader = new LibGdxLoader(data);
        loader.load(handle.file());
        Player boss = new Player(data.getEntity(0));
        boss.setPosition(240,300);
        boss.setScale(0.4f);
        boss.speed=15;
        boss.setAnimation("idle");
        return boss;
    }

    public Drawer loadDrawer(SpriteBatch batch) {
        ShapeRenderer renderer = new ShapeRenderer();

        FileHandle handle = Gdx.files.internal("spriter/animation.scml");
        data = new SCMLReader(handle.read()).getData();

        LibGdxLoader loader = new LibGdxLoader(data);
        loader.load(handle.file());

        Drawer drawer = new LibGdxDrawer(loader, batch, renderer);
        return drawer;
    }

    public Drawer loadBossDrawer(SpriteBatch batch) {
        ShapeRenderer renderer = new ShapeRenderer();

        FileHandle handle = Gdx.files.internal("spriter/boss/dragon.scml");
        data = new SCMLReader(handle.read()).getData();

        LibGdxLoader loader = new LibGdxLoader(data);
        loader.load(handle.file());

        Drawer drawer = new LibGdxDrawer(loader, batch, renderer);
        return drawer;
    }

    /**
     * Modification de l'etat du jeu en fonction
     * du temps passe
     */
    public void updateLogic(float delta) {
        autoSaveTimer += Gdx.graphics.getDeltaTime();
        increaseGoldTimer += Gdx.graphics.getDeltaTime();
        weatherTimer += Gdx.graphics.getDeltaTime();
        logicTimer += Gdx.graphics.getDeltaTime();

        if(newModuleIdList.size()>0){
            if (currentState.equals(GameState.IN_GAME)){
                for (int i=0;i<newModuleIdList.size();i++) {
//                    stationEntity.addModule(newModuleIdList.get(i));
                }
                newModuleIdList.clear();
            }
        }
        switch (currentState) {
            case IN_GAME:
                //Gdx.input.setInputProcessor(playScreen.inputMultiplexer);
                break;
            case MENU:
                //Gdx.input.setInputProcessor(playScreen.getHud().getStage());
                if (logicTimer > 1f) {
                    logicTimer=0f;
                    playScreen.getHud().update();
                }
                break;
            case CREDIT:
                //Gdx.input.setInputProcessor(playScreen.getHud().getStage());
                break;
            default:
                break;
        }

        // Autosave
        if(autoSaveTimer >= Constants.DELAY_AUTOSAVE){
            Gdx.app.debug("PlayScreen","Saving");
            gameInformationManager.saveInformation();
            autoSaveTimer=0f;
        }

        // Increase Gold passivly
        if(increaseGoldTimer >= Constants.DELAY_GENGOLD_PASSIV) {
            ressourceManager.increaseGoldPassive();
            Gdx.app.debug("PlayScreen","Increasing Gold by "+gameInformation.getGenGoldPassive()+" val "+gameInformation.getGenCurrencyPassive());
            ressourceManager.increaseGoldPassive();
            playScreen.getHud().updateGoldLabel();
            increaseGoldTimer=0f;
        }

     //   if (gameInformation.isOptionWeather()) {
     //       if (weatherTimer >= Constants.DELAY_WEATHER_CHANGE) {
     //           Gdx.app.debug("PlayScreen", "Changing weather");
     //           weatherManager.addRandomWeather();
     //           weatherTimer = 0f;
     //       }
     //   }
    }

    /**
     * todo from json
     */
    public void initEnemyQueue() {
        enemyActorQueue.clear();
        gameInformation.currentEnemyIdx=0;
        int randomNum=0;
       for (int i=0;i<10;i++) {
           randomNum = rand.nextInt((assetManager.enemyList.size()-1) + 1);
           enemyActorQueue.add(new EnemyActor(assetManager.enemyList.get(randomNum)));
        }
    }

    public void switchEnemy() {
        currentState = GameState.PAUSE;
        if (gameInformation.currentEnemyIdx+1<enemyActorQueue.size()) {
            gameInformation.currentEnemyIdx+=1;
            playScreen.getHud().battleNbLabel.setText(gameInformation.currentEnemyIdx+"/10");

            // Initialize position before moving
            playScreen.enemyActorList.get(0).clearActions();
            playScreen.enemyActorList.get(1).clearActions();
            playScreen.enemyActorList.get(2).clearActions();
            playScreen.enemyActorList.get(0).setPosition(130,220);
            playScreen.enemyActorList.get(1).setPosition(190,235);
            playScreen.enemyActorList.get(2).setPosition(220,235);
            playScreen.enemyActorList.get(2).getColor().a=0f;

            //playScreen.enemyActorList.get(0).setActiveAnimation("idle");
            playScreen.enemyActorList.get(1).setActiveAnimation("idle");
            playScreen.enemyActorList.get(2).setActiveAnimation("idle");

            playScreen.enemyActorList.get(0).addAction(Actions.sequence(Actions.fadeOut(1f), Actions.moveTo(220,235)));
            playScreen.enemyActorList.get(1).addAction(Actions.parallel(Actions.moveTo(130,220,1f), Actions.color(Color.WHITE,1f)));
            playScreen.enemyActorList.get(2).addAction(Actions.parallel(Actions.moveTo(190,235,1f),fadeIn(3f), Actions.color(Color.BLACK)));

            // Change order of enemy on screen (0: current, 1: visible, 2: swap
            Collections.swap(playScreen.enemyActorList,0,1);
            Collections.swap(playScreen.enemyActorList,1,2);

            // TODO faire disparaitre les mobs  & corriger numero du combat
            if (gameInformation.currentEnemyIdx+3 < enemyActorQueue.size()) {
                playScreen.enemyActorList.set(2, enemyActorQueue.get(gameInformation.currentEnemyIdx + 2));
                playScreen.layer1GraphicObject.addActor(playScreen.enemyActorList.get(2));
                playScreen.enemyActorList.get(2).setPosition(220, 235);
                playScreen.enemyActorList.get(2).getColor().a = 0f;
            } else {

            }

            // first actor always on top
            playScreen.layer1GraphicObject.swapActor(playScreen.enemyActorList.get(0), playScreen.enemyActorList.get(1));

            // Set new Current Actor
            currentEnemyActor = playScreen.enemyActorList.get(0);
            // Add little delay before showing new enemy bar & restarting action
            currentEnemyActor.addAction(Actions.sequence(Actions.delay(0.8f),Actions.run(new Runnable() {
                @Override
                public void run() {
                    playScreen.getHud().initEnemyInformation(currentEnemyActor); currentState = GameState.IN_GAME;
                }
            })));

        }
    }

    /**
     * Value d'un coup critique
     * @return
     */
    public float getCriticalValue(){
        return (gameInformation.getTapDamage() * gameInformation.getCriticalRate());
    }

    /**
     * hurtEnemy
     */
    public void hurtEnemy() {
        //currentEnemyActor.hurt();
        currentEnemyActor.hp -= gameInformation.getTapDamage();
        // Case of enemy death
        if (currentEnemyActor.hp <= 0) {
            handleEnemyDeath();
        }
        playScreen.getHud().updateEnemyInformation(gameInformation.getTapDamage());
    }

    public void handleEnemyDeath(){
        currentEnemyActor.hp=0;
        currentEnemyActor.death();

        // if enemy generate gold (reward Manager || ScreenAnimationManager)
        Runnable incGold = new Runnable() {
            @Override
            public void run() {
                ressourceManager.increaseGoldActive();
            }
        };
        float speed=1f;
        float fadeOut=0.75f;
        GoldActor goldActor = new GoldActor(currentEnemyActor.getX()+currentEnemyActor.getWidth()/2, currentEnemyActor.getY()+currentEnemyActor.getHeight());
        goldActor.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH-50, Constants.V_HEIGHT, speed), Actions.fadeOut(fadeOut)), Actions.removeActor()));
        GoldActor goldActor2 = new GoldActor(currentEnemyActor.getX()+currentEnemyActor.getWidth()/2, currentEnemyActor.getY()+currentEnemyActor.getHeight()-30);
        goldActor2.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH-30, Constants.V_HEIGHT, speed), Actions.fadeOut(fadeOut)), Actions.removeActor()));
        GoldActor goldActor3 = new GoldActor(currentEnemyActor.getX()+currentEnemyActor.getWidth()/2, currentEnemyActor.getY()+currentEnemyActor.getHeight()+15);
        goldActor3.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH-50, Constants.V_HEIGHT, speed), Actions.fadeOut(fadeOut)), Actions.removeActor()));
        GoldActor goldActor4 = new GoldActor(currentEnemyActor.getX()+currentEnemyActor.getWidth()/2, currentEnemyActor.getY()+currentEnemyActor.getHeight()+7);
        goldActor4.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH, Constants.V_HEIGHT, speed), Actions.sequence(Actions.fadeOut(fadeOut), Actions.run(incGold))),Actions.removeActor()));

        playScreen.stage.addActor(goldActor);
        playScreen.stage.addActor(goldActor2);
        playScreen.stage.addActor(goldActor3);
        playScreen.stage.addActor(goldActor4);

        // go upstair or stay
        if (gameInformation.currentEnemyIdx < nbMandatoryFight) {
            switchEnemy();
        } else {
            //playScreen.getHud().ascendButton.setVisible(true);
            //playScreen.getHud().ascendButton.addAction(new BlinkAction(1f,2));
            initEnemyQueue();
            switchEnemy();
        }
    }

    /**
     * TODO Classe d'experimentation
     */
    public void switchFloor() {
        Vector2 targetPosition = new Vector2(Constants.V_WIDTH/2,Constants.V_HEIGHT);
        playScreen.stage.addAction(CameraMoveToAction.action(playScreen.camera, 1f, targetPosition.x, targetPosition.y, playScreen.camera.position.z, Interpolation.fade));
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.sequence(Actions.fadeOut(1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                playScreen.camera.position.x=Constants.V_WIDTH/2;
                playScreen.camera.position.y=50;
                playScreen.camera.position.z=0;
                playScreen.stage.addAction(CameraMoveToAction.action(playScreen.camera, 0.5f, Constants.V_WIDTH/2,Constants.V_HEIGHT/2, playScreen.camera.position.z, Interpolation.circle));
            }
        }),fadeIn(1f)));
//        playScreen.stage.getRoot().getColor().a = 0;
        playScreen.stage.getRoot().addAction(sequenceAction);

    }
}
