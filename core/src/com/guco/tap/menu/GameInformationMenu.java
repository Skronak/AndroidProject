package com.guco.tap.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.guco.tap.manager.GameManager;

/**
 * Created by Skronak on 21/08/2017.
* TODO a recharger en live
 */
public class GameInformationMenu extends AbstractMenu {

    private Label goldLabel;
    private Label activGoldGenLabel;
    private Label passivGoldGenLabel;
    private Label criticalHitLabel;
    private Label gameTimeLabel;
    private Label tapNumberLabel;

    public GameInformationMenu(GameManager gameManager) {
        super(gameManager);
        goldLabel = new Label(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.getCurrentGold(), gameManager.gameInformation.getCurrency()), skin);
        activGoldGenLabel = new Label(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.getTapDamage(), gameManager.gameInformation.getGenCurrencyActive()), skin);
        passivGoldGenLabel = new Label(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.getGenGoldPassive(), gameManager.gameInformation.getGenCurrencyPassive()), skin);
        criticalHitLabel = new Label(String.valueOf("x "+gameManager.gameInformation.getCriticalRate()), skin);
        gameTimeLabel = new Label(String.valueOf((gameManager.gameInformation.getTotalGameTime()/ (1000*60*60)) + " hours"), skin);
        tapNumberLabel = new Label(String.valueOf(gameManager.gameInformation.getTotalTapNumber()), skin);
        customizeMenuTable();
    }

    public void customizeMenuTable() {
        parentTable.add(new Label("GAME INFORMATION ", skin)).bottom().padTop(20).colspan(2);
        parentTable.row();
        parentTable.add(new Label("Total play time: ", skin)).left();
        parentTable.add(gameTimeLabel).left();
        parentTable.row();
        parentTable.add(new Label("Total tap number: ", skin)).left();
        parentTable.add(tapNumberLabel).left();
        parentTable.row();
        parentTable.add(new Label("Current gold: ", skin)).left();
        parentTable.add(goldLabel).left();
        parentTable.row();
        parentTable.add(new Label("Damage: ", skin)).left();
        parentTable.add(activGoldGenLabel).left();
        parentTable.row();
        parentTable.add(new Label("Passive income: ", skin)).left();
        parentTable.add(passivGoldGenLabel).left();
        parentTable.row();
        parentTable.add(new Label("Critical rate: ", skin)).left();
        parentTable.add(criticalHitLabel).left();
        parentTable.row();
        parentTable.add(new Label("Total enemies slayed: ", skin)).left();
        parentTable.row();
        parentTable.add(new Label("- skeleton: ", skin)).left().padLeft(20);
        parentTable.row();
        parentTable.add(new Label("- orc: ", skin)).left().padLeft(20);
        parentTable.row();
        parentTable.add(new Label("- demon: ", skin)).left().padLeft(20);
        parentTable.row();
        parentTable.add(new Label("- devils: ", skin)).left().padLeft(20);
        parentTable.row();
    }

    @Override
    public void update() {
        goldLabel.setText(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.getCurrentGold(), gameManager.gameInformation.getCurrency()));
        activGoldGenLabel.setText(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.getTapDamage(), gameManager.gameInformation.getGenCurrencyActive()));
        passivGoldGenLabel.setText(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.getGenGoldPassive(), gameManager.gameInformation.getGenCurrencyPassive()));
        criticalHitLabel.setText("x "+gameManager.gameInformation.getCriticalRate());
        gameTimeLabel.setText(String.valueOf((gameManager.gameInformation.getTotalGameTime()/ (1000*60*60)) + " hours"));
        tapNumberLabel.setText(String.valueOf(gameManager.gameInformation.getTotalTapNumber()));
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************
}
