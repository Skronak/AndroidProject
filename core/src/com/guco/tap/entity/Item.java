package com.guco.tap.entity;

import java.util.List;

public class Item {
    public int id;
    public int type;
    public String name;
    public String description;
    public float damageValue;
    public int damageCurrency;
    public float criticalRate;
    public int reqLvl;
    public int level;
    public int damageRate;
    public int baseCostValue;
    public int baseCostCurrency;
    public float baseCostRate;
    public int mapId;
    public String mapName;
    public String icon;
    public float width;
    public float height;
    public TiersUpgrades upgrades;
    public List<ItemUpgrade> selectedUpgrades;
    public CalculatedStat calculatedStat;
}
