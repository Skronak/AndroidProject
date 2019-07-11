package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.save.GameInformationDTO;
import com.guco.tap.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class GameInformationManager {

    private Json json;
    private String PREF_NAME ="value";
    private Preferences prefs;
    public GameInformation gameInformation;
    public RessourceManager ressourceManager;
    private GameInformationDTO gameInformationDTO;

    public GameInformationManager(RessourceManager ressourceManager) {
        this.json = new Json();
        this.ressourceManager = ressourceManager;
        prefs = Gdx.app.getPreferences(Constants.APP_NAME);
    }

    /**
     * Sauvegarde les informations courantes
     * dans le fichier de pref
     */
    public void saveInformation() {
        GameInformationDTO gameInformationDTO = new GameInformationDTO();

        gameInformation.lastLogin = System.currentTimeMillis();
        gameInformation.totalGameTime = gameInformation.totalGameTime+(System.currentTimeMillis() - gameInformation.lastLogin);
        String jsonVal = json.toJson(gameInformation);

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

        gameInformation.currentGoldValue =0;
        gameInformation.currentGoldCurrency =0;
        gameInformation.passivGoldValue =2;
        gameInformation.tapDamageValue =2;
        gameInformation.passivGoldCurrency = 0;
        gameInformation.tapDamageCurrency =0;
        gameInformation.criticalRate=5;
        gameInformation.skillPoint=0;
        gameInformation.lastLogin=System.currentTimeMillis();
        gameInformation.totalTapNumber=0;
        gameInformation.totalGameTime=0L;
        gameInformation.levelBaseGold=5;
        gameInformation.levelBaseCurrency=1;
        gameInformation.depth=1;
        gameInformation.upgradedItem = new HashMap<Integer, Item>();
        ArrayList upgradeLevelList = new ArrayList();
        for (int i = 0; i<ressourceManager.getModuleElementList().size(); i++){
            upgradeLevelList.add(0);
        }
        gameInformation.attributeLevel =upgradeLevelList;
        ArrayList achievList = new ArrayList();
        for (int i = 0; i<ressourceManager.getAchievementElementList().size(); i++){
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
