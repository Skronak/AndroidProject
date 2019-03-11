package com.guco.tap.manager;

import com.guco.tap.entity.GameInformation;
import com.guco.tap.utils.ValueDTO;

public class RessourceManager {

    private GameManager gameManager;

    public RessourceManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    /**
     * methode d'ajout d'or au tap
     */
    public void increaseGoldActive(){
        ValueDTO newValue = gameManager.largeMath.increaseValue(GameInformation.INSTANCE.getCurrentGold(), GameInformation.INSTANCE.getCurrency(), GameInformation.INSTANCE.getGenGoldActive(), GameInformation.INSTANCE.getGenCurrencyActive());
        GameInformation.INSTANCE.setCurrentGold(newValue.getValue());
        GameInformation.INSTANCE.setCurrency(newValue.getCurrency());
        gameManager.largeMath.formatGameInformation();
    }

    /**
     * Ajout d'or passif
     */
    public void increaseGoldPassive(){
        ValueDTO newValue = gameManager.largeMath.increaseValue(GameInformation.INSTANCE.getCurrentGold(), GameInformation.INSTANCE.getCurrency(), GameInformation.INSTANCE.getGenGoldPassive(), GameInformation.INSTANCE.getGenCurrencyPassive());
        GameInformation.INSTANCE.setCurrentGold(newValue.getValue());
        GameInformation.INSTANCE.setCurrency(newValue.getCurrency());
        gameManager.largeMath.formatGameInformation();
    }

    // Methode d'ajout d'or lors d'un critique
    public void increaseGoldCritical() {
//        GameInformation.INSTANCE.setCurrentGold(GameInformation.INSTANCE.getCurrentGold() + getCriticalValue());
    }

    public void calculateGold(){

    }


}
