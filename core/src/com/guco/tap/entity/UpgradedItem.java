package com.guco.tap.entity;

import java.util.List;

public class UpgradedItem extends Item {
    public float baseDamageValue;
    public int baseDamageCurrency;
    public int currentCriticalRate;
    public List<ItemUpgrade> selectedUpgrade;
}
