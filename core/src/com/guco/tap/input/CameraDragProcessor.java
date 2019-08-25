package com.guco.tap.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.guco.tap.screen.AbstractScreen;

/**
 * Created by Skronak on 20/11/2017.
 *
 * listener de touhé du mainscreen pour le drag
 */
public class CameraDragProcessor implements InputProcessor {

    private AbstractScreen screen;

    public CameraDragProcessor(AbstractScreen screen){
        this.screen = screen;
    }

    @Override
    public boolean keyDown(int keycode) {
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
            float newPos = Gdx.input.getDeltaX();
            screen.stage.getCamera().translate(-newPos, 0f, 0f);
        return true;
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
