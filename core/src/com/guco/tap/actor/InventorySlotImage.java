package com.guco.tap.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.guco.tap.entity.Item;

public class InventorySlotImage extends Table {
    private Image selectOverlayImage, equipeOverlayImage;
    private Item item;
    private Texture itemTexture;

    public InventorySlotImage(Item item, Texture frameTexture, Texture selectOverlay, Texture equipOverlay) {
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

        if(selectOverlay != null) {
            selectOverlay.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            selectOverlayImage = new Image(selectOverlay);
            selectOverlayImage.setVisible(false);
            stack.add(selectOverlayImage);
        }

        if(equipOverlay != null) {
            equipOverlay.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            equipeOverlayImage = new Image(equipOverlay);
            equipeOverlayImage.setVisible(false);
            stack.add(equipeOverlayImage);
        }

        add(stack);
        setSize(getPrefWidth(), getPrefHeight());
    }

    public void selectSlot(boolean isSelected) {
        selectOverlayImage.setVisible(isSelected);
        isSelected = !isSelected;
    }

    public void equipSlot(boolean isEquiped) {
        selectOverlayImage.setVisible(false);
        equipeOverlayImage.setVisible(isEquiped);
    }
}