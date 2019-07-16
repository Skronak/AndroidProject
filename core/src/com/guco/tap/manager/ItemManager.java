package com.guco.tap.manager;

import com.guco.tap.entity.CalculatedStat;
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
                Item upgradedItem = gameManager.gameInformation.weaponItemList.get(i);
                if (upgradedItem.level==0) {
                    item.calculatedStat = new CalculatedStat(upgradedItem.damageValue, upgradedItem.damageCurrency, upgradedItem.criticalRate);
                } else {
                    item.calculatedStat = new CalculatedStat(item.damageValue*item.damageRate, item.damageCurrency, item.criticalRate);
                }
            }
        }
    }

    public void increaseItemLevel(Item item){
        item.level = item.level+1;
        item.calculatedStat.damageValue=item.damageValue*(item.damageRate*item.level);
//        gameManager.gameInformation.upgradedItem.put(item.id,item);
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

