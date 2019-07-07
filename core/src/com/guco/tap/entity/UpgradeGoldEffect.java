package com.guco.tap.entity;

public class UpgradeGoldEffect extends AbstractUpgradeEffect {

    @Override
    public void apply(ItemEntity itemEntity) {
        originValue = gameInformation.levelBaseGold;
        originCurrency = gameInformation.levelBaseCurrency;

        gameInformation.levelBaseGold = (int) (gameInformation.levelBaseGold*value);
    }

    @Override
    public void unapply(ItemEntity itemEntity) {
        gameInformation.levelBaseGold = (int) originValue;
    }
}
