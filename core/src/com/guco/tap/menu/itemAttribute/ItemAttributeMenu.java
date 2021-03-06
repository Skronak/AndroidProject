package com.guco.tap.menu.itemAttribute;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.entity.Item;
import com.guco.tap.entity.ItemUpgrade;
import com.guco.tap.entity.TiersUpgrades;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 */
public class ItemAttributeMenu extends AbstractMenu {
    private Label costLabel;
    private Label descriptionLabel;
    private Label levelLabel;
    private Label titleLabel;
    private VerticalGroup scrollContainerVG;
    private Table detailTable;
    private ItemUpgrade selectedItemUpgrade;
    private Item selectedItem;
    private ScrollPane currentPane;
    private List<ScrollPane> weaponUpgradePanes;

    public ItemAttributeMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();
    }

    public void customizeMenuTable() {
        TextButton applySkillButton = new TextButton("Apply",gameManager.assetsManager.getModuleMenuBuyTxtBtnStyle());
        InputListener applyButtonListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                selectedItemUpgrade.upgradeEffect.apply(selectedItem);
                gameManager.gameInformation.unlockedWeaponList.get(selectedItemUpgrade.weaponId).selectedUpgrades.add(selectedItemUpgrade);
                return true;
            }
        };
        applySkillButton.addListener(applyButtonListener);

        levelLabel = new Label("",gameManager.assetsManager.getSkin());
        titleLabel = new Label("",gameManager.assetsManager.getSkin());
        descriptionLabel = new Label("",gameManager.assetsManager.getSkin());

        addMenuHeader("ITEM ATTRIBUTE",1);

        //TODO load pane on click only
        weaponUpgradePanes = new ArrayList<ScrollPane>();
        for (int i = 0; i < gameManager.assetsManager.weaponUpgradeList.size(); i++) {
            ScrollPane weaponUpgradePane = initUpgradePane(gameManager.assetsManager.weaponUpgradeList.get(i));
            weaponUpgradePanes.add(weaponUpgradePane);
        }
        currentPane = weaponUpgradePanes.get(0);


        detailTable = new Table();
        detailTable.add(levelLabel).height(50).width(50).expandX();
        detailTable.row();
        detailTable.add(titleLabel);
        detailTable.row();
        detailTable.add(descriptionLabel);
        detailTable.row();
        detailTable.add(applySkillButton).height(50).width(100).padTop(10).padBottom(10);

        parentTable.add(currentPane);
        parentTable.row();
        parentTable.add(detailTable).fill();
    }

    public void switchWeaponAttribute(int weaponId){
        parentTable.getCell(currentPane).setActor(weaponUpgradePanes.get(weaponId));
        currentPane = weaponUpgradePanes.get(weaponId);
    }
    /**
     * Methode d'initialisation des modules disponibles a
     * l'upgrade
     *
     * @return
     */
    private ScrollPane initUpgradePane(TiersUpgrades tiersUpgrades) {
        scrollContainerVG = new VerticalGroup();
        scrollContainerVG.space(20f);
        scrollContainerVG.padTop(20);
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.getScrollTexture(), 10, 50));

        ScrollPane pane = new ScrollPane(scrollContainerVG, paneStyle);
        pane.setScrollingDisabled(true, false);
        Item item = gameManager.assetsManager.weaponList.get(tiersUpgrades.firstTier.get(0).weaponId);
        ItemAttributeRowElement itemAttributeRowElement = new ItemAttributeRowElement(gameManager,this, tiersUpgrades.firstTier,1);
        scrollContainerVG.addActor(itemAttributeRowElement);
        itemAttributeRowElement = new ItemAttributeRowElement(gameManager,this, tiersUpgrades.secondTier,2);
        scrollContainerVG.addActor(itemAttributeRowElement);
        itemAttributeRowElement = new ItemAttributeRowElement(gameManager,this, tiersUpgrades.thirdTier,3);
        scrollContainerVG.addActor(itemAttributeRowElement);

        return pane;
    }

    public void showSkillDetail(ItemUpgrade itemUpgrade, Item item) {
        titleLabel.setText(itemUpgrade.name);
        descriptionLabel.setText(itemUpgrade.description);
        selectedItemUpgrade = itemUpgrade;
        selectedItem = item;

    }

    /**
     * Update all module buybutton to check if spriterPlayer can click them
     */
    public void updateBuyButton () {
        for (int i = 0; i<gameManager.assetsManager.getAttributeElementList().size(); i++) {
        }
    }

    @Override
    public void update() {
        updateBuyButton();
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************
}
