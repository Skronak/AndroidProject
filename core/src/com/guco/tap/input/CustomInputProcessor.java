package com.guco.tap.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.manager.GameManager;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;

import java.util.Random;

/**
 * Created by Skronak on 29/01/2017.
 * Listener des input sur le Playscreen
 */
public class CustomInputProcessor implements InputProcessor {

    private Random random;
    private PlayScreen playScreen;
    private GameManager gameManager;

    public CustomInputProcessor(PlayScreen playScreen) {
        this.playScreen = playScreen;
        this.random = new Random();
        this.gameManager = playScreen.getGameManager();
    }

    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) )
            gameManager.gameInformation.saveInformation();
            Gdx.app.debug("Closing application", "close");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.app.debug("touch",String.valueOf(playScreen.getMousePosInGameWorld().x + " // "+playScreen.getMousePosInGameWorld().y));

        if (gameManager.currentState.equals(GameState.IN_GAME)) {
            int randCritical = random.nextInt(Constants.CRITICAL_CHANCE) + 1;
            playScreen.processHit();
            if (randCritical == 1) {
//                gameManager.ressourceManager.increaseGoldCritical();
                playScreen.processCriticalHit(gameManager.getCriticalValue());
            } else {
//                gameManager.ressourceManager.increaseGoldActive();
                playScreen.processNormalHit();
            }
            playScreen.processPointerHitAnimation(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        gameManager.gameInformation.setTotalTapNumber(gameManager.gameInformation.getTotalTapNumber()+1);

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
