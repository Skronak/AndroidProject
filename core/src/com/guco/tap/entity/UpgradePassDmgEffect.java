package com.guco.tap.entity;

public class UpgradePassDmgEffect extends AbstractUpgradeEffect {

    @Override
    public void apply(ItemEntity itemEntity) {
        originValue = gameInformation.passivDamageValue;
        originCurrency= gameInformation.passivDamageCurrency;

        gameInformation.passivDamageValue = (int) (gameInformation.passivDamageValue * value);
    }

    @Override
    public void unapply(ItemEntity itemEntity) {
        gameInformation.passivDamageValue = (int) originValue;
    }
}
