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

        gameInformation.setCurrentGold(0);
        gameInformation.setCurrency(0);
        gameInformation.setGenGoldPassive(2);
        gameInformation.setTapDamage(2);
        gameInformation.setGenCurrencyPassive(0);
        gameInformation.setGenCurrencyActive(0);
        gameInformation.setCriticalRate(5);
        gameInformation.setSkillPoint(0);
        gameInformation.setLastLogin(System.currentTimeMillis());
        gameInformation.setTotalTapNumber(0);
        gameInformation.setTotalGameTime(0L);
        ArrayList upgradeLevelList = new ArrayList();
        for (int i=0;i<game.assetManager.getModuleElementList().size();i++){
            upgradeLevelList.add(0);
        }
        gameInformation.setUpgradeLevelList(upgradeLevelList);
        ArrayList achievList = new ArrayList();
        for (int i=0;i<game.assetManager.getAchievementElementList().size();i++){
            achievList.add(0);
        }
        gameInformation.setAchievList(achievList);
    }

    public void initGamePreference(){
        gameInformation.setFirstPlay(true);
        gameInformation.setOptionSound(true);
        gameInformation.setOptionWeather(true);
        gameInformation.setOptionFps(false);
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
