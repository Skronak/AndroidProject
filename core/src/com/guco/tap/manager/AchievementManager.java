package com.guco.tap.manager;

import com.guco.tap.achievement.AchievementElement;
import com.guco.tap.entity.GameInformation;

import java.util.List;

/**
 * Todo menu is calculting achivement status, to do here
 */
public class AchievementManager {
    private GameManager gameManager;
    public List<AchievementElement> achievementElementList;

    public AchievementManager(GameManager gameManager) {
        this.gameManager = gameManager;

        // Retrieve Achievement from AssetManager
        this.achievementElementList = AssetManager.INSTANCE.getAchievementElementList();

        // Init all achievement status with information from current GameInformation
        updateAchivementElementStatus();
    }

    /**
     * Init achievementElement based on saved GameInformation
     * if achievement wasn't unlock, check if is now unlocked
     */
    public void updateAchivementElementStatus() {
        for (int i = 0; i < achievementElementList.size(); i++) {
            switch (GameInformation.INSTANCE.getAchievList().get(i)) {
                case 0:
                    achievementElementList.get(i).isAchieved = achievementElementList.get(i).condition.isAchieved();
                    achievementElementList.get(i).isClaimed = false;
                    if (achievementElementList.get(i).isAchieved){
                        GameInformation.INSTANCE.getAchievList().set(i,0);
                    } else {
                        GameInformation.INSTANCE.getAchievList().set(i, 0);
                    }
                    break;
                case 1:
                    achievementElementList.get(i).isAchieved = true;
                    achievementElementList.get(i).isClaimed = false;
                    break;
                case 2:
                    achievementElementList.get(i).isAchieved = true;
                    achievementElementList.get(i).isClaimed = true;
                    break;
                default:
                    break;
            }
        }
    }

}
