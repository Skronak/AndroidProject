package com.guco.tap.action;

import com.guco.tap.manager.DataManager;

public class IncreaseGoldAction implements Runnable {
    private DataManager dataManager;

    public IncreaseGoldAction(DataManager dataManager){
        this.dataManager = dataManager;
    }

    @Override
    public void run() {
        dataManager.increaseGold();
    }
}
