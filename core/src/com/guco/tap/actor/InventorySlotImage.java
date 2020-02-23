package com.guco.tap.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class InventorySlotImage extends Table {
    private Image overlayImage;
    private boolean isSelected = false;

    public InventorySlotImage(Texture frameTexture, Texture itemTexture, Texture overlayTexture) {

        frameTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        itemTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image frameImage = new Image(frameTexture);
        Image itemImage = new Image(itemTexture);
        Stack stack = new Stack();
        stack.add(frameImage);
        stack.add(itemImage);

        if(overlayTexture!=null) {
            overlayTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            overlayImage = new Image(overlayTexture);
            overlayImage.setVisible(false);
            stack.add(overlayImage);
        }

        add(stack);
        setSize(getPrefWidth(), getPrefHeight());
    }

    public void toggleSelected() {
        overlayImage.setVisible(!isSelected);
        isSelected =!isSelected;
    }
}