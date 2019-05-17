package com.guco.tap.utils;

/**
 * Created by Skronak on 19/02/2017.
 */
public enum MenuState {
    //Objets directement construits
    WEAPON("Weapon"),
    BODY ("Body"),
    HEAD ("Head"),
    WEAPONPERK ("weaponPerk"),
    BODYPERK ("bodyPerk"),
    HEADPERK ("headperk");

    private String state = "";

    //Constructeur
    MenuState(String state){
        this.state = state;
    }

    public String toString(){
        return state;
    }
}
