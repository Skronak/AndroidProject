package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.LibGdxDrawer;
import com.brashmonkey.spriter.LibGdxLoader;
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.utils.Constants;

import static com.badlogic.gdx.Gdx.files;

/**
 * Created by Skronak on 29/01/2017.
 *Classe de test de Spriter
 **/
public class TestScreen implements Screen, InputProcessor {

    private SpriteBatch spriteBatch;
    public Stage stage;
    public OrthographicCamera camera;
    private Viewport viewport;
    private com.guco.tap.utils.BitmapFontGenerator generator;
    private Image backgroundImage;
    private Image doorImage;
    private Group layer0GraphicObject = new Group(); // Background
    public Group layer1GraphicObject = new Group(); // Objects
    private Group layer2GraphicObject = new Group(); // Foreground
    Texture backgroundTexture;

    Drawer<Sprite> drawer;
    LibGdxLoader loader;
    SpriterPlayer spriterPlayer;
    ShapeRenderer renderer;

    int currentSwitch=0;
    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.V_WIDTH, Constants.V_HEIGHT);
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);

        stage = new Stage(viewport, spriteBatch);

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        backgroundTexture = new Texture(files.internal("sprites/badlogic.jpg"));
        backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backgroundImage = new Image(backgroundTexture);
        backgroundImage.setSize(410,Constants.V_HEIGHT+15);
        backgroundImage.setPosition(-90,-20);

        Texture doorTexture= new Texture(files.internal("sprites/background/dg_door.png"));
        doorImage = new Image(doorTexture);
        doorImage.setSize(backgroundImage.getWidth(),backgroundImage.getHeight());
        doorImage.setPosition(backgroundImage.getX(),backgroundImage.getY());

        // Gestion des calques
        stage.addActor(layer0GraphicObject);
        stage.addActor(layer1GraphicObject);
        stage.addActor(layer2GraphicObject);

        // Ajout des objets dans les calques
        layer0GraphicObject.addActor(backgroundImage);
        layer0GraphicObject.addActor(doorImage);
        layer2GraphicObject.addActor(doorImage);


        FileHandle handle = Gdx.files.internal("spriter/animation2.scml");
        Data data = new SCMLReader(handle.read()).getData();
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        loader = new LibGdxLoader(data);
        loader.load(handle.file());
        drawer = new LibGdxDrawer(loader, spriteBatch, renderer);
        spriterPlayer = new SpriterPlayer(data.getEntity(0));
        spriterPlayer.setPosition(100,200);
        spriterPlayer.setScale(0.5f);
        spriterPlayer.speed = 5;
        spriterPlayer.setAnimation("withtag");

        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.setProjectionMatrix(camera.combined);

        stage.act();
        stage.draw();

        // spriter
        spriterPlayer.update();

        spriteBatch.begin();
        drawer.draw(spriterPlayer);
        spriteBatch.end();
        renderer.begin();
        drawer.drawBones(spriterPlayer);
        renderer.end();

        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            spriterPlayer.characterMaps[1] = spriterPlayer.getEntity().getCharacterMap("head2");
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            Entity.CharacterMap map = spriterPlayer.getEntity().getCharacterMap("body2");
            spriterPlayer.characterMaps[2] = map;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            Entity.CharacterMap map = spriterPlayer.getEntity().getCharacterMap("arms3");
            spriterPlayer.setAnimation("atk");
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            spriterPlayer.characterMaps[0] = null;
        }
    }

    @Override
    public void resize(int width, int height) {
        Gdx.app.debug("PlayScreen", "Resize occured w"+width+" h"+height);
        viewport.update(width, height);

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
        Gdx.app.debug("PlayScreen","dispose");
        spriteBatch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        switch (currentSwitch){
            case 0:
                spriterPlayer.characterMaps[0] = spriterPlayer.getEntity().getCharacterMap("sword2");
                break;
            case 1:
                spriterPlayer.characterMaps[0] = spriterPlayer.getEntity().getCharacterMap("sword3");
                break;
            case 2:
                spriterPlayer.characterMaps[0] = spriterPlayer.getEntity().getCharacterMap("sword4");
                break;
            case 3:
                spriterPlayer.characterMaps[0] = null;
                break;
            default:
                spriterPlayer.characterMaps[0] = null;
                break;
        }

        if (currentSwitch<3){
            currentSwitch+=1;
        } else {
            currentSwitch=0;
        }


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
