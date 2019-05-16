package com.guco.tap.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.manager.GameManager;
import com.guco.tap.manager.ModuleManager;

/**
 * Created by Skronak on 29/01/2017.
 * Listener du bouton UPGRADE d'un module
 */
public class BuyUpgradeButtonListener extends ClickListener {

    // Identifiant du module rattaché au listener
    private ModuleManager moduleManager;
    private int idModule;

    public BuyUpgradeButtonListener(ModuleManager moduleManager, int id) {
        this.moduleManager = moduleManager;
        this.idModule = id;
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        if (moduleManager.isAvailableUpgrade(idModule)) {
            moduleManager.increaseModuleLevel(idModule);
            moduleManager.updateModuleMenuInformation(idModule);
        }
        return false;
    }
}
