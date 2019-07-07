package com.guco.tap.achievement.condition;

public class GoldCondition extends AbstractCondition {

    @Override
    public boolean isAchieved() {
        boolean val = false;
        if (gameInformation.currentCurrency>=conditionCurrency) {
            val=true;
        } else if (gameInformation.currentCurrency==conditionCurrency
                &&gameInformation.currentGold>conditionValue){
            val=true;
        } else {
            val=false;
        }
        return val;
    }
    @Override
    public int getConditionProgression(){
        float progression = (gameInformation.currentCurrency/conditionCurrency)*100;
        return (int) progression;
    }

}
