package com.guco.tap.dto;

import java.util.List;

import com.guco.tap.actor.AnimatedBackgroundObject;

public class Area {
    public int id;
    public String name;
    public int levels;
    public int fights;
    public String background;
    public String icon;
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
