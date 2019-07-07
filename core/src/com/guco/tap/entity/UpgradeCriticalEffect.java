package com.guco.tap.entity;

public class UpgradeCriticalEffect extends AbstractUpgradeEffect {
    public float criticalRate;

    @Override
    public void apply(ItemEntity itemEntity){
        originValue=gameInformation.criticalRate;
        gameInformation.criticalRate=criticalRate;
    }

    @Override
    public void unapply(ItemEntity itemEntity) {
        gameInformation.criticalRate = originValue;
    }
}
