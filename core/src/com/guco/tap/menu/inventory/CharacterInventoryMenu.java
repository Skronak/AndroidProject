package com.guco.tap.menu.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.menu.AbstractMenu;

public class CharacterInventoryMenu extends AbstractMenu {

    public CharacterInventoryMenu(TapDungeonGame game) {
        super(game.gameManager);

        customizeMenuTable();
    }

    private void customizeMenuTable() {
        parentTable.add(new Image(new Texture("sprites/badlogic1.jpg"))).size(50,50);
        parentTable.add(new Image(new Texture("sprites/badlogic1.jpg"))).size(50,50);
        parentTable.add(new Image(new Texture("sprites/badlogic1.jpg"))).size(50,50);
        parentTable.add(new Image(new Texture("sprites/badlogic1.jpg"))).size(50,50);
    }

}
