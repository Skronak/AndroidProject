package com.guco.tap.achievement.condition;

/**
 * Condition sur le nombre d'upgrade debloquee
 */
public class ModuleNumberCondition extends AbstractCondition {

    @Override
    public boolean isAchieved() {
        currentValue=0;
        for (int i = 0; i<gameInformation.attributeLevel.size(); i++) {
            if (gameInformation.attributeLevel.get(i)>0){
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
