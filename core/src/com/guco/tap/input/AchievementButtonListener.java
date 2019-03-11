package com.guco.tap.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.achievement.AchievementElement;
import com.guco.tap.menu.AchievementMenu;

/**
 * Created by Skronak on 29/01/2017.
 * Listener du bouton UPGRADE d'un module
 */
public class AchievementButtonListener extends ClickListener {

    // Identifiant du module rattaché au listener
    private AchievementMenu achievementMenu;
    private AchievementElement achievementElement;


    public AchievementButtonListener(AchievementMenu achievementMenu, AchievementElement achievementElement) {
        this.achievementMenu = achievementMenu;
        this.achievementElement = achievementElement;
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        achievementMenu.titleLabel.setText(achievementElement.title);
        achievementMenu.descriptionLabel.setText(achievementElement.description);
        achievementMenu.claimAchievementButtonListener.achievementElement = achievementElement;

        return false;
     }
}
