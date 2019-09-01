package com.guco.tap.dto;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.guco.tap.entity.AttributeElementLevel;

import java.util.List;

/**
 * Created by Skronak on 17/07/2017.
 * Module element from a station
 *
 */
public class ModuleElementDTO extends Image {
    private int id;
    private String title;
    private String description;
    private List<AttributeElementLevel> level;
    private float posX;
    private float posY;
    private String icon;
    private float width;
    private float height;
    private float iconPosX;
    private float iconPosY;

    public ModuleElementDTO() {

    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AttributeElementLevel> getLevel() {
        return level;
    }

    public void setLevel(List<AttributeElementLevel> level) {
        this.level = level;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getIconPosX() {
        return iconPosX;
    }

    public void setIconPosX(float iconPosX) {
        this.iconPosX = iconPosX;
    }

    public float getIconPosY() {
        return iconPosY;
    }

    public void setIconPosY(float iconPosY) {
        this.iconPosY = iconPosY;
    }

}
