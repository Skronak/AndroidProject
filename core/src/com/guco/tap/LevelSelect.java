package com.guco.tap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.Constants;

public class LevelSelect {

    private GameManager gameManager;
    private TextureRegionDrawable selectedRegionDrawable, backgroundRegionDrawable;
    public ScrollPane pane;

    public LevelSelect(GameManager gameManager) {
        VerticalGroup levelList = new VerticalGroup();
        levelList.space(5f);
        Image image = new Image(new Texture("sprites/badlogic1.jpg"));
        levelList.addActor(image);
        image = new Image(new Texture("sprites/badlogic1.jpg"));
        levelList.addActor(image);
        image = new Image(new Texture("sprites/badlogic1.jpg"));
        levelList.addActor(image);
        image = new Image(new Texture("sprites/badlogic1.jpg"));
        levelList.addActor(image);
        image = new Image(new Texture("sprites/badlogic1.jpg"));
        levelList.addActor(image);

        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.getScrollTexture(), 10, 50));

        pane = new ScrollPane(levelList);
        pane.setScrollingDisabled(true, false);
        pane.setSize(40,170);
        pane.layout();
        pane.setScrollPercentY(.6f);
        pane.updateVisualScroll();
        pane.setPosition(Constants.V_WIDTH-pane.getWidth()-10,Constants.V_HEIGHT/2-pane.getHeight()/2);
    }
}
