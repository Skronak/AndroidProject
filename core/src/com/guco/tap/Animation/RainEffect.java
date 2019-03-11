package com.guco.tap.Animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.guco.tap.utils.Constants;

/**
 * Created by Skronak on 23/08/2017.
 */

public class RainEffect extends Actor {

    private final TextureRegion textureRegion;
    private Rectangle textureRegionBounds1;
    private Rectangle textureRegionBounds2;
    private int speed = 500;

    public RainEffect() {
        textureRegion = new TextureRegion(new Texture(Gdx.files.internal("sprites/background/rain.png")));
        textureRegionBounds1 = new Rectangle(0 - Constants.V_WIDTH, 100, Constants.V_WIDTH, Constants.V_HEIGHT);
        textureRegionBounds2 = new Rectangle(Constants.V_WIDTH, 100, Constants.V_WIDTH, Constants.V_HEIGHT);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (leftBoundsReached(delta)) {
            resetBounds();
        } else {
            updateYBounds(-delta);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, 0, textureRegionBounds1.y, Constants.V_WIDTH,Constants.V_HEIGHT);
        batch.draw(textureRegion, 0, textureRegionBounds2.y, Constants.V_WIDTH,Constants.V_HEIGHT);
    }

    private boolean leftBoundsReached(float delta) {
        return (textureRegionBounds2.y - (delta * speed)) <= -100;
    }

    private void updateYBounds(float delta) {
        textureRegionBounds1.y += delta * speed;
        textureRegionBounds2.y += delta * speed;
    }

    private void resetBounds() {
        textureRegionBounds1 = textureRegionBounds2;
        textureRegionBounds2 = new Rectangle(Constants.V_HEIGHT, 200, Constants.V_HEIGHT, Constants.V_WIDTH);
    }
}
