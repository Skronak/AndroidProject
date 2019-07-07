package com.guco.tap.entity;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class ItemEntity {
    public int id;
    public int type;
    public String name;
    public String description;
    public float damageRate;
    public float baseDamage;
    public float currentDamage;
    public float criticalRate;
    public int reqLvl;
    public int level;
    public int mapId;
    public String mapName;
    public String icon;
    public float width;
    public float height;
    public TiersUpgrades upgrades;
}
