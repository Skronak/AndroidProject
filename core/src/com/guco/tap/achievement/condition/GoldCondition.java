package com.guco.tap.achievement.condition;

public class GoldCondition extends AbstractCondition {

    @Override
    public boolean isAchieved() {
        boolean val = false;
        if (gameInformation.currentGoldCurrency >=conditionCurrency) {
            val=true;
        } else if (gameInformation.currentGoldCurrency ==conditionCurrency
                &&gameInformation.currentGoldValue >conditionValue){
            val=true;
        } else {
            val=false;
        }
        return val;
    }
    @Override
    public int getConditionProgression(){
        float progression = (gameInformation.currentGoldCurrency /conditionCurrency)*100;
        return (int) progression;
    }

}
