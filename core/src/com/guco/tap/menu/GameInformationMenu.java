package com.guco.tap.menu;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.manager.GameManager;

/**
 * Created by Skronak on 21/08/2017.
* TODO a recharger en live
 */
public class GameInformationMenu extends AbstractMenu {

    private Label stationLvlLabel;
    private Label goldLabel;
    private Label activGoldGenLabel;
    private Label passivGoldGenLabel;
    private Label criticalHitLabel;
    private Label gameTimeLabel;
    private Label tapNumberLabel;

    public GameInformationMenu(GameManager gameManager) {
        super(gameManager);
        stationLvlLabel = new Label(String.valueOf(GameInformation.INSTANCE.getStationId()), skin);
        goldLabel = new Label(gameManager.largeMath.getDisplayValue(GameInformation.INSTANCE.getCurrentGold(), GameInformation.INSTANCE.getCurrency()), skin);
        activGoldGenLabel = new Label(gameManager.largeMath.getDisplayValue(GameInformation.INSTANCE.getGenGoldActive(), GameInformation.INSTANCE.getGenCurrencyActive()), skin);
        passivGoldGenLabel = new Label(gameManager.largeMath.getDisplayValue(GameInformation.INSTANCE.getGenGoldPassive(), GameInformation.INSTANCE.getGenCurrencyPassive()), skin);
        criticalHitLabel = new Label(String.valueOf("x "+GameInformation.INSTANCE.getCriticalRate()), skin);
        gameTimeLabel = new Label(String.valueOf((GameInformation.INSTANCE.getTotalGameTime()/ (1000*60*60)) + " hours"), skin);
        tapNumberLabel = new Label(String.valueOf(GameInformation.INSTANCE.getTotalTapNumber()), skin);
        customizeMenuTable();
    }

    public void customizeMenuTable() {
        parentTable.add(new Label("GAME INFORMATION ", skin)).left().top();
        parentTable.row();
        parentTable.row();
        parentTable.add(new Label("Station lvl: ", skin)).left();
        parentTable.add(stationLvlLabel).left();
        parentTable.row();
        parentTable.add(new Label("Current gold: ", skin)).left();
        parentTable.add(goldLabel).left();
        parentTable.row();
        parentTable.add(new Label("Active Gold generation: ", skin)).left();
        parentTable.add(activGoldGenLabel).left();
        parentTable.row();
        parentTable.add(new Label("Passive gold generation: ", skin)).left();
        parentTable.add(passivGoldGenLabel).left();
        parentTable.row();
        parentTable.add(new Label("Critical rate: ", skin)).left();
        parentTable.add(criticalHitLabel).left();
        parentTable.row();
        parentTable.add(new Label("Total Game time: ", skin)).left();
        parentTable.add(gameTimeLabel).left();
        parentTable.row();
        parentTable.add(new Label("Total tap number: ", skin)).left();
        parentTable.add(tapNumberLabel).left();
        parentTable.row();
    }

    @Override
    public void update() {
        stationLvlLabel.setText(String.valueOf(GameInformation.INSTANCE.getStationId()));
        goldLabel.setText(gameManager.largeMath.getDisplayValue(GameInformation.INSTANCE.getCurrentGold(), GameInformation.INSTANCE.getCurrency()));
        activGoldGenLabel.setText(gameManager.largeMath.getDisplayValue(GameInformation.INSTANCE.getGenGoldActive(), GameInformation.INSTANCE.getGenCurrencyActive()));
        passivGoldGenLabel.setText(gameManager.largeMath.getDisplayValue(GameInformation.INSTANCE.getGenGoldPassive(), GameInformation.INSTANCE.getGenCurrencyPassive()));
        criticalHitLabel.setText("x "+GameInformation.INSTANCE.getCriticalRate());
        gameTimeLabel.setText(String.valueOf((GameInformation.INSTANCE.getTotalGameTime()/ (1000*60*60)) + " hours"));
        tapNumberLabel.setText(String.valueOf(GameInformation.INSTANCE.getTotalTapNumber()));
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************
}
