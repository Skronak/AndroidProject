package com.guco.tap.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.guco.tap.manager.GameManager;

public class UiLevelSelect extends Table {

    private GameManager gameManager;
    private Image previousLevel, currentLevel, nextLevel, activeLevel;
    private Label previousLevelLabel, currentLevelLabel, nextLevelLabel;
    public UiLevelSelect(GameManager gameManager) {
        this.gameManager = gameManager;
        previousLevel = new Image(new Texture("sprites/badlogic1.jpg"));
        currentLevel = new Image(new Texture("sprites/badlogic1.jpg"));
        nextLevel = new Image(new Texture("sprites/badlogic1.jpg"));
        previousLevelLabel = new Label("1", gameManager.ressourceManager.getSkin());
        currentLevelLabel = new Label("2", gameManager.ressourceManager.getSkin());
        nextLevelLabel = new Label("3", gameManager.ressourceManager.getSkin());

        add(previousLevel).size(30,30);
        add(currentLevel).size(30,30).padLeft(5);
        add(nextLevel).size(30,30).padLeft(5);
    }

    public void selectLevel(int level){

    }

    private void updateVisibleLevel(){

    }
}
