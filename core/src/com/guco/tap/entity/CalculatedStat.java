package com.guco.tap.entity;

public class CalculatedStat {

    public float damageValue;
    public int damageCurrency;
    public float criticalRate;

    public CalculatedStat(float damageValue, int damageCurrency, float criticalRate) {
        this.damageValue = damageValue;
        this.damageCurrency = damageCurrency;
        this.criticalRate = criticalRate;
    }
}
