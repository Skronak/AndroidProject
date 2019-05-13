package com.guco.tap.achievement;

import com.guco.tap.entity.GameInformation;

public class GoldCondition extends AbstractCondition {

    @Override
    public boolean isAchieved() {
        boolean val = false;
        if (gameInformation.getCurrency()>=conditionCurrency) {
            val=true;
        } else if (gameInformation.getCurrency()==conditionCurrency
                &&gameInformation.getCurrentGold()>conditionValue){
            val=true;
        } else {
            val=false;
        }
        return val;
    }
    @Override
    public int getConditionProgression(){
        float progression = (gameInformation.getCurrency()/conditionCurrency)*100;
        return (int) progression;
    }

}
