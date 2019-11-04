package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.guco.tap.dto.Area;
import com.guco.tap.entity.AttributeElement;
import com.guco.tap.entity.Enemy;
import com.guco.tap.entity.Item;
import com.guco.tap.entity.TiersUpgrades;
import com.guco.tap.menu.achievement.element.AchievementElement;
import com.guco.tap.utils.BitmapFontGenerator;

import java.util.ArrayList;

/**
 * Created by Skronak on 02/08/2017.
 *
 * Classe de chargement des assets du jeu
 * TODO Utiliser atlas & asset manager
 */
public class AssetsManager {

    private AssetManager assetManager;

    private Json json;
    private ArrayList<AttributeElement> attributeElementList;
    public TextButton.TextButtonStyle moduleMenuBuyTxtBtnStyle,moduleMenuUpgradeTxtBtnStyle;
    private TextureRegionDrawable menuBackgroundTexture;
    private BitmapFont font;

    private String ICON_PATH = "sprites/icon/";
    private String UI_PATH = "sprites/ui/";
    private String BACKGROUND_PATH="sprites/background/";
    private String OBJECT_PATH="sprites/object/";
    private String JSON_PATH="json/";

    private Texture goldIcon;
    private ArrayList<Texture> moduleDrawableUpList;
    private Texture disabledIcon;
    private Texture scrollTexture;
    private Skin skin;
    private ArrayList<Texture> upgradeLvlImageList;
    private ArrayList<AchievementElement> achievementElementList;
    public ArrayList<Enemy> enemyList;
    private ArrayList<Item> itemList;
    public ArrayList<Item> weaponList,helmList, bodyList;
    public ArrayList<Area> areaList;
    public ArrayList<Texture> backgroundImageList;
    public ArrayList<TiersUpgrades> weaponUpgradeList;
    public Texture background1Texture,background2Texture,background3Texture,background4Texture,background5Texture;
    public Texture redTexture, orangeTexture, crossTexture, greyTexture, lightGreyTexture,brownTexture, upTexture, grey9Texture;
    public Texture bodyHTexture,headHTexture,weapHTexture,bodyHTextureR,headHTextureR,weapHTextureR;
    public Texture diffTexture0,diffTexture1,diffTexture2,diffTexture3,diffTexture4, torchTexture, achievementTexture,achievementAvaibleTexture;
    public Texture upgradeButtonTextureUp,skillButtonTextureUp,achievButtonTextureUp,upgradeButtonTextureDown,skillButtonTextureDown,achievButtonTextureDown,mapButtonTextureDown,mapButtonTextureUp,passivButtonTextureup,passivButtonTextureDown,button6TextureUp,button6TextureDown, ascendButtonTextureUp,ascendButtonTextureDown,lockedButton;

    private int loadValue;

    public AssetsManager() {
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");

        json = new Json();
        assetManager = new AssetManager();

        loadValue=0;
    }

    public void loadAsset(){
        readFromFile();
        loadIcons();
        loadImage();
        loadTexture();
        initAsset();
    }

    private void initAsset() {
        BitmapFontGenerator generator = new BitmapFontGenerator();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        font = generator.getFont();
        generator.dispose();

        TextureRegionDrawable buyDrawableUp = new TextureRegionDrawable( new TextureRegion(new Texture(Gdx.files.internal(UI_PATH+"goldButtonUp.png"))) );
        TextureRegionDrawable buyDrawableDown = new TextureRegionDrawable( new TextureRegion(new Texture(Gdx.files.internal(UI_PATH+"goldButtonDown.png"))) );
        TextureRegionDrawable buyDrawableChecked = new TextureRegionDrawable( new TextureRegion(new Texture(Gdx.files.internal(UI_PATH+"goldButtonUp.png"))) );
        moduleMenuBuyTxtBtnStyle = new TextButton.TextButtonStyle(buyDrawableUp, buyDrawableDown,buyDrawableChecked, font);

        TextureRegionDrawable upgradeDrawableUp = new TextureRegionDrawable( new TextureRegion(new Texture(Gdx.files.internal(ICON_PATH+"hud_b5.png"))) );
        TextureRegionDrawable upgradeDrawableDown = new TextureRegionDrawable( new TextureRegion(new Texture(Gdx.files.internal(ICON_PATH+"hud_b5_r.png"))) );
        TextureRegionDrawable upgradeDrawableChecked = new TextureRegionDrawable( new TextureRegion(new Texture(Gdx.files.internal(ICON_PATH+"hud_b5_r.png"))) );
        moduleMenuUpgradeTxtBtnStyle = new TextButton.TextButtonStyle(upgradeDrawableUp, upgradeDrawableDown,upgradeDrawableChecked, font);

        loadValue+=1;
        debugLogLoading();
    }

    private void readFromFile() {
        attributeElementList = new ArrayList<AttributeElement>();
        attributeElementList = json.fromJson(ArrayList.class, AttributeElement.class, Gdx.files.internal(JSON_PATH+"moduleElement.json"));

        achievementElementList = new ArrayList<AchievementElement>();
        achievementElementList = json.fromJson(ArrayList.class, AchievementElement.class, Gdx.files.internal(JSON_PATH+"achievementElement.json"));

        enemyList = json.fromJson(ArrayList.class, Enemy.class, Gdx.files.internal(JSON_PATH+"enemyJSON.json"));
        weaponList = json.fromJson(ArrayList.class, Item.class, Gdx.files.internal(JSON_PATH+"weapon.json"));
        helmList = json.fromJson(ArrayList.class, Item.class, Gdx.files.internal(JSON_PATH+"helm.json"));
        bodyList = json.fromJson(ArrayList.class, Item.class, Gdx.files.internal(JSON_PATH+"body.json"));
        weaponUpgradeList = json.fromJson(ArrayList.class, TiersUpgrades.class, Gdx.files.internal(JSON_PATH+"itemUpgrade.json"));
        weaponUpgradeList = json.fromJson(ArrayList.class, TiersUpgrades.class, Gdx.files.internal(JSON_PATH+"itemUpgrade.json"));
        areaList = json.fromJson(ArrayList.class, Area.class, Gdx.files.internal(JSON_PATH+"areas.json"));

        loadValue+=1;
        debugLogLoading();
    }

    private void loadTexture() {
        scrollTexture = new Texture(Gdx.files.internal(ICON_PATH+"bar.png"));
        menuBackgroundTexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal(BACKGROUND_PATH+"menuBackground.png"))));
        torchTexture = new Texture(Gdx.files.internal(OBJECT_PATH+"torch.png"));

        upTexture = new Texture(Gdx.files.internal(ICON_PATH+"up.png"));
        achievementTexture = new Texture(ICON_PATH+"achievement.png");
        grey9Texture = new Texture(UI_PATH+"grey.9.png");
        achievementAvaibleTexture = new Texture(ICON_PATH+"achievement2.png");

        upgradeButtonTextureUp = new Texture(Gdx.files.internal(ICON_PATH+"hud_b2.png"));
        skillButtonTextureUp = new Texture(Gdx.files.internal(ICON_PATH+"hud_b1.png"));
        achievButtonTextureUp = new Texture(Gdx.files.internal(ICON_PATH+"hud_b3.png"));
        mapButtonTextureUp = new Texture(Gdx.files.internal(ICON_PATH+"hud_b4.png"));
        upgradeButtonTextureDown = new Texture(Gdx.files.internal(ICON_PATH+"hud_b2_r.png"));
        skillButtonTextureDown = new Texture(Gdx.files.internal(ICON_PATH+"hud_b1_r.png"));
        achievButtonTextureDown = new Texture(Gdx.files.internal(ICON_PATH+"hud_b3_r.png"));
        mapButtonTextureDown = new Texture(Gdx.files.internal(ICON_PATH+"hud_b4_r.png"));
        passivButtonTextureup = new Texture(Gdx.files.internal(ICON_PATH+"hud_b5_r.png"));
        passivButtonTextureDown = new Texture(Gdx.files.internal(ICON_PATH+"hud_b5.png"));
        button6TextureUp = new Texture(Gdx.files.internal(ICON_PATH+"hud_b6.png"));
        button6TextureDown = new Texture(Gdx.files.internal(ICON_PATH+"hud_b6_r.png"));
        ascendButtonTextureUp = new Texture(Gdx.files.internal(ICON_PATH+"ascend.png"));
        ascendButtonTextureDown = new Texture(Gdx.files.internal(ICON_PATH+"ascend_r.png"));
        lockedButton = new Texture(Gdx.files.internal(ICON_PATH+"locked_button.png"));

        backgroundImageList = new ArrayList<Texture>();
        for(int i=0;i<areaList.size();i++) {
            Texture backgroundTexture = new Texture(Gdx.files.internal(BACKGROUND_PATH) + "\\" + areaList.get(i).background);
            backgroundTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            backgroundImageList.add(backgroundTexture);
        }

        loadValue+=1;
        debugLogLoading();
    }

    private void loadIcons(){
        goldIcon = new Texture(Gdx.files.internal(ICON_PATH+"gold+.png"));
        disabledIcon = new Texture(Gdx.files.internal(ICON_PATH+"lock.png"));

        loadValue+=1;
        debugLogLoading();
    }

    public void loadImage() {
        moduleDrawableUpList = new ArrayList<Texture>();
        upgradeLvlImageList = new ArrayList<Texture>();

        for (int i = 0; i < getAttributeElementList().size(); i++) {
            moduleDrawableUpList.add(new Texture(Gdx.files.internal(ICON_PATH + getAttributeElementList().get(i).getIcon())));
        }
        redTexture = new Texture(Gdx.files.internal(UI_PATH+"red.png"));
        orangeTexture = new Texture(Gdx.files.internal(UI_PATH+"orange.png"));
        greyTexture = new Texture(Gdx.files.internal(UI_PATH+"grey.png"));
        crossTexture = new Texture(Gdx.files.internal(UI_PATH+"red_orange.png"));
        lightGreyTexture = new Texture(Gdx.files.internal(UI_PATH+"lightGrey.png"));
        brownTexture = new Texture(Gdx.files.internal(UI_PATH+"brown.png"));

        diffTexture0 = new Texture(Gdx.files.internal(ICON_PATH+"diff0.png"));
        diffTexture1 = new Texture(Gdx.files.internal(ICON_PATH+"diff1.png"));
        diffTexture2 = new Texture(Gdx.files.internal(ICON_PATH+"diff2.png"));
        diffTexture3 = new Texture(Gdx.files.internal(ICON_PATH+"diff3.png"));
        diffTexture4 = new Texture(Gdx.files.internal(ICON_PATH+"diff4.png"));

        bodyHTexture = new Texture(Gdx.files.internal(ICON_PATH+"icon_header_body.png"));
        headHTexture = new Texture(Gdx.files.internal(ICON_PATH+"icon_header_head.png"));
        weapHTexture = new Texture(Gdx.files.internal(ICON_PATH+"icon_header_sword.png"));
        bodyHTextureR = new Texture(Gdx.files.internal(ICON_PATH+"icon_header_body_r.png"));
        headHTextureR = new Texture(Gdx.files.internal(ICON_PATH+"icon_header_head_r.png"));
        weapHTextureR = new Texture(Gdx.files.internal(ICON_PATH+"icon_header_sword_r.png"));

        loadValue+=1;
        debugLogLoading();
    }

    private void debugLogLoading(){
        Gdx.app.log("AssetLoad",String.valueOf(loadValue) + "/ 5");
    }
//*****************************************************
//                  GETTER & SETTER
// ****************************************************

    public TextButton.TextButtonStyle getModuleMenuBuyTxtBtnStyle() {
        return moduleMenuBuyTxtBtnStyle;
    }

    public Texture getGoldIcon() {
        return goldIcon;
    }

    public ArrayList<Texture> getModuleDrawableUpList() {
        return moduleDrawableUpList;
    }

    public Texture getScrollTexture() {
        return scrollTexture;
    }

    public Skin getSkin() {
        return skin;
    }

    public ArrayList<Texture> getUpgradeLvlImageList() {
        return upgradeLvlImageList;
    }

    public int getLoadValue() {
        return loadValue;
    }

    public ArrayList<AttributeElement> getAttributeElementList() {
        return attributeElementList;
    }

    public TextureRegionDrawable getMenuBackgroundTexture() {
        return menuBackgroundTexture;
    }

    public Texture getDisabledIcon() {
        return disabledIcon;
    }

    public BitmapFont getFont() {
        return font;
    }

    public ArrayList<AchievementElement> getAchievementElementList() {
        return achievementElementList;
    }

    public TextButton.TextButtonStyle getModuleMenuUpgradeTxtBtnStyle() {
        return moduleMenuUpgradeTxtBtnStyle;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }
}