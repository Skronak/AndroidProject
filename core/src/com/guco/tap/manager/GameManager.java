package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
import com.guco.tap.action.BlinkAction;
import com.guco.tap.action.CameraMoveToAction;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.actor.CharacterAnimatedActor;
import com.guco.tap.entity.GameInformation;
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

    public CharacterAnimatedActor characterActor;

    public ArrayList<Integer> newModuleIdList;

    // Enemy present on this floor
    public ArrayList<EnemyActor> enemyActorQueue;

    // Etat du jeu
    public GameState currentState;

    private EnemyActor currentEnemyActor;

    Random rand = new Random();

    public int nbMandatoryFight;

    private Data data;

    public Player player;

    public GameManager(PlayScreen playScreen) {
        currentState = GameState.IN_GAME;
        this.playScreen = playScreen;
        largeMath = new LargeMath();
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
        GameInformation.INSTANCE.currentEnemyIdx=0;
        moduleManager.initialize(playScreen.getHud().getModuleMenu());
        initEnemyQueue();
        currentEnemyActor = enemyActorQueue.get(GameInformation.INSTANCE.currentEnemyIdx);
        playScreen.getHud().initEnemyInformation(currentEnemyActor);
        nbMandatoryFight=5;
        playScreen.getHud().postInitMenu();
    }

    public Player loadPlayer(){
        player = new Player(data.getEntity(0));
        player.setPosition(85,220);
        player.setScale(0.4f);
        player.speed=15;
        player.setAnimation("idle_1");
        player.addListener(new PlayerListenerImpl(player,playScreen));
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

    public CharacterAnimatedActor initializeCharacter(){
        if (null==characterActor) {
            characterActor = new CharacterAnimatedActor(270,200);
        }
        return characterActor;
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
            GameInformation.INSTANCE.saveInformation();
            autoSaveTimer=0f;
        }

        // Increase Gold passivly
        if(increaseGoldTimer >= Constants.DELAY_GENGOLD_PASSIV) {
            ressourceManager.increaseGoldPassive();
            Gdx.app.debug("PlayScreen","Increasing Gold by "+GameInformation.INSTANCE.getGenGoldPassive()+" val "+GameInformation.INSTANCE.getGenCurrencyPassive());
            ressourceManager.increaseGoldPassive();
            playScreen.getHud().updateGoldLabel();
            increaseGoldTimer=0f;
        }

     //   if (GameInformation.INSTANCE.isOptionWeather()) {
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
        GameInformation.INSTANCE.currentEnemyIdx=0;
        int randomNum=0;
       for (int i=0;i<10;i++) {
           randomNum = rand.nextInt((AssetManager.INSTANCE.enemyList.size()-1) + 1);
           enemyActorQueue.add(new EnemyActor(AssetManager.INSTANCE.enemyList.get(randomNum)));
        }
    }

    public void switchEnemy() {
        currentState = GameState.PAUSE;
        if (GameInformation.INSTANCE.currentEnemyIdx+1<enemyActorQueue.size()) {
            GameInformation.INSTANCE.currentEnemyIdx+=1;
            playScreen.getHud().battleNbLabel.setText(GameInformation.INSTANCE.currentEnemyIdx+"/10");

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
            if (GameInformation.INSTANCE.currentEnemyIdx+3 < enemyActorQueue.size()) {
                playScreen.enemyActorList.set(2, enemyActorQueue.get(GameInformation.INSTANCE.currentEnemyIdx + 2));
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
        return (GameInformation.INSTANCE.getGenGoldActive() * GameInformation.INSTANCE.getCriticalRate());
    }

    /**
     * hurtEnemy
     */
    public void hurtEnemy() {
        //currentEnemyActor.hurt();
        currentEnemyActor.hp -= GameInformation.INSTANCE.getGenGoldActive();
        // Case of enemy death
        if (currentEnemyActor.hp <= 0) {
            handleEnemyDeath();
        }
        playScreen.getHud().updateEnemyInformation(GameInformation.INSTANCE.getGenGoldActive());
    }

    public void handleEnemyDeath(){
        currentEnemyActor.hp=0;
        currentEnemyActor.death();
        // if enemy generate gold (reward Manager || ScreenAnimationManager)
        GoldActor goldActor = new GoldActor(currentEnemyActor.getX()+currentEnemyActor.getWidth()/2, currentEnemyActor.getY()+currentEnemyActor.getHeight());
        goldActor.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH/2-20, Constants.V_HEIGHT, 2f), Actions.fadeOut(1.5f)), Actions.removeActor()));
        GoldActor goldActor2 = new GoldActor(currentEnemyActor.getX()+currentEnemyActor.getWidth()/2, currentEnemyActor.getY()+currentEnemyActor.getHeight()-30);
        goldActor2.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH/2-30, Constants.V_HEIGHT, 2f), Actions.fadeOut(1.5f)), Actions.removeActor()));
        GoldActor goldActor3 = new GoldActor(currentEnemyActor.getX()+currentEnemyActor.getWidth()/2, currentEnemyActor.getY()+currentEnemyActor.getHeight()+15);
        goldActor3.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH/2-50, Constants.V_HEIGHT, 2f), Actions.fadeOut(1.5f)), Actions.removeActor()));
        GoldActor goldActor4 = new GoldActor(currentEnemyActor.getX()+currentEnemyActor.getWidth()/2, currentEnemyActor.getY()+currentEnemyActor.getHeight()+7);
        goldActor4.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH/2, Constants.V_HEIGHT, 2f), Actions.fadeOut(1.5f)), Actions.removeActor()));

        playScreen.stage.addActor(goldActor);
        playScreen.stage.addActor(goldActor2);
        playScreen.stage.addActor(goldActor3);
        playScreen.stage.addActor(goldActor4);
        if (GameInformation.INSTANCE.currentEnemyIdx+1 < nbMandatoryFight) {
            switchEnemy();
        } else {
            playScreen.getHud().ascendButton.setVisible(true);
            playScreen.getHud().ascendButton.addAction(new BlinkAction(1f,2));
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
