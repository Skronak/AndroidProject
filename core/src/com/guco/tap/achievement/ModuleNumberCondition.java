package com.guco.tap.achievement;

/**
 * Condition sur le nombre d'upgrade debloquee
 */
public class ModuleNumberCondition extends AbstractCondition {

    @Override
    public boolean isAchieved() {
        currentValue=0;
        for (int i=0;i<gameInformation.getUpgradeLevelList().size();i++) {
            if (gameInformation.getUpgradeLevelList().get(i)>0){
                currentValue+=1;
            }
        }
        return currentValue>=conditionValue;
    }
    @Override
    public int getConditionProgression() {
        float progression = (currentValue/conditionValue)*100;
        return (int) progression;
    }

}
