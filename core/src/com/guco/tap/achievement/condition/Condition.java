package com.guco.tap.achievement.condition;

import com.guco.tap.entity.GameInformation;

public interface Condition {

    public boolean isAchieved();

    public int getConditionProgression();
}
