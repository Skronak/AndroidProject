package com.guco.tap.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.guco.tap.manager.GameManager;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;

import java.util.Random;

/**
 * Created by Skronak on 29/01/2017.
 * Listener des input sur le Playscreen
 */
public class TapInputProcessor implements InputProcessor {
    private GameManager gameManager;

    public TapInputProcessor(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public boolean keyDown(int keycode) {
        if ((keycode == Input.Keys.ESCAPE) || (keycode == Input.Keys.BACK) ) {
            gameManager.exitGame();
        }
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
        if (gameManager.currentState.equals(GameState.IN_GAME)) {
            gameManager.hitEnemy(screenX, screenY);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        gameManager.gameInformation.totalTapNumber=(gameManager.gameInformation.totalTapNumber+1);
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
