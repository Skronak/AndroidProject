package com.guco.tap.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;
import com.guco.tap.manager.AssetManager;


/**
 * Fps counter that auto update itself each draw call
 */
public class FpsActor extends com.badlogic.gdx.scenes.scene2d.ui.Label {
    private long lastTimeCounted;
    private float sinceChange;

    public FpsActor(AssetManager assetManager){
        super("", assetManager.getSkin());
        lastTimeCounted = TimeUtils.millis();
        sinceChange = 0;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        long delta = TimeUtils.timeSinceMillis(lastTimeCounted);
        lastTimeCounted = TimeUtils.millis();
        sinceChange += delta;

        if(sinceChange >= 1000) {
            sinceChange = 0;
            setText(String.valueOf(Gdx.graphics.getFramesPerSecond()));
        }
        super.draw(batch, parentAlpha);
    }

}
