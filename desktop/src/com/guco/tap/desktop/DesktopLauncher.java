package com.guco.tap.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.utils.Constants;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width= com.guco.tap.utils.Constants.V_WIDTH;
		config.height= com.guco.tap.utils.Constants.V_HEIGHT;
		config.foregroundFPS = com.guco.tap.utils.Constants.MAX_FPS;
		config.title = Constants.APP_NAME;
		new LwjglApplication(new TapDungeonGame(true,null), config);
	}
}
