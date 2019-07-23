package com.guco.tap.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by Skronak on 30/01/2017.
 *
 * Classe de stat & information sur le compte du jeu
 * // TODO store all current AttributeElement
 */
public class GameInformation {

    public Long lastLogin;
    public float currentGoldValue;
    public int currentGoldCurrency;
    public float passivGoldValue;
    public int passivGoldCurrency;
    public float tapDamageValue;
    public int tapDamageCurrency;
    public int passivDamageValue;
    public int passivDamageCurrency;

    public float criticalRate;
    public boolean firstPlay;
    public List<Integer> attributeLevel;
    public Long totalGameTime;
    public int totalTapNumber;
    public int dungeonLevel;
    public List<Integer> achievList; //0: locked 1: unlocked,2: claimed
    public boolean optionWeather, optionSound, optionFps;
    public int skillPoint;
    public int currentEnemyIdx;
    public Item equipedWeapon;
    public int equipedHead;
    public int equipedBody;
    public List<Item> weaponItemList;
    public List<Item> headItemList;
    public List<Item> bodyItemList;
    public int levelBaseCurrency;
    public int levelBaseGold;

    public GameInformation() {
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************

}
