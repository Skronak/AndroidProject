package com.guco.tap.manager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.guco.tap.action.IncreaseGoldAction;
import com.guco.tap.object.GoldActor;
import com.guco.tap.utils.Constants;

public class GoldManager {

    private GameManager gameManager;
    private float MOVE_DURATION =1.25f;
    private float FADE_OUT_DURATION =1f;// keep .25 between move & fade
    private int[] RANDOM_POSITION_X={30,50,10,-10};
    private int[] RANDOM_POSITION_Y={30,15,7,5};
    private IncreaseGoldAction increaseGoldAction;

    public GoldManager(final GameManager gameManager){
        this.gameManager=gameManager;
        increaseGoldAction = new IncreaseGoldAction(gameManager.dataManager);
    }

    public void addGoldCoin(Vector2 position, int nbCoin) {
        for (int i=0;i<nbCoin;i++){
            GoldActor goldCoin = new GoldActor(position.x, position.y+RANDOM_POSITION_X[i]);
            goldCoin.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH-RANDOM_POSITION_X[i], Constants.V_HEIGHT, MOVE_DURATION), Actions.fadeOut(FADE_OUT_DURATION)), Actions.removeActor()));
            gameManager.playScreen.layer2GraphicObject.addActor(goldCoin);
        }
        GoldActor goldCoin = new GoldActor(position.x, position.y+RANDOM_POSITION_Y[nbCoin]);
        goldCoin.addAction(Actions.sequence(Actions.parallel(Actions.moveTo(Constants.V_WIDTH, Constants.V_HEIGHT, MOVE_DURATION), Actions.sequence(Actions.fadeOut(FADE_OUT_DURATION), Actions.run(increaseGoldAction))),Actions.removeActor()));
        gameManager.playScreen.layer2GraphicObject.addActor(goldCoin);
    }
}
