package com.guco.tap.achievement.condition;

import com.guco.tap.entity.GameInformation;

public abstract class AbstractCondition implements Condition {

    public GameInformation gameInformation;
    protected int conditionValue;
    protected int conditionCurrency;
    protected int currentValue;
}
