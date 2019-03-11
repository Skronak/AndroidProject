package com.guco.tap.achievement;

import com.guco.tap.entity.GameInformation;

public class ModuleLevelCondition implements Condition {
    private int conditionValue;
    private int conditionNumber;

    private int currentValue;

    public ModuleLevelCondition(){
    }

    @Override
    public boolean isAchieved() {
        currentValue=0;
        for (int i=0;i<GameInformation.INSTANCE.getUpgradeLevelList().size();i++) {
            if (GameInformation.INSTANCE.getUpgradeLevelList().get(i)>conditionValue){
                currentValue=+1;
            }
        }
        return currentValue>=conditionNumber;
    }

    @Override
    public int getConditionProgression() {
        float progression = (currentValue/conditionNumber)*100;
        return (int) progression;
    }

}
