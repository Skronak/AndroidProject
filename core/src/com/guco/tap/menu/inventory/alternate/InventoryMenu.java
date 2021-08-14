package com.guco.tap.menu.inventory.alternate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.LibGdxDrawer;
import com.brashmonkey.spriter.LibGdxLoader;
import com.brashmonkey.spriter.SCMLReader;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.entity.CalculatedStat;
import com.guco.tap.entity.Item;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.utils.MenuState;
import com.guco.tap.utils.ValueDTO;

import java.util.List;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 */
public class InventoryMenu extends AbstractMenu {
    private ImageButton headButton,bodyButton,weapButton;
    private InventoryPane inventoryPane;
    private Label damageLabel;
    private Label weaponDamageLabel;
    private Label weaponDamageNextLvlLabel;
    public MenuState menuState;
    public TextButton upgradeButton;
    private String TOTAL_DMG_LABEL="Total atk ";
    private String WEAPON_DMG_LABEL="Item atk ";
    private String WEAPON_DMG_NEXT_LVL_LABEL="Item atk ";
    private String WEAPON_RATE_LABEL="Atk rate ";
    private String NEXT_LEVEL = "Next Level";
    private String CURRENT_LEVEL="Current level";
    private Label nextDamageLabel;
    private Image upImage;
    private List<Item> newItems;

    SpriterPlayer spriterPlayer;
    Data playerData;


    // FOR TEST ONLY
    private Drawer drawer;
    private SpriteBatch batch;
    public SpriterPlayer itemSpriterPlayer;

    public InventoryMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();

        batch = new SpriteBatch();
        drawer = loadPlayerDrawer(batch);
        float height = 20;
        float ppu = Gdx.graphics.getHeight() / height;
        float width = Gdx.graphics.getWidth() / ppu;
        itemSpriterPlayer = loadPlayer();
        itemSpriterPlayer.setScale(0.5f);
        itemSpriterPlayer.setEntity(playerData.getEntity("inventoryMenu"));
//        itemSpriterPlayer.setPosition(70, 320);
        itemSpriterPlayer.setPosition(250, 320);
        itemSpriterPlayer.speed=1;

        menuState = MenuState.WEAPON;
        inventoryPane.switchTab();
    }

    public Drawer loadPlayerDrawer(SpriteBatch batch) {
        ShapeRenderer renderer = new ShapeRenderer();

        FileHandle handle = Gdx.files.internal("spriter/animation.scml");
        playerData = new SCMLReader(handle.read()).getData();

        LibGdxLoader loader = new LibGdxLoader(playerData);
        loader.load(handle.file());

        Drawer drawer = new LibGdxDrawer(loader, batch, renderer);
        return drawer;
    }

    public SpriterPlayer loadPlayer() {
        int weaponMap = 0;
        int headMap = 1;
        int bodyMap = 2;
        spriterPlayer = new SpriterPlayer(playerData.getEntity(0));
        spriterPlayer.setPosition(85, 220);
        spriterPlayer.setScale(0.37f);
        spriterPlayer.speed = 15;
        spriterPlayer.setAnimation("idle");
//        spriterPlayer.addListener(new PlayerListenerImpl(spriterPlayer, gameManager));
        spriterPlayer.characterMaps[weaponMap] = spriterPlayer.getEntity().getCharacterMap(gameManager.gameInformation.equipedWeapon.mapName);
        spriterPlayer.characterMaps[headMap] = spriterPlayer.getEntity().getCharacterMap(gameManager.assetsManager.helmList.get(gameManager.gameInformation.equipedHead).mapName);
        spriterPlayer.characterMaps[bodyMap] = spriterPlayer.getEntity().getCharacterMap(gameManager.assetsManager.bodyList.get(gameManager.gameInformation.equipedBody).mapName);

        return spriterPlayer;
    }
    public void customizeMenuTable() {
        damageLabel = new Label("",skin);
        damageLabel.setFontScale(0.7f);
        weaponDamageLabel = new Label("",skin);
        weaponDamageLabel.setFontScale(0.7f);
        weaponDamageNextLvlLabel = new Label("",skin);
        weaponDamageNextLvlLabel.setFontScale(0.7f);
        damageLabel.setFontScale(0.7f);
        nextDamageLabel=new Label("99 G",skin);
        nextDamageLabel.setFontScale(0.7f);
        upImage = new Image(gameManager.assetsManager.upTexture);

        upgradeButton = new TextButton("UPGRADE",skin);
        upgradeButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                increaseItemLevel();
                return true;
            }});

        addMenuHeader("INVENTORY2",2);
        parentTable.add(initScrollableItemComponent()).top();
        parentTable.add(initSelectedItemDetails());

    }

    private Table initSelectedItemDetails() {
        Table selectedItemDetails = new Table();
        selectedItemDetails.top().left();
        Image image = new Image();
        selectedItemDetails.add(image).height(250).pad(10).row();
        selectedItemDetails.add(new Label(CURRENT_LEVEL, skin)).left().row();
        selectedItemDetails.add(damageLabel).left().padLeft(10);
        selectedItemDetails.add(upImage).size(15,15).row();
        selectedItemDetails.add(weaponDamageLabel).left().padLeft(10).row();
        selectedItemDetails.add(new Label(NEXT_LEVEL, skin)).left().row();
        selectedItemDetails.add(weaponDamageNextLvlLabel).left().padLeft(10).row();
        selectedItemDetails.add(upgradeButton);
        return selectedItemDetails;
    }

    public Table initScrollableItemComponent() {
        final Table table = new Table();
        ImageButton.ImageButtonStyle styleHead = new ImageButton.ImageButtonStyle();
        styleHead.up = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.headHTexture));
        styleHead.down = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.headHTextureR));
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
        styleBody.up = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.bodyHTexture));
        styleBody.down = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.bodyHTextureR));
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
        weapBody.up = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.weapHTexture));
        weapBody.down = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.weapHTextureR));
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
        table.add(weapButton).right().size(45,40).row();
        table.add(inventoryPane).colspan(3);

        return table;
    }

    /**
     * FOR TEST ONLY
     */
    @Override
    public void draw() {
        batch.begin();
        itemSpriterPlayer.update();
        drawer.draw(itemSpriterPlayer);
        batch.end();
    }

    public void equipItem(InventoryElement inventoryElement) {
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
        gameManager.battleScreen.playerActor.spriterPlayer.characterMaps[itemSource.mapId] = gameManager.battleScreen.playerActor.spriterPlayer.getEntity().getCharacterMap(itemSource.mapName);
    }

    public void updateBuyButton () {
    }

    public void previewItemDetail(InventoryElement inventoryElement) {
        Item selectedItem = inventoryElement.itemSource;
        itemSpriterPlayer.characterMaps[0]= itemSpriterPlayer.getEntity().getCharacterMap(selectedItem.mapName); // charactermap 0 wrong
        updateItemInformation(selectedItem);
    }

    public void updateItemInformation(Item itemSource){
        Item selectedItem = itemSource;
        CalculatedStat calculatedStat = gameManager.itemManager.calculateItemStat(itemSource, itemSource.level+1);

        String damage = gameManager.largeMath.getDisplayValue(gameManager.gameInformation.tapDamageValue, gameManager.gameInformation.tapDamageCurrency);
        damageLabel.setText(TOTAL_DMG_LABEL + damage);
        String weapDamage = gameManager.largeMath.getDisplayValue(selectedItem.calculatedStat.damageValue, selectedItem.calculatedStat.damageCurrency);
        weaponDamageLabel.setText(WEAPON_DMG_LABEL + weapDamage);

        ValueDTO valueDTO = gameManager.largeMath.adjustCurrency(calculatedStat.damageValue, calculatedStat.damageCurrency);
        weapDamage = gameManager.largeMath.getDisplayValue(valueDTO);
        weaponDamageNextLvlLabel.setText(WEAPON_DMG_NEXT_LVL_LABEL + weapDamage);

        upgradeButton.setText("UPGRADE "+gameManager.largeMath.getDisplayValue(new ValueDTO(itemSource.calculatedStat.costValue,itemSource.calculatedStat.costCurrency)));

        if(selectedItem.calculatedStat.damageCurrency > gameManager.gameInformation.tapDamageCurrency){
            upImage.setVisible(true);
        } else if (gameManager.gameInformation.tapDamageCurrency == selectedItem.calculatedStat.damageCurrency
                && selectedItem.calculatedStat.damageCurrency > gameManager.gameInformation.tapDamageValue) {
            upImage.setVisible(true);
        } else {
            upImage.setVisible(false);
        }
    }

    public void increaseItemLevel() {
        gameManager.itemManager.increaseItemLevel(inventoryPane.selectedItemElement.itemSource);
        updateItemInformation(inventoryPane.selectedItemElement.itemSource);
        inventoryPane.selectedItemElement.update();
        if (inventoryPane.selectedItemElement.itemSource.equals(gameManager.gameInformation.equipedWeapon)){
            gameManager.dataManager.calculateTapDamage();
        }
    }

    public void show() {
        parentTable.setVisible(true);
        inventoryPane.setDefaultEquipedItem(); // fonctionne premiere fois mais plus ensuite
    }

    @Override
    public void update() {
        updateBuyButton();
    }

}
