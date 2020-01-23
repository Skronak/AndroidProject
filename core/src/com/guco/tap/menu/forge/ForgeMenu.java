package com.guco.tap.menu.forge;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.entity.Item;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.DualTextButton;
import com.guco.tap.utils.ValueDTO;

/**
 * Menu to change game settings
 */
public class ForgeMenu extends AbstractMenu {

    private TextButton rerollButton;
    private DualTextButton rollButton;
    private DualTextButton sellButton;
    private TextButton validateButton;
    private Label resultAtkLabel;
    private Label gradeLabel;
    private Label nameLabel;
    private ItemFactory itemFactory;
    private Drawer drawer;
    private SpriteBatch batch;
    public SpriterPlayer itemSpriterPlayer;
    private Table resultTable;
    private Table resultButtonTable;
    private Table rollButtonTable;
    private Item currentItem;
    private Label craftingLabel;
    private boolean rewardPending;

    public ForgeMenu(GameManager gameManager, SpriteBatch batch) {
        super(gameManager);
        this.batch = batch;

        addMenuHeader("FORGE", 1);
        itemFactory = new ItemFactory(gameManager);
        createButton();
        createDrawer();
        createLoadingLabel();
        createResultTable();
        createButtonTables();

        resetForge();
    }

    private void createButton() {
        rerollButton = new TextButton("REROLL",gameManager.assetsManager.getSkin());
        rerollButton.setDisabled(true);
        rerollButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                if (!rerollButton.isDisabled()) {
                    rollItem();
                }
                return true;
            }
        });

        rollButton = new DualTextButton("ROLL","50B",gameManager.assetsManager.getSkin());
        rollButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                rollItem();
                return true;
            }
        });

        validateButton = new TextButton("WATCH ADD TO REROLL", gameManager.assetsManager.getSkin());
        validateButton.setDisabled(true);
        validateButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                gameManager.gameInformation.weaponItemList.add(currentItem);
                rewardPending = true;
                gameManager.game.adController.showRewardedAd();
                return true;
            }
        });

        sellButton = new DualTextButton("SELL", "20 A", gameManager.assetsManager.getSkin());
        sellButton.setDisabled(true);
        sellButton.addListener(new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                resetForge();
                return true;
            }
        });
    }

    private void createDrawer() {
        drawer = gameManager.loadForgeDrawer(batch);
        float height = 20;
        float ppu = Gdx.graphics.getHeight() / height;
        float width = Gdx.graphics.getWidth() / ppu;
        itemSpriterPlayer = gameManager.loadForgePlayer();
        itemSpriterPlayer.setScale(0.4f);
        itemSpriterPlayer.setPosition(Constants.V_WIDTH/2, 280);
    }

    private void createLoadingLabel(){
        craftingLabel = new Label("CRAFTING...", skin);
        parentTable.addActor(craftingLabel);
        craftingLabel.setPosition(Constants.V_WIDTH/2-craftingLabel.getWidth()/2,350);
    }

    private void createResultTable(){
        resultAtkLabel =new Label("",skin);
        gradeLabel = new Label("",skin);
        nameLabel = new Label("", skin);

        resultTable = new Table();
        resultTable.add(nameLabel).center().expandX();
        resultTable.row();
        resultTable.add(gradeLabel).padTop(180);
        resultTable.row();
        resultTable.add(resultAtkLabel).center();
        resultTable.row();

        parentTable.add(resultTable).expandX().fill();
    }

    private void createButtonTables() {
        Stack stack = new Stack();

        resultButtonTable = new Table();
        resultButtonTable.add(rerollButton).size(80,50);
        resultButtonTable.add(sellButton).size(80,50).padLeft(10);
        resultButtonTable.add(validateButton).size(80,50).padLeft(10);
        stack.addActor(resultButtonTable);

        rollButtonTable = new Table();
        rollButtonTable.add(rollButton).size(80,50);
        stack.addActor(rollButtonTable);

        parentTable.row();
        parentTable.add(stack).padTop(50);
    }

    private void rollItem() {
        currentItem = itemFactory.rollItem();

        craftingLabel.setVisible(true);
        rollButtonTable.setVisible(false);
        resultButtonTable.setVisible(false);
        itemSpriterPlayer.setAnimation(itemSpriterPlayer.getEntity().getAnimation("load"));
        resultTable.setVisible(false);

        parentTable.addAction(Actions.sequence(Actions.delay(Constants.FORGE_DELAY),
           Actions.run(new Runnable() {
            @Override
            public void run() {
                showItemResult();
            }
        })));
    }

    private void resetForge() {
        craftingLabel.setVisible(false);
        rollButtonTable.setVisible(true);
        resultButtonTable.setVisible(false);
        resultTable.setVisible(false);
        itemSpriterPlayer.characterMaps[0]= itemSpriterPlayer.getEntity().getCharacterMap("default");
        itemSpriterPlayer.characterMaps[1]= itemSpriterPlayer.getEntity().getCharacterMap("default");
        itemSpriterPlayer.characterMaps[2]= itemSpriterPlayer.getEntity().getCharacterMap("default");
    }

    private void showItemResult() {
        gradeLabel.setColor(Color.WHITE);
        if (currentItem.grade==0){
            gradeLabel.setText("Grade - COMMON");
        } else if (currentItem.grade==1){
            gradeLabel.setText("Grade - UNCOMMON");
            gradeLabel.setColor(Color.BLUE);
        } else if (currentItem.grade==2){
            gradeLabel.setText("Grade - RARE");
            gradeLabel.setColor(Color.CYAN);
        } else if (currentItem.grade==3){
            gradeLabel.setText("Grade - EPIQ");
            gradeLabel.setColor(Color.GOLD);
        }
        craftingLabel.setVisible(false);
        resultTable.setVisible(true);
        nameLabel.setText(currentItem.name);
        ValueDTO valueDTO = gameManager.largeMath.adjustCurrency(currentItem.damageValue, currentItem.damageCurrency);
        resultAtkLabel.setText("DAMAGE "+gameManager.largeMath.getDisplayValue(valueDTO));
        itemSpriterPlayer.characterMaps[0]= itemSpriterPlayer.getEntity().getCharacterMap(currentItem.skin.bladeMap);
        itemSpriterPlayer.characterMaps[1]= itemSpriterPlayer.getEntity().getCharacterMap(currentItem.skin.guardMap);
        itemSpriterPlayer.characterMaps[2]= itemSpriterPlayer.getEntity().getCharacterMap(currentItem.skin.hiltMap);
        itemSpriterPlayer.setAnimation(itemSpriterPlayer.getEntity().getAnimation("idle"));

        resultButtonTable.setVisible(true);
    }

    @Override
    public void update() {
        super.update();
        if (rewardPending && gameManager.game.adController.isRewardEarned()) {
            rewardPending = false;
            gameManager.game.adController.claimReward();

            unlockRerollButton();
        }
    }

    public void unlockRerollButton(){
        rerollButton.setDisabled(false);
    }

    @Override
    public void draw(){
        batch.setProjectionMatrix(gameManager.playScreen.stage.getCamera().combined);
        batch.begin();
        itemSpriterPlayer.update();
        drawer.draw(itemSpriterPlayer);
        batch.end();
    }

}
