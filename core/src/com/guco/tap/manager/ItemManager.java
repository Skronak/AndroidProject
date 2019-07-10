package com.guco.tap.manager;

import com.guco.tap.entity.Item;

public class ItemManager {
    private GameManager gameManager;

    public ItemManager(GameManager gameManager){
        this.gameManager = gameManager;
        updateWeaponList();
    }

    public void updateWeaponList() {
        for (int i = 0; i < gameManager.ressourceManager.weaponList.size(); i++) {
            Item item = gameManager.ressourceManager.weaponList.get(i);
            if (gameManager.ressourceManager.weaponUpgradeList.size()>i) {
                item.upgrades = gameManager.ressourceManager.weaponUpgradeList.get(i);
            }
        }
    }

    public void increaseItemLevel(Item item){
//        gameManager.gameInformation.get
    }

    public void unlockItem(){

    }
    public void increasePerkLevel(){

    }

    public void selectPerk(){

    }

    public void calculateCurrentDamage(Item item){
       // item.currentDamageValue = item.level * (item.damageValue * item.damageRate);
    }

}

