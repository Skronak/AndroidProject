package com.guco.tap.entity;

import com.guco.tap.utils.ValueDTO;

import java.io.Serializable;

/**
 * Created by Skronak on 17/07/2017.
 * Classe representant l'ensemble des modules du jeu
 *
 */
public class ModuleElementLevel implements Serializable {

    // Cout du niveau suivant
    private ValueDTO cost;
    // Generation passive du niveau en cours
    private ValueDTO passGen;
    // Generation actif du niveau en cours
    private ValueDTO actGen;
    // Sprite
    private String sprite;

//*****************************************************
//                  GETTER & SETTER
// ****************************************************

    public ValueDTO getCost() {
        return cost;
    }

    public void setCost(ValueDTO cost) {
        this.cost = cost;
    }

    public ValueDTO getPassGen() {
        return passGen;
    }

    public void setPassGen(ValueDTO passGen) {
        this.passGen = passGen;
    }

    public ValueDTO getActGen() {
        return actGen;
    }

    public void setActGen(ValueDTO actGen) {
        this.actGen = actGen;
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }
}
