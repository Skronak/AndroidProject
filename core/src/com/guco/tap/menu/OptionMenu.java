package com.guco.tap.menu;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;

/**
 * Menu to change game settings
 */
public class OptionMenu extends AbstractMenu {

    private TextButton weatherButton;
    private TextButton soundButton;
    private TextButton resetButton;
    private TextButton fpsButton;
    private TextButton goldButton;

    public OptionMenu(GameManager gameManager) {
        super(gameManager);

        resetButton = new TextButton("reset account",gameManager.assetManager.getSkin());
        resetButton.setDisabled(true);
        resetButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                triggerReset();
                return true;
            }
        });

        weatherButton = new TextButton("disable weather",gameManager.assetManager.getSkin());
        weatherButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                switchWeatherMode();
                return true;
            }
        });

        soundButton = new TextButton("disable sound", gameManager.assetManager.getSkin());
        soundButton.setDisabled(true);
        soundButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                switchSoundMode();
                return true;
            }
        });

        fpsButton = new TextButton("Show FPS",gameManager.assetManager.getSkin());
        fpsButton.setDisabled(true);
        fpsButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                switchFpsMode();
                return true;
            }
        });

        goldButton = new TextButton("Max Gold",gameManager.assetManager.getSkin());
        goldButton.setDisabled(true);
        goldButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                addGoldMode();
                return true;
            }
        });

        customizeMenuTable();
    }


    public void switchSoundMode(){
        gameManager.gameInformation.setOptionSound(!gameManager.gameInformation.isOptionSound());
        update();
    }

    public void switchFpsMode(){
        gameManager.gameInformation.setOptionFps(!gameManager.gameInformation.isOptionFps());
        gameManager.playScreen.getHud().fpsActor.setVisible(gameManager.gameInformation.isOptionFps());
        update();
    }

    public void switchWeatherMode(){
        gameManager.gameInformation.setOptionWeather(!gameManager.gameInformation.isOptionWeather());
        //gameManager.weatherManager.stopAll();
        update();
    }

    public void triggerReset(){
        gameManager.gameInformation.reset();
        gameManager.moduleManager.evaluateModuleGeneration();
        gameManager.playScreen.getHud().updateCurrentMenu();
//        gameManager.stationEntity.initModules();
    }

    public void addGoldMode(){
        gameManager.gameInformation.setCurrentGold(999);
        gameManager.gameInformation.setCurrency(99);
    }

    public void customizeMenuTable() {
        parentTable.add(new Label("OPTION", skin)).top();
        parentTable.row();
        parentTable.add(weatherButton).expandX().left().pad(20);
        parentTable.row();
        parentTable.add(soundButton).left().pad(20);
        parentTable.row();
        parentTable.add(fpsButton).left().pad(20);
        parentTable.row();
        parentTable.add(new Label("***DEBUG***", gameManager.assetManager.getSkin()));
        parentTable.row();
        parentTable.add(resetButton).expandX().left().pad(20);
        parentTable.row();
        parentTable.add(goldButton).left().pad(20);
    }

    @Override
    public void updateOnShow(){
        if (gameManager.gameInformation.isOptionWeather()){
            weatherButton.setText("Disable Weather");
        } else {
            weatherButton.setText("Enable Weather");
        }
        if (gameManager.gameInformation.isOptionSound()){
            soundButton.setText("Disable Sound");
        } else {
            soundButton.setText("Enable Sound");
        }
        if (gameManager.gameInformation.isOptionFps()){
            fpsButton.setText("Hide Fps");
        } else {
            fpsButton.setText("Show Fps");
        }
    }
}
