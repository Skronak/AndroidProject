package com.guco.tap.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.SkillMenuElement;
import com.guco.tap.manager.ModuleManager;
import com.guco.tap.menu.SkillMenu;

/**
 * Created by Skronak on 29/01/2017.
 * Listener du bouton UPGRADE d'un module
 */
public class SkillSelectButtonListener extends ClickListener {

    private SkillMenu skillMenu;
    private SkillMenuElement skillMenuElement;

    public SkillSelectButtonListener(SkillMenuElement skillMenuElement, SkillMenu skillMenu) {
        this.skillMenu= skillMenu;
        this.skillMenuElement= skillMenuElement;
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        skillMenu.showDetailTable(skillMenuElement);
        event.cancel();
        return true;
    }
}
