package com.guco.tap.menu.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.dto.Area;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 * // TODO: super menu desactivant l'input listener, gerer un state?
 */
public class AreaMenu extends AbstractMenu {

    private Table equipTable;
    private Image background, area1, area2, area3, area4;
    Image[] backgroundImages;
    private Area currentArea;
    private Label areaNameLabel;
    private ScrollPane levelPane;

    public AreaMenu(GameManager gameManager) {
        super(gameManager);

        ArrayList<Area> areaList = gameManager.assetsManager.areaList;
        currentArea = areaList.get(gameManager.gameInformation.areaId);

        backgroundImages = new Image[areaList.size()];
        for (int i=0;i<gameManager.assetsManager.areaList.size();i++) {
            backgroundImages[i] = new Image(gameManager.assetsManager.menuBackgroundTextureList.get(i));
        }
        customizeMenuTable();
    }

    public void customizeMenuTable() {
        addMenuHeader("MENU",1);
        background = new Image();
        parentTable.row();
        parentTable.add(background).fill();
        parentTable.row();
        areaNameLabel = new Label("", skin);
        parentTable.addActor(areaNameLabel);
        ScrollPane areaPane = createAreaPane();
        parentTable.add(areaPane).expandY().bottom();
        levelPane = createLevelPane();
        levelPane.setPosition(0,100);
        parentTable.addActor(levelPane);

    }

    @Override
    public void show(){
        super.show();
        selectArea(gameManager.gameInformation.areaId);
    }

    private ScrollPane createAreaPane(){
        Table container = new Table();
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.getScrollTexture(), 10, 50));
        ScrollPane pane = new ScrollPane(container, paneStyle);
        pane.setScrollingDisabled(false, true);
        for (int i = 0; i<backgroundImages.length; i++) {
            final int areaId=i;
            ImageButton.ImageButtonStyle styleHead = new ImageButton.ImageButtonStyle();
            styleHead.up = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sprites/background/area"+areaId+"_icon.png"))));
            styleHead.down = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("sprites/background/area"+areaId+"_icon_r.png"))));
            ImageButton areaSelectButton = new ImageButton(styleHead);

            areaSelectButton.addListener(new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    selectArea(areaId);
                    return true;
                }
            });
            container.add(areaSelectButton).size(60,120);
        }
        return pane;
    }

    private ScrollPane createLevelPane(){
        Table container = new Table();
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.getScrollTexture(), 10, 50));
        ScrollPane pane = new ScrollPane(container, paneStyle);
        pane.setScrollingDisabled(true, false);

        for (int i = 0; i<currentArea.floors; i++) {
            TextButton textButton = new TextButton(i+"1-"+(i+1)+"0",gameManager.assetsManager.getSkin());
            textButton.addListener(new InputListener(){
                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    return true;
                }
            });
            container.add(textButton).size(100,40).padTop(5);
            container.row();
        }

        pane.setHeight(300);
        return pane;
    }

    private void selectArea(int areaId) {
        currentArea = gameManager.assetsManager.areaList.get(areaId);
        background.setDrawable(backgroundImages[areaId].getDrawable());
        initialiseLevelPane();
        areaNameLabel.setText(currentArea.name);
        areaNameLabel.setPosition(Constants.V_WIDTH/2-areaNameLabel.getPrefWidth()/2,Constants.V_HEIGHT-170);
    }

    private void initialiseLevelPane() {
        parentTable.removeActor(levelPane);
        levelPane = createLevelPane();
        levelPane.setPosition(-100,100);
        parentTable.addActor(levelPane);
        levelPane.addAction(Actions.sequence(Actions.alpha(0.5f), Actions.parallel(Actions.fadeIn(1f),Actions.moveTo(0,100,0.5f))));
    }

    @Override
    public void update() {
    }
}
