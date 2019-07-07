package com.guco.tap.entity;

public abstract class AbstractUpgradeEffect implements UpgradeEffect {
    public GameInformation gameInformation;
    public float value;
    public float currency;
    public float originValue;
    public float originCurrency;
}
