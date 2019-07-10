package com.guco.tap.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.manager.GameInformationManager;
import com.guco.tap.manager.RessourceManager;
import com.guco.tap.manager.GameManager;
import com.guco.tap.screen.Hud;
import com.guco.tap.screen.LoadingScreen;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.screen.SplashScreen;

public class TapDungeonGame extends Game {
	public PlayScreen playScreen;
	public SplashScreen splashScreen;
	public LoadingScreen loadingScreen;
	public GameManager gameManager;
	public RessourceManager ressourceManager;
	public GameInformationManager gameInformationManager;
	private boolean devMode;
    public GameInformation gameInformation;

    public TapDungeonGame(boolean devMode){
        this.devMode=devMode;
    }

	@Override
	public void create () {
        ressourceManager = new RessourceManager();
		gameInformationManager = new GameInformationManager(ressourceManager);

		loadingScreen = new LoadingScreen(this);
		loadingScreen.loadAsset();
        gameInformationManager.loadGameInformation();
        gameInformation = gameInformationManager.gameInformation;

        if(devMode) {
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
			gameManager = new GameManager(this);
			playScreen = new PlayScreen(gameManager);
			gameManager.playScreen=playScreen;
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
		gameInformationManager.saveInformation();
		playScreen.dispose();
	}
}