package com.guco.tap.menu.forge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.utils.Constants;

/**
 * Menu to change game settings
 */
public class ForgeMenu extends AbstractMenu {

    private TextButton rerollButton;
    private TextButton rollButton;
    private TextButton validateButton;
    private Label resultAtkLabel;
    private Label resultPerk1Label;
    private Label resultPerk2Label;
    private Label resultPerk3Label;

    private Drawer drawer;
    private SpriteBatch batch;
    public SpriterPlayer itemSpriterPlayer;

    public ForgeMenu(GameManager gameManager) {
        super(gameManager);
        addMenuHeader("FORGE", 1);

        createButton();
        createDrawer();

        createResultTable();
        createCommandTable();

        parentTable.debug();
    }

    private void createButton(){
        rerollButton = new TextButton("REROLL",gameManager.ressourceManager.getSkin());
        rerollButton.setDisabled(true);
        rerollButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                rollItem();
                return true;
            }
        });

        rollButton = new TextButton("ROLL",gameManager.ressourceManager.getSkin());
        rollButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        validateButton = new TextButton("VALIDATE", gameManager.ressourceManager.getSkin());
        validateButton.setDisabled(true);
        validateButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
    }

    private void createDrawer(){
        batch = new SpriteBatch();//a recuperer
        drawer = gameManager.loadForgeDrawer(batch);
        float height = 20;
        float ppu = Gdx.graphics.getHeight() / height;
        float width = Gdx.graphics.getWidth() / ppu;
        itemSpriterPlayer = gameManager.loadForgePlayer();
        itemSpriterPlayer.setScale(0.4f);
        itemSpriterPlayer.setPosition(Constants.V_WIDTH/2, 300);
    }

    private void createResultTable(){
        resultAtkLabel =new Label("Atk: 512A",skin);
        resultPerk1Label=new Label("Perks: 1 - attk + 1%",skin);
        resultPerk2Label =new Label("      2 - gold +1%",skin);
        resultPerk3Label =new Label("      3 - ",skin);

        Table resultTable = new Table();
        resultTable.add(resultAtkLabel).left().padTop(200).expandX();
        resultTable.row();
        resultTable.add(resultPerk1Label).left();
        resultTable.row();
        resultTable.add(resultPerk2Label).left();
        resultTable.row();
        resultTable.add(resultPerk3Label).left();

        parentTable.add(resultTable).expandX();
    }

    private void createCommandTable() {
        Table commandTable = new Table();
        commandTable.add(rerollButton);
        commandTable.add(validateButton);

        parentTable.row();
        parentTable.add(commandTable).padTop(50);
    }

    @Override
    public void show(){
        super.show();
    }

    private void rollItem() {
        itemSpriterPlayer.characterMaps[0]= itemSpriterPlayer.getEntity().getCharacterMap("sword3");
        itemSpriterPlayer.characterMaps[1]= itemSpriterPlayer.getEntity().getCharacterMap("gard3");
        itemSpriterPlayer.characterMaps[2]= itemSpriterPlayer.getEntity().getCharacterMap("pom3");
    }

    @Override
    public void draw(){
        batch.setProjectionMatrix(gameManager.playScreen.camera.combined);
        batch.begin();
        itemSpriterPlayer.update();
        drawer.draw(itemSpriterPlayer);
        batch.end();
    }

}
