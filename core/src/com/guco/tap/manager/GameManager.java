package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.LibGdxDrawer;
import com.brashmonkey.spriter.LibGdxLoader;
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.dto.Area;
import com.guco.tap.dto.SpriterDto;
import com.guco.tap.entity.EnemyTemplateEntity;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.input.PlayerListenerImpl;
import com.guco.tap.screen.AreaScreen;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.badlogic.gdx.Gdx.files;
import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;

/**
 * Created by Skronak on 30/07/2017.
 * <p>
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

    public AreaManager areaManager;

    public float autoSaveTimer, weatherTimer, increaseGoldTimer, logicTimer;

    public ArrayList<Integer> newModuleIdList;

    public ItemManager itemManager;

    // EnemyTemplateEntity present on this floor
    public ArrayList<EnemyActor> enemyActorQueue;

    // Etat du jeu
    public GameState currentState;

    public EnemyActor currentEnemyActor;

    Random rand = new Random();

    public int nbMandatoryFight;

    public Data playerData;

    public Data forgeData;

    public SpriterPlayer spriterPlayer;

    public TapDungeonGame game;

    public AssetsManager assetsManager;

    public GameInformationManager gameInformationManager;

    public GameInformation gameInformation;

    public GoldManager goldManager;

    public Area currentArea;

    public GameManager(TapDungeonGame game) {
        this.game = game;
        this.gameInformationManager = game.gameInformationManager;
        currentState = GameState.IN_GAME;
        this.assetsManager = game.assetsManager;
        this.gameInformation = game.gameInformation;
        this.areaManager = game.areaManager;
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

    public void loadArea() {
        int ID_AREA = 1;
        Area area = assetsManager.areaList.get(ID_AREA);
        AreaScreen areaScreen = new AreaScreen(game);
        areaScreen.backgroundImage = new Image(assetsManager.menuBackgroundTextureList.get(ID_AREA));
        areaScreen.backgroundImage.setSize(Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_LENGTH);
        areaScreen.backgroundImage.setPosition(Constants.BACKGROUND_POS_X, Constants.BACKGROUND_POS_Y);

        EnemyActor enemyActor = enemyActorQueue.get(0);
        enemyActor.setPosition(area.enemyPosX, area.enemyPosY);
        EnemyActor nextEnemyActor = enemyActorQueue.get(1);
        nextEnemyActor.setPosition(area.enemyBackPosX, area.enemyBackPosY);
        nextEnemyActor.setColor(Color.BLACK);
        EnemyActor hiddenEnemyActor = enemyActorQueue.get(2);
        hiddenEnemyActor.setPosition(area.enemyHiddenPosX, area.enemyHiddenPosY);
        hiddenEnemyActor.getColor().a = 0f;

        areaScreen.enemyActorList = new ArrayList<EnemyActor>();
        areaScreen.enemyActorList.add(enemyActor);
        areaScreen.enemyActorList.add(nextEnemyActor);
        areaScreen.enemyActorList.add(hiddenEnemyActor);

        for (int i = 0; i < area.objectList.size(); i++) {
            areaScreen.layer0GraphicObject.addActor((Actor) area.objectList.get(i));
            area.objectList.get(i).start();
        }
    }

    public void initialiseGame() {
        initArea();
        initFloorEnemies();

        startGame();

//        attributeManager.initialize(game.hud.characterAttributeMenu);
    }

    private void startGame(){
        game.hud.floorLabel.setText(currentArea.name + " - "+gameInformation.areaLevel);
        game.hud.enemyInformation.init(currentEnemyActor);
        //game.hud.initFight(currentEnemyActor);
    }
    public SpriterPlayer loadPlayer() {
        int weaponMap = 0;
        int headMap = 1;
        int bodyMap = 2;
        spriterPlayer = new SpriterPlayer(playerData.getEntity(0));
        spriterPlayer.setPosition(85, 220);
        spriterPlayer.setScale(0.37f);
        spriterPlayer.speed = 15;
        spriterPlayer.setAnimation("idle");
        spriterPlayer.addListener(new PlayerListenerImpl(spriterPlayer, playScreen));
        spriterPlayer.characterMaps[weaponMap] = spriterPlayer.getEntity().getCharacterMap(gameInformation.equipedWeapon.mapName);
        spriterPlayer.characterMaps[headMap] = spriterPlayer.getEntity().getCharacterMap(assetsManager.helmList.get(gameInformation.equipedHead).mapName);
        spriterPlayer.characterMaps[bodyMap] = spriterPlayer.getEntity().getCharacterMap(assetsManager.bodyList.get(gameInformation.equipedBody).mapName);

        return spriterPlayer;
    }

    // TODO useless, to include in forgeMenu
    public SpriterPlayer loadForgePlayer() {
        int blade = 0;
        int gard = 1;
        int pom = 2;
        spriterPlayer = new SpriterPlayer(forgeData.getEntity(0));
        spriterPlayer.setPosition(85, 220);
        spriterPlayer.setScale(0.30f);
        spriterPlayer.speed = 10;
        spriterPlayer.setAnimation("idle");
        //spriterPlayer.addListener(new PlayerListenerImpl(spriterPlayer,playScreen));

        return spriterPlayer;
    }

    public SpriterPlayer loadBoss() {
        FileHandle handle = Gdx.files.internal("spriter/boss/dragon.scml");
        Data data = new SCMLReader(handle.read()).getData();
        LibGdxLoader loader = new LibGdxLoader(data);
        loader.load(handle.file());
        SpriterPlayer boss = new SpriterPlayer(data.getEntity(0));
        boss.setPosition(240, 300);
        boss.setScale(0.4f);
        boss.speed = 15;
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

    public SpriterDto loadDrawer(SpriteBatch batch, String pathToScml) {
        FileHandle handle = Gdx.files.internal("spriter/"+pathToScml);
        Data data = new SCMLReader(handle.read()).getData();

        LibGdxLoader loader = new LibGdxLoader(data);
        loader.load(handle.file());

        ShapeRenderer renderer = new ShapeRenderer();
        Drawer drawer = new LibGdxDrawer(loader, batch, renderer);

        SpriterDto spriterDto = new SpriterDto(data, drawer);

        return spriterDto;
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

    public void hitEnemy(int posX, int posY) {
        playScreen.spriterPlayer.setAnimation("atk");
        gameInformation.totalTapNumber = (gameInformation.totalTapNumber + 1);

        int randCritical = random.nextInt(Constants.CRITICAL_CHANCE) + 1;
        if (randCritical == 1) {
        }

        String damageString = largeMath.getDisplayValue(gameInformation.tapDamageValue, gameInformation.tapDamageCurrency);
        playScreen.showTapActor(posX, posY);
        playScreen.showDamageLabel(damageString);

        ValueDTO damageData = new ValueDTO(gameInformation.tapDamageValue, gameInformation.tapDamageCurrency);
        hurtEnemy(damageData);
    }

    public void updateLogic(float delta) {
        autoSaveTimer += Gdx.graphics.getDeltaTime();
        increaseGoldTimer += Gdx.graphics.getDeltaTime();
        weatherTimer += Gdx.graphics.getDeltaTime();
        logicTimer += Gdx.graphics.getDeltaTime();

        switch (currentState) {
            case IN_GAME:
                break;
            case MENU:
                if (logicTimer > 1f) {
                    logicTimer = 0f;
                    game.hud.update();
                }
                break;
            case CREDIT:
                break;
            default:
                break;
        }

        // Autosave
        if (autoSaveTimer >= Constants.DELAY_AUTOSAVE) {
            Gdx.app.debug("PlayScreen", "Saving");
            gameInformationManager.saveData();
            autoSaveTimer = 0f;
        }

        // Increase Gold passivly
        if (increaseGoldTimer >= Constants.DELAY_GENGOLD_PASSIV) {
            dataManager.increaseGoldPassive();
            dataManager.increaseGoldPassive();
            game.hud.updateGoldLabel();
            increaseGoldTimer = 0f;
        }
    }

    public void initFloorEnemies() {
        enemyActorQueue.clear();
        gameInformation.currentEnemyIdx=0;

        for (int i = 0; i < 10; i++) {
            int randomNum = rand.nextInt((currentArea.enemiesId.length - 1) + 1);
            EnemyTemplateEntity enemyTemplateEntity = assetsManager.enemyTemplateEntityList.get(randomNum);
            EnemyActor enemyActor = new EnemyActor(enemyTemplateEntity, gameInformation.areaLevel);
            enemyActorQueue.add(enemyActor);
        }
        currentEnemyActor = enemyActorQueue.get(gameInformation.currentEnemyIdx);
    }

    public void exitGame() {
        gameInformationManager.saveData();
    }

    public void showNextEnemy() {
        gameInformation.currentEnemyIdx += 1;
        game.hud.battleNbLabel.setText(gameInformation.currentEnemyIdx + "/10");

        // Initialize position before moving
        playScreen.enemyActorList.get(0).clearActions();
        playScreen.enemyActorList.get(1).clearActions();
        playScreen.enemyActorList.get(2).clearActions();
        playScreen.enemyActorList.get(0).setPosition(130, 220);
        playScreen.enemyActorList.get(1).setPosition(190, 235);
        playScreen.enemyActorList.get(2).setPosition(220, 235);
        playScreen.enemyActorList.get(2).getColor().a = 0f;

        //playScreen.enemyActorList.get(0).setActiveAnimation("idle");
        playScreen.enemyActorList.get(1).setActiveAnimation("idle");
        playScreen.enemyActorList.get(2).setActiveAnimation("idle");

        playScreen.enemyActorList.get(0).addAction(Actions.sequence(Actions.fadeOut(1f), Actions.moveTo(220, 235)));
        playScreen.enemyActorList.get(1).addAction(Actions.parallel(Actions.moveTo(130, 220, 1f), Actions.color(Color.WHITE, 1f)));
        playScreen.enemyActorList.get(2).addAction(Actions.parallel(Actions.moveTo(190, 235, 1f), fadeIn(3f), Actions.color(Color.BLACK)));

        // Change order of enemy on screen (0: current, 1: visible, 2: swap
        Collections.swap(playScreen.enemyActorList, 0, 1);
        Collections.swap(playScreen.enemyActorList, 1, 2);

        // TODO faire disparaitre les mobs  & corriger numero du combat
        if (gameInformation.currentEnemyIdx + 3 < enemyActorQueue.size()) {
            playScreen.enemyActorList.set(2, enemyActorQueue.get(gameInformation.currentEnemyIdx + 2));
            playScreen.layer1GraphicObject.addActor(playScreen.enemyActorList.get(2));
            playScreen.enemyActorList.get(2).setPosition(220, 235);
            playScreen.enemyActorList.get(2).getColor().a = 0f;
        }

        // first actor always on top
        playScreen.layer1GraphicObject.swapActor(playScreen.enemyActorList.get(0), playScreen.enemyActorList.get(1));

        // Set new Current Actor
        currentEnemyActor = playScreen.enemyActorList.get(0);
        // Add little delay before showing new enemy bar & restarting action
        currentEnemyActor.addAction(Actions.sequence(Actions.delay(0.8f), Actions.run(new Runnable() {
            @Override
            public void run() {
                game.hud.initFight(currentEnemyActor);
                currentState = GameState.IN_GAME;
            }
        })));
    }

    public float getCriticalValue() {
        return (gameInformation.tapDamageValue * gameInformation.criticalRate);
    }

    private void hurtEnemy(ValueDTO damageData) {
        boolean noDamage = false;
        ValueDTO previousEnemyLifePoint = currentEnemyActor.lifePoint;
        currentEnemyActor.lifePoint = largeMath.decreaseValue(currentEnemyActor.lifePoint, damageData);
        if (currentEnemyActor.lifePoint.currency == previousEnemyLifePoint.currency && currentEnemyActor.lifePoint.value == previousEnemyLifePoint.value) {
            noDamage = true;
        }
        if (!noDamage) {
            game.hud.updateEnemyInformation(damageData);
        }

        if (currentEnemyActor.lifePoint.value <= 0) {
            winBattle();
        }
    }

    public void winBattle() {
        currentState = GameState.PAUSE;

        killCurrentEnemy();
        addReward();

        if (gameInformation.currentEnemyIdx < currentArea.fights) {
            showNextEnemy();
        } else {
            updateArea();
        }
        currentState = GameState.IN_GAME;
    }

    private void updateArea() {
        if (gameInformation.areaLevel < 10) {
            initArea();
        } else {
            updateCurrentArea();
        }
    }

    public void killCurrentEnemy() {
        currentEnemyActor.death();
    }

    private void addReward() {
        goldManager.addGoldCoin(new Vector2(currentEnemyActor.getX(), currentEnemyActor.getY()));
    }

    private void updateCurrentArea() {
        gameInformation.areaLevel += 1;
        initFloorEnemies();
        showNextEnemy();
    }

    public void initArea() {
        currentArea = assetsManager.areaList.get(gameInformation.areaId);

        Texture backgroundTexture = new Texture(files.internal("sprites/background/"+currentArea.backgroundTexture));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegionDrawable drawable = new TextureRegionDrawable(backgroundTexture);

        playScreen.initScreen(drawable);
    }
}
