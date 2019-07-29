package com.guco.tap.entity;

import com.guco.tap.utils.ValueDTO;

public class CalculatedStat {

    public float damageValue;
    public int damageCurrency;
    public float criticalRate;
    public float costValue;
    public int costCurrency;


    public CalculatedStat(float damageValue, int damageCurrency, float criticalRate) {
        this.damageValue = damageValue;
        this.damageCurrency = damageCurrency;
        this.criticalRate = criticalRate;
    }

    public CalculatedStat(ValueDTO damage, ValueDTO cost, float criticalRate){
        this.damageValue = damage.value;
        this.damageCurrency = damage.currency;
        this.costValue = cost.value;
        this.costCurrency = cost.currency;
        this.criticalRate = criticalRate;
    }

    public CalculatedStat(Item item) {
        this.damageValue = item.damageValue;
        this.damageCurrency = item.damageCurrency;
        this.criticalRate = item.criticalRate;
        this.costValue = item.baseCostValue;
        this.costCurrency = item.baseCostCurrency;
    }
}
