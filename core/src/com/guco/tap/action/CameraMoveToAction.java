package com.guco.tap.action;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class CameraMoveToAction extends TemporalAction {

    public static CameraMoveToAction action (Camera camera, float duration,
                                             float targetX, float targetY, float targetZ, Interpolation interpolation){
        CameraMoveToAction action = Actions.action(CameraMoveToAction.class);
        action.setCamera(camera);
        action.setDuration(duration);
        action.setPosition(targetX, targetY, targetZ);
        action.setInterpolation(interpolation);
        return action;
    }

    private float startX, startY, startZ;
    private float endX, endY, endZ;
    private Camera camera;

    protected void begin () {
        if (camera == null) camera = actor.getStage().getViewport().getCamera();
        Vector3 position = camera.position;
        startX = position.x;
        startY = position.y;
        startZ = position.z;
    }

    protected void update (float percent) {
        camera.position.set(startX + (endX - startX) * percent,
                startY + (endY - startY) * percent,
                startZ + (endZ - startZ) * percent);
        camera.update();
    }

    public void reset () {
        super.reset();
        camera = null;
    }

    public void setPosition (float x, float y, float z) {
        endX = x;
        endY = y;
        endZ = z;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
