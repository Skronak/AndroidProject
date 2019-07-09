package com.guco.tap.entity;

public class UpgradeDamageEffect extends AbstractUpgradeEffect {

    @Override
    public void apply(ItemEntity itemEntity) {
        originValue = itemEntity.baseDamage;
        itemEntity.currentDamageValue = itemEntity.currentDamageValue + (itemEntity.baseDamage * value);
    }

    @Override
    public void unapply(ItemEntity itemEntity) {
        itemEntity.currentDamageValue = originValue;
    }
}
