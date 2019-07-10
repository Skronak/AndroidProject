package com.guco.tap.entity;

public class UpgradeGoldEffect extends AbstractUpgradeEffect {

    @Override
    public void apply(Item item) {
        originValue = gameInformation.levelBaseGold;
        originCurrency = gameInformation.levelBaseCurrency;

        gameInformation.levelBaseGold = (int) (gameInformation.levelBaseGold*value);
    }

    @Override
    public void unapply(Item item) {
        gameInformation.levelBaseGold = (int) originValue;
    }
}
