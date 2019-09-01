package com.guco.tap.menu.inventory.alternate;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.entity.Item;
import com.guco.tap.manager.GameManager;

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
    private Label levelReqLabel;
    private Label descriptionLabel;
    private Image skillIcon;
    private TextButton unlockButton;
    private TextButton equipButton;
    public TextButton sellButton;
    private TextButton perkButton;
    private String ICON_PATH = "sprites/icon/";
    private String DAMAGE_LABEL = "Damage ";

    private InventoryPane inventoryPane;
    private Stack overlapButtons;
    private Stack overlapLabel;
    public Item itemSource;
    private Table lockedItemContent;

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

        itemNameLabel = new Label("", gameManager.assetsManager.getSkin());
        itemNameLabel.setText(itemSource.name);
        itemNameLabel.setWrap(true);
        itemNameLabel.setFontScale(0.7f);

        damageLabel = new Label("", gameManager.assetsManager.getSkin());
        damageLabel.setFontScale(0.7f);

        levelLabel = new Label("Lv "+currentLevel, gameManager.assetsManager.getSkin());
        overlapLabel = new Stack();
        levelReqLabel = new Label("Req Lv "+itemSource.reqLvl, gameManager.assetsManager.getSkin());
        descriptionLabel = new Label(itemSource.description, gameManager.assetsManager.getSkin());
        overlapLabel.add(levelReqLabel);
        overlapLabel.add(damageLabel);

        upgradeButton = new TextButton("",gameManager.assetsManager.getModuleMenuUpgradeTxtBtnStyle());
        upgradeButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.playScreen.getHud().showUpgradeMenu(itemSource.id);
                return true;
            }});

        perkButton = new TextButton("PERK", gameManager.assetsManager.getSkin());
        perkButton.getLabel().setFontScale(0.7f);
        perkButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.playScreen.getHud().showUpgradeMenu(itemSource.id);
                return true;
            }});

        unlockButton = new TextButton("UNLOCK", gameManager.assetsManager.getSkin());
        unlockButton.getLabel().setFontScale(0.7f);
        unlockButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                unlockItem();
                return true;
            }
        });
        equipButton = new TextButton("EQUIP", gameManager.assetsManager.getSkin());
        equipButton.getLabel().setFontScale(0.7f);
        equipButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                equipItem();
                return true;
            }
        });
        sellButton = new TextButton("SELL", gameManager.assetsManager.getSkin());
        sellButton.getLabel().setFontScale(0.7f);
        sellButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                confirmSell();
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

        moduleLevelGroup.add(overlapButtons).height(20).width(80).padLeft(10).padBottom(5);
        moduleLevelGroup.row();
        moduleLevelGroup.add(sellButton).height(20).width(80).padLeft(10).padBottom(5);
        moduleLevelGroup.row();
        moduleLevelGroup.add(perkButton).height(20).width(80).padLeft(10);

        //moduleLevelGroup.add(upgradeButton).height(20).width(40).right();

        if (itemSource.level==0){
            sellButton.setVisible(false);
            perkButton.setVisible(false);
        }
        //TODO specialize content for locked item, switch to another content if unlocked
        lockedItemContent = new Table();
        lockedItemContent.add();

        this.add(skillIcon).width(50).height(75);
        this.add(moduleLevelGroup).left();

        update();
    }

    public void unlockItem() {
        boolean unlocked = gameManager.itemManager.unlockItem(itemSource);
        if (unlocked) {
            update();
            showUnlockedItemOption();
        }
    }

    public void showUnlockedItemOption(){
        sellButton.setVisible(true);
        perkButton.setVisible(true);

    }

    public void equipItem(){
        inventoryPane.equipItem(this);
    }

    private void sellItem(){
        this.remove();
    }

    public void confirmSell(){
        Dialog dialog = new Dialog("Warning", gameManager.assetsManager.getSkin(), "dialog") {
            public void result(Object obj) {
                if (obj.equals(true))
                sellItem();
            }
        };
        dialog.text("Are you sure you want to sell it?");
        dialog.button("Yes", true); //sends "true" as the result
        dialog.button("No", false);  //sends "false" as the result
        dialog.key(Input.Keys.ENTER, true); //sends "true" when the ENTER key is pressed
        dialog.show(gameManager.playScreen.getHud().stage);
    }
    public void update() {
        String damage = gameManager.largeMath.getDisplayValue(itemSource.calculatedStat.damageValue, itemSource.calculatedStat.damageCurrency);
        this.levelLabel.setText(String.valueOf("Lv "+itemSource.level));
        this.damageLabel.setText(DAMAGE_LABEL+damage);
        if (gameManager.gameInformation.areaLevel >= itemSource.reqLvl) {
            if (itemSource.level > 0) {
                previewItem(false, true, false, true);
            } else {
                previewItem(true, false, false, false);
                inventoryPane.previewLockedItem(this);
            }
        } else {
            previewItem(false, false, true, false);
            inventoryPane.previewLockedItem(this);
        }

        //        for (int i=0;i<itemSource.selectedUpgrades.size();i++){
//            damageLabel.setText(damageLabel.getText()+itemSource.selectedUpgrades.get(i).name);
//        }
//        this.damageLabel.setText("Damage "+item.calculatedStat.damageValue);
    }

    private void previewItem(boolean b, boolean b2, boolean b3, boolean b4) {
        unlockButton.setVisible(b);
        equipButton.setVisible(b2);
        levelReqLabel.setVisible(b3);
        damageLabel.setVisible(b2);
        upgradeButton.setVisible(b4);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}
