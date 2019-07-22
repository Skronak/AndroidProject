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
    private String DAMAGE_LABEL = "Damage ";

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
        skillIcon = new Image(new Texture(ICON_PATH+itemSource.icon));

        itemNameLabel = new Label("", gameManager.ressourceManager.getSkin());
        itemNameLabel.setText(itemSource.name);
        itemNameLabel.setWrap(true);
        itemNameLabel.setFontScale(0.7f);

        damageLabel = new Label("", gameManager.ressourceManager.getSkin());
        damageLabel.setFontScale(0.7f);

        levelLabel = new Label("Lv "+currentLevel, gameManager.ressourceManager.getSkin());
        overlapLabel = new Stack();
        levelReqLabel = new Label("Req Lv "+itemSource.reqLvl, gameManager.ressourceManager.getSkin());
        descriptionLabel = new Label(itemSource.description, gameManager.ressourceManager.getSkin());
        overlapLabel.add(levelReqLabel);
        overlapLabel.add(damageLabel);

        upgradeButton = new TextButton("",gameManager.ressourceManager.getModuleMenuUpgradeTxtBtnStyle());
        upgradeButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.playScreen.getHud().showUpgradeMenu(itemSource.id);
                return true;
            }});

        unlockButton = new TextButton("UNLOCK", gameManager.ressourceManager.getSkin());
        unlockButton.getLabel().setFontScale(0.7f);
        unlockButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        equipButton = new TextButton("EQUIP", gameManager.ressourceManager.getSkin());
        equipButton.getLabel().setFontScale(0.7f);
        equipButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                equipItem();
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

        update();
    }

    public void equipItem(){
        inventoryPane.equipItem(this);
    }

    public void update() {
        String damage = gameManager.largeMath.getDisplayValue(itemSource.calculatedStat.damageValue, itemSource.calculatedStat.damageCurrency);
        this.levelLabel.setText(String.valueOf("Lv "+itemSource.level));
        this.damageLabel.setText(DAMAGE_LABEL+damage);
        if (gameManager.gameInformation.dungeonLevel >= itemSource.reqLvl) {
            if (itemSource.level > 0) {
                previewItem(false, true, false);
            } else {
                previewItem(true, false, false);
                inventoryPane.previewLockedItem(this);
            }
        } else {
            previewItem(false, false, true);
            inventoryPane.previewLockedItem(this);
        }

        //        for (int i=0;i<itemSource.selectedUpgrades.size();i++){
//            damageLabel.setText(damageLabel.getText()+itemSource.selectedUpgrades.get(i).name);
//        }
//        this.damageLabel.setText("Damage "+item.calculatedStat.damageValue);
    }

    private void previewItem(boolean b, boolean b2, boolean b3) {
        unlockButton.setVisible(b);
        equipButton.setVisible(b2);
        levelReqLabel.setVisible(b3);
        damageLabel.setVisible(b2);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}
