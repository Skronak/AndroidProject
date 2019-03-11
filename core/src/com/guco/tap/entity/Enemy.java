package com.guco.tap.entity;

import java.util.List;

public class Enemy {
    private String id;
    private String name;
    private int category;
    private int hp;
    private int time;
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int gold;
    private List<String> framesIdle;
    private List<String> framesHurt;
    private List<String> framesDeath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public List<String> getFramesDeath() {
        return framesDeath;
    }

    public void setFramesDeath(List<String> framesDeath) {
        this.framesDeath = framesDeath;
    }

    public List<String> getFramesIdle() {
        return framesIdle;
    }

    public void setFramesIdle(List<String> framesIdle) {
        this.framesIdle = framesIdle;
    }

    public List<String> getFramesHurt() {
        return framesHurt;
    }

    public void setFramesHurt(List<String> framesHurt) {
        this.framesHurt = framesHurt;
    }
}
