package com.guco.tap.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Player;
import com.guco.tap.entity.ItemEntity;
import com.guco.tap.entity.ItemMenuElement;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.MenuState;

/**
 * Created by Skronak on 01/02/2017.
 * Menu d'update
 */
public class ItemMenu extends AbstractMenu {
    private VerticalGroup weaponVG, bodyVG, headVG, currentVG;
    private ImageButton headButton,bodyButton,weapButton;
    private ScrollPane menuPane;
    private TextureRegionDrawable selectedRegionDrawable, backgroundRegionDrawable;
    private Label damageLabel;
    private Label weaponDamageLabel;
    private Label passiveLabel;
    private TextButton unlockButton;
    private MenuState menuState;
    private TextButton upgradeButton;
    // FOR TEST ONLY
    private Drawer drawer;
    private SpriteBatch batch;
    public Player itemPlayer;

    public ItemMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();

        batch = new SpriteBatch();
        drawer = gameManager.loadDrawer(batch);
        float height = 20;
        float ppu = Gdx.graphics.getHeight() / height;
        float width = Gdx.graphics.getWidth() / ppu;
        itemPlayer = gameManager.loadPlayer();
        itemPlayer.setScale(0.5f);
        itemPlayer.setEntity(gameManager.data.getEntity("itemMenu"));
        itemPlayer.setPosition(70, 350);
        itemPlayer.speed=5;

        menuState = MenuState.WEAPON; // default menu value
    }

    public void customizeMenuTable() {
        damageLabel = new Label("",skin);
        damageLabel.setFontScale(0.7f);
        weaponDamageLabel = new Label("",skin);
        weaponDamageLabel.setFontScale(0.7f);
        damageLabel.setFontScale(0.7f);
        passiveLabel = new Label("",skin);
        Image upImage = new Image(gameManager.assetManager.upTexture);

        upgradeButton = new TextButton("UPGRADE",skin);
        parentTable.add(new Label("INVENTORY", skin)).bottom().padTop(10).padBottom(20).colspan(2);
        parentTable.row();
        Table leftTable = new Table();
        leftTable.top().left();
//        Image image = new Image(new Texture(Gdx.files.internal("sprites/test/body1_idle_1.png")));
        Image image = new Image();
        leftTable.add(image).height(250);
        leftTable.row();
        leftTable.add(damageLabel).left();
        leftTable.add(upImage).size(20,20);
        leftTable.row();
        leftTable.add(weaponDamageLabel).left();
        leftTable.row();
        leftTable.add(upgradeButton);

        parentTable.add(leftTable).top().width(100).expand().top().height(200).padTop(5);
        parentTable.add(initMenuContent()).expandX().top().padTop(5);

        parentTable.setDebug(true);
    }

    public Table initMenuContent() {
        final Table table = new Table();
        selectedRegionDrawable = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.greyTexture));
        selectedRegionDrawable.setMinHeight(1);
        selectedRegionDrawable.setMinWidth(1);

        backgroundRegionDrawable= new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.lightGreyTexture));
        backgroundRegionDrawable.setMinHeight(1);
        backgroundRegionDrawable.setMinWidth(1);

        ImageButton.ImageButtonStyle styleHead = new ImageButton.ImageButtonStyle();
        styleHead.up = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.headHTexture));
        styleHead.down = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.headHTextureR));
        headButton = new ImageButton(styleHead);
        headButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                menuState=MenuState.HEAD;
                switchVG();
                return true;
            }
        });

        ImageButton.ImageButtonStyle styleBody = new ImageButton.ImageButtonStyle();
        styleBody.up = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.bodyHTexture));
        styleBody.down = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.bodyHTextureR));
        bodyButton = new ImageButton(styleBody);
        bodyButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                menuState=MenuState.BODY;
                switchVG();
                return true;
            }
        });

        ImageButton.ImageButtonStyle weapBody = new ImageButton.ImageButtonStyle();
        weapBody.up = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.weapHTexture));
        weapBody.down = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.weapHTextureR));
        weapButton = new ImageButton(weapBody);
        weapButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                menuState=MenuState.WEAPON;
                switchVG();
                return true;
            }
        });

        initVG();
        menuPane = initPane(weaponVG);

        table.add(headButton).right().expandX().size(35,30);
        table.add(bodyButton).right().size(35,30);
        table.add(weapButton).right().size(35,30);
        table.row();
        table.add(menuPane).colspan(3);

        return table;
    }

    /**
     * Methode d'initialisation des modules disponibles a
     * l'upgrade
     *
     * @return
     */
    public ScrollPane initPane(VerticalGroup defaultVG) {
        currentVG = defaultVG;
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.getScrollTexture(), 10, 50));

        ScrollPane pane = new ScrollPane(currentVG, paneStyle);
        pane.setScrollingDisabled(true, false);

        return pane;
    }

    /**
     * FOR TEST ONLY
     */
    @Override
    public void draw(){
        batch.setProjectionMatrix(gameManager.playScreen.camera.combined);
        batch.begin();
        itemPlayer.update();
        drawer.draw(itemPlayer);
        batch.end();
    }

    public void initVG() {
        bodyVG = new VerticalGroup();
        //bodyVG.space(5f);
        for (int i = 0; i < gameManager.assetManager.bodyList.size(); i++) {
            final ItemMenuElement itemMenuElement = new ItemMenuElement(gameManager, this);
            final ItemEntity itemEntity = gameManager.assetManager.bodyList.get(i);
            itemMenuElement.initItemMenuElement(itemEntity);
            itemMenuElement.setBackground(backgroundRegionDrawable);
            itemMenuElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    setSelectedItem(itemMenuElement);
                    return true;
            }});
            bodyVG.addActor(itemMenuElement);
        }

        weaponVG = new VerticalGroup();
        //weaponVG.space(10f);
        for (int i = 0; i < gameManager.assetManager.weaponList.size(); i++) {
            ItemEntity itemEntity = gameManager.assetManager.weaponList.get(i);
            final ItemMenuElement itemMenuElement = new ItemMenuElement(gameManager, this);
            itemMenuElement.initItemMenuElement(itemEntity);
            itemMenuElement.setBackground(backgroundRegionDrawable);
            itemMenuElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    setSelectedItem(itemMenuElement);
                    return true;
                }});
            weaponVG.addActor(itemMenuElement);
        }

        headVG = new VerticalGroup();
        //headVG.space(10f);
        for (int i = 0; i < gameManager.assetManager.helmList.size(); i++) {
            final ItemEntity itemEntity = gameManager.assetManager.helmList.get(i);
            final ItemMenuElement itemMenuElement = new ItemMenuElement(gameManager, this);
            itemMenuElement.initItemMenuElement(gameManager.assetManager.helmList.get(i));
            itemMenuElement.setBackground(backgroundRegionDrawable);
            itemMenuElement.addListener(new ClickListener(){
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    setSelectedItem(itemMenuElement);
                    return true;
                }});
            headVG.addActor(itemMenuElement);
        }
    }

    public void setEquipedItem(ItemMenuElement itemMenuElement){
        for( int i=0; i<currentVG.getChildren().size;i++) {
            ((ItemMenuElement) currentVG.getChildren().get(i)).setBackground(backgroundRegionDrawable);
        }
        itemMenuElement.setBackground(selectedRegionDrawable);
    }

    public void setSelectedItem(ItemMenuElement itemMenuElement){
        for( int i=0; i<currentVG.getChildren().size;i++) {
            ItemEntity itemEntity = itemMenuElement.itemEntitySource;
            itemPlayer.characterMaps[0]= itemPlayer.getEntity().getCharacterMap(itemEntity.mapName); // charactermap 0 wrong
            damageLabel.setText("Total atk " + (gameManager.gameInformation.tapDamage + itemEntity.baseDamage));
            weaponDamageLabel.setText("Weapon atk " + itemEntity.baseDamage);

            switch( menuState){
                case WEAPON:
                    gameManager.gameInformation.equipedWeapon=itemMenuElement.itemEntitySource.id;
                    break;
                case BODY:
                    gameManager.gameInformation.equipedBody=itemMenuElement.itemEntitySource.id;
                    break;
                case HEAD:
                    gameManager.gameInformation.equipedHead=itemMenuElement.itemEntitySource.id;
                    break;
            }
        }
    }

    public void updateBuyButton () {
    }

    /**
     * Change verticalGroup shown
     */
    public void switchVG(){
        int itemId;
        if (menuState.equals(MenuState.WEAPON)){
            itemId = gameManager.gameInformation.equipedWeapon;
            currentVG = weaponVG;
        } else if (menuState.equals(MenuState.BODY)){
            itemId = gameManager.gameInformation.equipedBody;
            currentVG = bodyVG;
        } else {
            itemId = gameManager.gameInformation.equipedHead;
            currentVG = headVG;
        }
        menuPane.setActor(currentVG);
        setEquipedItem((ItemMenuElement) currentVG.getChildren().get(itemId));
        setSelectedItem((ItemMenuElement) currentVG.getChildren().get(itemId));
    }

    public void show(){
        int itemId;
        if (menuState.equals(MenuState.WEAPON)){
            itemId = gameManager.gameInformation.equipedWeapon;
        } else if (menuState.equals(MenuState.BODY)){
            itemId = gameManager.gameInformation.equipedBody;
        } else {
            itemId = gameManager.gameInformation.equipedHead;
        }
        setEquipedItem((ItemMenuElement) currentVG.getChildren().get(itemId));
        setSelectedItem((ItemMenuElement) currentVG.getChildren().get(itemId));
        getParentTable().setVisible(true);
    }

    @Override
    public void update() {
        updateBuyButton();
    }

}
