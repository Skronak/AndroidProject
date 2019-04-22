package com.guco.tap.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.entity.GameInformation;
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
    private Label costLabel;
    private Label descriptionLabel;
    private Label levelLabel;
    private Label titleLabel;
    // indique le skill actuellement selectionne
    private int currentSelection;
    private List<ImageButton> moduleButtonList;
    private VerticalGroup scrollContainerVG;
    private Table detailTable;
    private Table buyTable;
    private Stack tableStack;
    private SkillMenuElement currentElement;//a set dans les listener

    public SkillMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();
        currentSelection = 1;         // selection 1 module par defaut
    }

    public void customizeMenuTable() {
        TextButton buyButton = new TextButton("10 A",AssetManager.INSTANCE.getModuleMenuBuyTxtBtnStyle());
        InputListener buyButtonListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                updateDetailLabel();
                return true;
            }
        };
        buyButton.addListener(buyButtonListener);
        TextButton equipButton = new TextButton("Equip",AssetManager.INSTANCE.getModuleMenuBuyTxtBtnStyle());
        InputListener equipButtonListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        };
        equipButton.addListener(equipButtonListener);
        levelLabel = new Label("Lv 5",AssetManager.INSTANCE.getSkin());
        titleLabel = new Label("Skill 1",AssetManager.INSTANCE.getSkin());
        descriptionLabel = new Label("+10% Attk",AssetManager.INSTANCE.getSkin());

        parentTable.add(new Label("UPGRADE", skin)).bottom().padTop(20);
        parentTable.row();
        ScrollPane scrollPane = initScrollingModuleSelection();
        InputListener paneListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                buyTable.setVisible(true);
                detailTable.setVisible(false);
                Gdx.app.log("scroll","scroll");
                return true;
            }
        };
        scrollPane.addListener(paneListener);
        parentTable.add(scrollPane);
        parentTable.row();

        tableStack = new Stack();
        detailTable = new Table();
        detailTable.add(titleLabel);
        detailTable.add(descriptionLabel);
        detailTable.setVisible(false);

        buyTable = new Table();
        buyTable.add(levelLabel).height(50).width(50).expandX();
        buyTable.add(buyButton).height(50).width(100).padTop(10).padBottom(10);

        tableStack.add(detailTable);
        tableStack.add(buyTable);
        parentTable.add(tableStack).fill();
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
//        for (int i = 0; i < AssetManager.INSTANCE.getModuleElementList().size(); i++) {
        for (int i = 1; i < 6; i++) {
            SkillMenuElement moduleMenuElement = new SkillMenuElement(gameManager, this);
            moduleMenuElement.initModuleMenuElement(i);
            scrollContainerVG.addActor(moduleMenuElement);
        }
        Gdx.app.log("ModuleMenu", "Generation des boutons de Module terminee");

        return pane;
    }

    public void showDetailTable(SkillMenuElement skillMenuElement){
        titleLabel.setText("title from element");
        buyTable.setVisible(false);
        detailTable.setVisible(true);
        Gdx.app.log("button","button");
    }
    /**
     * Update all module buybutton to check if player can click them
     */
    public void updateBuyButton () {
        for (int i=0;i<AssetManager.INSTANCE.getModuleElementList().size();i++) {
        }
    }

    public void updateDetailLabel(){
    }

    @Override
    public void update() {
        updateBuyButton();
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************
}
