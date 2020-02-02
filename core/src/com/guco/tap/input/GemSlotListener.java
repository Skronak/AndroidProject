package com.guco.tap.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.menu.fuse.FuseMenu;

public class GemSlotListener extends ClickListener {
    private FuseMenu fuseMenu;

    public GemSlotListener(FuseMenu fuseMenu) {
        this.fuseMenu = fuseMenu;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        fuseMenu.inventoryPanel.toggle();
        return true;
    };
}
