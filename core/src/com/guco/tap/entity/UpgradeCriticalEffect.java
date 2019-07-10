package com.guco.tap.entity;

public class UpgradeCriticalEffect extends AbstractUpgradeEffect {
    public float criticalRate;

    @Override
    public void apply(Item item){
        originValue=gameInformation.criticalRate;
        gameInformation.criticalRate=criticalRate;
    }

    @Override
    public void unapply(Item item) {
        gameInformation.criticalRate = originValue;
    }
}
