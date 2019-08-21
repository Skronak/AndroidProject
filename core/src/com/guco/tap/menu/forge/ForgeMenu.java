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
import com.guco.tap.entity.Item;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.ValueDTO;

/**
 * Menu to change game settings
 */
public class ForgeMenu extends AbstractMenu {

    private TextButton rerollButton;
    private TextButton rollButton;
    private TextButton validateButton;
    private Label resultAtkLabel;
    private Label gradeLabel;
    private Label nameLabel;
    private ItemFactory itemFactory;
    private Drawer drawer;
    private SpriteBatch batch;
    public SpriterPlayer itemSpriterPlayer;

    public ForgeMenu(GameManager gameManager) {
        super(gameManager);
        addMenuHeader("FORGE", 1);
        itemFactory = new ItemFactory(gameManager);
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
                itemFactory.rollItem();
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
        resultAtkLabel =new Label("",skin);
        gradeLabel = new Label("",skin);
        nameLabel = new Label("", skin);

        Table resultTable = new Table();
        resultTable.add(nameLabel).left().padTop(200).expandX();
        resultTable.row();
        resultTable.add(gradeLabel);
        resultTable.row();
        resultTable.add(resultAtkLabel).left();
        resultTable.row();

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
        Item item = itemFactory.rollItem();
        showItem(item);
    }

    private void showItem(Item item){
        String grade = "C";
        if (item.grade==0){
            grade = "C";
        } else if (item.grade==1){
            grade = "B";
        } else if (item.grade==2){
            grade = "A";
        } else if (item.grade==3){
            grade = "SSS";
        }

        gradeLabel.setText("Quality "+grade);
        nameLabel.setText("Evil sword of Shadow");
        ValueDTO valueDTO = gameManager.largeMath.adjustCurrency(item.damageValue, item.damageCurrency);
        resultAtkLabel.setText("Atk "+gameManager.largeMath.getDisplayValue(valueDTO));
        itemSpriterPlayer.characterMaps[0]= itemSpriterPlayer.getEntity().getCharacterMap(item.skin.bladeMap);
        itemSpriterPlayer.characterMaps[1]= itemSpriterPlayer.getEntity().getCharacterMap(item.skin.guardMap);
        itemSpriterPlayer.characterMaps[2]= itemSpriterPlayer.getEntity().getCharacterMap(item.skin.hiltMap);
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
