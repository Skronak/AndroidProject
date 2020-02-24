package com.guco.tap.menu.inventory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.utils.Constants;

public class CharacterInventoryMenu extends AbstractMenu {

    public CharacterInventoryMenu(TapDungeonGame game) {
        super(game.gameManager);

        customizeMenuTable();

        ScrollPane pane = initPane();
        pane.setSize(parentTable.getWidth(),195);
        pane.setPosition(0,parentTable.getHeight()- Constants.MENU_PAD_EXTERNAL_HEIGHT - Constants.MENU_PAD_INTERNAL - pane.getHeight());
        parentTable.addActor(pane);
    }

    private void customizeMenuTable() {
        addMenuHeader("WEAPONS",1);
    }

    private ScrollPane initPane() {
        Table table = new Table();
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.getScrollTexture(), 10, 50));

        ScrollPane pane = new ScrollPane(table, paneStyle);
        pane.setScrollingDisabled(true, false);

        for (int i = 0; i < 5; i++) {
            for (int y=0;y<5;y++) {
                Image actor = new Image(new Texture("sprites/badlogic1.jpg"));
                table.add(actor).size(50,50).pad(2);
            }
            table.row();
        }

        return pane;
    }

}
