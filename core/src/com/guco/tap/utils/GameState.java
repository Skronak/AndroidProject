package com.guco.tap.utils;

/**
 * Created by Skronak on 19/02/2017.
 */
public enum GameState {
    //Objets directement construits
    MENU ("Menu"),
    IN_GAME ("Ingame"),
    CREDIT ("Credit"),
    PAUSE ("Pause");

    private String state = "";

    //Constructeur
    GameState(String state){
        this.state = state;
    }

    public String toString(){
        return state;
    }
}
