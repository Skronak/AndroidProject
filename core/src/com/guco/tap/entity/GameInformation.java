package com.guco.tap.entity;

import java.util.List;

/**
 *
 * Classe de stat & information sur le compte du jeu
 * // TODO store all current AttributeElement
 */
public class GameInformation {

    public Long lastLogin;
    public String accountName;
    public float currentGoldValue;
    public int currentGoldCurrency;
    public float passivGoldValue;
    public int passivGoldCurrency;
    public float tapDamageValue;
    public int tapDamageCurrency;
    public int passivDamageValue;
    public int passivDamageCurrency;
//    public Weapon currentWeapon;

    public float criticalRate;
    public boolean firstPlay;
    public List<Integer> attributeLevel;
    public Long totalGameTime;
    public int totalTapNumber;
    public int areaLevel;
    public int areaId;
    public List<Integer> achievList; //0: locked 1: unlocked,2: claimed
    public boolean optionWeather, optionSound, optionFps;
    public int skillPoint;
    public int currentEnemyIdx;
    public Item equipedWeapon;
    public int equipedHead;
    public int equipedBody;
    public List<Item> unlockedWeaponList;
    public List<Item> unlockedHeadList;
    public List<Item> unlockedBodyList;
    public int levelBaseGoldCurrency;
    public int levelBaseGoldValue;
    public List<Ressource> ressources;

    public GameInformation() {
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************

}
