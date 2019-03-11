package com.guco.tap.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.utils.Constants;

/**
 * Created by Skronak on 22/11/2017.
 */
//TODO faire apparaitre objet en fonction du bouton cliqué
public class CameraDragListener extends InputListener {
    private float tapSquareSize = 14, touchDownX = -1, touchDownY = -1, stageTouchDownX = -1, stageTouchDownY = -1;
    private int pressedPointer = -1;
    private int button;
    private boolean dragging;
    private float deltaX, deltaY;
    private PlayScreen playScreen;

    public CameraDragListener(PlayScreen playScreen){
        this.playScreen = playScreen;
    }

    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        if (pressedPointer != -1) return false;
        if (pointer == 0 && this.button != -1 && button != this.button) return false;
        pressedPointer = pointer;
        touchDownX = x;
        touchDownY = y;
        stageTouchDownX = event.getStageX();
        stageTouchDownY = event.getStageY();

        event.stop();

        return false;
    }

    public void touchDragged (InputEvent event, float x, float y, int pointer) {
        if (pointer != pressedPointer) return;
        if (!dragging && (Math.abs(touchDownX - x) > tapSquareSize || Math.abs(touchDownY - y) > tapSquareSize)) {
            dragging = true;
            dragStart(event, x, y, pointer);
            deltaX = x;
            deltaY = y;
        }
        if (dragging) {
            deltaX -= x;
            deltaY -= y;
            drag(event, x, y, pointer);
            deltaX = x;
            deltaY = y;
        }
    }

    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == pressedPointer) {
            if (dragging) dragStop(event, x, y, pointer);
            cancel();
        }
    }

    public void dragStart (InputEvent event, float x, float y, int pointer) {
        Gdx.app.log("parentActor", "dragStart");
    }

    /**
     * Move camera according to the mouse/touch
     *
     * @param event
     * @param x
     * @param y
     * @param pointer
     */
    public void drag(InputEvent event, float x, float y, int pointer) {
        Gdx.app.debug("camera", String.valueOf(playScreen.camera.position.y));
        float translateY = deltaY * (playScreen.camera.viewportHeight / Constants.V_HEIGHT);

        // check bond
        if (playScreen.camera.position.y + translateY < Constants.CAMERA_MAX_Y && playScreen.camera.position.y + translateY > Constants.CAMERA_MIN_Y ) {
            playScreen.camera.translate(0, translateY);
            playScreen.camera.update();
        }
    }

    /**
     * Add actor to the stage
     * @param event
     * @param x
     * @param y
     * @param pointer
     */
    public void dragStop (InputEvent event, float x, float y, int pointer) {

    }

    /* If a drag is in progress, no further drag methods will be called until a new drag is started. */
    public void cancel () {
        dragging = false;
        pressedPointer = -1;
    }
}
