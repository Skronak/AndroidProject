package com.guco.tap.menu.inventory.alternate;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

    public InventoryPane(GameManager gameManager, InventoryMenu inventoryMenu) {
        this.gameManager = gameManager;
        this.inventoryMenu = inventoryMenu;

        initStyle();
        initTabs();

        activeTab=weaponTab;
        pane.setActor(activeTab);
        //maxWidth(160);
        setActor(pane);
    }

    public void initStyle(){
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.getScrollTexture(), 10, 50));
        pane = new ScrollPane(activeTab, paneStyle);
        pane.setScrollingDisabled(true, false);

        selectedRegionDrawable = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.greyTexture));
        selectedRegionDrawable.setMinHeight(1);
        selectedRegionDrawable.setMinWidth(1);
        backgroundRegionDrawable= new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.lightGreyTexture));
        backgroundRegionDrawable.setMinHeight(1);
        backgroundRegionDrawable.setMinWidth(1);
    }

    public void initTabs() {
        bodyTab = new VerticalGroup();
        for (int i = 0; i < gameManager.ressourceManager.bodyList.size(); i++) {
            final InventoryElement inventoryElement = new InventoryElement(gameManager, this);
            final Item item = gameManager.ressourceManager.bodyList.get(i);
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
        for (int i = 0; i < gameManager.ressourceManager.weaponList.size(); i++) {
            Item item = gameManager.ressourceManager.weaponList.get(i);
            final InventoryElement inventoryElement = new InventoryElement(gameManager, this);
            inventoryElement.initItemMenuElement(item);
            inventoryElement.setBackground(backgroundRegionDrawable);
            inventoryElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    previewItem(inventoryElement);
                    return true;
                }});
            inventoryElement.getPrefWidth();
            weaponTab.addActor(inventoryElement);
        }

        headTab = new VerticalGroup();
        for (int i = 0; i < gameManager.ressourceManager.helmList.size(); i++) {
            final Item item = gameManager.ressourceManager.helmList.get(i);
            final InventoryElement inventoryElement = new InventoryElement(gameManager, this);
            inventoryElement.initItemMenuElement(gameManager.ressourceManager.helmList.get(i));
            inventoryElement.setBackground(backgroundRegionDrawable);
            inventoryElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    previewItem(inventoryElement);
                    return true;
                }});
            headTab.addActor(inventoryElement);
        }
    }

    public void switchTab() {
        int itemId;
        if (inventoryMenu.menuState.equals(MenuState.WEAPON)){
            itemId = gameManager.gameInformation.equipedWeapon;
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
        }
        selectedItemElement.setBackground(selectedRegionDrawable);
        inventoryMenu.setEquipedItem(selectedItemElement);
    }

    public void previewItem(InventoryElement inventoryElement) {
        selectedItemElement = inventoryElement;
        inventoryMenu.previewItemDetail(inventoryElement);
    }
}

