package com.guco.tap.dto;

import com.guco.tap.actor.AnimatedBackgroundObject;

import java.util.List;

public class Area {
    public int id;
    public String name;
    public int levels;
    public int fights;
    public String backgroundTexture;
    public String menuBackgroundTexture;
    public String icon;
    public int[] enemiesId;
    public int enemyPosX;
    public int enemyPosY;
    public int enemyBackPosX;
    public int enemyBackPosY;
    public int enemyHiddenPosX;
    public int enemyHiddenPosY;
    public int playerPosX;
    public int playerPosY;
    public List<AnimatedBackgroundObject> objectList;
}
