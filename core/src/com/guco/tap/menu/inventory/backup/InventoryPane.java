package com.guco.tap.menu.inventory.backup;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.entity.Item;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.inventory.element.InventoryElement;

public class InventoryPane extends Group {
/*    private GameManager gameManager;
    private TextureRegionDrawable selectedRegionDrawable, backgroundRegionDrawable;
    private ScrollPane pane;
    private VerticalGroup weaponVG, bodyVG, headVG, activeTab;

    public InventoryPane(GameManager gameManager) {
        this.gameManager = gameManager;

        selectedRegionDrawable = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.greyTexture));
        selectedRegionDrawable.setMinHeight(1);
        selectedRegionDrawable.setMinWidth(1);

        backgroundRegionDrawable= new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.lightGreyTexture));
        backgroundRegionDrawable.setMinHeight(1);
        backgroundRegionDrawable.setMinWidth(1);


        initTabs();

        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.getScrollTexture(), 10, 50));
        pane = new ScrollPane(activeTab, paneStyle);
        pane.setScrollingDisabled(true, false);
    }

    public void initTabs() {

        bodyVG = new VerticalGroup();
        //bodyVG.space(5f);
        for (int i = 0; i < gameManager.ressourceManager.bodyList.size(); i++) {
            final InventoryElement inventoryElement = new InventoryElement(gameManager, this);
            final Item item = gameManager.ressourceManager.bodyList.get(i);
            inventoryElement.initItemMenuElement(item);
            inventoryElement.setBackground(backgroundRegionDrawable);
            inventoryElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    //setSelectedItem(inventoryElement);
                    return true;
                }});
            bodyVG.addActor(inventoryElement);
        }

        weaponVG = new VerticalGroup();
        //weaponVG.space(10f);
        for (int i = 0; i < gameManager.ressourceManager.weaponList.size(); i++) {
            Item item = gameManager.ressourceManager.weaponList.get(i);
            final InventoryElement inventoryElement = new InventoryElement(gameManager, this);
            inventoryElement.initItemMenuElement(item);
            inventoryElement.setBackground(backgroundRegionDrawable);
            inventoryElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    setSelectedItem(inventoryElement);
                    return true;
                }});
            weaponVG.addActor(inventoryElement);
        }

        headVG = new VerticalGroup();
        //headVG.space(10f);
        for (int i = 0; i < gameManager.ressourceManager.helmList.size(); i++) {
            final Item item = gameManager.ressourceManager.helmList.get(i);
            final InventoryElement inventoryElement = new InventoryElement(gameManager, this);
            inventoryElement.initItemMenuElement(gameManager.ressourceManager.helmList.get(i));
            inventoryElement.setBackground(backgroundRegionDrawable);
            inventoryElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    setSelectedItem(inventoryElement);
                    return true;
                }});
            headVG.addActor(inventoryElement);
        }
    }*/
}
