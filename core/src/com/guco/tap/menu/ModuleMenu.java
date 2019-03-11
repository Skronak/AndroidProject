package com.guco.tap.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.entity.ModuleMenuElement;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 */
public class ModuleMenu extends AbstractMenu {
    private Label detailGold;
    private Label detailDescription;
    private Label detailLevel;
    private Label detailTitre;
    private Stack stack;
    // indique le skill actuellement selectionne
    private int currentSelection;
    private List<ImageButton> moduleButtonList;
    private VerticalGroup scrollContainerVG;

    public ModuleMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();
        currentSelection = 1;         // selection 1 module par defaut
    }

    public void customizeMenuTable() {
        parentTable.add(new Label("UPGRADE", skin)).bottom().padTop(20);
        parentTable.row();
        parentTable.add(initScrollingModuleSelection());
    }

    /**
     * Methode d'initialisation des modules disponibles a
     * l'upgrade
     *
     * @return
     */
    public ScrollPane initScrollingModuleSelection() {
        scrollContainerVG = new VerticalGroup();

        scrollContainerVG.space(10f);
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(AssetManager.INSTANCE.getScrollTexture(), 10, 50));

        ScrollPane pane = new ScrollPane(scrollContainerVG, paneStyle);
        pane.setScrollingDisabled(true, false);

        // Definition drawables possibles pour les boutons
        moduleButtonList = new ArrayList<ImageButton>();

        for (int i = 0; i < AssetManager.INSTANCE.getModuleElementList().size(); i++) {
            ModuleMenuElement moduleMenuElement = new ModuleMenuElement(gameManager);
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
            if (gameManager.moduleManager.isAvailableUpgrade(i)){
                ((ModuleMenuElement) getScrollContainerVG().getChildren().get(i)).getBuyButton().setTouchable(Touchable.enabled);
                ((ModuleMenuElement) getScrollContainerVG().getChildren().get(i)).getBuyButton().setColor(Color.YELLOW);
            } else {
                ((ModuleMenuElement) getScrollContainerVG().getChildren().get(i)).getBuyButton().setTouchable(Touchable.disabled);
                ((ModuleMenuElement) getScrollContainerVG().getChildren().get(i)).getBuyButton().setColor(Color.GRAY);
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

    public Label getDetailGold() {
        return detailGold;
    }

    public void setDetailGold(Label detailGold) {
        this.detailGold = detailGold;
    }

    public Label getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(Label detailDescription) {
        this.detailDescription = detailDescription;
    }

    public Label getDetailLevel() {
        return detailLevel;
    }

    public void setDetailLevel(Label detailLevel) {
        this.detailLevel = detailLevel;
    }

    public Label getDetailTitre() {
        return detailTitre;
    }

    public void setDetailTitre(Label detailTitre) {
        this.detailTitre = detailTitre;
    }

    public int getCurrentSelection() {
        return currentSelection;
    }

    public void setCurrentSelection(int currentSelection) {
        this.currentSelection = currentSelection;
    }

    public List<ImageButton> getModuleButtonList() {
        return moduleButtonList;
    }

    public void setModuleButtonList(List<ImageButton> moduleButtonList) {
        this.moduleButtonList = moduleButtonList;
    }

    public VerticalGroup getScrollContainerVG() {
        return scrollContainerVG;
    }
}
