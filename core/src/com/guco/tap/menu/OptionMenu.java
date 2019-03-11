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

        resetButton = new TextButton("reset account",AssetManager.INSTANCE.getSkin());
        resetButton.setDisabled(true);
        resetButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                triggerReset();
                return true;
            }
        });

        weatherButton = new TextButton("disable weather",AssetManager.INSTANCE.getSkin());
        weatherButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                switchWeatherMode();
                return true;
            }
        });

        soundButton = new TextButton("disable sound", AssetManager.INSTANCE.getSkin());
        soundButton.setDisabled(true);
        soundButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                switchSoundMode();
                return true;
            }
        });

        fpsButton = new TextButton("Show FPS",AssetManager.INSTANCE.getSkin());
        fpsButton.setDisabled(true);
        fpsButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                switchFpsMode();
                return true;
            }
        });

        goldButton = new TextButton("Max Gold",AssetManager.INSTANCE.getSkin());
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
        GameInformation.INSTANCE.setOptionSound(!GameInformation.INSTANCE.isOptionSound());
        update();
    }

    public void switchFpsMode(){
        GameInformation.INSTANCE.setOptionFps(!GameInformation.INSTANCE.isOptionFps());
        gameManager.playScreen.getHud().fpsActor.setVisible(GameInformation.INSTANCE.isOptionFps());
        update();
    }

    public void switchWeatherMode(){
        GameInformation.INSTANCE.setOptionWeather(!GameInformation.INSTANCE.isOptionWeather());
        //gameManager.weatherManager.stopAll();
        update();
    }

    public void triggerReset(){
        GameInformation.INSTANCE.reset();
        gameManager.moduleManager.evaluateModuleGeneration();
        gameManager.playScreen.getHud().updateCurrentMenu();
//        gameManager.stationEntity.initModules();
    }

    public void addGoldMode(){
        GameInformation.INSTANCE.setCurrentGold(999);
        GameInformation.INSTANCE.setCurrency(99);
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
        parentTable.add(new Label("***DEBUG***", AssetManager.INSTANCE.getSkin()));
        parentTable.row();
        parentTable.add(resetButton).expandX().left().pad(20);
        parentTable.row();
        parentTable.add(goldButton).left().pad(20);
    }

    @Override
    public void updateOnShow(){
        if (GameInformation.INSTANCE.isOptionWeather()){
            weatherButton.setText("Disable Weather");
        } else {
            weatherButton.setText("Enable Weather");
        }
        if (GameInformation.INSTANCE.isOptionSound()){
            soundButton.setText("Disable Sound");
        } else {
            soundButton.setText("Enable Sound");
        }
        if (GameInformation.INSTANCE.isOptionFps()){
            fpsButton.setText("Hide Fps");
        } else {
            fpsButton.setText("Show Fps");
        }
    }
}
