package com.guco.tap.menu.shop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Player;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 * // TODO: super menu desactivant l'input listener, gerer un state?
 */
public class ShopMenu extends AbstractMenu {

    private Table equipTable;
    private Player player;
    private Drawer drawer;
    private SpriteBatch spriteBatch;

    public ShopMenu(GameManager gameManager) {
        super(gameManager);

        customizeMenuTable();
    }

    public void postInit(){
        player = gameManager.player;
        spriteBatch = new SpriteBatch();
        drawer = gameManager.loadDrawer(spriteBatch);
    }

    public void customizeMenuTable() {
        equipTable = new Table();
        parentTable.add(new Label("SHOP", skin)).padTop(10).padBottom(40);
        parentTable.row();

        parentTable.add(equipTable);
    }

    @Override
    public void update() {
        if (null == player){
            postInit();
        } else {
            player.update();
        }
        spriteBatch.setProjectionMatrix(gameManager.playScreen.getHud().getStage().getCamera().combined);
        spriteBatch.begin();
        drawer.draw(player);
        spriteBatch.end();

    }
}
