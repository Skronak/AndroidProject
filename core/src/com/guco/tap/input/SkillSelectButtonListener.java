package com.guco.tap.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.entity.ItemEntity;
import com.guco.tap.entity.ItemUpgradeEntity;
import com.guco.tap.menu.itemAttribute.ItemAttributeMenu;

/**
 * Created by Skronak on 29/01/2017.
 * Listener du bouton UPGRADE d'un module
 */
public class SkillSelectButtonListener extends ClickListener {

    private ItemAttributeMenu itemAttributeMenu;
    private ItemEntity itemEntity;
    private ItemUpgradeEntity itemUpgradeEntity;

    public SkillSelectButtonListener(ItemAttributeMenu itemAttributeMenu, ItemUpgradeEntity itemUpgradeEntity, ItemEntity itemEntity) {
        this.itemAttributeMenu = itemAttributeMenu;
        this.itemUpgradeEntity = itemUpgradeEntity;
        this.itemEntity = itemEntity;
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        itemAttributeMenu.showSkillDetail(itemUpgradeEntity, itemEntity);
        event.cancel();
        return true;
    }
}
