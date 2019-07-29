package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.action.ScaleLabelAction;
import com.guco.tap.actor.AnimatedActor;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.actor.TapActor;
import com.guco.tap.actor.TorchActor;
import com.guco.tap.effect.TorchParticleSEffect;
import com.guco.tap.input.CustomInputProcessor;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.badlogic.gdx.Gdx.files;

/**
 * Created by Skronak on 29/01/2017.
 */
public class LevelSelectionScreen implements Screen {
    public LevelSelectionScreen(GameManager gameManager) {
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
