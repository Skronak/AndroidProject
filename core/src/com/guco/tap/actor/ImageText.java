package com.guco.tap.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

public class ImageText extends Table {
    private Label label;

    public ImageText(Image image, String text, Skin skin) {
        this.label = new Label(text, skin);

        label.setAlignment(Align.center);
        image.setScaling(Scaling.fit);

        add(image);
        addActor(label);
        label.setPosition(image.getImageX(), image.getImageY());
        setSize(getPrefWidth(), getPrefHeight());
    }
}