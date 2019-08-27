package com.guco.tap.menu.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 * // TODO: super menu desactivant l'input listener, gerer un state?
 */
public class AreaMenu extends AbstractMenu {

    private Table equipTable;
    private SpriteBatch spriteBatch;
    private Image background;


    public AreaMenu(GameManager gameManager) {
        super(gameManager);

        customizeMenuTable();
    }

    public void postInit(){
        spriteBatch = new SpriteBatch();
    }

    public void customizeMenuTable() {
        addMenuHeader("MENU",1);
        background = new Image(new Texture(Gdx.files.internal("sprites/background/cimetary.jpg")));
        parentTable.row();
        parentTable.add(background).fill();
        parentTable.row();
        ScrollPane pane = createLevelSelect();
        parentTable.add(pane).expandY().bottom();
    }

    private ScrollPane createLevelSelect(){
        Table container = new Table();
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.getScrollTexture(), 10, 50));
        ScrollPane pane = new ScrollPane(container, paneStyle);
        pane.setScrollingDisabled(false, true);
        for (int i = 0; i<12; i++){
            Image levelImage = new Image(new Texture(Gdx.files.internal("sprites/badlogic1.jpg")));
            container.add(levelImage).size(60,120);
        }
        return pane;
    }

    @Override
    public void update() {
    }
}
