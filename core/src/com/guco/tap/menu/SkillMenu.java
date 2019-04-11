package com.guco.tap.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.entity.ModuleMenuElement;
import com.guco.tap.entity.SkillMenuElement;
import com.guco.tap.input.BuyUpgradeButtonListener;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 */
public class SkillMenu extends AbstractMenu {
    private Label detailGold;
    private Label detailDescription;
    private Label detailLevel;
    private Label detailTitre;
    private Stack stack;
    // indique le skill actuellement selectionne
    private int currentSelection;
    private List<ImageButton> moduleButtonList;
    private VerticalGroup scrollContainerVG;

    public SkillMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();
        currentSelection = 1;         // selection 1 module par defaut
    }

    public void customizeMenuTable() {
        TextButton buyButton = new TextButton("UPGRADE",AssetManager.INSTANCE.getModuleMenuBuyTxtBtnStyle());

        parentTable.add(new Label("UPGRADE", skin)).bottom().padTop(20);
        parentTable.row();
        parentTable.add(initScrollingModuleSelection());
        parentTable.row();
        parentTable.add(buyButton).height(50).width(200).padTop(10).padBottom(10);

    }

    /**
     * Methode d'initialisation des modules disponibles a
     * l'upgrade
     *
     * @return
     */
    public ScrollPane initScrollingModuleSelection() {
        scrollContainerVG = new VerticalGroup();

        scrollContainerVG.space(20f);
        scrollContainerVG.padTop(20);
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(AssetManager.INSTANCE.getScrollTexture(), 10, 50));

        ScrollPane pane = new ScrollPane(scrollContainerVG, paneStyle);
        pane.setScrollingDisabled(true, false);

        // Definition drawables possibles pour les boutons
        moduleButtonList = new ArrayList<ImageButton>();

        // Add SkillMenuElement element to menu
        for (int i = 0; i < AssetManager.INSTANCE.getModuleElementList().size(); i++) {
            SkillMenuElement moduleMenuElement = new SkillMenuElement(gameManager);
            moduleMenuElement.initModuleMenuElement(i);
            scrollContainerVG.addActor(moduleMenuElement);
        }
        Gdx.app.log("ModuleMenu", "Generation des boutons de Module terminee");

        return pane;
    }

    /**
     * Update all module buybutton to check if player can click them
     */
    public void updateBuyButton () {
        for (int i=0;i<AssetManager.INSTANCE.getModuleElementList().size();i++) {
        }
    }

    @Override
    public void update() {
        updateBuyButton();
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************
}
