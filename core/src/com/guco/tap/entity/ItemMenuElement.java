package com.guco.tap.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.ItemMenu;

/**
 * Represente un module dans le menu deroulant des modules
 * Created by Skronak on 21/03/2018.
 *
 */

public class ItemMenuElement extends Table {
    private GameManager gameManager;
    private Label itemNameLabel;
    private TextButton upgradeButton;
    private Label activeGoldLabel;
    private Label levelLabel;
    private Label passiveGoldLabel;
    private Label nextPassiveGoldLabel;
    private Image skillIcon;
    private GameInformation gameInformation;
    private TextButton buyButton;
    private TextButton equipButton;
    private String ICON_PATH = "sprites/icon/";
    private ItemMenu itemMenu;

    public ItemEntity itemEntitySource;

    public ItemMenuElement(GameManager gameManager, ItemMenu itemMenu){
        this.gameInformation = gameManager.gameInformation;
        this.gameManager = gameManager;
        this.itemMenu = itemMenu;
    }

    /**
     * Methode d'initialise du module a partir de sa position
     * dans la liste de tous les modules.
     * @param source
     */
    public void initItemMenuElement(final ItemEntity source) {
        itemEntitySource = source;
//        int currentLevel = gameInformation.getUpgradeLevelList().get(i);
        int currentLevel = itemEntitySource.level;
        skillIcon = new Image(new Texture(ICON_PATH+source.icon));
        itemNameLabel = new Label("", gameManager.assetManager.getSkin());
        itemNameLabel.setWrap(true);
        itemNameLabel.setFontScale(0.7f);
        activeGoldLabel = new Label("Damage +0%", gameManager.assetManager.getSkin());
        activeGoldLabel.setFontScale(0.7f);
        passiveGoldLabel = new Label("", gameManager.assetManager.getSkin());
        passiveGoldLabel.setFontScale(0.7f);
        nextPassiveGoldLabel= new Label("", gameManager.assetManager.getSkin());
        nextPassiveGoldLabel.setFontScale(0.9f);
        levelLabel = new Label("Lv "+currentLevel, gameManager.assetManager.getSkin());
        upgradeButton = new TextButton("",gameManager.assetManager.getModuleMenuUpgradeTxtBtnStyle());
        upgradeButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.playScreen.getHud().showUpgradeMenu();
                return true;
            }});

        equipButton = new TextButton("EQUIP", gameManager.assetManager.getSkin());
        equipButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setEquiped();
            return true;
            }
        });

        // Icon enable/disabled
        if (currentLevel==0) {
            //Texture skillTexture = gameManager.assetManager.getDisabledIcon();
            //skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            //skillIcon = new Image(skillTexture);
        }

        // Liste level actuel du module
        Table moduleLevelGroup = new Table();
        moduleLevelGroup.add(itemNameLabel).expandX().left();
        moduleLevelGroup.add(levelLabel).width(50).right();
        moduleLevelGroup.row();
        moduleLevelGroup.add(activeGoldLabel).left();
        moduleLevelGroup.row();
        moduleLevelGroup.add(equipButton).fill().width(50);
        moduleLevelGroup.add(upgradeButton).height(40).width(40).right();
        this.add(skillIcon).width(50).height(75);
        this.add(moduleLevelGroup).left();
        update();
    }

    public void setEquiped(){
        gameManager.playScreen.player.characterMaps[itemEntitySource.mapId] = gameManager.playScreen.player.getEntity().getCharacterMap(itemEntitySource.mapName);
        itemMenu.setEquipedItem(this);
    }

    public void update() {
        itemNameLabel.setText(itemEntitySource.name);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}
