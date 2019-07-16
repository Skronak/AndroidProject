package com.guco.tap.save;

import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;

import java.util.Map;

public class SavedData {
    public Long lastLogin;
    public float currentGoldValue;
    public int currentGoldCurrency;
    public int[] attributeLevel;
    public Long totalGameTime;
    public int totalTapNumber;
    public int dungeonLevel;
    public int currentEnemyIdx;
    public int[] achievList; //0: locked 1: unlocked,2: claimed
    public boolean optionWeather, optionSound, optionFps;
    public int skillPoint;
    public int[] currentEquipment;
    public int[] weaponItemList;
    public int[] headItemList;
    public int[] bodyItemList;
    public transient Map<Integer, Item> upgradedItem;

    public SavedData(){
    }

    public SavedData(GameInformation gameInformation){
        this.lastLogin = System.currentTimeMillis();
        this.currentGoldValue=gameInformation.currentGoldValue;
        this.currentGoldCurrency=gameInformation.currentGoldCurrency;
        this.attributeLevel = new int[gameInformation.attributeLevel.size()];
        this.totalGameTime = gameInformation.totalGameTime+(System.currentTimeMillis() - gameInformation.lastLogin);
        this.totalTapNumber=gameInformation.totalTapNumber;
        this.dungeonLevel=gameInformation.dungeonLevel;
        this.currentEnemyIdx=gameInformation.currentEnemyIdx;
        this.achievList=new int[gameInformation.achievList.size()];
        this.optionFps=gameInformation.optionFps;
        this.optionSound=gameInformation.optionSound;
        this.optionWeather=gameInformation.optionWeather;
        this.skillPoint=gameInformation.skillPoint;
        this.currentEquipment = new int[3];
        currentEquipment[0]=gameInformation.equipedWeapon;
        currentEquipment[1]=gameInformation.equipedHead;
        currentEquipment[2]=gameInformation.equipedBody;
        this.weaponItemList=new int[gameInformation.weaponItemList.size()];
        this.bodyItemList = new int[gameInformation.bodyItemList.size()];
        this.headItemList = new int[gameInformation.headItemList.size()];

        for (int i=0;i<gameInformation.attributeLevel.size();i++){
            attributeLevel[i]=gameInformation.attributeLevel.get(i);
        }
        for (int i=0;i<gameInformation.achievList.size();i++){
            achievList[i]=gameInformation.achievList.get(i);
        }
        for (int i=0;i<gameInformation.weaponItemList.size();i++){
            weaponItemList[i]=gameInformation.weaponItemList.get(i).level;
        }

        for (int i=0;i<gameInformation.bodyItemList.size();i++){
            bodyItemList[i]=gameInformation.bodyItemList.get(i).level;
        }

        for (int i=0;i<gameInformation.headItemList.size();i++){
            headItemList[i]=gameInformation.headItemList.get(i).level;
        }
    }
}
