package com.guco.tap.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Player;
import com.guco.tap.actor.CharacterAnimatedActor;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 * // TODO: super menu desactivant l'input listener, gerer un state?
 */
public class EquipMenu extends AbstractMenu {

    private CharacterAnimatedActor characterActor;
    private Table equipTable;
    private Player player;
    private Drawer drawer;
    private SpriteBatch spriteBatch;

    public EquipMenu(GameManager gameManager) {
        super(gameManager);

        customizeMenuTable();
    }

    public void postInit(){
        player = gameManager.player;
        characterActor = new CharacterAnimatedActor(200,160);
        spriteBatch = new SpriteBatch();
        drawer = gameManager.loadDrawer(spriteBatch);
    }

    public void customizeMenuTable() {
        equipTable = new Table();
        parentTable.add(new Label("Equip gear", skin)).pad(60);
        parentTable.row();

        Table buttonTable = new Table();
        TextButton weap1Button = new TextButton("weapon1", AssetManager.INSTANCE.getSkin());
        InputListener buttonListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                characterActor.switchWeapon(1);
                gameManager.playScreen.characterActor.switchWeapon(1);
                return false;
            }
        };
        weap1Button.addListener(buttonListener);

        TextButton weap2Button = new TextButton("weapon2", AssetManager.INSTANCE.getSkin());
        InputListener buttonListener2 = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                characterActor.switchWeapon(2);
                gameManager.playScreen.characterActor.switchWeapon(2);
                return false;
            }
        };
        weap2Button.addListener(buttonListener2);

        TextButton weap3Button = new TextButton("weapon3", AssetManager.INSTANCE.getSkin());
        InputListener buttonListener3 = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                characterActor.switchWeapon(3);
                gameManager.playScreen.characterActor.switchWeapon(3);
                return false;
            }
        };
        weap3Button.addListener(buttonListener3);

        TextButton head1Button = new TextButton("head1", AssetManager.INSTANCE.getSkin());
        InputListener buttonListener4 = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                characterActor.switchHead(1);
                gameManager.playScreen.characterActor.switchHead(1);

                return false;
            }
        };
        head1Button.addListener(buttonListener4);

        TextButton head2Button = new TextButton("head2", AssetManager.INSTANCE.getSkin());
        InputListener buttonListener5 = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                characterActor.switchHead(2);
                gameManager.playScreen.characterActor.switchHead(2);
                return false;
            }
        };
        head2Button.addListener(buttonListener5);

        TextButton body1Button = new TextButton("body1", AssetManager.INSTANCE.getSkin());
        InputListener buttonListener6 = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                characterActor.switchBody(1);
                gameManager.playScreen.characterActor.switchBody(1);
                return false;
            }
        };
        body1Button.addListener(buttonListener6);

        TextButton body2Button = new TextButton("body2", AssetManager.INSTANCE.getSkin());
        InputListener buttonListener7 = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                characterActor.switchBody(2);
                gameManager.playScreen.characterActor.switchBody(2);
                return false;
            }
        };
        body2Button.addListener(buttonListener7);

        buttonTable.add(new Label("Head", AssetManager.INSTANCE.getSkin()));
        buttonTable.row();
        buttonTable.add(head1Button).width(80).pad(1);
        buttonTable.row();
        buttonTable.add(head2Button).width(80).pad(1);
        buttonTable.row();
        buttonTable.add(new Label("Weapon", AssetManager.INSTANCE.getSkin())).padTop(10).padLeft(1).padRight(1).padBottom(1);
        buttonTable.row();
        buttonTable.add(weap1Button).width(80).pad(1);
        buttonTable.row();
        buttonTable.add(weap2Button).width(80).pad(1);
        buttonTable.row();
        buttonTable.add(weap3Button).width(80).pad(1);
        buttonTable.row();
        buttonTable.add(new Label("Body", AssetManager.INSTANCE.getSkin())).padTop(10).padLeft(1).padRight(1).padBottom(1);
        buttonTable.row();
        buttonTable.add(body1Button).width(80).pad(1);
        buttonTable.row();
        buttonTable.add(body2Button).width(80).pad(1);

        equipTable.top();
        //player.setPosition(-10,100);
        equipTable.add().width(200);
        equipTable.add(new Container<CharacterAnimatedActor>());
        equipTable.add(buttonTable);

        parentTable.add(equipTable);
    }

    @Override
    public void update() {
        if (null == player){
            postInit();
        } else {
            player.update();
        }
        spriteBatch.begin();
        drawer.draw(player);
        spriteBatch.end();

    }
}
