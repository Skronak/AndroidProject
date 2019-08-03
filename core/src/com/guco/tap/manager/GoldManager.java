package com.guco.tap.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.action.IncreaseGoldAction;
import com.guco.tap.object.GoldActor;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.ValueDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GoldManager {

    private GameManager gameManager;
    private float MOVE_DURATION =1.25f;
    private float FADE_OUT_DURATION =1f;// keep .25 between move & fade
    private Integer[] RANDOM_POSITION_X={30,50,10,-10};
    private ArrayList<Integer> randomPositionXList;
    private Integer[] RANDOM_POSITION_Y={30,15,7,5};
    private IncreaseGoldAction increaseGoldAction;
    private ArrayList<GoldActor> goldCoinList;
    private int MAX_GOLDCOIN = 4;

    public GoldManager(final GameManager gameManager) {
        this.gameManager = gameManager;
        increaseGoldAction = new IncreaseGoldAction(gameManager.dataManager);
        goldCoinList = new ArrayList<GoldActor>();
        randomPositionXList = new ArrayList<Integer>(Arrays.asList(RANDOM_POSITION_X));
    }

    public void addGoldCoin(Vector2 position) {
        if (goldCoinList.size()>=MAX_GOLDCOIN) {
            resetRandomGoldPosition();
            collectAllGoldCoins();
        }

        if (randomPositionXList.isEmpty()) {
            resetRandomGoldPosition();
        }
        final GoldActor goldCoin = new GoldActor(position.x+randomPositionXList.get(0), position.y);
        randomPositionXList.remove(0);

        gameManager.playScreen.getHud().sceneLayer.addActor(goldCoin);
        goldCoinList.add(goldCoin);
        goldCoin.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                collectGoldCoin(goldCoin);
                goldCoinList.remove(goldCoin);
                return true;
            }
        });
    }

    private void collectAllGoldCoins(){
        for (int i = 0; i < goldCoinList.size(); i++) {
            collectGoldCoin(goldCoinList.get(i));
        }
        goldCoinList.clear();
    }
    private void resetRandomGoldPosition(){
        randomPositionXList = new ArrayList<Integer>(Arrays.asList(RANDOM_POSITION_X));
        Collections.shuffle(randomPositionXList);
    }

    public void collectGoldCoin(GoldActor goldActor) {
        goldActor.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH, Constants.V_HEIGHT, MOVE_DURATION), Actions.sequence(Actions.fadeOut(FADE_OUT_DURATION), Actions.run(increaseGoldAction),Actions.removeActor()))));

        ValueDTO goldValue = gameManager.dataManager.calculateGoldPerMonster();
        String value = gameManager.largeMath.getDisplayValue(goldValue);
        Label goldValueLabel = new Label("+ "+value ,gameManager.ressourceManager.getSkin());
        goldValueLabel.setPosition(goldActor.getX(),goldActor.getY());
        gameManager.playScreen.getHud().sceneLayer.addActor(goldValueLabel);
        goldValueLabel.addAction(Actions.sequence(Actions.parallel(Actions.moveBy(0,50,1f),Actions.fadeOut(0.5f)),Actions.removeActor()));
    }
}
