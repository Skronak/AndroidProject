package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

public class DataManager {

    private LargeMath largeMath;
    private GameInformation gameInformation;
    private AssetsManager assetsManager;

    public DataManager(GameManager gameManager){
        Gdx.app.debug(this.getClass().getSimpleName(), "Instantiate");

        this.largeMath = gameManager.largeMath;
        this.gameInformation = gameManager.gameInformation;
        this.assetsManager = gameManager.assetsManager;
    }

    public void increaseGoldPassive(){
        ValueDTO newValue = largeMath.increaseValue(gameInformation.currentGoldValue, gameInformation.currentGoldCurrency, gameInformation.passivGoldValue, gameInformation.passivGoldCurrency);
        gameInformation.currentGoldValue =newValue.value;
        gameInformation.currentGoldCurrency =newValue.currency;
    }

    // Methode d'ajout d'or lors d'un critique
    public void increaseGoldCritical() {
//        gameInformation.setCurrentGold(gameInformation.currentGoldValue + getCriticalValue());
    }

    public void increaseGold(){
        ValueDTO addedValue = calculateGoldPerMonster();
        increaseGold(addedValue);
    }

    public void increaseGold(ValueDTO addedValue){
        ValueDTO baseValue = new ValueDTO(gameInformation.currentGoldValue, gameInformation.currentGoldCurrency);
        ValueDTO newValue = largeMath.increaseValue(baseValue, addedValue);
        gameInformation.currentGoldValue = newValue.value;
        gameInformation.currentGoldCurrency = newValue.currency;
    }

    public ValueDTO calculateGoldPerMonster(){
        ValueDTO valueDTO = new ValueDTO(gameInformation.areaLevel * gameInformation.levelBaseGoldValue,gameInformation.levelBaseGoldCurrency);
        return valueDTO;
    }

    public void decreaseGold(){
    }

    public void calculateTapDamage(){
        Item weapon = gameInformation.equipedWeapon;
//        gameInformation.tapDamageValue = weapon.calculatedStat.damageValue;
//        gameInformation.tapDamageCurrency = weapon.calculatedStat.damageCurrency;
        gameInformation.tapDamageValue = weapon.damageValue;
        gameInformation.tapDamageCurrency = weapon.damageCurrency;

        assetsManager.helmList.get(gameInformation.equipedBody);
        assetsManager.bodyList.get(gameInformation.equipedBody);

    }

    public void calculatePassiveDamage(){

    }


}
