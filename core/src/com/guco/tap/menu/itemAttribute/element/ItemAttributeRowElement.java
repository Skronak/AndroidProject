package com.guco.tap.menu.itemAttribute.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.guco.tap.entity.Item;
import com.guco.tap.entity.ItemUpgrade;
import com.guco.tap.input.SkillSelectButtonListener;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.itemAttribute.ItemAttributeMenu;

import java.util.ArrayList;

/**
 * Represente un module dans le menu deroulant des modules
 * Created by Skronak on 21/03/2018.
 *
 */

public class ItemAttributeRowElement extends Table {
    private GameManager gameManager;
    public TextButton button;
    private Image icon;
    private ItemAttributeMenu itemAttributeMenu;
    private ArrayList<ItemUpgrade> itemUpgradeList;
    private int tier;

    public ItemAttributeRowElement(GameManager gameManager, ItemAttributeMenu itemAttributeMenu, ArrayList<ItemUpgrade> itemUpgradeList, int tier){
        this.gameManager = gameManager;
        this.itemAttributeMenu = itemAttributeMenu;
        this.itemUpgradeList = itemUpgradeList;
        this.tier = tier;
        initMenuRow();
    }

    /**
     * Methode d'initialise du module a partir de sa position
     * dans la liste de tous les modules.
     * @param
     */
    public void initMenuRow() {
        Texture texture = new Texture(Gdx.files.internal("sprites/icon/skillLevel"+tier+".png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        icon = new Image(texture);
        this.add(icon).height(45).width(80).pad(5);

        for (int i=0;i<itemUpgradeList.size();i++) {
            button = new TextButton("", gameManager.assetsManager.getModuleMenuBuyTxtBtnStyle());
            itemUpgradeList.get(i).upgradeEffect.gameInformation = gameManager.gameInformation;
            Item item = gameManager.assetsManager.weaponList.get(itemUpgradeList.get(i).weaponId);
            button.addListener(new SkillSelectButtonListener(itemAttributeMenu, itemUpgradeList.get(i), item));

            this.add(button).height(45).width(45).padRight(10).padLeft(10);
        }
        update();
    }

    public void update() {
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************
}
