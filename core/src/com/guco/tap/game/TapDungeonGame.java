package com.guco.tap.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guco.tap.ad.AdController;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.manager.AreaManager;
import com.guco.tap.manager.AssetsManager;
import com.guco.tap.manager.GameInformationManager;
import com.guco.tap.manager.GameManager;
import com.guco.tap.manager.ItemManager;
import com.guco.tap.screen.BattleScreen;
import com.guco.tap.screen.Hud;
import com.guco.tap.screen.LoadingScreen;
import com.guco.tap.screen.SplashScreen;
import com.guco.tap.utils.LargeMath;


public class TapDungeonGame extends Game {

	public BattleScreen battleScreen;
	public LoadingScreen loadingScreen;
	public GameManager gameManager;
	public AssetsManager assetsManager;
	public GameInformationManager gameInformationManager;
	public AreaManager areaManager;
	private boolean devMode;
    public GameInformation gameInformation;
	public LargeMath largeMath;
	public SpriteBatch sb;
	public AdController adController;
	public ItemManager itemManager;
	public Hud hud;

	public TapDungeonGame(boolean devMode, AdController adController) {
		this.adController = adController;
        this.devMode=devMode;
    }

	@Override
	public void create () {
		loadingScreen = new LoadingScreen(this);

        if(devMode) {
			Gdx.app.setLogLevel(Application.LOG_DEBUG);
			loadingScreen.load();
			setScreen(battleScreen);
		} else {
			Gdx.app.setLogLevel(Application.LOG_ERROR);
			setScreen(new SplashScreen(this));
		}
	}

	@Override
	public void render () {
		super.render();
		Gdx.graphics.setTitle("FPS: " + Gdx.graphics.getFramesPerSecond());
	}

	@Override
	public void dispose () {
		gameInformationManager.saveData();
		battleScreen.dispose();
	}

}