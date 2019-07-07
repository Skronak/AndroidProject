package com.guco.tap.achievement;

import com.guco.tap.achievement.condition.AbstractCondition;
import com.guco.tap.utils.ValueDTO;

import java.util.Date;

public class AchievementElement {

    public int id;

    public String title;

    public String description;

    public String icon;

    public AbstractCondition condition;

    public String rewardType;

    public ValueDTO rewardValue;

    public int skillPoint;

    public boolean isClaimed;

    public boolean isNew;

    public boolean isAchieved;

    public Date date;
}
