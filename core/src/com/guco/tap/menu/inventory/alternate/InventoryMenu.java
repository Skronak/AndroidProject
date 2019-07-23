package com.guco.tap.menu.inventory.alternate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.entity.Item;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.utils.MenuState;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 */
public class InventoryMenu extends AbstractMenu {
    private ImageButton headButton,bodyButton,weapButton;
    private InventoryPane inventoryPane;
    private Label damageLabel;
    private Label weaponDamageLabel;
    public MenuState menuState;
    public TextButton upgradeButton;
    private String TOTAL_DMG_LABEL="Total atk ";
    private String WEAPON_DMG_LABEL="Item atk ";
    private String WEAPON_RATE_LABEL="Atk rate ";
    private String NEXT_LEVEL = "Next Level";
    private String CURRENT_LEVEL="Current level";
    private Label nextDamageLabel;
    private Image upImage;

    // FOR TEST ONLY
    private Drawer drawer;
    private SpriteBatch batch;
    public SpriterPlayer itemSpriterPlayer;

    public InventoryMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();

        batch = new SpriteBatch();
        drawer = gameManager.loadDrawer(batch);
        float height = 20;
        float ppu = Gdx.graphics.getHeight() / height;
        float width = Gdx.graphics.getWidth() / ppu;
        itemSpriterPlayer = gameManager.loadPlayer();
        itemSpriterPlayer.setScale(0.5f);
        itemSpriterPlayer.setEntity(gameManager.data.getEntity("inventoryMenu"));
        itemSpriterPlayer.setPosition(70, 320);
        itemSpriterPlayer.speed=5;

        menuState = MenuState.WEAPON;
    }

    public void customizeMenuTable() {
        damageLabel = new Label("",skin);
        damageLabel.setFontScale(0.7f);
        weaponDamageLabel = new Label("",skin);
        weaponDamageLabel.setFontScale(0.7f);
        damageLabel.setFontScale(0.7f);
        nextDamageLabel=new Label("99 G",skin);
        nextDamageLabel.setFontScale(0.7f);
        upImage = new Image(gameManager.ressourceManager.upTexture);

        upgradeButton = new TextButton("UPGRADE",skin);
        upgradeButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                increaseItemLevel();
                return true;
            }});

        parentTable.add(new Label("INVENTORY", skin)).bottom().padTop(10).padBottom(20).colspan(2);
        parentTable.row();
        Table leftTable = new Table();
        leftTable.top().left();
        Image image = new Image();
        leftTable.add(image).height(250);
        leftTable.row();
        leftTable.add(new Label(CURRENT_LEVEL, skin)).left();
        leftTable.row();
        leftTable.add(damageLabel).left().padLeft(10);
        leftTable.add(upImage).size(15,15);
        leftTable.row();
        leftTable.add(weaponDamageLabel).left().padLeft(10);
        leftTable.row();
        leftTable.add(new Label(NEXT_LEVEL, skin)).left();
        leftTable.row();
        leftTable.add(nextDamageLabel).left().padLeft(40);
        leftTable.row();
        leftTable.add(upgradeButton);

        parentTable.add(leftTable).top().width(100).expand().top().height(200).padTop(5);
        parentTable.add(initMenuContent()).expandX().top().padTop(5);
    }

    public Table initMenuContent() {
        final Table table = new Table();
        ImageButton.ImageButtonStyle styleHead = new ImageButton.ImageButtonStyle();
        styleHead.up = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.headHTexture));
        styleHead.down = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.headHTextureR));
        headButton = new ImageButton(styleHead);
        headButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                menuState=MenuState.HEAD;
                inventoryPane.switchTab();
                return true;
            }
        });

        ImageButton.ImageButtonStyle styleBody = new ImageButton.ImageButtonStyle();
        styleBody.up = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.bodyHTexture));
        styleBody.down = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.bodyHTextureR));
        bodyButton = new ImageButton(styleBody);
        bodyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                menuState=MenuState.BODY;
                inventoryPane.switchTab();
                return true;
            }
        });

        ImageButton.ImageButtonStyle weapBody = new ImageButton.ImageButtonStyle();
        weapBody.up = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.weapHTexture));
        weapBody.down = new TextureRegionDrawable(new TextureRegion(gameManager.ressourceManager.weapHTextureR));
        weapButton = new ImageButton(weapBody);
        weapButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                menuState=MenuState.WEAPON;
                inventoryPane.switchTab();
                return true;
            }
        });

        inventoryPane = new InventoryPane(gameManager, this);

        table.add(headButton).right().expandX().size(45,40);
        table.add(bodyButton).right().size(45,40);
        table.add(weapButton).right().size(45,40);
        table.row();
        table.add(inventoryPane).colspan(3);

        return table;
    }

    /**
     * FOR TEST ONLY
     */
    @Override
    public void draw(){
        batch.setProjectionMatrix(gameManager.playScreen.camera.combined);
        batch.begin();
        itemSpriterPlayer.update();
//        itemSpriterPlayer.
        drawer.draw(itemSpriterPlayer);
        batch.end();
    }

    public void equipItem(InventoryElement inventoryElement){
        switch( menuState){
            case WEAPON:
                gameManager.gameInformation.equipedWeapon= inventoryElement.itemSource;
                gameManager.dataManager.calculateTapDamage();
                break;
            case BODY:
                gameManager.gameInformation.equipedBody= inventoryElement.itemSource.id;
                break;
            case HEAD:
                gameManager.gameInformation.equipedHead= inventoryElement.itemSource.id;
                break;
        }

        gameManager.dataManager.calculateTapDamage();
        Item itemSource= inventoryPane.selectedItemElement.itemSource;
        gameManager.playScreen.spriterPlayer.characterMaps[itemSource.mapId] = gameManager.playScreen.spriterPlayer.getEntity().getCharacterMap(itemSource.mapName);
    }

    public void updateBuyButton () {
    }

    public void previewItemDetail(InventoryElement inventoryElement) {
        Item selectedItem = inventoryElement.itemSource;
        itemSpriterPlayer.characterMaps[0]= itemSpriterPlayer.getEntity().getCharacterMap(selectedItem.mapName); // charactermap 0 wrong
        updateItemInformation(inventoryElement);
    }

    public void updateItemInformation(InventoryElement inventoryElement){
        Item selectedItem = inventoryElement.itemSource;
        inventoryElement.update();
        String damage = gameManager.largeMath.getDisplayValue(gameManager.gameInformation.tapDamageValue, gameManager.gameInformation.tapDamageCurrency);
        damageLabel.setText(TOTAL_DMG_LABEL + damage);
        String weap_damage = gameManager.largeMath.getDisplayValue(selectedItem.calculatedStat.damageValue, selectedItem.calculatedStat.damageCurrency);
        weaponDamageLabel.setText(WEAPON_DMG_LABEL + weap_damage);
        if(selectedItem.calculatedStat.damageCurrency > gameManager.gameInformation.tapDamageCurrency){
            upImage.setVisible(true);
        } else if (gameManager.gameInformation.tapDamageCurrency == selectedItem.calculatedStat.damageCurrency
                && selectedItem.calculatedStat.damageCurrency > gameManager.gameInformation.tapDamageValue) {
            upImage.setVisible(true);
        } else {
            upImage.setVisible(false);
        }
    }

    public void increaseItemLevel(){
        gameManager.itemManager.increaseItemLevel(inventoryPane.selectedItemElement.itemSource);
        updateItemInformation(inventoryPane.selectedItemElement);
        if (inventoryPane.selectedItemElement.itemSource.equals(gameManager.gameInformation.equipedWeapon)){
            gameManager.dataManager.calculateTapDamage();
        }

    }

    public void show(){
        parentTable.setVisible(true);
    }

    @Override
    public void update() {
        updateBuyButton();
    }

}
