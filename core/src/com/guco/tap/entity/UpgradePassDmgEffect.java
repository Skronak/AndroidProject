package com.guco.tap.entity;

public class UpgradePassDmgEffect extends AbstractUpgradeEffect {

    @Override
    public void apply(Item item) {
        originValue = gameInformation.passivDamageValue;
        originCurrency= gameInformation.passivDamageCurrency;

        gameInformation.passivDamageValue = (int) (gameInformation.passivDamageValue * value);
    }

    @Override
    public void unapply(Item item) {
        gameInformation.passivDamageValue = (int) originValue;
    }
}
