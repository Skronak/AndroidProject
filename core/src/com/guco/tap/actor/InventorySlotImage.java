package com.guco.tap.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.guco.tap.entity.Item;

public class InventorySlotImage extends Table {
    private Image overlayImage;
    private Item item;
    private Texture itemTexture;

    public InventorySlotImage(Item item, Texture frameTexture, Texture overlayTexture) {
        this.item = item;
        frameTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Image frameImage = new Image(frameTexture);
        Stack stack = new Stack();
        stack.add(frameImage);

        if (item != null) {
            itemTexture = new Texture(Gdx.files.internal("sprites/icon/item/"+item.icon));
            itemTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        } else {
            itemTexture = new Texture(Gdx.files.internal("sprites/icon/item/empty.png"));
        }
        Image itemImage = new Image(itemTexture);
        stack.add(itemImage);

        if(overlayTexture != null) {
            overlayTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            overlayImage = new Image(overlayTexture);
            overlayImage.setVisible(false);
            stack.add(overlayImage);
        }

        add(stack);
        setSize(getPrefWidth(), getPrefHeight());
    }

    public void selectSlot(boolean isSelected) {
        overlayImage.setVisible(isSelected);
        isSelected = !isSelected;
    }
}