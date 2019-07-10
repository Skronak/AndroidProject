package com.guco.tap.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.Constants;


/**
 * Created by Skronak on 04/08/2017.
 * Default Menu layout used by all menu
 */
public abstract class AbstractMenu {
    protected GameManager gameManager;
    public Table parentTable;
    protected Skin skin;
    protected float menu_width;
    protected float menu_height;
    private TextureRegionDrawable menuBackground;

    public AbstractMenu(GameManager gameManager) {
        this.gameManager = gameManager;
        initMenu();
    }

    public void initMenu() {
        menu_width = Constants.V_WIDTH - Constants.UPDATE_MENU_PAD_EXTERNAL_WIDTH;
        menu_height = Constants.V_HEIGHT - Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT - (Constants.UPDATE_MENU_PAD_EXTERNAL_HEIGHT);
        menuBackground = gameManager.ressourceManager.getMenuBackgroundTexture();
        skin = gameManager.ressourceManager.getSkin();

        // Definition du menu
        parentTable = new Table();
        parentTable.setBackground(menuBackground);
        parentTable.setWidth(menu_width);
        parentTable.setHeight(menu_height);
        parentTable.setPosition(Constants.UPDATE_MENU_PAD_EXTERNAL_WIDTH/2,Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);
        parentTable.setVisible(false);
        parentTable.top();
    }

    /**
     * Called when menu is shown
     */
    public void show() {
        this.parentTable.setVisible(true);
    }

    public void draw() {

    }
    /**
     * Update Menu
     */
    public void update(){

    }

    /**
     * Definition du fond du menu
     * @param fname
     * @return
     */
    private NinePatch getNinePatch(String fname) {
        // Get the image
        final Texture t = new Texture(Gdx.files.internal(fname));
        return new NinePatch( new TextureRegion(t, 1, 1 , t.getWidth() - 2, t.getHeight() - 2), Constants.UPDATE_MENU_PAD_INTERNAL, Constants.UPDATE_MENU_PAD_INTERNAL, Constants.UPDATE_MENU_PAD_INTERNAL, Constants.UPDATE_MENU_PAD_INTERNAL);
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************
}
