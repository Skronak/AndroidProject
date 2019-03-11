package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Skronak on 28/09/2016.
 */
public class ScrollingBackground extends Actor {

    private final TextureRegion textureRegion;
    private Rectangle textureRegionBounds1;
    private Rectangle textureRegionBounds2;
    private int speed = 10;

    public ScrollingBackground(String path) {
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal(path)));
        textureRegionBounds1 = new Rectangle(0 - Constants.V_WIDTH, 0, Constants.V_WIDTH, Constants.V_HEIGHT);
        textureRegionBounds2 = new Rectangle(Constants.V_WIDTH, 0, Constants.V_WIDTH, Constants.V_HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (leftBoundsReached(delta)) {
            resetBounds();
        } else {
            updateXBounds(-delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, textureRegionBounds1.x, 200, Constants.V_WIDTH,
                Constants.V_HEIGHT/2);
        batch.draw(textureRegion, textureRegionBounds2.x, 200, Constants.V_WIDTH,
                Constants.V_HEIGHT/2);
    }

    private boolean leftBoundsReached(float delta) {
        return (textureRegionBounds2.x - (delta * speed)) <= 0;
    }

    private void updateXBounds(float delta) {
        textureRegionBounds1.x += delta * speed;
        textureRegionBounds2.x += delta * speed;
    }

    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        textureRegionBounds2 = new Rectangle(Constants.V_WIDTH, 0, Constants.V_WIDTH, Constants.V_HEIGHT);
    }
}