package com.guco.tap.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.entity.Item;
import com.guco.tap.entity.ItemUpgrade;
import com.guco.tap.menu.itemAttribute.ItemAttributeMenu;

/**
 * Created by Skronak on 29/01/2017.
 * Listener du bouton UPGRADE d'un module
 */
public class SkillSelectButtonListener extends ClickListener {

    private ItemAttributeMenu itemAttributeMenu;
    private Item item;
    private ItemUpgrade itemUpgrade;

    public SkillSelectButtonListener(ItemAttributeMenu itemAttributeMenu, ItemUpgrade itemUpgrade, Item item) {
        this.itemAttributeMenu = itemAttributeMenu;
        this.itemUpgrade = itemUpgrade;
        this.item = item;
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        itemAttributeMenu.showSkillDetail(itemUpgrade, item);
        event.cancel();
        return true;
    }
}
