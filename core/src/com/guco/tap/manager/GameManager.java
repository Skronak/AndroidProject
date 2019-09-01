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
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.action.CameraMoveToAction;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.dto.Area;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.input.PlayerListenerImpl;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;
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

    public AttributeManager attributeManager;

    public AchievementManager achievementManager;

    public DataManager dataManager;

    public float autoSaveTimer,weatherTimer, increaseGoldTimer, logicTimer;

    public ArrayList<Integer> newModuleIdList;

    public ItemManager itemManager;

    // Enemy present on this floor
    public ArrayList<EnemyActor> enemyActorQueue;

    // Etat du jeu
    public GameState currentState;

    public EnemyActor currentEnemyActor;

    Random rand = new Random();

    public int nbMandatoryFight;

    public Data playerData;

    public Data forgeData;

    public SpriterPlayer spriterPlayer;

    TapDungeonGame game;

    public AssetsManager assetsManager;

    public GameInformationManager gameInformationManager;

    public GameInformation gameInformation;

    public GoldManager goldManager;

    public Area currentArea;

    public GameManager(TapDungeonGame game) {
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");
        this.gameInformationManager = game.gameInformationManager;
        currentState = GameState.IN_GAME;
        this.assetsManager =game.assetsManager;
        this.gameInformation = game.gameInformation;
        largeMath = game.largeMath;
        newModuleIdList = new ArrayList<Integer>();
        //weatherManager = new WeatherManager(playScreen);
        attributeManager = new AttributeManager(this);
        achievementManager = new AchievementManager(this);
        dataManager = new DataManager(this);
        itemManager = game.itemManager;
        goldManager = new GoldManager(this);
        autoSaveTimer = 0f;
        increaseGoldTimer = 0f;
        weatherTimer = 0f;
        logicTimer = 0f;
        enemyActorQueue = new ArrayList<EnemyActor>();
    }

    public void initialiseGame() {
        gameInformation.currentEnemyIdx=0;
        //attributeManager.initialize(playScreen.getHud().getCharacterAttributeMenu());
        initEnemyQueue();
        attributeManager.initialize(playScreen.getHud().characterAttributeMenu);
        currentArea = assetsManager.areaList.get(gameInformation.areaId);

        currentEnemyActor = enemyActorQueue.get(gameInformation.currentEnemyIdx);
        playScreen.getHud().initFight(currentEnemyActor);
        nbMandatoryFight=5; // from floor information
        playScreen.getHud().postInitMenu();

    }

    public SpriterPlayer loadPlayer(){
        int weaponMap=0;
        int headMap=1;
        int bodyMap=2;
        spriterPlayer = new SpriterPlayer(playerData.getEntity(0));
        spriterPlayer.setPosition(85,220);
        spriterPlayer.setScale(0.37f);
        spriterPlayer.speed=15;
        spriterPlayer.setAnimation("idle");
        spriterPlayer.addListener(new PlayerListenerImpl(spriterPlayer,playScreen));
        spriterPlayer.characterMaps[weaponMap]= spriterPlayer.getEntity().getCharacterMap(gameInformation.equipedWeapon.mapName);
        spriterPlayer.characterMaps[headMap]= spriterPlayer.getEntity().getCharacterMap(assetsManager.helmList.get(gameInformation.equipedHead).mapName);
        spriterPlayer.characterMaps[bodyMap]= spriterPlayer.getEntity().getCharacterMap(assetsManager.bodyList.get(gameInformation.equipedBody).mapName);
        return spriterPlayer;
    }

    // TODO useless, to include in forgeMenu
    public SpriterPlayer loadForgePlayer(){
        int blade=0;
        int gard=1;
        int pom=2;
        spriterPlayer = new SpriterPlayer(forgeData.getEntity(0));
        spriterPlayer.setPosition(85,220);
        spriterPlayer.setScale(0.30f);
        spriterPlayer.speed=10;
        spriterPlayer.setAnimation("idle");
        //spriterPlayer.addListener(new PlayerListenerImpl(spriterPlayer,playScreen));
        return spriterPlayer;
    }

    public SpriterPlayer loadBoss(){
        FileHandle handle = Gdx.files.internal("spriter/boss/dragon.scml");
        Data data = new SCMLReader(handle.read()).getData();
        LibGdxLoader loader = new LibGdxLoader(data);
        loader.load(handle.file());
        SpriterPlayer boss = new SpriterPlayer(data.getEntity(0));
        boss.setPosition(240,300);
        boss.setScale(0.4f);
        boss.speed=15;
        boss.setAnimation("idle");
        return boss;
    }

    public Drawer loadPlayerDrawer(SpriteBatch batch) {
        ShapeRenderer renderer = new ShapeRenderer();

        FileHandle handle = Gdx.files.internal("spriter/animation.scml");
        playerData = new SCMLReader(handle.read()).getData();

        LibGdxLoader loader = new LibGdxLoader(playerData);
        loader.load(handle.file());

        Drawer drawer = new LibGdxDrawer(loader, batch, renderer);
        return drawer;
    }

    public Drawer loadBossDrawer(SpriteBatch batch) {
        ShapeRenderer renderer = new ShapeRenderer();

        FileHandle handle = Gdx.files.internal("spriter/boss/dragon.scml");
        forgeData = new SCMLReader(handle.read()).getData();

        LibGdxLoader loader = new LibGdxLoader(forgeData);
        loader.load(handle.file());

        Drawer drawer = new LibGdxDrawer(loader, batch, renderer);
        return drawer;
    }

    public Drawer loadForgeDrawer(SpriteBatch batch) {
        ShapeRenderer renderer = new ShapeRenderer();

        FileHandle handle = Gdx.files.internal("spriter/forge/forge.scml");
        forgeData = new SCMLReader(handle.read()).getData();

        LibGdxLoader loader = new LibGdxLoader(forgeData);
        loader.load(handle.file());

        Drawer drawer = new LibGdxDrawer(loader, batch, renderer);
        return drawer;
    }

    public void hitEnemy(int posX, int posY){
        spriterPlayer.setAnimation("atk");
        gameInformation.totalTapNumber=(gameInformation.totalTapNumber+1);

        int randCritical = random.nextInt(Constants.CRITICAL_CHANCE) + 1;
        if (randCritical == 1) {
        } else {
        }

        String damageString = largeMath.getDisplayValue(gameInformation.tapDamageValue, gameInformation.tapDamageCurrency);
        playScreen.showTapActor(posX, posY);
        playScreen.showDamageLabel(damageString);

        ValueDTO damageData = new ValueDTO(gameInformation.tapDamageValue, gameInformation.tapDamageCurrency);
        hurtEnemy(damageData);
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
            gameInformationManager.saveData();
            autoSaveTimer=0f;
        }

        // Increase Gold passivly
        if(increaseGoldTimer >= Constants.DELAY_GENGOLD_PASSIV) {
            dataManager.increaseGoldPassive();
//            Gdx.app.debug("PlayScreen","Increasing Gold by "+gameInformation.getGenGoldPassive()+" val "+gameInformation.getGenCurrencyPassive());
            dataManager.increaseGoldPassive();
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
           randomNum = rand.nextInt((assetsManager.enemyList.size()-1) + 1);
           enemyActorQueue.add(new EnemyActor(assetsManager.enemyList.get(randomNum),gameInformation.areaLevel));
        }
    }

    public void exitGame() {
        gameInformationManager.saveData();
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
                    playScreen.getHud().initFight(currentEnemyActor); currentState = GameState.IN_GAME;
                }
            })));

        }
    }

    /**
     * Value d'un coup critique
     * @return
     */
    public float getCriticalValue(){
        return (gameInformation.tapDamageValue * gameInformation.criticalRate);
    }

    /**
     * hurtEnemy
     */
    public void hurtEnemy(ValueDTO damageData) {
        //currentEnemyActor.hurt();
        boolean noDamage=false;
        ValueDTO previousEnemyLifePoint = currentEnemyActor.lifePoint;
        currentEnemyActor.lifePoint = largeMath.decreaseValue(currentEnemyActor.lifePoint,damageData);
        if (currentEnemyActor.lifePoint.currency==previousEnemyLifePoint.currency && currentEnemyActor.lifePoint.value == previousEnemyLifePoint.value) {
            noDamage=true;
        }
        if (currentEnemyActor.lifePoint.value <= 0) {
            killEnemy();
        }
        if (!noDamage) {
            playScreen.getHud().updateEnemyInformation(damageData);
        }
    }

    public void killEnemy(){
        currentEnemyActor.hp=0;
        currentEnemyActor.death();
        Runnable incGold = new Runnable() {
            @Override
            public void run() {
                dataManager.increaseGold();
            }
        };
        float speed=1f;
        float fadeOut=0.75f;
        goldManager.addGoldCoin(new Vector2(currentEnemyActor.getX(), currentEnemyActor.getY()));
        // go upstair or stay
        if (gameInformation.currentEnemyIdx < nbMandatoryFight) {
            switchEnemy();
        } else {
            //playScreen.getHud().goToNextAreaButton.setVisible(true);
            //playScreen.getHud().goToNextAreaButton.addAction(new BlinkAction(1f,2));
            gameInformation.areaLevel +=1;
            initEnemyQueue();
            switchEnemy();
            switchFloor();
        }
    }

    private void animateKillEnemyUI(){

    }
    private void getReward(){

    }

    private void changeAreaLevel() {
        currentArea = assetsManager.areaList.get(gameInformation.areaId);

        if (gameInformation.areaLevel < 10) {
            changeAreaLevel();
         } else {
//            changeArea();
         }
    }

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
