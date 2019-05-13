package com.guco.tap.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.guco.tap.entity.ItemMenuElement;
import com.guco.tap.manager.GameManager;

import java.util.List;

import javafx.scene.control.Tab;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 */
public class ItemMenu extends AbstractMenu {
    private Label detailGold;
    private Label detailDescription;
    private Label detailLevel;
    private Label detailTitre;
    // indique le skill actuellement selectionne
    private VerticalGroup weaponVG, bodyVG, headVG, currentVG;
    private ImageButton headButton,bodyButton,weapButton;
    private ScrollPane menuPane;
    private TextureRegionDrawable selectedRegionDrawable, backgroundRegionDrawable;

    public ItemMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();
    }

    public void customizeMenuTable() {
        parentTable.add(new Label("INVENTORY", skin)).bottom().padTop(20).colspan(2);
        parentTable.row();
        Image image = new Image(new Texture(Gdx.files.internal("sprites/test/body1_idle_1.png")));
        parentTable.add(image).top().width(100).fill().padTop(5).height(200);
        parentTable.add(initMenuContent()).expandX().top().padTop(5);
    }

    public Table initMenuContent() {
        final Table table = new Table();
        selectedRegionDrawable = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.greyTexture));
        selectedRegionDrawable.setMinHeight(1);
        selectedRegionDrawable.setMinWidth(1);

        backgroundRegionDrawable= new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.lightGreyTexture));
        backgroundRegionDrawable.setMinHeight(1);
        backgroundRegionDrawable.setMinWidth(1);

        ImageButton.ImageButtonStyle styleHead = new ImageButton.ImageButtonStyle();
        styleHead.up = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.headHTexture));
        styleHead.down = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.headHTextureR));
        headButton = new ImageButton(styleHead);
        headButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                currentVG = headVG;
                menuPane.setActor(currentVG);
                return true;
            }
        });

        ImageButton.ImageButtonStyle styleBody = new ImageButton.ImageButtonStyle();
        styleBody.up = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.bodyHTexture));
        styleBody.down = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.bodyHTextureR));
        bodyButton = new ImageButton(styleBody);
        bodyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                currentVG = bodyVG;
                menuPane.setActor(currentVG);
                return true;
            }
        });

        ImageButton.ImageButtonStyle weapBody = new ImageButton.ImageButtonStyle();
        weapBody.up = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.weapHTexture));
        weapBody.down = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.weapHTextureR));
        weapButton = new ImageButton(weapBody);
        weapButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                currentVG = weaponVG;
                menuPane.setActor(currentVG);
                return true;
            }
        });

        initVG();
        menuPane = initPane(weaponVG);

        table.add(headButton).right().expandX().size(35,30);
        table.add(bodyButton).right().size(35,30);
        table.add(weapButton).right().size(35,30);
        table.row();
        table.add(menuPane).colspan(3);

        return table;
    }

    /**
     * Methode d'initialisation des modules disponibles a
     * l'upgrade
     *
     * @return
     */
    public ScrollPane initPane(VerticalGroup defaultVG) {
        currentVG = defaultVG;
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.getScrollTexture(), 10, 50));

        ScrollPane pane = new ScrollPane(currentVG, paneStyle);
        pane.setScrollingDisabled(true, false);

        return pane;
    }

    public void initVG() {
        bodyVG = new VerticalGroup();
        //bodyVG.space(5f);
        for (int i = 0; i < gameManager.assetManager.bodyList.size(); i++) {
            ItemMenuElement itemMenuElement = new ItemMenuElement(gameManager, this);
            itemMenuElement.initModuleMenuElement(gameManager.assetManager.bodyList.get(i));
            itemMenuElement.setBackground(backgroundRegionDrawable);
            bodyVG.addActor(itemMenuElement);
        }

        weaponVG = new VerticalGroup();
        //weaponVG.space(10f);
        for (int i = 0; i < gameManager.assetManager.weaponList.size(); i++) {
            ItemMenuElement itemMenuElement = new ItemMenuElement(gameManager, this);
            itemMenuElement.initModuleMenuElement(gameManager.assetManager.weaponList.get(i));
            itemMenuElement.setBackground(backgroundRegionDrawable);
            weaponVG.addActor(itemMenuElement);
        }

        headVG = new VerticalGroup();
        //headVG.space(10f);
        for (int i = 0; i < gameManager.assetManager.helmList.size(); i++) {
            ItemMenuElement itemMenuElement = new ItemMenuElement(gameManager, this);
            itemMenuElement.initModuleMenuElement(gameManager.assetManager.helmList.get(i));
            itemMenuElement.setBackground(backgroundRegionDrawable);
            headVG.addActor(itemMenuElement);
        }
    }

    public void setSelectedItem(ItemMenuElement itemMenuElement){
        for( int i=0; i<currentVG.getChildren().size;i++) {
            ((ItemMenuElement) currentVG.getChildren().get(i)).setBackground(backgroundRegionDrawable);
        }
        itemMenuElement.setBackground(selectedRegionDrawable);
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
}
