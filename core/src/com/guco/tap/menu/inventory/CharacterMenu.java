package com.guco.tap.menu.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.actor.InventorySlotImage;
import com.guco.tap.dto.SpriterDto;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 */
public class CharacterMenu extends AbstractMenu {
    private Label characterNameLabel;
    private Label weaponDamageLabel;
    private Label criticalRateLabel;
    private InventorySlotImage weaponSlot, bodySlot, headSlot, legSlot, armSlot, mantleSlot, ringSlot1, ringSlot2; // A RENOMER SELON LE TYPE DE SLOT
    private Label hpLabel;
    private int SLOT_SIZE=40;
    private GameInformation gameInformation;
    private SpriterDto spriterDto;
    private TapDungeonGame game;
    public SpriterPlayer spriterPlayer;
    ScrollPane availableItemsPane;
    private List<InventorySlotImage> currentInventorySlotImages;
    private Label nameLabel;

    public CharacterMenu(final TapDungeonGame game, final CharacterInventoryMenu characterInventoryMenu) {
        super(game.gameManager);
        this.game = game;
        this.gameInformation = game.gameInformation;
        customizeMenuTable();

        spriterDto = gameManager.loadDrawer(game.sb, "/animation.scml");
        spriterPlayer = new SpriterPlayer(spriterDto.data.getEntity(0));
        spriterPlayer.setPosition(Constants.V_WIDTH/2, 220);
        spriterPlayer.setScale(0.5f);
        spriterPlayer.speed = 15;
        spriterPlayer.setAnimation("idle_sleeping");
//        spriterPlayer.addListener(new PlayerListenerImpl(spriterPlayer, playScreen));
        spriterPlayer.characterMaps[1] = spriterPlayer.getEntity().getCharacterMap("sleepingHead");
          //spriterPlayer.characterMaps[headMap] = spriterPlayer.getEntity().getCharacterMap(assetsManager.helmList.get(gameInformation.equipedHead).mapName);
//        spriterPlayer.characterMaps[bodyMap] = spriterPlayer.getEntity().getCharacterMap(assetsManager.bodyList.get(gameInformation.equipedBody).mapName);


        float padLeft = 15;

        String accountName = gameInformation.accountName + " Lv " + gameInformation.areaLevel;
        nameLabel = new Label(accountName, skin);
        nameLabel.setPosition(padLeft, 380);
        parentTable.addActor(nameLabel);

        float padRight = parentTable.getWidth() - SLOT_SIZE - 15;
        weaponSlot = addSlot(padLeft,300, gameInformation.equipedWeapon);
        bodySlot = addSlot(padLeft,240, null);
        headSlot = addSlot(padLeft,180,null);
        legSlot = addSlot(padRight,300,null);
        armSlot = addSlot(padRight,240,null);
        mantleSlot = addSlot(padRight,180, null);

        weaponSlot.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (availableItemsPane.isVisible()) {
                    availableItemsPane.setVisible(false);
                } else{
                    availableItemsPane.setVisible(true);
                }
                return true;
            }});
    }

    private InventorySlotImage addSlot(float posX, float posY, Item item) {
        Texture slotTexture;

        if (item != null) {
            slotTexture = new Texture(Gdx.files.internal("sprites/icon/item_frame_"+item.grade+".png"));
        } else {
            slotTexture = new Texture(Gdx.files.internal("sprites/icon/item_frame_0.png"));
        }
        InventorySlotImage inventorySlotImage = new InventorySlotImage(item, slotTexture, null);
        inventorySlotImage.setPosition(posX, posY);
        inventorySlotImage.setSize(SLOT_SIZE, SLOT_SIZE);

        initInventoryPanel();

        parentTable.addActor(inventorySlotImage);
        return inventorySlotImage;
    }

    private void initInventoryPanel() {
        final Table table = new Table();
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.getScrollTexture(), 10, 50));

        availableItemsPane = new ScrollPane(table, paneStyle);
        availableItemsPane.setScrollingDisabled(true, false);
        currentInventorySlotImages = new ArrayList<InventorySlotImage>();

        List<Item> availableWeapon = gameInformation.weaponItemList;
        for (int i = 0; i < 5; i++) {
            for (int y=0; y<5; y++) {
                Texture slotTexture = new Texture(Gdx.files.internal("sprites/icon/off_icon.png"));
                Texture selectTexture = new Texture(Gdx.files.internal("sprites/icon/iconSelected.png"));

                final Item item;
                final int itemIdx = (i * 5) + y;
                if (itemIdx < availableWeapon.size()) {
                    item = availableWeapon.get(itemIdx);
                } else {
                    item = null;
                }
                final InventorySlotImage inventorySlotImage = new InventorySlotImage(item, slotTexture, selectTexture);
                inventorySlotImage.setSize(SLOT_SIZE, SLOT_SIZE);
                if (item != null) {
                    inventorySlotImage.addListener(new ClickListener() {
                        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                            selectItem(itemIdx);
                            return true;
                        }
                    });
                }

                currentInventorySlotImages.add(inventorySlotImage);
                table.add(inventorySlotImage).size(50,50).pad(2);
            }
            table.row();
        }

       parentTable.addActor(availableItemsPane);
       availableItemsPane.setSize(parentTable.getWidth(),195);
       availableItemsPane.setPosition(0,Constants.MENU_PAD_EXTERNAL_HEIGHT + 100 - Constants.MENU_PAD_INTERNAL - availableItemsPane.getHeight());
       availableItemsPane.setVisible(false);
    }

    private void selectItem(int id) {
        for (int i = 0; i < currentInventorySlotImages.size(); i++) {
            currentInventorySlotImages.get(i).selectSlot(false);
        }
        currentInventorySlotImages.get(id).selectSlot(true);
    }

    private void customizeMenuTable() {
        characterNameLabel = new Label("",skin);
        characterNameLabel.setFontScale(0.7f);
        weaponDamageLabel = new Label("",skin);
        weaponDamageLabel.setFontScale(0.7f);
        criticalRateLabel = new Label("",skin);
        criticalRateLabel.setFontScale(0.7f);
        hpLabel = new Label("99 G",skin);
        hpLabel.setFontScale(0.7f);

        addMenuHeader("INVENTORY",2);
    }

    @Override
    public void show() {
        parentTable.setVisible(true);
    }

    @Override
    public void draw() {
        game.sb.begin();
        spriterPlayer.update();
        spriterDto.drawer.draw(spriterPlayer);
        game.sb.end();
    }
}
