package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.ItemEntity;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

public class RessourceManager {

    private LargeMath largeMath;
    private GameInformation gameInformation;
    private AssetManager assetManager;

    public RessourceManager(GameManager gameManager){
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");

        this.largeMath = gameManager.largeMath;
        this.gameInformation = gameManager.gameInformation;
        this.assetManager = gameManager.assetManager;
    }


    public void increaseGoldActive(){
    }

    public void increaseGoldPassive(){
        ValueDTO newValue = largeMath.increaseValue(gameInformation.currentGold, gameInformation.currentCurrency, gameInformation.genGoldPassive, gameInformation.genCurrencyPassive);
        gameInformation.currentGold=newValue.value;
        gameInformation.currentCurrency=newValue.currency;
        largeMath.formatGameInformation();
    }

    // Methode d'ajout d'or lors d'un critique
    public void increaseGoldCritical() {
//        gameInformation.setCurrentGold(gameInformation.currentGold + getCriticalValue());
    }

    public void increaseGold(){
        ValueDTO addedValue = new ValueDTO(gameInformation.depth*gameInformation.levelBaseGold,gameInformation.levelBaseCurrency);
        ValueDTO baseValue = new ValueDTO(gameInformation.currentGold, gameInformation.currentCurrency);
        ValueDTO newValue = largeMath.increaseValue(baseValue, addedValue);
        gameInformation.currentGold=newValue.value;
        gameInformation.currentCurrency=newValue.currency;
        largeMath.formatGameInformation();
        calculateTapDamage();
    }

    public void decreaseGold(){

    }

    public void calculateTapDamage(){
        int currency=0;
        int damage=0;
        ItemEntity weapon = assetManager.weaponList.get(gameInformation.equipedWeapon);
        float weaponDamage = weapon.currentDamage;

        assetManager.helmList.get(gameInformation.equipedBody);
        assetManager.bodyList.get(gameInformation.equipedBody);

    }

    public void calculatePassiveDamage(){

    }


}
