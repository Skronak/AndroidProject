package com.guco.tap.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.guco.tap.utils.ValueDTO;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Skronak on 17/07/2017.
 * Classe representant l'ensemble des modules du jeu
 *
 */
public class ModuleEntity implements Serializable {

    private int id;
    private String title;
    private String description;
    private ArrayList<ValueDTO> cost;
    private ArrayList<ValueDTO> generation;
    private int lvl;
    private float posX;
    private float posY;
    private TextureRegion textureRegion;
    private String sprite;
    private String icon;
    private String iconDisabled;
    private float width;
    private float height;
    private float iconPosX;
    private float iconPosY;

    public ModuleEntity() {
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

    public ArrayList<ValueDTO> getCost() {
        return cost;
    }

    public void setCost(ArrayList<ValueDTO> cost) {
        this.cost = cost;
    }

    public ArrayList<ValueDTO> getGeneration() {
        return generation;
    }

    public void setGeneration(ArrayList<ValueDTO> generation) {
        this.generation = generation;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
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

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconDisabled() {
        return iconDisabled;
    }

    public void setIconDisabled(String iconDisabled) {
        this.iconDisabled = iconDisabled;
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
