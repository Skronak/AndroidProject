package com.guco.tap.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.entity.ItemEntity;
import com.guco.tap.entity.ItemUpgradeEntity;
import com.guco.tap.entity.SkillMenuElement;
import com.guco.tap.entity.UpgradeEffect;
import com.guco.tap.manager.ItemManager;
import com.guco.tap.menu.ItemUpgradeMenu;

/**
 * Created by Skronak on 29/01/2017.
 * Listener du bouton UPGRADE d'un module
 */
public class SkillSelectButtonListener extends ClickListener {

    private ItemUpgradeMenu itemUpgradeMenu;
    private SkillMenuElement skillMenuElement;
    private ItemEntity itemEntity;
    private ItemUpgradeEntity itemUpgradeEntity;

    public SkillSelectButtonListener(SkillMenuElement skillMenuElement, ItemUpgradeMenu itemUpgradeMenu, ItemEntity itemEntity, ItemUpgradeEntity itemUpgradeEntity) {
        this.itemUpgradeMenu = itemUpgradeMenu;
        this.skillMenuElement= skillMenuElement;
        this.itemEntity = itemEntity;
        this.itemUpgradeEntity = itemUpgradeEntity;
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        itemUpgradeMenu.showDetailTable(skillMenuElement);
        itemUpgradeEntity.upgradeEffect.apply(itemEntity);
        event.cancel();
        return true;
    }
}
