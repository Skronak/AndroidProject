package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

public class DataManager {

    private LargeMath largeMath;
    private GameInformation gameInformation;
    private RessourceManager ressourceManager;

    public DataManager(GameManager gameManager){
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");

        this.largeMath = gameManager.largeMath;
        this.gameInformation = gameManager.gameInformation;
        this.ressourceManager = gameManager.ressourceManager;
    }


    public void increaseGoldActive(){
    }

    public void increaseGoldPassive(){
        ValueDTO newValue = largeMath.increaseValue(gameInformation.currentGoldValue, gameInformation.currentGoldCurrency, gameInformation.passivGoldValue, gameInformation.passivGoldCurrency);
        gameInformation.currentGoldValue =newValue.value;
        gameInformation.currentGoldCurrency =newValue.currency;
        largeMath.formatGameInformation();
    }

    // Methode d'ajout d'or lors d'un critique
    public void increaseGoldCritical() {
//        gameInformation.setCurrentGold(gameInformation.currentGoldValue + getCriticalValue());
    }

    public void increaseGold(){
        ValueDTO addedValue = new ValueDTO(gameInformation.dungeonLevel * gameInformation.levelBaseGold,gameInformation.levelBaseCurrency);
        ValueDTO baseValue = new ValueDTO(gameInformation.currentGoldValue, gameInformation.currentGoldCurrency);
        ValueDTO newValue = largeMath.increaseValue(baseValue, addedValue);
        gameInformation.currentGoldValue =newValue.value;
        gameInformation.currentGoldCurrency =newValue.currency;
        largeMath.formatGameInformation();
    }

    public void decreaseGold(){

    }

    // TODO
    public void calculateTapDamage(){
        Item weapon = gameInformation.equipedWeapon;
        gameInformation.tapDamageValue = weapon.calculatedStat.damageValue;
        gameInformation.tapDamageCurrency = weapon.calculatedStat.damageCurrency;

        ressourceManager.helmList.get(gameInformation.equipedBody);
        ressourceManager.bodyList.get(gameInformation.equipedBody);

    }

    public void calculatePassiveDamage(){

    }


}
