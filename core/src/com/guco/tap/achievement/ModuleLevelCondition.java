package com.guco.tap.achievement;

import com.guco.tap.entity.GameInformation;

/**
 * Condition sur le nombre d'upgrade a un certain niveau
 */
public class ModuleLevelCondition extends AbstractCondition {
    private int conditionNumber;

    @Override
    public boolean isAchieved() {
        currentValue=0;
        for (int i=0;i<gameInformation.getUpgradeLevelList().size();i++) {
            if (gameInformation.getUpgradeLevelList().get(i)>conditionValue){
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
