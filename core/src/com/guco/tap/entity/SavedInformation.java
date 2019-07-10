package com.guco.tap.entity;

import com.badlogic.gdx.Preferences;

import java.util.List;


/**
 * Created by Skronak on 30/01/2017.
 *
 * Classe de stat & information sur le compte du jeu
 * // TODO store all current ModuleElement
 */
public class SavedInformation {

    // dernier login
    public Long lastLogin;
    // Total d'or
    public float currentGold;
    // generation pasive d'or
    public float genGoldPassive;
    // generation active d'or
    public float tapDamage;
    // Currency (A=1, B=2, ... jusqua 9, AA=9+1
    public int currency;
    // currency de gengoldPassive
    public int genCurrencyPassive;
    // currency de gengoldActive
    public int genCurrencyActive;
    // multiplicateur d'or lors de critique
    public int criticalRate;
    // fichier de preference Android
    public transient Preferences prefs;
    // indicateur de premier lancment du jeu
    public boolean firstPlay;
    // liste des niveau d'upgrade du joueur pour faciliter son acces
    public List<Integer> upgradeLevelList;
    // Total gameTime
    public Long totalGameTime;
    // Tap number
    public int totalTapNumber;

    public int depth;

    public List<Integer> achievList; //0: locked 1: unlocked,2: claimed

    public boolean optionWeather, optionSound, optionFps;

    public int skillPoint;
    public int currentEnemyIdx;
    public int equipedWeapon;
    public int equipedHead;
    public int equipedBody;
    public List<Integer> weaponLevellist;
    public List<Integer> headLevellist;
    public List<Integer> bodyLevellist;

    public SavedInformation() {
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************


    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public float getCurrentGold() {
        return currentGold;
    }

    public void setCurrentGold(float currentGold) {
        this.currentGold = currentGold;
    }

    public float getGenGoldPassive() {
        return genGoldPassive;
    }

    public void setGenGoldPassive(float genGoldPassive) {
        this.genGoldPassive = genGoldPassive;
    }

    public float getTapDamage() {
        return tapDamage;
    }

    public void setTapDamage(float tapDamage) {
        this.tapDamage = tapDamage;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public int getGenCurrencyPassive() {
        return genCurrencyPassive;
    }

    public void setGenCurrencyPassive(int genCurrencyPassive) {
        this.genCurrencyPassive = genCurrencyPassive;
    }

    public int getGenCurrencyActive() {
        return genCurrencyActive;
    }

    public void setGenCurrencyActive(int genCurrencyActive) {
        this.genCurrencyActive = genCurrencyActive;
    }

    public int getCriticalRate() {
        return criticalRate;
    }

    public void setCriticalRate(int criticalRate) {
        this.criticalRate = criticalRate;
    }

    public boolean isFirstPlay() {
        return firstPlay;
    }

    public void setFirstPlay(boolean firstPlay) {
        this.firstPlay = firstPlay;
    }

    public List<Integer> getUpgradeLevelList() {
        return upgradeLevelList;
    }

    public void setUpgradeLevelList(List<Integer> upgradeLevelList) {
        this.upgradeLevelList = upgradeLevelList;
    }

    public Long getTotalGameTime() {
        return totalGameTime;
    }

    public void setTotalGameTime(Long totalGameTime) {
        this.totalGameTime = totalGameTime;
    }



}
