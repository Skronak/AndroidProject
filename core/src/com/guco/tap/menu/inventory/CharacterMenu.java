package com.guco.tap.menu.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.actor.InventorySlotImage;
import com.guco.tap.dto.SpriterDto;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.utils.Constants;

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
        float padRight = parentTable.getWidth() - SLOT_SIZE - 15;
        weaponSlot = addSlot(padLeft,300, gameInformation.equipedWeapon);
        bodySlot = addSlot(padLeft,240, null);
        headSlot = addSlot(padLeft,180,null);
        legSlot = addSlot(padRight,300,null);
        armSlot = addSlot(padRight,240,null);
        mantleSlot = addSlot(padRight,180, null);

        weaponSlot.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                characterInventoryMenu.show();
                return true;
            }});
    }

    private InventorySlotImage addSlot(float posX, float posY, Item item) {
        Texture slotTexture;
        Texture itemTexture;

        if (item != null) {
            slotTexture = new Texture(Gdx.files.internal("sprites/icon/item_frame_"+item.grade+".png"));
            itemTexture = new Texture(Gdx.files.internal("sprites/icon/item/"+item.icon));
        } else {
            slotTexture = new Texture(Gdx.files.internal("sprites/icon/item_frame_0.png"));
            itemTexture = new Texture(Gdx.files.internal("sprites/icon/item/empty.png"));
        }
        InventorySlotImage inventorySlotImage = new InventorySlotImage(slotTexture, itemTexture, null);
        inventorySlotImage.setPosition(posX, posY);
        inventorySlotImage.setSize(SLOT_SIZE, SLOT_SIZE);

        parentTable.addActor(inventorySlotImage);

        return inventorySlotImage;
    }

    private void customizeMenuTable() {
        characterNameLabel = new Label("",skin);
        characterNameLabel.setFontScale(0.7f);
        weaponDamageLabel = new Label("",skin);
        weaponDamageLabel.setFontScale(0.7f);
        criticalRateLabel = new Label("",skin);
        criticalRateLabel.setFontScale(0.7f);
        hpLabel =new Label("99 G",skin);
        hpLabel.setFontScale(0.7f);

        addMenuHeader("INVENTORY",2);
    }

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
