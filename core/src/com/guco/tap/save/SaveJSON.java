package com.guco.tap.save;

import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import java.util.Map;

public class SaveJSON {
    public String accountName;
    public Long lastLogin;
    public float currentGoldValue;
    public int currentGoldCurrency;
    public int[] attributeLevel;
    public Long totalGameTime;
    public int dungeonLevel;
    public int totalTapNumber;
    public int areaLevel;
    public int areaId;
    public int currentEnemyIdx;
    public int[] achievList; //0: locked 1: unlocked,2: claimed
    public boolean optionWeather, optionSound, optionFps;
    public int skillPoint;
    public int levelBaseCurrency;
    public int levelBaseGold;
    public int[] currentEquipment;
    public int[] weaponItemList;
    public int[] headItemList;
    public int[] bodyItemList;
    public transient Map<Integer, Item> upgradedItem;

    public SaveJSON(GameInformation gameInformation){
        this.accountName = gameInformation.accountName;
        this.lastLogin = System.currentTimeMillis();
        this.currentGoldValue = gameInformation.currentGoldValue;
        this.currentGoldCurrency = gameInformation.currentGoldCurrency;
//        this.attributeLevel = new int[gameInformation.attributeLevel.size()];
        this.totalGameTime = gameInformation.totalGameTime+(System.currentTimeMillis() - gameInformation.lastLogin);
        this.totalTapNumber = gameInformation.totalTapNumber;
        this.areaLevel = gameInformation.areaLevel;
        this.areaId = gameInformation.areaId;
        this.currentEnemyIdx = gameInformation.currentEnemyIdx;
        this.achievList = new int[gameInformation.achievList.size()];
        this.optionFps = gameInformation.optionFps;
        this.optionSound = gameInformation.optionSound;
        this.optionWeather = gameInformation.optionWeather;
        this.skillPoint = gameInformation.skillPoint;
        this.currentEquipment = new int[3];
        currentEquipment[0]= gameInformation.equipedWeapon.id;
        currentEquipment[1]= gameInformation.equipedHead;
        currentEquipment[2]= gameInformation.equipedBody;
        this.weaponItemList = new int[gameInformation.unlockedWeaponList.size()];
        this.bodyItemList = new int[gameInformation.unlockedBodyList.size()];
        this.headItemList = new int[gameInformation.unlockedHeadList.size()];
        this.levelBaseGold=5;
        this.levelBaseCurrency=0;

        //for (int i=0;i<gameInformation.attributeLevel.size();i++){
        //    attributeLevel[i]=gameInformation.attributeLevel.get(i);
        //}
        for (int i=0;i<gameInformation.achievList.size();i++){
            achievList[i]=gameInformation.achievList.get(i);
        }
        for (int i = 0; i<gameInformation.unlockedWeaponList.size(); i++){
            weaponItemList[i]=gameInformation.unlockedWeaponList.get(i).level;
        }

        for (int i = 0; i<gameInformation.unlockedBodyList.size(); i++){
            bodyItemList[i]=gameInformation.unlockedBodyList.get(i).level;
        }

        for (int i = 0; i<gameInformation.unlockedHeadList.size(); i++){
            headItemList[i]=gameInformation.unlockedHeadList.get(i).level;
        }
    }

    public SaveJSON(){};
}
