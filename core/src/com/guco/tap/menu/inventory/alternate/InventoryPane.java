package com.guco.tap.menu.inventory.alternate;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.entity.Item;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.MenuState;

public class InventoryPane extends Container {
    private GameManager gameManager;
    private TextureRegionDrawable selectedRegionDrawable, backgroundRegionDrawable;
    private ScrollPane pane;
    private VerticalGroup weaponTab, bodyTab, headTab, activeTab;
    private InventoryMenu inventoryMenu;
    public InventoryElement selectedItemElement;
    private ScrollPane.ScrollPaneStyle paneStyle;

    public InventoryPane(GameManager gameManager, InventoryMenu inventoryMenu) {
        this.gameManager = gameManager;
        this.inventoryMenu = inventoryMenu;

        initStyle();
        initTabs();

        activeTab=weaponTab;
        pane = new ScrollPane(activeTab, paneStyle);
        pane.setScrollingDisabled(true, false);
        setActor(pane);
    }

    public void initStyle(){
        paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.getScrollTexture(), 10, 50));

        selectedRegionDrawable = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.greyTexture));
        selectedRegionDrawable.setMinHeight(1);
        selectedRegionDrawable.setMinWidth(1);
        backgroundRegionDrawable= new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.lightGreyTexture));
        backgroundRegionDrawable.setMinHeight(1);
        backgroundRegionDrawable.setMinWidth(1);
    }

    public void initTabs() {
        bodyTab = new VerticalGroup();
        bodyTab.space(5f);
        for (int i = 0; i < gameManager.assetsManager.bodyList.size(); i++) {
            final InventoryElement inventoryElement = new InventoryElement(gameManager, this);
            final Item item = gameManager.assetsManager.bodyList.get(i);
            inventoryElement.initItemMenuElement(item);
            inventoryElement.setBackground(backgroundRegionDrawable);
            inventoryElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    previewItem(inventoryElement);
                    return true;
                }});
            bodyTab.addActor(inventoryElement);
        }

        weaponTab = new VerticalGroup();
        weaponTab.space(5f);
        for (int i = 0; i < gameManager.assetsManager.weaponList.size(); i++) {
            Item item = gameManager.assetsManager.weaponList.get(i);
            final InventoryElement inventoryElement = new InventoryElement(gameManager, this);
            inventoryElement.initItemMenuElement(item);
            inventoryElement.setBackground(backgroundRegionDrawable);
            inventoryElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    previewItem(inventoryElement);
                    return true;
                }});
            //add lockedItem
            weaponTab.addActor(inventoryElement);
        }

        headTab = new VerticalGroup();
        headTab.space(5f);
        for (int i = 0; i < gameManager.assetsManager.helmList.size(); i++) {
            final Item item = gameManager.assetsManager.helmList.get(i);
            final InventoryElement inventoryElement = new InventoryElement(gameManager, this);
            inventoryElement.initItemMenuElement(item);
            inventoryElement.setBackground(backgroundRegionDrawable);
            inventoryElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    previewItem(inventoryElement);
                    return true;
                }});
            headTab.addActor(inventoryElement);
        }
    }

    public void setDefaultEquipedItem(){
        for (int i=0;i<headTab.getChildren().size;i++){
            if ( ((InventoryElement) headTab.getChildren().get(i)).itemSource.id == gameManager.gameInformation.equipedHead) {
                inventoryMenu.menuState=MenuState.HEAD;
                equipItem((InventoryElement) headTab.getChildren().get(i));
            }
        }
        for (int i=0;i<bodyTab.getChildren().size;i++){
            if ( ((InventoryElement) bodyTab.getChildren().get(i)).itemSource.id == gameManager.gameInformation.equipedBody) {
                inventoryMenu.menuState=MenuState.BODY;
                equipItem((InventoryElement) bodyTab.getChildren().get(i));
            }
        }
        for (int i=0;i<weaponTab.getChildren().size;i++){
            if ( ((InventoryElement) weaponTab.getChildren().get(i)).itemSource.id == gameManager.gameInformation.equipedWeapon.id) {
                inventoryMenu.menuState=MenuState.WEAPON;
                equipItem((InventoryElement) weaponTab.getChildren().get(i));
            }
        }
    }

    public void switchTab() {
        int itemId;
        if (inventoryMenu.menuState.equals(MenuState.WEAPON)){
            itemId = gameManager.gameInformation.equipedWeapon.id;
            activeTab = weaponTab;
        } else if (inventoryMenu.menuState.equals(MenuState.BODY)){
            itemId = gameManager.gameInformation.equipedBody;
            activeTab = bodyTab;
        } else {
            itemId = gameManager.gameInformation.equipedHead;
            activeTab = headTab;
        }
        pane.setActor(activeTab);
        selectedItemElement = (InventoryElement) activeTab.getChildren().get(itemId);
        inventoryMenu.previewItemDetail(selectedItemElement);
    }

    public void equipItem(InventoryElement inventoryElement) {
        selectedItemElement = inventoryElement;
        for( int i=0; i<activeTab.getChildren().size;i++) {
            ((InventoryElement) activeTab.getChildren().get(i)).setBackground(backgroundRegionDrawable);
            //((InventoryElement) activeTab.getChildren().get(i)).sellButton.setVisible(true);
        }
        selectedItemElement.setBackground(selectedRegionDrawable);
        selectedItemElement.sellButton.setVisible(false);
        inventoryMenu.equipItem(selectedItemElement);
        inventoryMenu.upgradeButton.setVisible(true);
    }

    public void previewItem(InventoryElement inventoryElement) {
        selectedItemElement = inventoryElement;
        inventoryMenu.previewItemDetail(inventoryElement);
        inventoryMenu.upgradeButton.setVisible(true);
    }

    public void previewLockedItem(InventoryElement inventoryElement) {
        selectedItemElement = inventoryElement;
        inventoryMenu.upgradeButton.setVisible(false);
    }
}

