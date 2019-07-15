package com.guco.tap.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.guco.tap.manager.AttributeManager;

/**
 * Created by Skronak on 29/01/2017.
 * Listener du bouton UPGRADE d'un module
 */
public class BuyUpgradeButtonListener extends ClickListener {

    // Identifiant du module rattaché au listener
    private AttributeManager attributeManager;
    private int idModule;

    public BuyUpgradeButtonListener(AttributeManager attributeManager, int id) {
        this.attributeManager = attributeManager;
        this.idModule = id;
    }

    @Override
    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
        if (attributeManager.isAvailableUpgrade(idModule)) {
            attributeManager.increaseAttributeLevel(idModule);
            attributeManager.updateAttributeMenuInformation(idModule);
        }
        return false;
    }
}
