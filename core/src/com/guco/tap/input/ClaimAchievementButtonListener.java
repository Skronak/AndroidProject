package com.guco.tap.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.achievement.AchievementElement;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.menu.AchievementMenu;

/**
 *
 */
public class ClaimAchievementButtonListener extends ClickListener {

    // Identifiant du module rattaché au listener
    private AchievementMenu achievementMenu;
    public AchievementElement achievementElement;

    public ClaimAchievementButtonListener(AchievementMenu achievementMenu) {
        this.achievementMenu = achievementMenu;
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        GameInformation.INSTANCE.setSkillPoint(GameInformation.INSTANCE.getSkillPoint()+ achievementElement.skillPoint);
        achievementMenu.animateClaim();
        achievementMenu.descriptionLabel.setText(achievementElement.description);
        return false;
    }
}
