package com.guco.tap.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class ImageTextActor extends Actor {
    private String text;
    private TextureRegion mainTextureRegion;
    private TextureRegion overlayTextureRegion;
    private boolean isSelected;
    private BitmapFont font;

    public ImageTextActor(Texture mainTexture, Texture overlayTexture, String text, Skin skin) {
        this.text = text;
        this.font = skin.get(Label.LabelStyle.class).font;

        mainTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        overlayTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        overlayTextureRegion = new TextureRegion(overlayTexture);
        mainTextureRegion = new TextureRegion(mainTexture);

    }

    public void toggleSelected() {
        isSelected =!isSelected;
    }

    public void setText(String text){
        this.text = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(mainTextureRegion, getX(), getY(), getOriginX(), getOriginY(),
                getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());

        // TODO Text is broken
        font.draw(batch, text, getX(), getY());

        if(isSelected) {
            batch.draw(overlayTextureRegion, getX(), getY(), getOriginX(), getOriginY(),
                    getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }
}