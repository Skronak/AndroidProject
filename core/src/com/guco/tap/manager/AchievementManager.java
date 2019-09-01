package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.guco.tap.menu.achievement.element.AchievementElement;

import java.util.List;

/**
 * Todo menu is calculting achivement status, to do here
 */
public class AchievementManager {
    private GameManager gameManager;
    public List<AchievementElement> achievementElementList;

    public AchievementManager(GameManager gameManager) {
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");
        this.gameManager = gameManager;

        // Retrieve Achievement from AssetsManager
        this.achievementElementList = gameManager.assetsManager.getAchievementElementList();

        // Init all achievement status with information from current GameInformation
        updateAchivementElementStatus();
    }

    /**
     * Init achievementElement based on saved GameInformation
     * if achievement wasn't unlock, check if is now unlocked
     */
    public void updateAchivementElementStatus() {
        for (int i = 0; i < achievementElementList.size(); i++) {
            achievementElementList.get(i).condition.gameInformation = gameManager.gameInformation;
            switch (gameManager.gameInformation.achievList.get(i)) {
                case 0:
                    achievementElementList.get(i).isAchieved = achievementElementList.get(i).condition.isAchieved();
                    achievementElementList.get(i).isClaimed = false;
                    achievementElementList.get(i).isNew = true;
                    if (achievementElementList.get(i).isAchieved){
                        gameManager.gameInformation.achievList.set(i,0);
                    } else {
                        gameManager.gameInformation.achievList.set(i, 0);
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
