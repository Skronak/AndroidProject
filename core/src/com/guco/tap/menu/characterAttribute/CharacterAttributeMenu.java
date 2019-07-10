package com.guco.tap.menu.characterAttribute;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.menu.characterAttribute.element.CharacterAttributeElement;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 */
public class CharacterAttributeMenu extends AbstractMenu {
    private VerticalGroup scrollContainerVG;

    public CharacterAttributeMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();
    }

    public void customizeMenuTable() {
        parentTable.add(new Label("UPGRADE", skin)).bottom().padTop(10).padBottom(20);
        parentTable.row();

        ScrollPane pane = initPane();
        parentTable.add(pane);
    }

    /**
     * Methode d'initialisation des modules disponibles a
     * l'upgrade
     *
     * @return
     */
    public ScrollPane initPane() {
        scrollContainerVG = new VerticalGroup();

        scrollContainerVG.space(10f);
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.getScrollTexture(), 10, 50));

        ScrollPane pane = new ScrollPane(scrollContainerVG, paneStyle);
        pane.setScrollingDisabled(true, false);

        for (int i = 0; i < gameManager.ressourceManager.getModuleElementList().size(); i++) {
            CharacterAttributeElement characterAttributeElement = new CharacterAttributeElement(gameManager);
            characterAttributeElement.initModuleMenuElement(i);
            scrollContainerVG.addActor(characterAttributeElement);
        }
        Gdx.app.log("CharacterAttributeMenu", "Generation des boutons de Module terminee");

        return pane;
    }

    /**
     * Update all module buybutton to check if player can click them
     */
    public void updateBuyButton () {
        for (int i = 0; i<gameManager.ressourceManager.getModuleElementList().size(); i++) {
            if (gameManager.moduleManager.isAvailableUpgrade(i)){
                ((CharacterAttributeElement) getScrollContainerVG().getChildren().get(i)).getBuyButton().setTouchable(Touchable.enabled);
                ((CharacterAttributeElement) getScrollContainerVG().getChildren().get(i)).getBuyButton().setColor(Color.YELLOW);
            } else {
                ((CharacterAttributeElement) getScrollContainerVG().getChildren().get(i)).getBuyButton().setTouchable(Touchable.disabled);
                ((CharacterAttributeElement) getScrollContainerVG().getChildren().get(i)).getBuyButton().setColor(Color.GRAY);
            }
        }
    }

    @Override
    public void update() {
        updateBuyButton();
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************

    public VerticalGroup getScrollContainerVG() {
        return scrollContainerVG;
    }
}
