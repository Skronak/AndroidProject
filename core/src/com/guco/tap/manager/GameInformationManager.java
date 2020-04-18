package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;
import com.guco.tap.entity.CalculatedStat;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.entity.ItemUpgrade;
import com.guco.tap.save.SaveJSON;
import com.guco.tap.utils.Constants;

import java.util.ArrayList;

public class GameInformationManager {

    private Json json;
    private String PREF_NAME = "value";
    private Preferences prefs;
    public GameInformation gameInformation;
    public AssetsManager assetsManager;
    private SaveJSON saveJSON;
    private ItemManager itemManager;

    public GameInformationManager(AssetsManager assetsManager, ItemManager itemManager) {
        this.json = new Json();
        this.assetsManager = assetsManager;
        this.itemManager = itemManager;
        prefs = Gdx.app.getPreferences(Constants.APP_NAME);
    }

    public void saveData() {
        SaveJSON saveJSON = new SaveJSON(gameInformation);

        String jsonVal = json.toJson(saveJSON);
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

        saveJSON = json.fromJson(SaveJSON.class, prefs.getString(PREF_NAME));

        if (saveJSON !=  null) {
            loadGameInformationFromSave();
        } else {
            Gdx.app.debug("GameInformation", "Initialisation du compte par defaut");
            initGameInformation();
            initGamePreference();
        }
    }

    private void loadGameInformationFromSave() {
        gameInformation = new GameInformation();
        gameInformation.accountName = saveJSON.accountName;
        gameInformation.lastLogin = System.currentTimeMillis();
        gameInformation.currentGoldValue = saveJSON.currentGoldValue;
        gameInformation.currentGoldCurrency = saveJSON.currentGoldCurrency;
//            gameInformation.attributeLevel = new ArrayList<Integer>();
        gameInformation.totalGameTime = saveJSON.totalGameTime;
        gameInformation.totalTapNumber = saveJSON.totalTapNumber;
        gameInformation.areaLevel  = saveJSON.areaLevel;
        gameInformation.currentEnemyIdx = saveJSON.currentEnemyIdx;
        gameInformation.achievList = new ArrayList<Integer>();
        gameInformation.optionFps = saveJSON.optionFps;
        gameInformation.optionSound = saveJSON.optionSound;
        gameInformation.optionWeather = saveJSON.optionWeather;
        gameInformation.skillPoint = saveJSON.skillPoint;
//            gameInformation.levelBaseGoldValue  = saveJSON.levelBaseGold;
//            gameInformation.levelBaseGoldCurrency  = saveJSON.levelBaseCurrency;
        gameInformation.equipedWeapon = assetsManager.weaponList.get(saveJSON.currentEquipment[0]);
        gameInformation.equipedHead = saveJSON.currentEquipment[1];
        gameInformation.equipedBody = saveJSON.currentEquipment[2];
        gameInformation.areaLevel =  saveJSON.areaLevel;
        gameInformation.areaId = saveJSON.areaId;

        //TODO to implement
        //gameInformation.currentWeapon = new Weapon();
        //gameInformation.currentWeapon.lvl = 1;
        //gameInformation.currentWeapon.damage_value = 1;
        //gameInformation.currentWeapon.damage_currency = 1;

        //for (int i = 0; i< assetsManager.getAttributeElementList().size(); i++){
        //    gameInformation.attributeLevel.add(i,saveJSON.attributeLevel[i]);
        //}
        for (int i = 0; i< assetsManager.getAchievementElementList().size(); i++){
            gameInformation.achievList.add(saveJSON.achievList[i]);
        }

        gameInformation.unlockedWeaponList = new ArrayList<Item>();
        for (int i = 0; i< assetsManager.weaponList.size(); i++){
            Item item = assetsManager.weaponList.get(i);
            item.level = saveJSON.weaponItemList[i];
            CalculatedStat calculatedStat = itemManager.calculateItemStat(item,item.level);
            item.calculatedStat = calculatedStat;
            item.selectedUpgrades = new ArrayList<ItemUpgrade>();
            gameInformation.unlockedWeaponList.add(item);
        }

        gameInformation.unlockedBodyList = new ArrayList<Item>();
        for (int i = 0; i< assetsManager.bodyList.size(); i++){
            Item item = assetsManager.bodyList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            item.level = saveJSON.bodyItemList[i];
            gameInformation.unlockedBodyList.add(item);
            item.selectedUpgrades = new ArrayList<ItemUpgrade>();
        }
        gameInformation.unlockedHeadList = new ArrayList<Item>();
        for (int i = 0; i< assetsManager.helmList.size(); i++){
            Item item = assetsManager.helmList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            item.level = saveJSON.headItemList[i];
            gameInformation.unlockedHeadList.add(item);
            item.selectedUpgrades = new ArrayList<ItemUpgrade>();
        }
    }

    public void initGameInformation() {
        gameInformation = new GameInformation();
        gameInformation.accountName = "";
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
//        gameInformation.currentWeapon = new Weapon();
//        gameInformation.currentWeapon.lvl = 1;

        gameInformation.equipedWeapon =  assetsManager.weaponList.get(0);
        for (int i = 0; i< assetsManager.getAttributeElementList().size(); i++){
            gameInformation.attributeLevel.add(0);
        }
        gameInformation.achievList = new ArrayList();
        for (int i = 0; i< assetsManager.getAchievementElementList().size(); i++){
            gameInformation.achievList.add(0);
        }
        gameInformation.unlockedWeaponList = new ArrayList();
        for (int i = 0; i< assetsManager.weaponList.size(); i++){
            Item item = assetsManager.weaponList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            gameInformation.unlockedWeaponList.add(item);
        }
        gameInformation.unlockedBodyList = new ArrayList();
        for (int i = 0; i< assetsManager.bodyList.size(); i++){
            Item item = assetsManager.bodyList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            gameInformation.unlockedBodyList.add(item);
        }
        gameInformation.unlockedHeadList = new ArrayList();
        for (int i = 0; i< assetsManager.helmList.size(); i++){
            Item item = assetsManager.helmList.get(i);
            item.calculatedStat = new CalculatedStat(item);
            gameInformation.unlockedHeadList.add(item);
        }
    }
}
