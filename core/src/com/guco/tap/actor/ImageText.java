package com.guco.tap.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

public class ImageText extends Table {
    private Label label;
    private Image overlayImage;
    private boolean isSelected = false;

    public ImageText(Texture mainTexture, Texture overlayTexture, String text, Skin skin) {
        this.label = new Label(text, skin);
        label.setAlignment(Align.center);

        mainTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        overlayTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Image image = new Image(mainTexture);

        Stack stack = new Stack();
        stack.add(image);

        if (overlayTexture != null) {
            overlayImage = new Image(overlayTexture);
            overlayImage.setVisible(false);
            stack.add(overlayImage);
        }
        add(stack);
        stack.add(label);
        label.setAlignment(Align.bottomLeft);

        setSize(getPrefWidth(), getPrefHeight());
    }

    public void toggleSelected() {
        overlayImage.setVisible(!isSelected);
        isSelected =!isSelected;
    }

    public void setText(String text){
        label.setText(text);
    }
}