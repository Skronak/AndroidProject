package com.guco.tap.menu.fuse;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;

public class FuseMenu extends AbstractMenu {
    private Label goldLabel;
    private Label activGoldGenLabel;
    private Label passivGoldGenLabel;
    private Label criticalHitLabel;
    private Label gameTimeLabel;
    private Label tapNumberLabel;

    public FuseMenu(GameManager gameManager) {
        super(gameManager);
        goldLabel = new Label(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.currentGoldValue, gameManager.gameInformation.currentGoldCurrency), skin);
        activGoldGenLabel = new Label(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.tapDamageValue, gameManager.gameInformation.tapDamageCurrency), skin);
        passivGoldGenLabel = new Label(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.passivGoldValue, gameManager.gameInformation.passivGoldCurrency), skin);
        criticalHitLabel = new Label(String.valueOf("x " + gameManager.gameInformation.criticalRate), skin);
        gameTimeLabel = new Label(String.valueOf((gameManager.gameInformation.totalGameTime / (1000 * 60 * 60)) + " hours"), skin);
        tapNumberLabel = new Label(String.valueOf(gameManager.gameInformation.totalTapNumber), skin);
        customizeMenuTable();
    }

    public void customizeMenuTable() {
        addMenuHeader("FUSE MENU", 2);
        Table contentTable = new Table();
        contentTable.add(new Label("Total play time: ", skin)).left();
        contentTable.add(gameTimeLabel).left();
        contentTable.row();
        contentTable.add(new Label("Total tap number: ", skin)).left();
        contentTable.add(tapNumberLabel).left();
        contentTable.row();
        contentTable.add(new Label("Current gold: ", skin)).left();
        contentTable.add(goldLabel).left();
        contentTable.row();
        contentTable.add(new Label("Damage: ", skin)).left();
        contentTable.add(activGoldGenLabel).left();
        contentTable.row();
        contentTable.add(new Label("Passive income: ", skin)).left();
        contentTable.add(passivGoldGenLabel).left();
        contentTable.row();
        contentTable.add(new Label("Critical rate: ", skin)).left();
        contentTable.add(criticalHitLabel).left();
        contentTable.row();
        contentTable.add(new Label("Total enemies slayed: ", skin)).left();
        contentTable.row();
        contentTable.add(new Label("- skeleton: ", skin)).left().padLeft(20);
        contentTable.row();
        contentTable.add(new Label("- orc: ", skin)).left().padLeft(20);
        contentTable.row();
        contentTable.add(new Label("- demon: ", skin)).left().padLeft(20);
        contentTable.row();
        contentTable.add(new Label("- devils: ", skin)).left().padLeft(20);

        parentTable.add(contentTable).expandX();
    }

    @Override
    public void show() {
        update();
        super.show();
    }

    @Override
    public void update() {
        goldLabel.setText(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.currentGoldValue, gameManager.gameInformation.currentGoldCurrency));
        activGoldGenLabel.setText(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.tapDamageValue, gameManager.gameInformation.tapDamageCurrency));
        passivGoldGenLabel.setText(gameManager.largeMath.getDisplayValue(gameManager.gameInformation.passivGoldValue, gameManager.gameInformation.passivGoldCurrency));
        criticalHitLabel.setText("x " + gameManager.gameInformation.criticalRate);
        gameTimeLabel.setText(String.valueOf((gameManager.gameInformation.totalGameTime / (1000 * 60 * 60)) + " hours"));
        tapNumberLabel.setText(String.valueOf(gameManager.gameInformation.totalTapNumber));
    }
}
