package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.guco.tap.entity.CalculatedStat;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.entity.ItemUpgrade;
import com.guco.tap.save.SavedData;
import com.guco.tap.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

public class GameInformationManager {

    private Json json;
    private String PREF_NAME ="value";
    private Preferences prefs;
    public GameInformation gameInformation;
    public RessourceManager ressourceManager;
    private SavedData savedData;

    public GameInformationManager(RessourceManager ressourceManager) {
        this.json = new Json();
        this.ressourceManager = ressourceManager;
        prefs = Gdx.app.getPreferences(Constants.APP_NAME);
    }

    /**
     * Sauvegarde les informations courantes
     * dans le fichier de pref
     */
    public void saveData() {
        SavedData savedData = new SavedData(gameInformation);

        String jsonVal = json.toJson(savedData);
        prefs.putString(PREF_NAME, jsonVal);
        prefs.flush();
    }

    public void reset() {
        initGameInformation();
        initGamePreference();
        saveData();
    }

    public void initGamePreference(){
        gameInformation.firstPlay=true;
        gameInformation.optionSound=true;
        gameInformation.optionWeather=true;
        gameInformation.optionFps=false;
    }

    public void loadData() {
        savedData = json.fromJson(SavedData.class, prefs.getString(PREF_NAME));
        if (savedData != null) {
            gameInformation = new GameInformation();
            gameInformation.lastLogin=System.currentTimeMillis();
            gameInformation.currentGoldValue=savedData.currentGoldValue;
            gameInformation.currentGoldCurrency=savedData.currentGoldCurrency;
            gameInformation.attributeLevel = new ArrayList<Integer>();
            gameInformation.totalGameTime = savedData.totalGameTime;
            gameInformation.totalTapNumber=savedData.totalTapNumber;
            gameInformation.dungeonLevel=savedData.dungeonLevel;
            gameInformation.currentEnemyIdx=savedData.currentEnemyIdx;
            gameInformation.achievList=new ArrayList<Integer>();
            gameInformation.optionFps=savedData.optionFps;
            gameInformation.optionSound=savedData.optionSound;
            gameInformation.optionWeather=savedData.optionWeather;
            gameInformation.skillPoint=savedData.skillPoint;
            gameInformation.equipedWeapon = savedData.currentEquipment[0];
            gameInformation.equipedHead = savedData.currentEquipment[1];
            gameInformation.equipedBody = savedData.currentEquipment[2];
            gameInformation.weaponItemList=new ArrayList<Item>();
            gameInformation.bodyItemList = new ArrayList<Item>();
            gameInformation.headItemList = new ArrayList<Item>();
            for (int i=0;i<ressourceManager.getAttributeElementList().size();i++){
                gameInformation.attributeLevel.add(i,savedData.attributeLevel[i]);
            }
            for (int i = 0; i<ressourceManager.getAchievementElementList().size(); i++){
                gameInformation.achievList.add(savedData.achievList[i]);
            }
            for (int i=0;i<ressourceManager.weaponList.size();i++){
                Item item = ressourceManager.weaponList.get(i);
                item.calculatedStat = new CalculatedStat(item);
                item.level=savedData.weaponItemList[i];
                gameInformation.weaponItemList.add(item);
                item.selectedUpgrades = new ArrayList<ItemUpgrade>();
            }
            for (int i=0;i<ressourceManager.bodyList.size();i++){
                Item item = ressourceManager.bodyList.get(i);
                item.calculatedStat = new CalculatedStat(item);
                item.level=savedData.bodyItemList[i];
                gameInformation.bodyItemList.add(item);
                item.selectedUpgrades = new ArrayList<ItemUpgrade>();
            }
            for (int i=0;i<ressourceManager.helmList.size();i++){
                Item item = ressourceManager.helmList.get(i);
                item.calculatedStat = new CalculatedStat(item);
                item.level=savedData.headItemList[i];
                gameInformation.headItemList.add(item);
                item.selectedUpgrades = new ArrayList<ItemUpgrade>();
            }
        } else {
            Gdx.app.debug("GameInformation", "Initialisation du compte par defaut");
            initGameInformation();
            initGamePreference();
        }
    }

    public void initGameInformation() {
        gameInformation = new GameInformation();
        gameInformation.lastLogin=System.currentTimeMillis();
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
        gameInformation.dungeonLevel =1;
//        gameInformation.upgradedItem = new HashMap<Integer, Item>();
        gameInformation.attributeLevel = new ArrayList();
        for (int i=0;i<ressourceManager.getAttributeElementList().size();i++){
            gameInformation.attributeLevel.add(0);
        }
        gameInformation.achievList = new ArrayList();
        for (int i = 0; i<ressourceManager.getAchievementElementList().size(); i++){
            gameInformation.achievList.add(0);
        }
        gameInformation.weaponItemList = new ArrayList();
        for (int i=0;i<ressourceManager.weaponList.size();i++){
            Item item = ressourceManager.weaponList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            gameInformation.weaponItemList.add(item);
        }
        gameInformation.bodyItemList = new ArrayList();
        for (int i=0;i<ressourceManager.bodyList.size();i++){
            Item item = ressourceManager.bodyList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            gameInformation.bodyItemList.add(item);
        }
        gameInformation.headItemList = new ArrayList();
        for (int i=0;i<ressourceManager.helmList.size();i++){
            Item item = ressourceManager.helmList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            gameInformation.headItemList.add(item);
        }
    }
}
