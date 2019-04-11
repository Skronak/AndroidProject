package com.guco.tap.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;
import com.guco.tap.menu.AchievementMenu;
import com.guco.tap.menu.EquipMenu;
import com.guco.tap.menu.GameInformationMenu;
import com.guco.tap.menu.OptionMenu;
import com.guco.tap.menu.ModuleMenu;
import com.guco.tap.menu.SkillMenu;
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
    private Viewport viewport;
    private ModuleMenu moduleMenu;
    private EquipMenu equipMenu;
    private OptionMenu optionMenu;
    private AchievementMenu achievementMenu;
    private GameInformationMenu gameInformationMenu;
    private SkillMenu skillMenu;
    private Label versionLabel;
    public Label scoreLabel;
    private Label goldDecreaseLabel;
    private BitmapFont font;
    private com.guco.tap.utils.BitmapFontGenerator generator;
    private GameManager gameManager;
    private Table mainTable;
    private Button skillButton;
    private Button upgradeButton;
    private Button mapButton;
    private Button achievButton;
    private Button optionButton;
    private Button passiveButton;
    private LargeMath largeMath;
    private AbstractMenu currentMenu;
    // Liste de tous les menus du jeu
    private ArrayList<AbstractMenu> activeMenuList;
    private PlayScreen playScreen;
    private Label floorLabel;
    public FpsActor fpsActor;
    private EnemyInformation enemyInformation;
    public Label battleNbLabel;
    public ImageButton ascendButton;

    public Hud(SpriteBatch sb, GameManager gameManager, PlayScreen playscreen) {
        largeMath = gameManager.largeMath;
        this.gameManager = gameManager;
        OrthographicCamera camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, camera);
        stage = new Stage(viewport, sb);
        generator = new com.guco.tap.utils.BitmapFontGenerator();
        font = AssetManager.INSTANCE.getFont();
        generator.dispose();
        font.setColor(Color.WHITE);
        this.playScreen = playscreen;
        initMenu();
        initButton();
        initHud();
    }

    /**
     * Initialise les menu
     */
    private void initMenu() {
        moduleMenu = new ModuleMenu(gameManager);
        equipMenu = new EquipMenu(gameManager);
        gameInformationMenu = new GameInformationMenu(gameManager);
        optionMenu = new OptionMenu(gameManager);
        achievementMenu = new AchievementMenu(gameManager);
        skillMenu = new SkillMenu(gameManager);

        activeMenuList = new ArrayList<AbstractMenu>();
        activeMenuList.add(moduleMenu);
        activeMenuList.add(achievementMenu);
        activeMenuList.add(equipMenu);
        activeMenuList.add(gameInformationMenu);
        activeMenuList.add(optionMenu);
        activeMenuList.add(skillMenu);
    }

    public void postInitMenu(){
        equipMenu.postInit();
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
        Texture upgradeButtonTextureUp = new Texture(Gdx.files.internal("icons/hud_b2.png"));
        Texture skillButtonTextureUp = new Texture(Gdx.files.internal("icons/hud_b1.png"));
        Texture achievButtonTextureUp = new Texture(Gdx.files.internal("icons/hud_b3.png"));
        Texture mapButtonTextureUp = new Texture(Gdx.files.internal("icons/hud_b4.png"));
        Texture upgradeButtonTextureDown = new Texture(Gdx.files.internal("icons/hud_b2_r.png"));
        Texture skillButtonTextureDown = new Texture(Gdx.files.internal("icons/hud_b1_r.png"));
        Texture achievButtonTextureDown = new Texture(Gdx.files.internal("icons/hud_b3_r.png"));
        Texture mapButtonTextureDown = new Texture(Gdx.files.internal("icons/hud_b4_r.png"));
        Texture passivButtonTextureup = new Texture(Gdx.files.internal("icons/hud_b5_r.png"));
        Texture passivButtonTextureDown = new Texture(Gdx.files.internal("icons/hud_b5.png"));
        Texture button6TextureUp = new Texture(Gdx.files.internal("icons/hud_b6.png"));
        Texture button6TextureDown = new Texture(Gdx.files.internal("icons/hud_b6_r.png"));
        Texture ascendButtonTextureUp = new Texture(Gdx.files.internal("icons/ascend.png"));
        Texture ascendButtonTextureDown = new Texture(Gdx.files.internal("icons/ascend_r.png"));

        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(skillButtonTextureUp));
        style.down = new TextureRegionDrawable(new TextureRegion(skillButtonTextureDown));
        upgradeButton = new ImageButton(style);
        ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        style2.up = new TextureRegionDrawable(new TextureRegion(upgradeButtonTextureUp));
        style2.down =  new TextureRegionDrawable(new TextureRegion(upgradeButtonTextureDown));
        skillButton = new ImageButton(style2);
        ImageButton.ImageButtonStyle style3 = new ImageButton.ImageButtonStyle();
        style3.up = new TextureRegionDrawable(new TextureRegion(achievButtonTextureUp));
        style3.down = new TextureRegionDrawable(new TextureRegion(achievButtonTextureDown));
        mapButton = new ImageButton(style3);
        ImageButton.ImageButtonStyle style4 = new ImageButton.ImageButtonStyle();
        style4.up = new TextureRegionDrawable(new TextureRegion(mapButtonTextureUp));
        style4.down =  new TextureRegionDrawable(new TextureRegion(mapButtonTextureDown));
        achievButton = new ImageButton(style4);
        ImageButton.ImageButtonStyle style5 = new ImageButton.ImageButtonStyle();
        style5.up = new TextureRegionDrawable(new TextureRegion(button6TextureUp));
        style5.down =  new TextureRegionDrawable(new TextureRegion(button6TextureDown));
        passiveButton = new ImageButton(style5);
        ImageButton.ImageButtonStyle style6 = new ImageButton.ImageButtonStyle();
        style6.up = new TextureRegionDrawable(new TextureRegion(passivButtonTextureDown));
        style6.down = new TextureRegionDrawable(new TextureRegion(passivButtonTextureup));
        optionButton = new ImageButton(style6);
        ImageButton.ImageButtonStyle style7 = new ImageButton.ImageButtonStyle();
        style7.up = new TextureRegionDrawable(new TextureRegion(ascendButtonTextureUp));
        style7.down = new TextureRegionDrawable(new TextureRegion(ascendButtonTextureDown));
        ascendButton = new ImageButton(style7);

        // Declaration des listener
        InputListener buttonListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(0));
                return true;
            }
        };
        upgradeButton.addListener(buttonListener);

        InputListener achievementListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(1));
                return true;
            }
        };
        skillButton.addListener(achievementListener);

        InputListener buttonListenerCredit = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(2));
                return true;
            }
        };
        mapButton.addListener(buttonListenerCredit);

        InputListener equipListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(3));
                return true;
            }
        };
        achievButton.addListener(equipListener);

        InputListener buttonListenerOption = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(4));
                return true;
            }
        };
        optionButton.addListener(buttonListenerOption);

        InputListener buttonListenerAscend = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.switchFloor();
                return true;
            }
        };
        ascendButton.addListener(buttonListenerAscend);

        InputListener buttonListenerSkill = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMenu(activeMenuList.get(5));
                return true;
            }
        };
        passiveButton.addListener(buttonListenerSkill);
    }

    /**
     * Initialise les informations du HUD et
     * ajoute les elemnts dans le stage
     */
    private void initHud() {
        versionLabel = new Label(Constants.CURRENT_VERSION, new Label.LabelStyle(font, Color.WHITE));
        versionLabel.setFontScale(0.5f);
        versionLabel.setWrap(true);
        scoreLabel = new Label(largeMath.getDisplayValue(GameInformation.INSTANCE.getCurrentGold(), GameInformation.INSTANCE.getCurrency()), new Label.LabelStyle(font, Color.WHITE));
        scoreLabel.setAlignment(Align.center);
        scoreLabel.setFontScale(2);
        floorLabel = new Label(String.valueOf(""+GameInformation.INSTANCE.getDepth()), new Label.LabelStyle(font, Color.WHITE));
        battleNbLabel = new Label(GameInformation.INSTANCE.currentEnemyIdx+"/10", AssetManager.INSTANCE.getSkin());
        goldDecreaseLabel = new Label("", new Label.LabelStyle(font, Color.RED));
        goldDecreaseLabel.setVisible(false);
        goldDecreaseLabel.setFontScale(2);
        goldDecreaseLabel.setAlignment(1);
        // Add an animated label when gold is decreased
        Stack stack = new Stack();
        stack.add(scoreLabel);
        stack.add(goldDecreaseLabel);

        Table tableTop = new Table();
        tableTop.add(optionButton).height(40).width(40).left();
        //tableTop.add(stack);
        //tableTop.row();
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.addActor(floorLabel);
        verticalGroup.addActor(battleNbLabel);
        tableTop.add(verticalGroup).expandX().left().padLeft(90);

        TextureRegionDrawable backgroundImg = new TextureRegionDrawable(new Texture(Gdx.files.internal("sprites/ui/brown.png")));
        backgroundImg.setMinWidth(Constants.V_WIDTH);
        backgroundImg.setMinHeight(40);
        tableTop.setBackground(backgroundImg);
//        tableTop.debug();

        mainTable = new Table();
        mainTable.top();
        mainTable.setFillParent(true);

        // Add menu to the stage
        for(int i=0;i<activeMenuList.size();i++) {
            mainTable.addActor(activeMenuList.get(i).getParentTable());
        }

        // ***** OTHER *****
        // Hp bar & name
        enemyInformation = new EnemyInformation();
        stage.addActor(enemyInformation);

        // Visual button to go upstairs
        ascendButton.setSize(80,80);
        ascendButton.setPosition(Constants.V_WIDTH/2 - ascendButton.getWidth()/2,350);
        ascendButton.setVisible(false);
        stage.addActor(ascendButton);

        // Add buttons to the stage
        Table menuButtonTable = new Table();
        menuButtonTable.addActor(upgradeButton);
        menuButtonTable.addActor(skillButton);
        menuButtonTable.addActor(mapButton);
        menuButtonTable.addActor(achievButton);

        menuButtonTable.add(upgradeButton).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).padLeft(3);
        menuButtonTable.add(skillButton).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).padLeft(3);
        menuButtonTable.add(achievButton).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).padLeft(3);
        menuButtonTable.add(mapButton).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).padLeft(3);
        menuButtonTable.add(passiveButton).bottom().height(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).width(Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT).padLeft(3);

        // Assemble hud
        mainTable.add(tableTop).top();
        mainTable.row();
        //mainTable.add(versionLabel).align(Align.right).bottom();
        //mainTable.row();
        mainTable.add(menuButtonTable).bottom().expandY().colspan(activeMenuList.size()-1);
        //mainTable.debug();

       stage.addActor(mainTable);

        // Optionnal element
        fpsActor = new FpsActor();
        fpsActor.setVisible(GameInformation.INSTANCE.isOptionFps());
        fpsActor.setPosition(Constants.V_WIDTH-fpsActor.getWidth(), Constants.V_HEIGHT-fpsActor.getHeight());
        fpsActor.setFontScale(1.5f);
        stage.addActor(fpsActor);

    }

    /**
     * Methode draw specifique
     */
    public void draw () {
        stage.draw();
        stage.act();
    }

    public void animateCritical() {
        scoreLabel.addAction(Actions.sequence(
                Actions.color(Constants.CRITICAL_LABEL_COLOR),
                Actions.color(Color.WHITE,0.5f)
        ));
    }

    public void animateDecreaseGold(ValueDTO valueDto) {
        String text = gameManager.largeMath.getDisplayValue(valueDto.getValue(), valueDto.getCurrency());
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
     * Modification du listener d'input en fonction de l'etat
     */
    private void toggleMenu(AbstractMenu menu) {
        // Masque tous les menu
        for (int i = 0; i < activeMenuList.size(); i++) {
            activeMenuList.get(i).getParentTable().setVisible(false);
            gameManager.currentState=GameState.IN_GAME;
        }

        // Affiche le menu concerné si non visible sinon le ferme
        //if (menu.equals(currentMenu)) {
        //    gameManager.currentState=GameState.IN_GAME;
        //    menu.getParentTable().setVisible(false);
        //    currentMenu = null;
        //} else {

        // Affiche le menu concerné si non visible sinon le ferme
        if (!menu.equals(currentMenu)) {
            menu.getParentTable().setPosition(menu.getParentTable().getX(), -menu.getParentTable().getHeight()); //Menu Animation
            gameManager.currentState=GameState.MENU;
            menu.getParentTable().addAction(Actions.moveTo(menu.getParentTable().getX(), Constants.PLAYSCREEN_MENU_BUTTON_HEIGHT,0.2f, Interpolation.exp5Out)); // Menu Animation
            menu.updateOnShow();
            menu.getParentTable().setVisible(true);
            currentMenu = menu;
        } else {
            currentMenu = null;
        }
    }

    /**
     * Update Menu while shown
     */
    public void update(){
        updateGoldLabel();
        updateCurrentMenu();
    }

    public void initEnemyInformation(EnemyActor enemyActor){
        floorLabel.setText("Floor "+GameInformation.INSTANCE.getDepth());
        enemyInformation.reinitialise(enemyActor);
    }

    public void updateEnemyInformation(float value){
        enemyInformation.decrease(value);
        //Gdx.app.debug("HP",String.valueOf(playScreen.enemyActorList.get(0).hp));
    }

    // Met a jour l'affichage de l'or
    public void updateGoldLabel(){
        String scoreAffichage = largeMath.getDisplayValue(GameInformation.INSTANCE.getCurrentGold(), GameInformation.INSTANCE.getCurrency());
        scoreLabel.setText(scoreAffichage);
    }

    // Met a jour l'affichage du menu actif
    public void updateCurrentMenu() {
        if (null != currentMenu) {
            currentMenu.update();
        }
    }
    public Stage getStage() {
        return stage;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public ModuleMenu getModuleMenu() {
        return moduleMenu;
    }

}
