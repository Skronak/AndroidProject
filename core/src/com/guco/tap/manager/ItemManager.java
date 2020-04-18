package com.guco.tap.manager;

import com.guco.tap.entity.CalculatedStat;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

public class ItemManager {

    private LargeMath largeMath;
    private TapDungeonGame game;

    public ItemManager(TapDungeonGame game){
        this.largeMath=game.largeMath;
        this.game=game;
        //updateWeaponListWithUpgrade();
    }

    //public void updateWeaponListWithUpgrade() {
    //    for (int i = 0; i < gameManager.assetsManager.weaponList.size(); i++) {
    //        Item item = gameManager.assetsManager.weaponList.get(i);
    //        if (gameManager.assetsManager.weaponUpgradeList.size()>i) {
    //            item.upgrades = gameManager.assetsManager.weaponUpgradeList.get(i);
    //            Item upgradedItem = gameManager.gameInformation.unlockedWeaponList.get(i);
    //            if (upgradedItem.level==0) {
    //                item.calculatedStat = new CalculatedStat(upgradedItem.damageValue, upgradedItem.damageCurrency, upgradedItem.criticalRate);
    //            } else {
    //                item.calculatedStat = new CalculatedStat(item.damageValue*item.damageRate, item.damageCurrency, item.criticalRate);
    //            }
    //        }
    //    }
    //}

    public Item getWeapon(int id) {
        return game.assetsManager.weaponList.get(id);
    }

    public Item getHead(int id) {
        return game.assetsManager.helmList.get(id);
    }

    public Item getBody(int id) {
        return game.assetsManager.bodyList.get(id);
    }

    public void increaseItemLevel(Item item){
        item.level = item.level+1;
        item.calculatedStat = calculateItemStat(item, item.level);
    }

    public CalculatedStat calculateItemStat(Item item, int lvl) {

        ValueDTO damage = evaluateDamage(item,lvl);
        ValueDTO cost = evaluateCost(item, lvl);

        CalculatedStat calculatedStat = new CalculatedStat(damage, cost, item.criticalRate);

        return calculatedStat;
    }

    private ValueDTO evaluateDamage(Item item, int lvl){
        float damageValue = item.damageValue*(item.damageRate*(lvl));
        int damageCurrency = item.damageCurrency;
        ValueDTO damage = largeMath.adjustCurrency(damageValue,damageCurrency);
        return damage;
    }

    private ValueDTO evaluateCost(Item item, int lvl) {
        ValueDTO cost = largeMath.calculateCost(item.baseCostValue,item.baseCostRate,lvl);
        return cost;
    }


    public boolean unlockItem(Item item){
        GameInformation gameInformation = game.gameInformation;
        boolean success=false;
        ValueDTO cost = largeMath.calculateCost(item.baseCostValue, item.baseCostRate,0);
        ValueDTO currentGold = new ValueDTO(gameInformation.currentGoldValue, gameInformation.currentGoldCurrency);
        if (currentGold.compareTo(cost)>=0) {
            ValueDTO newGold = largeMath.decreaseValue(currentGold.value, currentGold.currency, cost.value, cost.currency);
            gameInformation.currentGoldValue = newGold.value;
            gameInformation.currentGoldValue = newGold.currency;
            item.level = 1;
            success=true;
        }
        return success;
    }
    public void increasePerkLevel(){

    }

    public void selectPerk(){

    }

    public void calculateCurrentDamage(Item item){
        //item.currentDamageValue = item.level * (item.damageValue * item.damageRate);
    }

}