package com.guco.tap.menu.inventory.alternate;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.entity.Item;
import com.guco.tap.manager.GameManager;

import java.util.List;

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
    private List<Label> uprageLabelList;
    private Label levelLabel;
    private Label levelReqLabel;
    private Label descriptionLabel;
    private Image skillIcon;
    private TextButton unlockButton;
    private TextButton equipButton;
    private String ICON_PATH = "sprites/icon/";
    private InventoryPane inventoryPane;
    private Stack overlapButtons;
    private Stack overlapLabel;
    public Item itemSource;

    public InventoryElement(GameManager gameManager, InventoryPane inventoryPane){
        this.gameManager = gameManager;
        this.inventoryPane = inventoryPane;
    }

    /**
     * Methode d'initialise du module a partir de sa position
     * dans la liste de tous les modules.
     * @param source
     */
    public void initItemMenuElement(final Item source) {
        itemSource = source;
        int currentLevel = itemSource.level;
        skillIcon = new Image(new Texture(ICON_PATH+source.icon));
        itemNameLabel = new Label("", gameManager.ressourceManager.getSkin());
        itemNameLabel.setText(itemSource.name);
        itemNameLabel.setWrap(true);
        itemNameLabel.setFontScale(0.7f);
        String damage = gameManager.largeMath.getDisplayValue(source.calculatedStat.damageValue, source.calculatedStat.damageCurrency);
        damageLabel = new Label("Damage "+damage, gameManager.ressourceManager.getSkin());
        damageLabel.setFontScale(0.7f);
        levelLabel = new Label("Lv "+currentLevel, gameManager.ressourceManager.getSkin());

        overlapLabel = new Stack();
        levelReqLabel = new Label("Req Lv "+source.reqLvl, gameManager.ressourceManager.getSkin());
        descriptionLabel = new Label(source.description, gameManager.ressourceManager.getSkin());
        overlapLabel.add(levelReqLabel);
        overlapLabel.add(damageLabel);

        upgradeButton = new TextButton("",gameManager.ressourceManager.getModuleMenuUpgradeTxtBtnStyle());
        upgradeButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.playScreen.getHud().showUpgradeMenu(source.id);
                return true;
            }});

        unlockButton = new TextButton("UNLOCK", gameManager.ressourceManager.getSkin());
        unlockButton.getLabel().setFontScale(0.7f);
        equipButton = new TextButton("EQUIP", gameManager.ressourceManager.getSkin());
        equipButton.getLabel().setFontScale(0.7f);
        equipButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                setEquiped();
                return true;
            }
        });
        overlapButtons = new Stack();
        overlapButtons.add(unlockButton);
        overlapButtons.add(equipButton);


        // Liste level actuel du module
        Table moduleLevelGroup = new Table();
        moduleLevelGroup.add(itemNameLabel).expandX().left().width(70);
        moduleLevelGroup.add(levelLabel).width(50).right();
        moduleLevelGroup.row();
        moduleLevelGroup.add(overlapLabel).left().width(50);
        moduleLevelGroup.row();
        moduleLevelGroup.add(overlapButtons).fill().width(50);
        moduleLevelGroup.add(upgradeButton).height(40).width(40).right();

        this.add(skillIcon).width(50).height(75);
        this.add(moduleLevelGroup).left();

        update(itemSource);

    }

    public void setEquiped(){
        inventoryPane.equipItem(this);
    }

    public void update(Item item) {
        this.levelLabel.setText(String.valueOf("Lv "+item.level));

        if (itemSource.level==0) {
            unlockButton.setVisible(true);
            equipButton.setVisible(false);
            levelReqLabel.setVisible(true);
            damageLabel.setVisible(false);
        } else {
            unlockButton.setVisible(false);
            equipButton.setVisible(true);
            levelReqLabel.setVisible(false);
            damageLabel.setVisible(true);
        }

        //        for (int i=0;i<itemSource.selectedUpgrades.size();i++){
//            damageLabel.setText(damageLabel.getText()+itemSource.selectedUpgrades.get(i).name);
//        }
//        this.damageLabel.setText("Damage "+item.calculatedStat.damageValue);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}
