package com.guco.tap.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.screen.SplashScreen;
import com.guco.tap.screen.TestScreen;

public class TapDungeonGame extends Game {
	private PlayScreen playScreen;
	private SplashScreen splashScreen;
	private boolean devMode;

    public TapDungeonGame(boolean devMode){
        this.devMode=devMode;
    }

	@Override
	public void create () {
		if(devMode) {
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
			playScreen = new PlayScreen();
			setScreen(playScreen);
		} else {
			Gdx.app.setLogLevel(Application.LOG_ERROR);
			splashScreen=new SplashScreen(this);
			setScreen(splashScreen);
		}
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		playScreen.dispose();
	}
}