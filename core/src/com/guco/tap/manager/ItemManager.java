package com.guco.tap.manager;

import com.guco.tap.entity.ItemEntity;
import com.guco.tap.entity.ItemUpgradeEntity;
import com.guco.tap.entity.UpgradeEffect;

public class ItemManager {
    private GameManager gameManager;

    public ItemManager(GameManager gameManager){
        this.gameManager = gameManager;
        updateWeaponList();
    }

    public void updateWeaponList() {
        for (int i = 0; i < gameManager.assetManager.weaponList.size(); i++) {
            ItemEntity itemEntity = gameManager.assetManager.weaponList.get(i);
            if (gameManager.assetManager.weaponUpgradeList.size()>i) {
                itemEntity.upgrades = gameManager.assetManager.weaponUpgradeList.get(i);
            }
        }
    }

    public void increaseItemLevel(ItemEntity itemEntity){
//        gameManager.gameInformation.get
    }

    public void unlockItem(){

    }
    public void increasePerkLevel(){

    }

    public void selectPerk(){

    }

    public void calculateCurrentDamage(ItemEntity itemEntity){
        itemEntity.currentDamage = itemEntity.level * (itemEntity.currentDamage * itemEntity.damageRate);
    }

}

