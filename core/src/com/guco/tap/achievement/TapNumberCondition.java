package com.guco.tap.achievement;

import com.guco.tap.entity.GameInformation;

public class TapNumberCondition extends AbstractCondition {

    @Override
    public boolean isAchieved() {
        return gameInformation.getTotalTapNumber()>=conditionValue;
    }
    @Override
    public int getConditionProgression(){
        float progression = (gameInformation.getTotalTapNumber()/conditionValue)*100;
        return (int) progression;
    }

}
