package com.guco.tap.menu.inventory.element;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.inventory.InventoryMenu;

/**
 * Represente un module dans le menu deroulant des modules
 * Created by Skronak on 21/03/2018.
 *
 */

public class InventoryElement extends Table {
    private GameManager gameManager;
    private Label itemNameLabel;
    private TextButton upgradeButton;
    private Label damageLabel;
    private Label levelLabel;
    private Label passiveGoldLabel;
    private Label nextPassiveGoldLabel;
    private Image skillIcon;
    private GameInformation gameInformation;
    private TextButton buyButton;
    private TextButton equipButton;
    private String ICON_PATH = "sprites/icon/";
    private InventoryMenu inventoryMenu;

    public Item itemSource;

    public InventoryElement(GameManager gameManager, InventoryMenu inventoryMenu){
        this.gameInformation = gameManager.gameInformation;
        this.gameManager = gameManager;
        this.inventoryMenu = inventoryMenu;
    }

    /**
     * Methode d'initialise du module a partir de sa position
     * dans la liste de tous les modules.
     * @param source
     */
    public void initItemMenuElement(final Item source) {
        itemSource = source;
//        int currentLevel = gameInformation.getUpgradeLevelList().get(i);
        int currentLevel = itemSource.level;
        skillIcon = new Image(new Texture(ICON_PATH+source.icon));
        itemNameLabel = new Label("", gameManager.ressourceManager.getSkin());
        itemNameLabel.setWrap(true);
        itemNameLabel.setFontScale(0.7f);
        damageLabel = new Label("Damage +0%", gameManager.ressourceManager.getSkin());
        damageLabel.setFontScale(0.7f);
        passiveGoldLabel = new Label("", gameManager.ressourceManager.getSkin());
        passiveGoldLabel.setFontScale(0.7f);
        nextPassiveGoldLabel= new Label("", gameManager.ressourceManager.getSkin());
        nextPassiveGoldLabel.setFontScale(0.9f);
        levelLabel = new Label("Lv "+currentLevel, gameManager.ressourceManager.getSkin());
        upgradeButton = new TextButton("",gameManager.ressourceManager.getModuleMenuUpgradeTxtBtnStyle());
        upgradeButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.playScreen.getHud().showUpgradeMenu(source.id);
                return true;
            }});

        equipButton = new TextButton("EQUIP", gameManager.ressourceManager.getSkin());
        equipButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setEquiped();
            return true;
            }
        });

        // Icon enable/disabled
        if (currentLevel==0) {
            //Texture skillTexture = gameManager.ressourceManager.getDisabledIcon();
            //skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            //skillIcon = new Image(skillTexture);
        }

        // Liste level actuel du module
        Table moduleLevelGroup = new Table();
        moduleLevelGroup.add(itemNameLabel).expandX().left();
        moduleLevelGroup.add(levelLabel).width(50).right();
        moduleLevelGroup.row();
        moduleLevelGroup.add(damageLabel).left();
        moduleLevelGroup.row();
        moduleLevelGroup.add(equipButton).fill().width(50);
        moduleLevelGroup.add(upgradeButton).height(40).width(40).right();
        this.add(skillIcon).width(50).height(75);
        this.add(moduleLevelGroup).left();
        update();
    }

    public void setEquiped(){
        gameManager.playScreen.spriterPlayer.characterMaps[itemSource.mapId] = gameManager.playScreen.spriterPlayer.getEntity().getCharacterMap(itemSource.mapName);
        inventoryMenu.setEquipedItem(this);
    }

    public void increaseLevel(Item item) {
        this.levelLabel.setText(String.valueOf("Lv "+item.level));
        this.damageLabel.setText("Damage "+item.calculatedStat.damageValue);
    }
    public void update() {
        itemNameLabel.setText(itemSource.name);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}
