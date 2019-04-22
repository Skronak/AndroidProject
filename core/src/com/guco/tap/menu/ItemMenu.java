package com.guco.tap.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Player;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.ItemMenuElement;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 */
public class ItemMenu extends AbstractMenu {
    private Label detailGold;
    private Label detailDescription;
    private Label detailLevel;
    private Label detailTitre;
    private Stack stack;
    // indique le skill actuellement selectionne
    private int currentSelection;
    private List<ImageButton> moduleButtonList;
    private VerticalGroup scrollContainerVG;
    private TextButton headButton,bodyButton,weapButton;

    public ItemMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();
        currentSelection = 1;         // selection 1 module par defaut
    }

    public void customizeMenuTable() {
        parentTable.add(new Label("INVENTORY", skin)).bottom().padTop(20).colspan(2);
        parentTable.row();
        parentTable.add(new Label("canvas         ", AssetManager.INSTANCE.getSkin())).padTop(10);
        parentTable.add(initHeaderButton());
    }

    public Table initHeaderButton(){
        headButton = new TextButton("head", AssetManager.INSTANCE.getSkin());
        bodyButton = new TextButton("body",AssetManager.INSTANCE.getSkin());
        weapButton = new TextButton("weap",AssetManager.INSTANCE.getSkin());
        Table table = new Table();
        table.add(headButton).right().expandX();
        table.add(bodyButton).right();
        table.add(weapButton).right().padRight(5);
        table.row();
        table.add(initScrollingModuleSelection()).colspan(3).padRight(5);
        return table;
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

        for (int i = 0; i < AssetManager.INSTANCE.getItemList().size(); i++) {
            final int val = i;
            ItemMenuElement itemMenuElement = new ItemMenuElement(gameManager);
            itemMenuElement.initModuleMenuElement(i);
            itemMenuElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    gameManager.playScreen.player.characterMaps[0] = gameManager.playScreen.player.getEntity().getCharacterMap("sword"+val);
                    Gdx.app.debug("rr","rrr");
                    return true;
                }
            });
            scrollContainerVG.addActor(itemMenuElement);
        }
        return pane;
    }

    /**
     * Update all module buybutton to check if player can click them
     */
    public void updateBuyButton () {
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
