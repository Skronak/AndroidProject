package com.guco.tap.entity;

import com.badlogic.gdx.Preferences;

import java.util.List;

/**
 * Created by Skronak on 30/01/2017.
 *
 * Classe de stat & information sur le compte du jeu
 * // TODO store all current ModuleElement
 */
public class GameInformation {

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

    public GameInformation() {
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

    public int getTotalTapNumber() {
        return totalTapNumber;
    }

    public void setTotalTapNumber(int totalTapNumber) {
        this.totalTapNumber = totalTapNumber;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public List<Integer> getAchievList() {
        return achievList;
    }

    public void setAchievList(List<Integer> achievList) {
        this.achievList = achievList;
    }

    public boolean isOptionWeather() {
        return optionWeather;
    }

    public void setOptionWeather(boolean optionWeather) {
        this.optionWeather = optionWeather;
    }

    public boolean isOptionSound() {
        return optionSound;
    }

    public void setOptionSound(boolean optionSound) {
        this.optionSound = optionSound;
    }

    public boolean isOptionFps() {
        return optionFps;
    }

    public void setOptionFps(boolean optionFps) {
        this.optionFps = optionFps;
    }

    public int getSkillPoint() {
        return skillPoint;
    }

    public void setSkillPoint(int skillPoint) {
        this.skillPoint = skillPoint;
    }

    public List<Integer> getWeaponLevellist() {
        return weaponLevellist;
    }

    public void setWeaponLevellist(List<Integer> weaponLevellist) {
        this.weaponLevellist = weaponLevellist;
    }

    public List<Integer> getHeadLevellist() {
        return headLevellist;
    }

    public void setHeadLevellist(List<Integer> headLevellist) {
        this.headLevellist = headLevellist;
    }

    public List<Integer> getBodyLevellist() {
        return bodyLevellist;
    }

    public void setBodyLevellist(List<Integer> bodyLevellist) {
        this.bodyLevellist = bodyLevellist;
    }
}
