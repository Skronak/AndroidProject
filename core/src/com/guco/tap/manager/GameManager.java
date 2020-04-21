package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.actor.PlayerActor;
import com.guco.tap.dto.Area;
import com.guco.tap.entity.EnemyTemplateEntity;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.input.PlayerListenerImpl;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

import java.util.ArrayList;
import java.util.Random;

import static com.badlogic.gdx.Gdx.files;
import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Classe de gestion globale du jeu
 * gerant le lien entre PlayScreen et GameInformation
 */
public class GameManager {

    public PlayScreen playScreen;

    public LargeMath largeMath;

    //public WeatherManager weatherManager;

    //public AttributeManager attributeManager;

    public AchievementManager achievementManager;

    public DataManager dataManager;

    public AreaManager areaManager;

    public float autoSaveTimer, increaseGoldTimer, logicTimer;

    public ItemManager itemManager;

    // EnemyTemplateEntity present on this floor
    public ArrayList<EnemyActor> waitingEnemies;

    // Etat du jeu
    public GameState currentState;

    public EnemyActor currentEnemyActor;

    Random rand = new Random();

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
        achievementManager = new AchievementManager(this);
        dataManager = new DataManager(this);
        itemManager = game.itemManager;
        goldManager = new GoldManager(this);
        autoSaveTimer = 0f;
        increaseGoldTimer = 0f;
        logicTimer = 0f;
        waitingEnemies = new ArrayList<EnemyActor>();
    }

    public void loadArea() {
/*        int ID_AREA = 1;
        Area area = assetsManager.areaList.get(ID_AREA);
        AreaScreen areaScreen = new AreaScreen(game);
        areaScreen.backgroundImage = new Image(assetsManager.menuBackgroundTextureList.get(ID_AREA));
        areaScreen.backgroundImage.setSize(Constants.BACKGROUND_WIDTH, Constants.BACKGROUND_LENGTH);
        areaScreen.backgroundImage.setPosition(Constants.BACKGROUND_POS_X, Constants.BACKGROUND_POS_Y);

        EnemyActor enemyActor = waitingEnemies.get(0);
        enemyActor.setPosition(area.enemyPosX, area.enemyPosY);
        EnemyActor nextEnemyActor = waitingEnemies.get(1);
        nextEnemyActor.setPosition(area.enemyBackPosX, area.enemyBackPosY);
        nextEnemyActor.setColor(Color.BLACK);
        EnemyActor hiddenEnemyActor = waitingEnemies.get(2);
        hiddenEnemyActor.setPosition(area.enemyHiddenPosX, area.enemyHiddenPosY);
        hiddenEnemyActor.getColor().a = 0f;

        areaScreen.enemyActorList = new ArrayList<EnemyActor>();
        areaScreen.enemyActorList.add(enemyActor);
        areaScreen.enemyActorList.add(nextEnemyActor);
        areaScreen.enemyActorList.add(hiddenEnemyActor);

        for (int i = 0; i < area.objectList.size(); i++) {
            areaScreen.layer0GraphicObject.addActor((Actor) area.objectList.get(i));
            area.objectList.get(i).start();
        }*/
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
    }

    public PlayerActor loadPlayerActor(SpriteBatch spriteBatch) {
        PlayerActor playerActor = new PlayerActor(spriteBatch,"spriter/animation.scml");
        playerActor.setPosition(85, 220);
        playerActor.setScale(0.37f);
        playerActor.spriterPlayer.speed = 15;
        playerActor.spriterPlayer.setAnimation("idle");
        playerActor.spriterPlayer.addListener(new PlayerListenerImpl(playerActor.spriterPlayer, this));
        playerActor.spriterPlayer.characterMaps[0] = playerActor.spriterPlayer.getEntity().getCharacterMap(gameInformation.equipedWeapon.mapName);
        playerActor.spriterPlayer.characterMaps[1] = playerActor.spriterPlayer.getEntity().getCharacterMap(assetsManager.helmList.get(gameInformation.equipedHead).mapName);
        playerActor.spriterPlayer.characterMaps[2] = playerActor.spriterPlayer.getEntity().getCharacterMap(assetsManager.bodyList.get(gameInformation.equipedBody).mapName);

        return playerActor;
    }


    public void hitEnemy() {
        playScreen.playerActor.setAnimation("atk");
        gameInformation.totalTapNumber = (gameInformation.totalTapNumber + 1); // updateStat

        int randCritical = random.nextInt(Constants.CRITICAL_CHANCE) + 1;
        if (randCritical == 1) {
        }

        ValueDTO damageData = new ValueDTO(gameInformation.tapDamageValue, gameInformation.tapDamageCurrency);
        hurtEnemy(new ValueDTO(gameInformation.tapDamageValue, gameInformation.tapDamageCurrency));
        String damageString = largeMath.getDisplayValue(damageData);
        playScreen.addDamageLabel(damageString);
    }

    public void updateLogic(float delta) {
        autoSaveTimer += delta;// TODO a desactiver
        increaseGoldTimer += delta;
        logicTimer += delta;

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
        waitingEnemies.clear();

        gameInformation.currentEnemyIdx = 1; // TODO ne peux pas reprendre si moins de 2 enemies pour init playscreen
        // Generate random enemy list from enemy available at this stage
        for (int i = gameInformation.currentEnemyIdx; i < currentArea.fights+1; i++) {
            int randomNum = rand.nextInt((currentArea.enemiesId.length - 1) + 1);
            EnemyTemplateEntity enemyTemplateEntity = assetsManager.enemyTemplateEntityList.get(randomNum);
            EnemyActor enemyActor = new EnemyActor(enemyTemplateEntity, gameInformation.areaLevel);
            waitingEnemies.add(enemyActor);
        }
        currentEnemyActor = waitingEnemies.get(currentArea.fights-gameInformation.currentEnemyIdx);

        playScreen.initFloor();
    }

    public void showNextEnemy() {
        gameInformation.currentEnemyIdx += 1;
        game.hud.battleNbLabel.setText(gameInformation.currentEnemyIdx + "/" + currentArea.fights);

//        playScreen.swapEnemy();
        playScreen.layerEnemy.addActor(playScreen.enemyActorList.get(0));

//        if (gameInformation.currentEnemyIdx + 3 < waitingEnemies.size()) { // TODO ???
//            playScreen.enemyActorList.set(2, waitingEnemies.get(gameInformation.currentEnemyIdx + 2));
//            playScreen.layerEnemy.addActor(playScreen.enemyActorList.get(2));
//            playScreen.enemyActorList.get(2).setPosition(220, 235);
//            playScreen.enemyActorList.get(2).getColor().a = 0f;
//        }

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

     public void showBoss(){
//         game.hud.battleNbLabel.setText(gameInformation.currentEnemyIdx + "/10");
     }

    private void hurtEnemy(ValueDTO damageData) {
        currentEnemyActor.lifePoint = largeMath.decreaseValue(currentEnemyActor.lifePoint, damageData);
        game.hud.updateEnemyInformation(damageData);
        if (currentEnemyActor.lifePoint.value <= 0) {
            winBattle();
        }
    }

    public void winBattle() {
        currentState = GameState.PAUSE;

        killCurrentEnemy();
        addReward();

        if (gameInformation.currentEnemyIdx == currentArea.fights) {
            //showBoss();
            updateArea();
        } else if (gameInformation.currentEnemyIdx < currentArea.fights) {
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
        currentEnemyActor.addAction(Actions.sequence(Actions.delay(0.5f), Actions.run(new Runnable() {
            @Override
            public void run() {
                currentEnemyActor.destroy();
            }
        })));
        waitingEnemies.remove(0);
        if (waitingEnemies.isEmpty()) {
            initFloorEnemies();
        }
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
