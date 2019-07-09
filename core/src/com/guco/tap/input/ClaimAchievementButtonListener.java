package com.guco.tap.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.menu.achievement.element.AchievementElement;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.achievement.AchievementMenu;

/**
 *
 */
public class ClaimAchievementButtonListener extends ClickListener {

    // Identifiant du module rattaché au listener
    private AchievementMenu achievementMenu;
    public AchievementElement achievementElement;
    private GameManager gameManager;

    public ClaimAchievementButtonListener(GameManager gameManager, AchievementMenu achievementMenu) {
        this.achievementMenu = achievementMenu;
        this.gameManager = gameManager;
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        gameManager.gameInformation.skillPoint=(gameManager.gameInformation.skillPoint+ achievementElement.skillPoint); // TODO pas ici
        achievementMenu.animateClaim();
        achievementMenu.descriptionLabel.setText(achievementElement.description);
        return false;
    }
}
