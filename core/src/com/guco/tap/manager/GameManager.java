package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.actor.SpriterActor;
import com.guco.tap.actor.SpriterEnemyActor;
import com.guco.tap.dto.Area;
import com.guco.tap.entity.EnemyTemplateEntity;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.input.EffectActorListenerImpl;
import com.guco.tap.input.PlayerSpriterListenerImpl;
import com.guco.tap.input.SpriterActorListenerImpl;
import com.guco.tap.screen.BattleScreen;
import com.guco.tap.utils.AnimationStatusEnum;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Gdx.files;
import static com.badlogic.gdx.math.MathUtils.random;

/**
 * Classe de gestion globale du jeu
 * gerant le lien entre PlayScreen et GameInformation
 */
public class GameManager {

    public BattleScreen battleScreen;
    public LargeMath largeMath;
    public AchievementManager achievementManager;
    public DataManager dataManager;
    public AreaManager areaManager;
    public ItemManager itemManager;
    public AssetsManager assetsManager;
    public GameInformationManager gameInformationManager;
    public GoldManager goldManager;

    private float autoSaveTimer, increaseGoldTimer, logicTimer;
    public ArrayList<EnemyActor> waitingEnemies;
    public ArrayList<SpriterEnemyActor> floorEnemies;
    public GameState currentState;
    Random rand = new Random();
    public SpriterPlayer spriterPlayer;
    public TapDungeonGame game;
    public GameInformation gameInformation;
    public Area currentArea;
    private SpriterActor player;
    private SpriterEnemyActor currentEnemy;

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
        floorEnemies= new ArrayList<SpriterEnemyActor>();
    }

    public void initialiseGame(SpriteBatch sb) {
        initArea();
        initEnemies(sb);

        startGame();
    }

    private void initArea() {
        currentArea = assetsManager.areaList.get(gameInformation.areaId);

        Texture backgroundTexture = new Texture(files.internal("sprites/background/" + currentArea.backgroundTexture));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegionDrawable drawable = new TextureRegionDrawable(backgroundTexture);

        battleScreen.changeBackround(drawable);
    }

    public void initEnemies(SpriteBatch sb) {
        floorEnemies.clear();
        List<EnemyTemplateEntity> availableEnemy = new ArrayList<EnemyTemplateEntity>();
        for (int i =0; i < currentArea.enemiesId.length; i++) {
            availableEnemy.add(assetsManager.enemyTemplateList.get(currentArea.enemiesId[i]));
        }

        for (int i =0; i < currentArea.fights;i++) {
            int randomNum = rand.nextInt((availableEnemy.size() - 1) + 1);
            EnemyTemplateEntity enemyTemplateEntity = availableEnemy.get(randomNum);

            SpriterEnemyActor enemyActor = new SpriterEnemyActor(sb,this,"spriter", 3f, enemyTemplateEntity, gameInformation.areaLevel);
            enemyActor.spriterPlayer.addListener(new SpriterActorListenerImpl(enemyActor));
            enemyActor.setPosition(enemyTemplateEntity.getPosX(), enemyTemplateEntity.getPosY());
            enemyActor.setScale(enemyTemplateEntity.getScale());
            enemyActor.spriterPlayer.speed = 15;
            enemyActor.spriterPlayer.setAnimation("idle");
            if (enemyTemplateEntity.isSwitchX()) {
                enemyActor.spriterPlayer.flipX();
            }

            floorEnemies.add(enemyActor);
        }
    }

    private void startGame() {
//        game.hud.floorLabel.setText(currentArea.name + " - " + gameInformation.areaLevel);
//        game.hud.enemyInformation.init(currentEnemyActor);
        currentEnemy = floorEnemies.get(gameInformation.currentEnemyIdx);
        game.hud.initFightInformation(currentEnemy);
    }

    public SpriterActor loadPlayerActor(SpriteBatch spriteBatch) {
        SpriterActor playerActor = new SpriterActor(spriteBatch, "spriter/animation.scml");
        playerActor.setPosition(85, 220);
        playerActor.setScale(0.37f);
        playerActor.spriterPlayer.speed = 15;
        playerActor.spriterPlayer.setAnimation("idle");
        playerActor.spriterPlayer.addListener(new PlayerSpriterListenerImpl(playerActor.spriterPlayer, this));
        playerActor.spriterPlayer.characterMaps[0] = playerActor.spriterPlayer.getEntity().getCharacterMap(gameInformation.equipedWeapon.mapName);
        playerActor.spriterPlayer.characterMaps[1] = playerActor.spriterPlayer.getEntity().getCharacterMap(assetsManager.helmList.get(gameInformation.equipedHead).mapName);
        playerActor.spriterPlayer.characterMaps[2] = playerActor.spriterPlayer.getEntity().getCharacterMap(assetsManager.bodyList.get(gameInformation.equipedBody).mapName);

        return playerActor;
    }

    public SpriterActor loadPlayer(SpriteBatch spriteBatch) {
        player = new SpriterActor(spriteBatch, "spriter/PlatformerSKIN_CAT/player.scml");
        player.spriterPlayer.addListener(new SpriterActorListenerImpl(player));
        player.setPosition(100, 220);
        player.setScale(0.37f);
        player.spriterPlayer.speed = 15;
        player.spriterPlayer.setAnimation("idle");

        return player;
    }

    public SpriterActor loadEffect(SpriteBatch spriteBatch) {
        SpriterActor effectActor = new SpriterActor(spriteBatch,"spriter/Effects/Impacts.scml");
        effectActor.spriterPlayer.addListener(new EffectActorListenerImpl(effectActor));
        effectActor.setPosition(130, 250);
        effectActor.setScale(0.5f);
        effectActor.spriterPlayer.speed = 15;
        effectActor.spriterPlayer.setAnimation("impact_0");
        effectActor.setVisible(false);

        return effectActor;
    }

    public void hitEnemy() {
        player.isAttacking = true;
        player.setAnimation(AnimationStatusEnum.ATTACK);
        if (!currentEnemy.isAttacking) {
            currentEnemy.setAnimation(AnimationStatusEnum.HIT);
        }
        updateStats();

        int randCritical = random.nextInt(Constants.CRITICAL_CHANCE) + 1;
        if (randCritical == 1) {
        }

        ValueDTO damageData = new ValueDTO(gameInformation.tapDamageValue, gameInformation.tapDamageCurrency);
        damageData.currency=5;//debug
        hurtEnemy(damageData);
        String damageString = largeMath.getDisplayValue(damageData);
        battleScreen.addDamageLabel(damageString);
    }

    public void updateStats() {
        gameInformation.totalTapNumber = (gameInformation.totalTapNumber + 1); // updateStat
    }

    public void updateLogic(float delta) {
        autoSaveTimer += delta;// TODO a desactiver
        increaseGoldTimer += delta;
        logicTimer += delta;

        switch (currentState) {
            case IN_GAME:
                currentEnemy.update(delta);
//                battleScreen.currentEnemyActor.update(delta); //TODO a changer
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

        // todo Desactiver l'auto save au profit d'un save des uq'on gagne objet ou bat enemy
        if (autoSaveTimer >= Constants.DELAY_AUTOSAVE) {
            Gdx.app.debug("PlayScreen", "Saving");
//            gameInformationManager.saveData();
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

    public SpriterEnemyActor getEnemyInShadow() {
        int currentEnemyIdx = gameInformation.currentEnemyIdx;
        if (floorEnemies.size() != currentEnemyIdx) {
            SpriterEnemyActor enemyInShadow = floorEnemies.get(currentEnemyIdx + 1);
            enemyInShadow.spriterPlayer.scale(0.9f);
            enemyInShadow.spriterPlayer.setPosition(270, 240);
            enemyInShadow.renderBlack();
            enemyInShadow.active=false;
            return enemyInShadow;
        } else {
            return null;
        }
    }


    @Deprecated
    public void showNextEnemy() {
        if (gameInformation.currentEnemyIdx >=floorEnemies.size()-1) {
            gameInformation.currentEnemyIdx = 0;
        } else {
            gameInformation.currentEnemyIdx += 1;
        }
        game.hud.battleNbLabel.setText(gameInformation.currentEnemyIdx + "/" + currentArea.fights);//todo a bouger

        final SpriterEnemyActor newEnemy = floorEnemies.get(gameInformation.currentEnemyIdx);

        currentEnemy = newEnemy;

        currentEnemy.setAnimation(AnimationStatusEnum.WALK);
        battleScreen.layerFrontObjects.addActor(currentEnemy);

        game.hud.initFightInformation(currentEnemy);

        currentEnemy.setPosition(350, currentEnemy.getPosition().y);
        float walkAnimDuration = 1f;
        currentEnemy.addAction(Actions.sequence(Actions.moveTo(200, currentEnemy.getPosition().y, walkAnimDuration)));
        // force la position de spriterPlayer a suivre celle du parent
        RepeatAction forever = Actions.forever(Actions.run(new Runnable() {
            @Override
            public void run() {
                currentEnemy.spriterPlayer.setPosition(currentEnemy.getX(), currentEnemy.getY());
            }
        }));
        currentEnemy.addAction(Actions.parallel(forever, Actions.sequence(Actions.delay(walkAnimDuration), Actions.removeAction(forever), Actions.run(new Runnable() {
            @Override
            public void run() {
                currentEnemy.setAnimation(AnimationStatusEnum.IDLE);
                currentState = GameState.IN_GAME;
            }
        }))));
    }

    private void showNextEnemyDisfonctionnel() {
        //        currentEnemy.addAction(Actions.sequence(Actions.delay(1.5f), Actions.removeActor()));
        final SpriterEnemyActor enemyInShadow = battleScreen.enemyInShadow;

        // defini la position du parent enemyShadow, pour lui appliquer un deplacement
        enemyInShadow.setPosition(enemyInShadow.spriterPlayer.getPosition().x,enemyInShadow.spriterPlayer.getPosition().y );
        enemyInShadow.setAnimation(AnimationStatusEnum.IDLE);
        enemyInShadow.addAction(Actions.sequence(Actions.moveTo(currentEnemy.getPosition().x, currentEnemy.getPosition().y,1f), Actions.run(new Runnable() {
            @Override
            public void run() {
                enemyInShadow.renderNormal();
            }
        })));

        // force la position de spriterPlayer a suivre celle du parent
        RepeatAction forever = Actions.forever(Actions.run(new Runnable() {
            @Override
            public void run() {
                enemyInShadow.spriterPlayer.setPosition(enemyInShadow.getX(), enemyInShadow.getY());
            }
        }));
        enemyInShadow.addAction(Actions.parallel(forever, Actions.sequence(Actions.delay(1f), Actions.removeAction(forever))));

        currentEnemy.addAction(Actions.delay(1f, Actions.removeActor()));
        currentEnemy = enemyInShadow;
        currentEnemy.addAction(Actions.sequence(Actions.delay(2f), Actions.run(new Runnable() {
            @Override
            public void run() {
                battleScreen.layerEnemy.addActor(currentEnemy);
                game.hud.initFightInformation(currentEnemy);
                currentState = GameState.IN_GAME;
            }
        })));
        SpriterEnemyActor newEnemyInShadow = getEnemyInShadow();
        battleScreen.layerEnemy.addActor(newEnemyInShadow);
    }

    public void showBoss() {
//         game.hud.battleNbLabel.setText(gameInformation.currentEnemyIdx + "/10");
    }

    private void hurtEnemy(ValueDTO damageData) {
        currentEnemy.lifePoint = largeMath.decreaseValue(currentEnemy.lifePoint, damageData);
        game.hud.updateEnemyInformation(damageData);
        if (currentEnemy.lifePoint.value <= 0) {
            winBattle();
        }
    }

    public void winBattle() {
        currentState = GameState.PAUSE;
        currentEnemy.die();

        if (waitingEnemies.isEmpty()) {
        }
        addReward();

        if (gameInformation.currentEnemyIdx == currentArea.fights) { // terminé tous les combats de la zone
            if (gameInformation.areaLevel < 10) { // augmente le lvl et on continue
                increaseAreaLevel();
            } else {
                loadNextArea();
            }

        } else {
            gameInformation.areaLevel+=1;
            game.hud.updateCurrentLevelCount(currentArea.fights, gameInformation.areaLevel);
            // affiche l'enemi suivant
            showNextEnemy();
        }
    }

    private void addReward() {
        goldManager.addGoldCoin(new Vector2(currentEnemy.getPosition().x, player.spriterPlayer.getPosition().y));
    }

    private void loadNextArea() {
        gameInformation.currentEnemyIdx = 0;

        Texture backgroundTexture = new Texture(files.internal("sprites/background/" + currentArea.backgroundTexture));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegionDrawable drawable = new TextureRegionDrawable(backgroundTexture);

        battleScreen.changeBackround(drawable);
    }

    public void increaseAreaLevel() {
        gameInformation.currentEnemyIdx = 0;
        gameInformation.areaLevel += 1;

        game.hud.currentLevelLabel.setText(gameInformation.areaId+" :"+gameInformation.areaLevel);

        EnemyTemplateEntity enemyTemplateEntity = game.assetsManager.enemyTemplateList.get(0);
        SpriterEnemyActor enemyActor = new SpriterEnemyActor(game.sb,this,"spriter", 3f, enemyTemplateEntity, gameInformation.areaLevel);
        floorEnemies.clear();
        floorEnemies.add(enemyActor);
        showNextEnemy();

//        initEnemies(game.sb);
//        showNextEnemy();
    }

    public void handleEnemyAttack() {
        Gdx.app.log(this.getClass().toString(), String.valueOf(player.isAttacking));
        if (!player.isAttacking) {
            battleScreen.player.setAnimation(AnimationStatusEnum.BLOCK_HIT);
            battleScreen.effectActor.setVisible(true);
        } else {
            battleScreen.player.setAnimation(AnimationStatusEnum.HIT);
        }
    }

    public SpriterEnemyActor getCurrentEnemy() {
        return currentEnemy;
    }

}
