package com.guco.tap.entity;

public class UpgradeDamageEffect extends AbstractUpgradeEffect {

    @Override
    public void apply(Item item) {
        originValue = item.damageValue;
       // item.currentDamageValue = item.damageValue + (item.damageValue * value);
    }

    @Override
    public void unapply(Item item) {
        //item.currentDamageValue = originValue;
    }
}
