package com.guco.tap.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 */
public class ScaleLabelAction extends TemporalAction {

    public static ScaleLabelAction action (Label label, float scale, float duration, Interpolation interpolation){
        ScaleLabelAction action = Actions.action(ScaleLabelAction.class);
        action.startScale = label.getFontScaleX();
        action.endScale=scale;
        action.setDuration(duration);
        action.setInterpolation(interpolation);
        action.refLabel=label;
        return action;
    }

    private float startScale, endScale;
    private Label refLabel;

    protected void begin () {
    }

    protected void update (float percent) {
        refLabel.setFontScale(startScale+(endScale-startScale)*percent);
    }

    public void reset () {
        super.reset();
    }

}
