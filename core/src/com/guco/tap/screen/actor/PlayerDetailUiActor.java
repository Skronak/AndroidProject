package com.guco.tap.screen.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.guco.tap.manager.GameManager;

public class PlayerDetailUiActor extends Table {
    GameManager gameManager;

    public PlayerDetailUiActor(GameManager gameManager) {
        this.gameManager = gameManager;
        Image defaultImage = new Image(gameManager.assetsManager.playerInfos);
        this.add(defaultImage).left();
    }

}
