package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;


public class Scaler {
    private static Rectangle _tmpRec0 = new Rectangle();
    private static Vector2 _tmpVec0 = new Vector2();

    public static void fitActorToActor(Actor actor, Actor toActor) {
        actor.setX(0);
        actor.setY(0);
        actor.setScale(1);

        Vector2 a1 = toActor.localToStageCoordinates(new Vector2(0, 0));
        Vector2 a2 = toActor.localToStageCoordinates(new Vector2(toActor.getWidth(), toActor.getHeight()));

        Vector2 start = actor.stageToLocalCoordinates(a1);
        Vector2 end = actor.stageToLocalCoordinates(a2);

        Vector2 diff = _tmpVec0.set(end.x - start.x, end.y - start.y);

        fitActorToRectangle(actor, _tmpRec0.set(start.x, start.y, diff.x, diff.y));
    }

    public static void fillActorToActor(Actor actor, Actor toActor) {
        actor.setX(0);
        actor.setY(0);
        actor.setScale(1);

        Vector2 a1 = toActor.localToStageCoordinates(new Vector2(0, 0));
        Vector2 a2 = toActor.localToStageCoordinates(new Vector2(toActor.getWidth(), toActor.getHeight()));

        Vector2 start = actor.stageToLocalCoordinates(a1);
        Vector2 end = actor.stageToLocalCoordinates(a2);

        Vector2 diff = _tmpVec0.set(end.x - start.x, end.y - start.y);

        fillActorToRectangle(actor, _tmpRec0.set(start.x, start.y, diff.x, diff.y));
    }

    public static void fitActorToRectangle(Actor actor, Rectangle rect) {
        fitActorToRectangle(actor, rect, false);
    }

    public static void fitActorToRectangle(Actor actor, Rectangle rect, boolean invertI) {
        fitActorToRectangle(actor, rect, 1.0f, invertI);
    }

    public static void fitActorToRectangle(Actor actor, Rectangle rect, float scaleAdd) {
        fitActorToRectangle(actor, rect, scaleAdd, false);
    }

    public static void fitActorToScreen(Actor actor, boolean invertI) {
        fitActorToScreen(actor, 1.0f, invertI);
    }

    public static void fitActorToScreen(Actor actor, float scaleAdd, boolean invertI) {
        Rectangle rect = new Rectangle(0, 0, Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
        fitActorToRectangle(actor, rect, scaleAdd, invertI);
    }

    public static void fitActorToScreen(Actor actor) {
        fitActorToScreen(actor, false);
    }

    public static void fitActorToScreen(Actor actor, float scaleAdd) {
        fitActorToScreen(actor, scaleAdd, false);
    }

    public static void fillActorToRectangle(Actor actor, Rectangle rect, float nativeWidth, float nativeHeight) {
        fillActorToRectangle(actor, rect, nativeWidth, nativeHeight, 1.0f);
    }

    public static void fillActorToRectangle(Actor actor, Rectangle rect) {
        fillActorToRectangle(actor, rect, 1.0f);
    }

    public static void fillActorToRectangle(Actor actor, Rectangle rect, float nativeWidth, float nativeHeight, float scaleAdd) {
        fillActorToRectangle(actor, _tmpRec0.set(rect).setWidth(rect.getWidth()/nativeWidth).setHeight(rect.getHeight()/nativeHeight));
    }

    public static void centerActorToGroup(Actor actor, Group groupTo) {
        centerActorToRectangle(actor, _tmpRec0.set(0, 0, groupTo.getWidth(), groupTo.getHeight()));
    }

    public static void centerActorToActor(Actor actor, Actor actorTo) {
        centerActorToRectangle(actor, _tmpRec0.set(actorTo.getX(), actorTo.getY(), actorTo.getWidth()*actorTo.getScaleX(), actorTo.getHeight()*actorTo.getScaleY()));
    }

    public static void centerActorToRectangle(Actor actor, Rectangle rect) {
        actor.setX(rect.x + rect.width/2 - actor.getWidth()*actor.getScaleX()/2);
        actor.setY(rect.y + rect.height/2 - actor.getHeight()*actor.getScaleY()/2);
    }

    public static void fillActorToRectangle(Actor actor, Rectangle rect, float scaleAdd) {
        float screenWidth = rect.width;
        float screenHeight = rect.height;

        float scaleX = screenWidth/actor.getWidth();
        float scaleY = screenHeight/actor.getHeight();

        float scale = Math.max(scaleX, scaleY)*scaleAdd;
        actor.setScale(scale);

        //offset
        float offsetX = rect.width/2 - actor.getWidth()*actor.getScaleX()/2;
        float offsetY = rect.height/2 - actor.getHeight()*actor.getScaleY()/2;

        actor.setX(rect.x + offsetX);
        actor.setY(rect.y + offsetY);
    }

    public static void fitActorToRectangle(Actor actor, Rectangle rect, float scaleAdd, boolean invertI) {
        float screenWidth = rect.width;
        float screenHeight = rect.height;

        //scale
        float scaleX = screenWidth/actor.getWidth();
        float scaleY = screenHeight/actor.getHeight();

        int invert = 1;
        if(invertI) {
            invert = -1;
        }

        float scale = Math.min(scaleX, scaleY*invert)*scaleAdd;


        //offset
        float offsetX = Math.abs(screenWidth - (actor.getWidth()*actor.getScaleX())*scale)/2.0f;
        float offsetY = Math.abs(screenHeight - (actor.getHeight()*actor.getScaleY())*scale)/2.0f;

        //group
        actor.setScale(scale);
        actor.setPosition(rect.x + offsetX, rect.y + offsetY);
    }
}
