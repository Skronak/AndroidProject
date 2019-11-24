package com.guco.tap.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.manager.AreaManager;
import com.guco.tap.manager.AssetsManager;
import com.guco.tap.manager.GameInformationManager;
import com.guco.tap.manager.GameManager;
import com.guco.tap.manager.ItemManager;
import com.guco.tap.screen.Hud;
import com.guco.tap.screen.LoadingScreen;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.screen.SplashScreen;
import com.guco.tap.utils.LargeMath;

public class TapDungeonGame extends Game {
	public PlayScreen playScreen;
	public SplashScreen splashScreen;
	public LoadingScreen loadingScreen;
	public GameManager gameManager;
	public AssetsManager assetsManager;
	public GameInformationManager gameInformationManager;
	public AreaManager areaManager;
	private boolean devMode;
    public GameInformation gameInformation;
	public LargeMath largeMath;
	public SpriteBatch sb;

	public Hud hud;

	public TapDungeonGame(boolean devMode){
        this.devMode=devMode;
    }
	public ItemManager itemManager;

	@Override
	public void create () {
		largeMath = new LargeMath();
    	itemManager = new ItemManager(this);
        assetsManager = new AssetsManager();
		areaManager = new AreaManager(this);
		gameInformationManager = new GameInformationManager(assetsManager, itemManager);
		loadingScreen = new LoadingScreen(this);
		loadingScreen.loadAsset();

        gameInformationManager.loadGameData();
        gameInformation = gameInformationManager.gameInformation;

        if(devMode) {
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
			sb = new SpriteBatch();

			gameManager = new GameManager(this);
			hud = new Hud(this);
			playScreen = new PlayScreen(this);
//			AreaScreen areaScreen = new AreaScreen(this);
			gameManager.playScreen = playScreen;
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
		Gdx.graphics.setTitle("FPS: "+Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void dispose () {
		gameInformationManager.saveData();
		playScreen.dispose();
	}

}