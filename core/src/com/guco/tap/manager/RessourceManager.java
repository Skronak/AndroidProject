package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.guco.tap.utils.ValueDTO;

public class RessourceManager {

    private GameManager gameManager;

    public RessourceManager(GameManager gameManager){
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");

        this.gameManager = gameManager;
    }

    /**
     * Methode d'ajout d'or
     */
    public void increaseGoldActive(){
        ValueDTO newValue = gameManager.largeMath.increaseValue(gameManager.gameInformation.getCurrentGold(), gameManager.gameInformation.getCurrency(), gameManager.gameInformation.getTapDamage(), gameManager.gameInformation.getGenCurrencyActive());
        gameManager.gameInformation.setCurrentGold(newValue.getValue());
        gameManager.gameInformation.setCurrency(newValue.getCurrency());
        gameManager.largeMath.formatGameInformation();
    }

    /**
     * Ajout d'or passif
     */
    public void increaseGoldPassive(){
        ValueDTO newValue = gameManager.largeMath.increaseValue(gameManager.gameInformation.getCurrentGold(), gameManager.gameInformation.getCurrency(), gameManager.gameInformation.getGenGoldPassive(), gameManager.gameInformation.getGenCurrencyPassive());
        gameManager.gameInformation.setCurrentGold(newValue.getValue());
        gameManager.gameInformation.setCurrency(newValue.getCurrency());
        gameManager.largeMath.formatGameInformation();
    }

    // Methode d'ajout d'or lors d'un critique
    public void increaseGoldCritical() {
//        gameManager.gameInformation.setCurrentGold(gameManager.gameInformation.getCurrentGold() + getCriticalValue());
    }

    public void calculateGold(){

    }


}
