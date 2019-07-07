package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.utils.Constants;

import java.util.ArrayList;

public class GameInformationManager {

    private TapDungeonGame game;
    private Json json;
    private String PREF_NAME ="valJson";
    private Preferences prefs;
    public GameInformation gameInformation;

    public GameInformationManager(TapDungeonGame game) {
        this.json = new Json();
        this.game=game;
        prefs = Gdx.app.getPreferences(Constants.APP_NAME);
    }

    /**
     * Sauvegarde les informations courantes
     * dans le fichier de pref
     */
    public void saveInformation() {
        gameInformation.lastLogin = System.currentTimeMillis();
        gameInformation.totalGameTime = gameInformation.totalGameTime+(System.currentTimeMillis() - gameInformation.lastLogin);
        String jsonVal = json.toJson(game.gameInformation);

        prefs.putString(PREF_NAME, jsonVal);
        prefs.flush();
    }

    public void reset() {
        initGameInformation();
        initGamePreference();
        saveInformation();
    }


    public void initGameInformation() {
        gameInformation = new GameInformation();

        gameInformation.currentGold=0;
        gameInformation.currentCurrency=0;
        gameInformation.genGoldPassive=2;
        gameInformation.tapDamage=2;
        gameInformation.genCurrencyPassive = 0;
        gameInformation.genCurrencyActive=0;
        gameInformation.criticalRate=5;
        gameInformation.skillPoint=0;
        gameInformation.lastLogin=System.currentTimeMillis();
        gameInformation.totalTapNumber=0;
        gameInformation.totalGameTime=0L;
        gameInformation.levelBaseGold=5;
        gameInformation.levelBaseCurrency=1;
        gameInformation.depth=1;
        ArrayList upgradeLevelList = new ArrayList();
        for (int i=0;i<game.assetManager.getModuleElementList().size();i++){
            upgradeLevelList.add(0);
        }
        gameInformation.moduleLevelList =upgradeLevelList;
        ArrayList achievList = new ArrayList();
        for (int i=0;i<game.assetManager.getAchievementElementList().size();i++){
            achievList.add(0);
        }
        gameInformation.achievList=achievList;
    }

    public void initGamePreference(){
        gameInformation.firstPlay=true;
        gameInformation.optionSound=true;
        gameInformation.optionWeather=true;
        gameInformation.optionFps=false;
    }

    public void loadGameInformation() {
        gameInformation = json.fromJson(GameInformation.class, prefs.getString(PREF_NAME));
        if (gameInformation == null) {
            Gdx.app.debug("GameInformation", "Initialisation du compte par defaut");
            initGameInformation();
            initGamePreference();
        }
    }
}
