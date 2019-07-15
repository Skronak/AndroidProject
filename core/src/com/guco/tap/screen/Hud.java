package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
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
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.menu.characterAttribute.CharacterAttributeMenu;
import com.guco.tap.menu.achievement.AchievementMenu;
import com.guco.tap.menu.shop.ShopMenu;
import com.guco.tap.menu.gameInformation.GameInformationMenu;
import com.guco.tap.menu.inventory.InventoryMenu;
import com.guco.tap.menu.option.OptionMenu;
import com.guco.tap.menu.itemAttribute.ItemAttributeMenu;
import com.guco.tap.object.FpsActor;
import com.guco.tap.object.EnemyInformation;
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

    public CharacterAttributeMenu characterAttributeMenu;

    private Viewport viewport;
    private ShopMenu shopMenu;
    private OptionMenu optionMenu;
    private AchievementMenu achievementMenu;
    private GameInformationMenu gameInformationMenu;
    private ItemAttributeMenu itemAttributeMenu;
    private InventoryMenu inventoryMenu;
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
    private Label floorLabel;
    public FpsActor fpsActor;
    private EnemyInformation enemyInformation;
    public Label battleNbLabel;
    public ImageButton ascendButton;
    private GameInformation gameInformation;
    
    public Hud(SpriteBatch sb, GameManager gameManager) {
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");

        largeMath = gameManager.largeMath;
        this.gameManager = gameManager;
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
        stage = new Stage(viewport, sb);
        generator = new com.guco.tap.utils.BitmapFontGenerator();
        font = gameManager.ressourceManager.getFont();
        gameInformation = gameManager.gameInformation;
        generator.dispose();
        font.setColor(Color.WHITE);
        initMenu();
        initButton();
        initHud();
    }

    /**
     * Initialise les menu
     */
    private void initMenu() {
        characterAttributeMenu = new CharacterAttributeMenu(gameManager);
        shopMenu = new ShopMenu(gameManager);
        inventoryMenu = new InventoryMenu(gameManager);
        gameInformationMenu = new GameInformationMenu(gameManager);
        optionMenu = new OptionMenu(gameManager);
        achievementMenu = new AchievementMenu(gameManager);
        itemAttributeMenu = new ItemAttributeMenu(gameManager);

        activeMenuList = new ArrayList<AbstractMenu>();
        activeMenuList.add(gameInformationMenu);
        activeMenuList.add(characterAttributeMenu);
        activeMenuList.add(inventoryMenu);
        activeMenuList.add(shopMenu);
        activeMenuList.add(achievementMenu);
        activeMenuList.add(optionMenu);
    }

    public void postInitMenu(){
        shopMenu.postInit();
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
        Texture upgradeButtonTextureUp = gameManager.ressourceManager.upgradeButtonTextureUp;
        Texture skillButtonTextureUp =  gameManager.ressourceManager.skillButtonTextureUp;
        Texture achievButtonTextureUp = gameManager.ressourceManager.achievButtonTextureUp;
        Texture mapButtonTextureUp = gameManager.ressourceManager.mapButtonTextureUp;
        Texture upgradeButtonTextureDown = gameManager.ressourceManager.upgradeButtonTextureDown;
        Texture skillButtonTextureDown =gameManager.ressourceManager.skillButtonTextureDown;
        Texture achievButtonTextureDown = gameManager.ressourceManager.achievButtonTextureDown;
        Texture mapButtonTextureDown = gameManager.ressourceManager.mapButtonTextureDown;
        Texture passivButtonTextureup = gameManager.ressourceManager.passivButtonTextureup;
        Texture passivButtonTextureDown = gameManager.ressourceManager.passivButtonTextureDown;
        Texture button6TextureUp = gameManager.ressourceManager.button6TextureUp;
        Texture button6TextureDown = gameManager.ressourceManager.button6TextureDown;
        Texture ascendButtonTextureUp = gameManager.ressourceManager.ascendButtonTextureUp;
        Texture ascendButtonTextureDown = gameManager.ressourceManager.ascendButtonTextureDown;
        Texture lockedButton = gameManager.ressourceManager.lockedButton;

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
        ascendButton = new ImageButton(style7);

        // Declaration des listener
        InputListener buttonListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(0));
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

        InputListener buttonListenerAscend = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.switchFloor();
                return true;
            }
        };
        ascendButton.addListener(buttonListenerAscend);
    }

    /**
     * Initialise les informations du HUD et
     * ajoute les elemnts dans le stage
     */
    private void initHud() {
        versionLabel = new Label(Constants.CURRENT_VERSION, new Label.LabelStyle(font, Color.WHITE));
        versionLabel.setFontScale(0.5f);
        versionLabel.setWrap(true);
        goldLabel = new Label(largeMath.getDisplayValue(gameInformation.currentGoldValue, gameInformation.currentGoldCurrency), new Label.LabelStyle(font, Color.WHITE));
        goldLabel.setAlignment(Align.center);
//        goldLabel.setFontScale(2);
        floorLabel = new Label(String.valueOf(""+gameInformation.dungeonLevel), gameManager.ressourceManager.getSkin());
        battleNbLabel = new Label(gameInformation.currentEnemyIdx+"/10", gameManager.ressourceManager.getSkin());
        battleNbLabel.setFontScale(0.9f,0.9f);
        goldDecreaseLabel = new Label("", new Label.LabelStyle(font, Color.RED));
        goldDecreaseLabel.setVisible(false);
        goldDecreaseLabel.setFontScale(2);
        goldDecreaseLabel.setAlignment(1);

        Image goldIcon = new Image(gameManager.ressourceManager.getGoldIcon());
        // Add an animated label when gold is decreased
        Stack stack = new Stack();
        stack.add(goldLabel);
        stack.add(goldDecreaseLabel);

        Table tableTop = new Table();
        tableTop.add(button_5).height(40).width(40).left();
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.addActor(floorLabel);
        verticalGroup.addActor(battleNbLabel);
        tableTop.add(verticalGroup).expandX().left().padLeft(90);
        tableTop.add(goldIcon).size(30,30).right();
        tableTop.add(stack).right();

        TextureRegionDrawable backgroundImg = new TextureRegionDrawable(gameManager.ressourceManager.brownTexture);
        backgroundImg.setMinWidth(Constants.V_WIDTH);
        backgroundImg.setMinHeight(40);
        tableTop.setBackground(backgroundImg);

        mainTable = new Table();
        mainTable.top();
        mainTable.setFillParent(true);

        // Add menu to the stage
        for(int i=0;i<activeMenuList.size();i++) {
            mainTable.addActor(activeMenuList.get(i).parentTable);
        }
        mainTable.addActor(itemAttributeMenu.parentTable);

        // ***** OTHER *****
        // Hp bar & name
        enemyInformation = new EnemyInformation(gameManager);
        stage.addActor(enemyInformation);

        // Visual button to go upstairs
        ascendButton.setSize(80,80);
        ascendButton.setPosition(Constants.V_WIDTH/2 - ascendButton.getWidth()/2,350);
        ascendButton.setVisible(false);
        stage.addActor(ascendButton);

        // Add buttons to the stage
        Table menuButtonTable = new Table();
        menuButtonTable.addActor(button_0);
        menuButtonTable.addActor(button_1);
        menuButtonTable.addActor(button_2);
        menuButtonTable.addActor(button_3);

        menuButtonTable.add(button_0).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);
        menuButtonTable.add(button_1).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);
        menuButtonTable.add(button_2).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);
        menuButtonTable.add(button_3).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);
        menuButtonTable.add(button_4).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);
        menuButtonTable.add(button_6).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT);//.padLeft(3);

        // Assemble hud
        mainTable.add(tableTop).top().height(45);
        mainTable.row();
        //mainTable.add(versionLabel).align(Align.right).bottom();
        //mainTable.row();
        mainTable.add(menuButtonTable).bottom().expandY().colspan(activeMenuList.size()-1);
        //mainTable.debug();

       stage.addActor(mainTable);

        // Optionnal element
        fpsActor = new FpsActor(gameManager.ressourceManager);
        fpsActor.setVisible(gameInformation.optionFps);
        fpsActor.setPosition(Constants.V_WIDTH-fpsActor.getWidth(), Constants.V_HEIGHT-fpsActor.getHeight());
        fpsActor.setFontScale(1.5f);
        stage.addActor(fpsActor);

    }

    /**
     * Methode draw specifique
     */
    public void draw() {
        stage.act();
        stage.draw();

        if (currentMenu instanceof InventoryMenu && currentMenu.parentTable.getActions().size==0) {
            currentMenu.draw();
        }
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
        if (currentMenu == null) {
            menu.parentTable.setPosition(menu.parentTable.getX(), -menu.parentTable.getHeight()); //Menu Animation
            menu.parentTable.addAction(Actions.moveTo(menu.parentTable.getX(), Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT,0.2f, Interpolation.exp5Out)); // Menu Animation
            menu.show();
            currentMenu = menu;
            gameManager.currentState=GameState.MENU;
        } else if (menu.equals(currentMenu)) {
            currentMenu.parentTable.clearActions();
            menu.parentTable.addAction(Actions.sequence(Actions.moveTo(menu.parentTable.getX(), -menu.parentTable.getHeight(),0.2f),Actions.visible(false)));
            currentMenu = null;
            gameManager.currentState=GameState.IN_GAME;
        } else {
            currentMenu.parentTable.clearActions();
            currentMenu.parentTable.setVisible(false);
            currentMenu = menu;
            menu.parentTable.setPosition(menu.parentTable.getX(), Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT); //Menu Animation
            menu.show();
            gameManager.currentState=GameState.MENU;
        }
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

    public void initEnemyInformation(EnemyActor enemyActor){
        floorLabel.setText("Floor "+gameInformation.dungeonLevel);
        enemyInformation.reinitialise(enemyActor);
    }

    public void updateEnemyInformation(float value){
        enemyInformation.decrease(value);
        //Gdx.app.debug("HP",String.valueOf(playScreen.enemyActorList.get(0).hp));
    }

    // Met a jour l'affichage de l'or
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
}
