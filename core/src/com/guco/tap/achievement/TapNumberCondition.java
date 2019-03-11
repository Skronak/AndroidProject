package com.guco.tap.achievement;

import com.guco.tap.entity.GameInformation;

public class TapNumberCondition implements Condition {
    private int conditionValue;

    public TapNumberCondition(){}

    @Override
    public boolean isAchieved() {
        return GameInformation.INSTANCE.getTotalTapNumber()>=conditionValue;
    }
    @Override
    public int getConditionProgression(){
        float progression = (GameInformation.INSTANCE.getTotalTapNumber()/conditionValue)*100;
        return (int) progression;
    }

}
