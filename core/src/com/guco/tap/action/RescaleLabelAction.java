package com.guco.tap.action;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Created by Skronak on 01/02/2017.
 */
public class RescaleLabelAction extends Action {
    private Label label;
    private float newSize;

    public RescaleLabelAction(Label label, float newSize){
        this.label=label;
        this.newSize=newSize;
    }

    @Override
    public boolean act(float delta) {
        label.setFontScale(newSize);
        return false;
    }
}
