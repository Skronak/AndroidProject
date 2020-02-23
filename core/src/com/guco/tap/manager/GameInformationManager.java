package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.guco.tap.entity.CalculatedStat;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.entity.ItemUpgrade;
import com.guco.tap.entity.Weapon;
import com.guco.tap.save.SavedData;
import com.guco.tap.utils.Constants;

import java.util.ArrayList;

public class GameInformationManager {

    private Json json;
    private String PREF_NAME = "value";
    private Preferences prefs;
    public GameInformation gameInformation;
    public AssetsManager assetsManager;
    private SavedData savedData;
    private ItemManager itemManager;

    public GameInformationManager(AssetsManager assetsManager, ItemManager itemManager) {
        this.json = new Json();
        this.assetsManager = assetsManager;
        this.itemManager = itemManager;
        prefs = Gdx.app.getPreferences(Constants.APP_NAME);
    }

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
        gameInformation.firstPlay = true;
        gameInformation.optionSound = true;
        gameInformation.optionWeather = true;
        gameInformation.optionFps = false;
    }

    public void loadGameData() {

        savedData = json.fromJson(SavedData.class, prefs.getString(PREF_NAME));

        if (savedData !=  null) {
            gameInformation = new GameInformation();
            gameInformation.lastLogin = System.currentTimeMillis();
            gameInformation.currentGoldValue = savedData.currentGoldValue;
            gameInformation.currentGoldCurrency = savedData.currentGoldCurrency;
            gameInformation.attributeLevel = new ArrayList<Integer>();
            gameInformation.totalGameTime = savedData.totalGameTime;
            gameInformation.totalTapNumber = savedData.totalTapNumber;
            gameInformation.areaLevel  = savedData.areaLevel;
            gameInformation.currentEnemyIdx = savedData.currentEnemyIdx;
            gameInformation.achievList = new ArrayList<Integer>();
            gameInformation.optionFps = savedData.optionFps;
            gameInformation.optionSound = savedData.optionSound;
            gameInformation.optionWeather = savedData.optionWeather;
            gameInformation.skillPoint = savedData.skillPoint;
            gameInformation.levelBaseGoldValue  = savedData.levelBaseGold;
            gameInformation.levelBaseGoldCurrency  = savedData.levelBaseCurrency;
            gameInformation.equipedWeapon = assetsManager.weaponList.get(savedData.currentEquipment[0]);
            gameInformation.equipedHead = savedData.currentEquipment[1];
            gameInformation.equipedBody = savedData.currentEquipment[2];
            gameInformation.areaLevel =  savedData.areaLevel;
            gameInformation.areaId = savedData.areaId;

            //TODO to implement
            gameInformation.currentWeapon = new Weapon();
            gameInformation.currentWeapon.lvl = 1;
            gameInformation.currentWeapon.damage_value=1;
            gameInformation.currentWeapon.damage_currency=1;

            for (int i = 0; i< assetsManager.getAttributeElementList().size(); i++){
                gameInformation.attributeLevel.add(i,savedData.attributeLevel[i]);
            }
            for (int i = 0; i< assetsManager.getAchievementElementList().size(); i++){
                gameInformation.achievList.add(savedData.achievList[i]);
            }
            gameInformation.weaponItemList = new ArrayList<Item>();
            for (int i = 0; i< assetsManager.weaponList.size(); i++){
                Item item = assetsManager.weaponList.get(i);
                item.level = savedData.weaponItemList[i];
                CalculatedStat calculatedStat = itemManager.calculateItemStat(item,item.level);
                item.calculatedStat = calculatedStat;
                item.selectedUpgrades = new ArrayList<ItemUpgrade>();
                gameInformation.weaponItemList.add(item);
            }
            gameInformation.bodyItemList = new ArrayList<Item>();
            for (int i = 0; i< assetsManager.bodyList.size(); i++){
                Item item = assetsManager.bodyList.get(i);
                item.calculatedStat = new CalculatedStat(item);
                item.level = savedData.bodyItemList[i];
                gameInformation.bodyItemList.add(item);
                item.selectedUpgrades = new ArrayList<ItemUpgrade>();
            }
            gameInformation.headItemList = new ArrayList<Item>();
            for (int i = 0; i< assetsManager.helmList.size(); i++){
                Item item = assetsManager.helmList.get(i);
                item.calculatedStat = new CalculatedStat(item);
                item.level = savedData.headItemList[i];
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
        gameInformation.lastLogin = System.currentTimeMillis();
        gameInformation.currentGoldValue = 0;
        gameInformation.currentGoldCurrency = 0;
        gameInformation.passivGoldValue = 0;
        gameInformation.tapDamageValue = 1;
        gameInformation.passivGoldCurrency = 0;
        gameInformation.tapDamageCurrency = 0;
        gameInformation.criticalRate = 5;
        gameInformation.skillPoint = 0;
        gameInformation.lastLogin = System.currentTimeMillis();
        gameInformation.totalTapNumber = 0;
        gameInformation.totalGameTime = 0L;
        gameInformation.levelBaseGoldValue = 5;
        gameInformation.levelBaseGoldCurrency = 1;
        gameInformation.areaLevel = 1;
        gameInformation.areaId = 1;
        gameInformation.attributeLevel = new ArrayList();
        gameInformation.currentWeapon = new Weapon();
        gameInformation.currentWeapon.lvl = 1;

        gameInformation.equipedWeapon =  assetsManager.weaponList.get(0);
        for (int i = 0; i< assetsManager.getAttributeElementList().size(); i++){
            gameInformation.attributeLevel.add(0);
        }
        gameInformation.achievList = new ArrayList();
        for (int i = 0; i< assetsManager.getAchievementElementList().size(); i++){
            gameInformation.achievList.add(0);
        }
        gameInformation.weaponItemList = new ArrayList();
        for (int i = 0; i< assetsManager.weaponList.size(); i++){
            Item item = assetsManager.weaponList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            gameInformation.weaponItemList.add(item);
        }
        gameInformation.bodyItemList = new ArrayList();
        for (int i = 0; i< assetsManager.bodyList.size(); i++){
            Item item = assetsManager.bodyList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            gameInformation.bodyItemList.add(item);
        }
        gameInformation.headItemList = new ArrayList();
        for (int i = 0; i< assetsManager.helmList.size(); i++){
            Item item = assetsManager.helmList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            gameInformation.headItemList.add(item);
        }
    }
}
