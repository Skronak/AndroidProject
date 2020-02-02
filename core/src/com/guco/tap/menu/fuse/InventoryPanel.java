package com.guco.tap.menu.fuse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.manager.GameManager;

public class InventoryPanel {
    public ScrollPane pane;
    public boolean isShown;

    public InventoryPanel(GameManager gameManager) {
        Table fuseInventoryTable = new Table();
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.getScrollTexture(), 10, 50));

        pane = new ScrollPane(fuseInventoryTable, paneStyle);
        pane.setScrollingDisabled(true, false);


        for (int i = 0; i < 5; i++) {
            for (int y = 0; y < 4; y++) {
                Image slotImage = new Image(new Texture(Gdx.files.local("sprites/icon/empty_slot.png")));
                fuseInventoryTable.add(slotImage).size(55, 55);
            }
            fuseInventoryTable.row();
        }
    }

    public void toggle() {
        if (isShown) {
            pane.addAction(Actions.moveTo(0, -pane.getHeight(), 0.2f, Interpolation.exp5In));
            isShown=false;
        } else {
            pane.addAction(Actions.moveTo(0, -60, 0.2f, Interpolation.exp5Out));
            isShown=true;
        }
    }
}
