package com.guco.tap.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.manager.GameManager;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 * // TODO: super menu desactivant l'input listener, gerer un state?
 */
public class FactionMenu extends AbstractMenu {

    private Table factionTable;
    private ImageButton factionButton1;
    private ImageButton factionButton2;
    private ImageButton factionButton3;

    public FactionMenu(GameManager gameManager) {
        super(gameManager);
        factionTable = new Table();

        customizeMenuTable();

    }

    public void customizeMenuTable() {
        parentTable.add(new Label("CHOOSE A FACTION", skin)).pad(60);
        parentTable.row();
        factionButton1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sprites/menu/faction_log1.png")))));
        factionButton2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sprites/menu/faction_log2.png")))));
        factionButton3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sprites/menu/faction_log3.png")))));
        factionTable.top();
        factionTable.add(factionButton1).width(90).pad(2).top();
        factionTable.add(factionButton2).width(90).pad(2);
        factionTable.add(factionButton3).width(90).pad(2);
        parentTable.add(factionTable);
        factionTable.debug();
    }
}
