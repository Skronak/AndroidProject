package com.guco.tap.achievement.condition;

public class TapNumberCondition extends AbstractCondition {

    @Override
    public boolean isAchieved() {
        return gameInformation.totalTapNumber>=conditionValue;
    }
    @Override
    public int getConditionProgression(){
        float progression = (gameInformation.totalTapNumber/conditionValue)*100;
        return (int) progression;
    }

}
