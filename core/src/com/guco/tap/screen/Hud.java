package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guco.tap.actor.EnemyHealthBarUI;
import com.guco.tap.actor.FpsActor;
import com.guco.tap.actor.SpriterEnemyActor;
import com.guco.tap.actor.UiLevelSelect;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.game.TapDungeonGame;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.menu.achievement.AchievementMenu;
import com.guco.tap.menu.characterAttribute.CharacterAttributeMenu;
import com.guco.tap.menu.forge.ForgeMenu;
import com.guco.tap.menu.fuse.FuseMenu;
import com.guco.tap.menu.gameInformation.GameInformationMenu;
import com.guco.tap.menu.inventory.CharacterInventoryMenu;
import com.guco.tap.menu.inventory.CharacterMenu;
import com.guco.tap.menu.inventory.alternate.InventoryMenu;
import com.guco.tap.menu.itemAttribute.ItemAttributeMenu;
import com.guco.tap.menu.option.OptionMenu;
import com.guco.tap.menu.shop.AreaMenu;
import com.guco.tap.screen.actor.PlayerDetailUiActor;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.GameState;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

import java.util.ArrayList;


/**
 * Created by Skronak on 11/12/2016.
 *
 * Classe HUD contenant tous les boutons et menu
 * du jeu
 */
public class Hud implements Disposable {
    public Stage stage;

    private Viewport viewport;
    public CharacterAttributeMenu characterAttributeMenu;
    private AreaMenu areaMenu;
    private ForgeMenu forgemenu;
    private OptionMenu optionMenu;
    private AchievementMenu achievementMenu;
    private GameInformationMenu gameInformationMenu;
    private ItemAttributeMenu itemAttributeMenu;
    private InventoryMenu inventoryMenu;
    private CharacterMenu characterMenu;
    private FuseMenu fuseMenu;
    private CharacterInventoryMenu characterInventoryMenu;
    private Label versionLabel;
    public Label goldLabel;
    private Label goldDecreaseLabel;
    private BitmapFont font;
    private com.guco.tap.utils.BitmapFontGenerator generator;
    private GameManager gameManager;
    private Table mainTable;
    private Button button_1, button_0, button_3, button_2, button_4, button_5, button_6;
    private LargeMath largeMath;
    private AbstractMenu currentMenu;
    private ArrayList<AbstractMenu> activeMenuList;
    public Label floorLabel;
    public FpsActor fpsActor;
    public EnemyHealthBarUI enemyInformation;
    public Label battleNbLabel;
    public ImageButton goToNextAreaButton;
    private GameInformation gameInformation;
    public Group sceneLayer, menuLayer;
    private ImageButton skillButton0, skillButton1,skillButton2,skillButton3;
    private TapDungeonGame game;

    public Hud(TapDungeonGame game) {
        this.game = game;

        largeMath = game.largeMath;
        this.gameManager = game.gameManager;
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT);

        stage = new Stage(viewport, game.sb);
        generator = new com.guco.tap.utils.BitmapFontGenerator();
        font = game.assetsManager.getFont();
        gameInformation = game.gameInformation;
        generator.dispose();
        font.setColor(Color.WHITE);

        sceneLayer = new Group();
        menuLayer = new Group();
    }

    public void initializeHud() {
        initMenu();
        initButton();
        initHud();
    }

    private void initMenu() {
        characterAttributeMenu = new CharacterAttributeMenu(gameManager);
        areaMenu = new AreaMenu(gameManager);
        inventoryMenu = new InventoryMenu(gameManager);
        gameInformationMenu = new GameInformationMenu(gameManager);
        optionMenu = new OptionMenu(gameManager);
        achievementMenu = new AchievementMenu(gameManager);
        itemAttributeMenu = new ItemAttributeMenu(gameManager);
        forgemenu = new ForgeMenu(gameManager, (SpriteBatch) stage.getBatch());
        fuseMenu = new FuseMenu(gameManager);
        characterInventoryMenu = new CharacterInventoryMenu(game);
        characterMenu = new CharacterMenu(game, characterInventoryMenu);

        activeMenuList = new ArrayList<AbstractMenu>();
        activeMenuList.add(gameInformationMenu);
        activeMenuList.add(characterAttributeMenu);
        activeMenuList.add(inventoryMenu);
        activeMenuList.add(areaMenu);
        activeMenuList.add(achievementMenu);
        activeMenuList.add(optionMenu);
        activeMenuList.add(forgemenu);
//        activeMenuList.add(fuseMenu);
        activeMenuList.add(characterMenu);
        activeMenuList.add(inventoryMenu);
    }

    private void initTop(){
    }

    private void initMiddle(){
    }

    private void initBottom(){
    }

    /**
     * Initialisation des bouton du hud
     */
    private void initButton() {
        Texture upgradeButtonTextureUp = gameManager.assetsManager.upgradeButtonTextureUp;
        Texture skillButtonTextureUp =  gameManager.assetsManager.skillButtonTextureUp;
        Texture achievButtonTextureUp = gameManager.assetsManager.achievButtonTextureUp;
        Texture mapButtonTextureUp = gameManager.assetsManager.mapButtonTextureUp;
        Texture upgradeButtonTextureDown = gameManager.assetsManager.upgradeButtonTextureDown;
        Texture skillButtonTextureDown =gameManager.assetsManager.skillButtonTextureDown;
        Texture achievButtonTextureDown = gameManager.assetsManager.achievButtonTextureDown;
        Texture mapButtonTextureDown = gameManager.assetsManager.mapButtonTextureDown;
        Texture passivButtonTextureup = gameManager.assetsManager.passivButtonTextureup;
        Texture passivButtonTextureDown = gameManager.assetsManager.passivButtonTextureDown;
        Texture button6TextureUp = gameManager.assetsManager.button6TextureUp;
        Texture button6TextureDown = gameManager.assetsManager.button6TextureDown;
        Texture lockedButton = gameManager.assetsManager.lockedButton;

        ImageButton.ImageButtonStyle style0 = new ImageButton.ImageButtonStyle();
        style0.up = new TextureRegionDrawable(new TextureRegion(skillButtonTextureUp));
        style0.down = new TextureRegionDrawable(new TextureRegion(skillButtonTextureDown));
        button_0 = new ImageButton(style0);

        ImageButton.ImageButtonStyle style1 = new ImageButton.ImageButtonStyle();
        style1.up = new TextureRegionDrawable(new TextureRegion(upgradeButtonTextureUp));
        style1.down =  new TextureRegionDrawable(new TextureRegion(upgradeButtonTextureDown));
        button_1 = new ImageButton(style1);

        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        style2.up = new TextureRegionDrawable(new TextureRegion(achievButtonTextureUp));
        style2.down = new TextureRegionDrawable(new TextureRegion(achievButtonTextureDown));
        button_3 = new ImageButton(style2);

        ImageButton.ImageButtonStyle style3 = new ImageButton.ImageButtonStyle();
        style3.up = new TextureRegionDrawable(new TextureRegion(mapButtonTextureUp));
        style3.down =  new TextureRegionDrawable(new TextureRegion(mapButtonTextureDown));
        button_2 = new ImageButton(style3);

        ImageButton.ImageButtonStyle style4 = new ImageButton.ImageButtonStyle();
        style4.up = new TextureRegionDrawable(new TextureRegion(button6TextureUp));
        style4.down =  new TextureRegionDrawable(new TextureRegion(button6TextureDown));
        button_4 = new ImageButton(style4);

        ImageButton.ImageButtonStyle style5 = new ImageButton.ImageButtonStyle();
        style5.up = new TextureRegionDrawable(new TextureRegion(passivButtonTextureDown));
        style5.down = new TextureRegionDrawable(new TextureRegion(passivButtonTextureup));
        button_5 = new ImageButton(style5);

        ImageButton.ImageButtonStyle style6 = new ImageButton.ImageButtonStyle();
        style6.up = new TextureRegionDrawable(new TextureRegion(lockedButton));
        style6.down = new TextureRegionDrawable(new TextureRegion(lockedButton));
        button_6 = new ImageButton(style6);

        ImageButton.ImageButtonStyle style7 = new ImageButton.ImageButtonStyle();
        style7.up = new TextureRegionDrawable(new TextureRegion(passivButtonTextureDown));
        style7.down = new TextureRegionDrawable(new TextureRegion(passivButtonTextureup));
        goToNextAreaButton = new ImageButton(style7);

        // Declaration des listener
        InputListener buttonListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(6));
                gameManager.initArea();

                return true;
            }
        };
        button_0.addListener(buttonListener);

        InputListener achievementListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(1));
                return true;
            }
        };
        button_1.addListener(achievementListener);

        InputListener buttonListenerCredit = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(2));
                return true;
            }
        };
        button_2.addListener(buttonListenerCredit);

        InputListener equipListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(3));
                return true;
            }
        };
        button_3.addListener(equipListener);

        InputListener buttonListenerOption = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(4));
                return true;
            }
        };
        button_4.addListener(buttonListenerOption);

        InputListener buttonListenerSkill = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(5));
                return true;
            }
        };
        button_5.addListener(buttonListenerSkill);

        InputListener buttonListenerLevelSelect = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                switchToSelectScreen();
                toggleMenu(activeMenuList.get(7));
                return true;
            }
        };
        button_6.addListener(buttonListenerLevelSelect);

        InputListener buttonListenerAscend = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        };
        goToNextAreaButton.addListener(buttonListenerAscend);

        ImageButton.ImageButtonStyle skill0Head = new ImageButton.ImageButtonStyle();
        skill0Head.up = new TextureRegionDrawable(new Texture(Gdx.files.internal("sprites/icon/skillIcon1.png")));
        skill0Head.down = new TextureRegionDrawable(new Texture(Gdx.files.internal("sprites/icon/skillIcon1.png")));
        skillButton0 = new ImageButton(skill0Head);
        skillButton1 = new ImageButton(skill0Head);
        skillButton2 = new ImageButton(skill0Head);
        skillButton3 = new ImageButton(skill0Head);
    }

    /**
     * Initialise les informations du HUD et
     * ajoute les elements dans le stage
     */
    private void initHud() {
        versionLabel = new Label(Constants.CURRENT_VERSION, new Label.LabelStyle(font, Color.WHITE));
        versionLabel.setFontScale(0.5f);
        versionLabel.setWrap(true);

        goldLabel = new Label(largeMath.getDisplayValue(gameInformation.currentGoldValue, gameInformation.currentGoldCurrency), new Label.LabelStyle(font, Color.WHITE));
        goldLabel.setAlignment(Align.center);
//        goldLabel.setFontScale(2);
        floorLabel = new Label(String.valueOf(""+gameInformation.areaLevel), gameManager.assetsManager.getSkin());
        battleNbLabel = new Label(gameInformation.currentEnemyIdx+"/10", gameManager.assetsManager.getSkin());
        battleNbLabel.setFontScale(0.9f,0.9f);
        goldDecreaseLabel = new Label("", new Label.LabelStyle(font, Color.RED));
        goldDecreaseLabel.setVisible(false);
        goldDecreaseLabel.setFontScale(2);
        goldDecreaseLabel.setAlignment(1);

        Image goldIcon = new Image(gameManager.assetsManager.getGoldIcon());
        // Add an animated label when gold is decreased
        Stack stack = new Stack();
        stack.add(goldLabel);
        stack.add(goldDecreaseLabel);

        Table topHorizontalTable = new Table();
        PlayerDetailUiActor playerDetailActor = new PlayerDetailUiActor(gameManager);
        topHorizontalTable.add(playerDetailActor).expandX().height(40).width(130).left();
        topHorizontalTable.debug();
//        VerticalGroup verticalGroup = new VerticalGroup();
//        verticalGroup.addActor(floorLabel);
//        verticalGroup.addActor(battleNbLabel);
//        topHorizontalTable.add(verticalGroup).left();
        topHorizontalTable.add(goldIcon).size(30,30);//.right();
        topHorizontalTable.add(stack).right();

        TextureRegionDrawable backgroundImg = new TextureRegionDrawable(gameManager.assetsManager.brownTexture);
        backgroundImg.setMinWidth(Constants.V_WIDTH);
        backgroundImg.setMinHeight(40);
        topHorizontalTable.setBackground(backgroundImg);

        // ***** OTHER *****
        // Hp bar & name
        enemyInformation = new EnemyHealthBarUI(gameManager);
        sceneLayer.addActor(enemyInformation);

        // Visual button to go upstairs
        goToNextAreaButton.setSize(80,80);
        goToNextAreaButton.setPosition(Constants.V_WIDTH/2 - goToNextAreaButton.getWidth()/2,350);
        goToNextAreaButton.setVisible(false);
        sceneLayer.addActor(goToNextAreaButton);

        // Add buttons to the stage
        Table bottomMenuTable = new Table();
        bottomMenuTable.addActor(button_0);
        bottomMenuTable.addActor(button_1);
        bottomMenuTable.addActor(button_2);
        bottomMenuTable.addActor(button_3);

        bottomMenuTable.add(button_0).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);
        bottomMenuTable.add(button_1).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);
        bottomMenuTable.add(button_2).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);
        bottomMenuTable.add(button_3).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);
        bottomMenuTable.add(button_4).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);
        bottomMenuTable.add(button_6).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);

        UiLevelSelect levelSelect = new UiLevelSelect(gameManager);
        // Assemble hud
        mainTable = new Table();
        mainTable.top();
        mainTable.setFillParent(true);

        mainTable.add(topHorizontalTable).top().height(45).expandX();
        mainTable.row();
        mainTable.add(levelSelect).padTop(10);
        mainTable.row();

        Table skillMenuTable = new Table();
        skillMenuTable.add(skillButton0).size(50,50).padLeft(10);
        skillMenuTable.add(skillButton1).size(50,50).padLeft(10);
        skillMenuTable.add(skillButton2).size(50,50).padLeft(10);
        skillMenuTable.add(skillButton3).size(50,50).padLeft(10);
        mainTable.add(skillMenuTable).bottom().expandY().left().colspan(activeMenuList.size()-1).padBottom(10);
        mainTable.row();

        // Add menu to the stage
        for(int i=0;i<activeMenuList.size();i++) {
            mainTable.addActor(activeMenuList.get(i).parentTable);
        }
        mainTable.addActor(itemAttributeMenu.parentTable);
        mainTable.row();
        mainTable.add(bottomMenuTable);

        // Optionnal element
        fpsActor = new FpsActor(gameManager.assetsManager);
        fpsActor.setVisible(gameInformation.optionFps);
        fpsActor.setPosition(Constants.V_WIDTH-fpsActor.getWidth(), Constants.V_HEIGHT-fpsActor.getHeight());
        fpsActor.setFontScale(1.5f);
        sceneLayer.addActor(fpsActor);

        stage.addActor(sceneLayer);
        stage.addActor(menuLayer);
        stage.addActor(mainTable);

//        currentMenu = characterMenu;
//        characterMenu.show();
//        stage.setDebugAll(true);
    }

    /**
     * Methode draw specifique
     */
    public void draw() {
        stage.act();
        stage.draw();
//        if (currentMenu instanceof InventoryMenu && currentMenu.mainTable.getActions().size==0) {
        if (currentMenu != null) {
            currentMenu.draw();
        }
//        }
    }

    public void animateCritical() {
        goldLabel.addAction(Actions.sequence(
                Actions.color(Constants.CRITICAL_LABEL_COLOR),
                Actions.color(Color.WHITE,0.5f)
        ));
    }

    public void animateDecreaseGold(ValueDTO valueDto) {
        String text = gameManager.largeMath.getDisplayValue(valueDto.value, valueDto.currency);
        goldDecreaseLabel.setText("- " + text);
        goldDecreaseLabel.clearActions();
        goldDecreaseLabel.addAction(Actions.sequence(
                Actions.show(),
                Actions.fadeIn(0.5f),
                Actions.fadeOut(1f),
                Actions.hide()
        ));
        goldDecreaseLabel.addAction(Actions.sequence(
                Actions.delay(0.5f),
                Actions.moveTo(goldDecreaseLabel.getX(),goldDecreaseLabel.getY()-100,3f)
        ));
    }

    /**
     * Show or hide menu table and play
     * animation
     */
    private void toggleMenu(AbstractMenu menu) {
        menu.parentTable.clearActions();
        if (gameManager.currentState.equals(GameState.LEVEL)){
            game.setScreen(game.battleScreen);
        }

        if (currentMenu == null) {
            openMenu(menu);
        } else if (menu.equals(currentMenu)) {
            closeCurrentMenu();
        } else {
            switchMenu(menu);
        }
    }

    private void switchMenu(AbstractMenu menu) {
        currentMenu.parentTable.clearActions();
        currentMenu.parentTable.setVisible(false);
        currentMenu = menu;
        menu.parentTable.setPosition(menu.parentTable.getX(), Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT); //Menu Animation
        menu.show();
        gameManager.currentState=GameState.MENU;
    }

    public void closeCurrentMenu() {
        currentMenu.close();

        currentMenu = null;
        gameManager.currentState=GameState.IN_GAME;
    }

    private void openMenu(AbstractMenu menu) {
        menu.open();

        currentMenu = menu;
        gameManager.currentState=GameState.MENU;
    }

    public void showUpgradeMenu(int id){
        itemAttributeMenu.switchWeaponAttribute(id);
        toggleMenu(itemAttributeMenu);
    }

    /**
     * Update Menu while shown
     */
    public void update(){
        updateGoldLabel();
        updateCurrentMenu();
    }

    public void initFight(SpriterEnemyActor enemyActor) {
        floorLabel.setText(gameManager.currentArea.name + " - "+gameInformation.areaLevel);
        enemyInformation.init(enemyActor);
    }

    public void updateEnemyInformation(ValueDTO damage) {
        enemyInformation.updateLifeBar(damage);
    }

    // Met a jour l'affichage de l'orm
    public void updateGoldLabel(){
        String scoreAffichage = largeMath.getDisplayValue(gameInformation.currentGoldValue, gameInformation.currentGoldCurrency);
        goldLabel.setText(scoreAffichage);
    }

    // Met a jour l'affichage du menu actif
    public void updateCurrentMenu() {
        if (null != currentMenu) {
            currentMenu.update();
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void setVisible(boolean isVisible){
        if (isVisible){
            sceneLayer.addAction(Actions.fadeIn(1f));
            menuLayer.addAction(Actions.fadeIn(1f));
            mainTable.addAction(Actions.fadeIn(1f));
        } else {
            sceneLayer.getColor().a=0;
            menuLayer.getColor().a=0;
            mainTable.getColor().a=0;
        }
    }
}
