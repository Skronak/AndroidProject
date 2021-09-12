package com.guco.tap.entity;

import java.util.List;

public class EnemyTemplateEntity {
    private String id;
    private String name;

    public boolean isCanAttack() {
        return canAttack;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    private boolean canAttack;
    private int baseHp;
    private int time;
    private int posX;
    private int posY;
    private int width;
    private int height;
    private float attackDuration; // maybe useless, je le determine grace aux metas de l'animation
    private String scmlFile;
    private boolean switchX;
    private float scale;
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

    public int getBaseHp() {
        return baseHp;
    }

    public void setBaseHp(int baseHp) {
        this.baseHp = baseHp;
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

    public float getAttackDuration() {
        return attackDuration;
    }

    public void setAttackDuration(float attackDuration) {
        this.attackDuration = attackDuration;
    }

    public String getScmlFile() {
        return scmlFile;
    }

    public void setScmlFile(String scmlFile) {
        this.scmlFile = scmlFile;
    }

    public boolean isSwitchX() {
        return switchX;
    }

    public void setSwitchX(boolean switchX) {
        this.switchX = switchX;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
