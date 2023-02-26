package com.guco.tap.dto;

import com.guco.tap.actor.AnimatedBackgroundObject;

import java.util.List;

public class Area {
    public int id;
    public String name;
    public int enemiesBaseLevel;// monster level
    public int fights;//nb fight per level
    public int floors;// nb levels of this area
    public String backgroundTexture;
    public String menuBackgroundTexture;
    public String icon;
    public int[] enemiesId; // enemies that can appear at that level
    public int[] bossId;
    public int enemyPosX;
    public int enemyPosY;
    public int enemyBackPosX;
    public int enemyBackPosY;
    public int enemyHiddenPosX;
    public int enemyHiddenPosY;
    public int playerPosX;
    public int playerPosY;
    public List<AnimatedBackgroundObject> objectList;
    public int nextAreaId;
}
