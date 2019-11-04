package com.guco.tap.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public class ArcToAction extends MoveToAction {
    private float angle;
    private final Vector2 vec1 = new Vector2(), vec2 = new Vector2(), vec3 = new Vector2();
    private float bx, by;

    static public ArcToAction action (float x, float y, float duration, Interpolation interpolation) {
        ArcToAction action = Actions.action(ArcToAction .class);
        action.setPosition(x, y);
        action.setDuration(duration);
        action.setInterpolation(interpolation);
        return action;
    }

    @Override
    protected void begin () {

        super.begin();
        float ax = target.getX(getAlignment()); // have to recalculate these because private in parent
        float ay = target.getY(getAlignment());
        vec1.set(getX(), getY()).sub(ax, ay);
        vec2.set(vec1).rotate90(0);
        vec1.scl(0.5f).add(ax, ay);
        vec2.add(vec1);
        vec1.set(bx, by).sub(vec2); // CB
        vec3.set(ax, ay).sub(vec2); // CA
        angle = vec1.angle(vec3);
    }

    protected void update (float percent) {
        if (percent >= 1){
            target.setPosition(getX(), getY(), getAlignment());
            return;
        }

        vec1.set(vec3).rotate(percent * angle);
        target.setPosition(vec1.x, vec1.y, getAlignment());
    }

}