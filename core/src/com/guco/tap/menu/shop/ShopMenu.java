package com.guco.tap.menu.shop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 * // TODO: super menu desactivant l'input listener, gerer un state?
 */
public class ShopMenu extends AbstractMenu {

    private Table equipTable;
    private SpriterPlayer spriterPlayer;
    private Drawer drawer;
    private SpriteBatch spriteBatch;

    public ShopMenu(GameManager gameManager) {
        super(gameManager);

        customizeMenuTable();
    }

    public void postInit(){
        spriterPlayer = gameManager.spriterPlayer;
        spriteBatch = new SpriteBatch();
        drawer = gameManager.loadPlayerDrawer(spriteBatch);
    }

    public void customizeMenuTable() {
        equipTable = new Table();
        parentTable.add(new Label("SHOP", skin)).padTop(10).padBottom(40);
        parentTable.row();

        parentTable.add(equipTable);
    }

    @Override
    public void update() {
        if (null == spriterPlayer){
            postInit();
        } else {
            spriterPlayer.update();
        }
        spriteBatch.setProjectionMatrix(gameManager.playScreen.getHud().stage.getCamera().combined);
        spriteBatch.begin();
        drawer.draw(spriterPlayer);
        spriteBatch.end();

    }
}
