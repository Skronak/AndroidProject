package com.guco.tap.save;

import java.util.List;

public class GameInformationDTO {
    public Long lastLogin;
    public float currentGoldValue;
    public int currentGoldCurrency;
    public float passivGoldValue;
    public int passivGoldCurrency;
    public float tapDamageValue;
    public int tapDamageCurrency;
    public float criticalRate;
    public boolean firstPlay;
    public List<Integer> attributeLevel;
    public Long totalGameTime;
    public int totalTapNumber;
    public int depth;
    public int[] achievList; //0: locked 1: unlocked,2: claimed
    public boolean optionWeather, optionSound, optionFps;
    public int skillPoint;
    public int currentEnemyIdx;
    public int equipedWeapon;
    public int equipedHead;
    public int equipedBody;
    public int[] weaponLevellist;
    public int[] headLevellist;
    public int[] bodyLevellist;
    public int levelBaseCurrency;
    public int levelBaseGold;
    public int passivDamageValue;
    public int passivDamageCurrency;
}
