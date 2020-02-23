package com.guco.tap.entity;

public class UpgradeGoldEffect extends AbstractUpgradeEffect {

    @Override
    public void apply(Item item) {
        originValue = gameInformation.levelBaseGoldValue;
        originCurrency = gameInformation.levelBaseGoldCurrency;

        gameInformation.levelBaseGoldValue = (int) (gameInformation.levelBaseGoldValue *value);
    }

    @Override
    public void unapply(Item item) {
        gameInformation.levelBaseGoldValue = (int) originValue;
    }
}
