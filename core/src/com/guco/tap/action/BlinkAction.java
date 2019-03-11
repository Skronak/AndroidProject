package com.guco.tap.action;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;

public class BlinkAction extends SequenceAction {

    public BlinkAction(float duration) {
        super(Actions.fadeOut(duration/2),Actions.fadeIn(duration));
    }

    /**
     *
     * @param duration
     * @param occurence
     */
    public BlinkAction(float duration, int occurence) {
        super(Actions.fadeOut(duration/2),Actions.fadeIn(duration));
        if (occurence>1) {
            for (int i = 1; i < occurence; i++) {
                this.getActions().add(Actions.fadeOut(duration / 2), Actions.fadeIn(duration));
            }
        }
    }
}
