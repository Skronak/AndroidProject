package com.guco.tap.menu.option;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;

/**
 * Menu to change game settings
 */
public class OptionMenu extends AbstractMenu {

    private TextButton weatherButton;
    private TextButton soundButton;
    private TextButton resetButton;
    private TextButton fpsButton;
    private TextButton goldButton;
    private TextButton debugButton;

    public OptionMenu(GameManager gameManager) {
        super(gameManager);

        resetButton = new TextButton("reset account",gameManager.assetsManager.getSkin());
        resetButton.setDisabled(true);
        resetButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                triggerReset();
                return true;
            }
        });

        weatherButton = new TextButton("disable weather",gameManager.assetsManager.getSkin());
        weatherButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                switchWeatherMode();
                return true;
            }
        });

        soundButton = new TextButton("disable sound", gameManager.assetsManager.getSkin());
        soundButton.setDisabled(true);
        soundButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                switchSoundMode();
                return true;
            }
        });

        fpsButton = new TextButton("Show FPS",gameManager.assetsManager.getSkin());
        fpsButton.setDisabled(true);
        fpsButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                switchFpsMode();
                return true;
            }
        });

        goldButton = new TextButton("Max Gold",gameManager.assetsManager.getSkin());
        goldButton.setDisabled(true);
        goldButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                addGoldMode();
                return true;
            }
        });

        debugButton = new TextButton("Debug",gameManager.assetsManager.getSkin());
        debugButton.setDisabled(true);
        debugButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                debugMode();
                return true;
            }
        });

        customizeMenuTable();
    }


    public void switchSoundMode(){
        gameManager.gameInformation.optionSound=(!gameManager.gameInformation.optionSound);
        update();
    }

    public void switchFpsMode(){
        gameManager.gameInformation.optionFps=(!gameManager.gameInformation.optionFps);
        gameManager.battleScreen.getHud().fpsActor.setVisible(gameManager.gameInformation.optionFps);
        update();
    }

    public void switchWeatherMode(){
        gameManager.gameInformation.optionWeather=(!gameManager.gameInformation.optionWeather);
        //gameManager.weatherManager.stopAll();
        update();
    }

    public void triggerReset(){
        gameManager.gameInformationManager.reset();
//        gameManager.attributeManager.evaluateAttributeGeneration();
        gameManager.battleScreen.getHud().updateCurrentMenu();
    }

    public void addGoldMode(){
        gameManager.gameInformation.currentGoldValue =999;
        gameManager.gameInformation.currentGoldCurrency =99;
    }

    public void debugMode(){
        gameManager.gameInformation.skillPoint =999;
        gameManager.gameInformation.levelBaseGoldValue =99;
        gameManager.gameInformation.levelBaseGoldCurrency =99;
        gameManager.gameInformation.areaLevel =999;
    }

    public void customizeMenuTable() {
        addMenuHeader("OPTION", 2);
        parentTable.add(weatherButton).expandX().left().pad(20);
        parentTable.row();
        parentTable.add(soundButton).left().pad(20);
        parentTable.row();
        parentTable.add(fpsButton).left().pad(20);
        parentTable.row();
        parentTable.add(new Label("***DEBUG***", gameManager.assetsManager.getSkin()));
        parentTable.row();
        parentTable.add(resetButton).expandX().left().pad(10);
        parentTable.row();
        parentTable.add(goldButton).left().pad(10);
        parentTable.row();
        parentTable.add(debugButton).left().pad(10);
    }

    @Override
    public void show(){
        if (gameManager.gameInformation.optionWeather){
            weatherButton.setText("Disable Weather");
        } else {
            weatherButton.setText("Enable Weather");
        }
        if (gameManager.gameInformation.optionSound){
            soundButton.setText("Disable Sound");
        } else {
            soundButton.setText("Enable Sound");
        }
        if (gameManager.gameInformation.optionFps){
            fpsButton.setText("Hide Fps");
        } else {
            fpsButton.setText("Show Fps");
        }

        super.show();
    }
}
