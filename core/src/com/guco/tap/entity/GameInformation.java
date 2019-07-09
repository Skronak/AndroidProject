package com.guco.tap.entity;

import com.badlogic.gdx.Preferences;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

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

    // Currency (A=1, B=2, ... jusqua 9, AA=9+1
    public int currentCurrency;
    public float genGoldPassive;
    // generation active d'or
    public float tapDamageValue;
    // currency de gengoldPassive
    public int genCurrencyPassive;
    // currency de gengoldActive
    public int tapDamageCurrency;
    public float criticalRate;
    // indicateur de premier lancment du jeu
    public boolean firstPlay;
    // liste des niveau d'upgrade du joueur pour faciliter son acces
    public List<Integer> moduleLevelList;
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
    public int levelBaseCurrency;
    public int levelBaseGold;
    public int passivDamageValue;
    public int passivDamageCurrency;

    public GameInformation() {
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************

}
