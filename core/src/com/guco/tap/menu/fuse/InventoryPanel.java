package com.guco.tap.menu.fuse;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.guco.tap.utils.Constants;

public class InventoryPanel {
    public Table mainTable;

    public InventoryPanel() {
        float menu_width = Constants.V_WIDTH - Constants.UPDATE_MENU_PAD_EXTERNAL_WIDTH;
        float menu_height = Constants.V_HEIGHT - Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT - (Constants.UPDATE_MENU_PAD_EXTERNAL_HEIGHT);

        this.mainTable = new Table();
        //mainTable.setBackground(menuBackground);
        mainTable.setWidth(menu_width);
        mainTable.setHeight(menu_height);
        mainTable.setPosition(Constants.UPDATE_MENU_PAD_EXTERNAL_WIDTH/2,Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);
        mainTable.setVisible(true);
        mainTable.top();
        mainTable.debug();
    }

}
