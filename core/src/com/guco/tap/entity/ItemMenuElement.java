package com.guco.tap.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.input.BuyUpgradeButtonListener;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;

import static com.guco.tap.manager.AssetManager.INSTANCE;

/**
 * Represente un module dans le menu deroulant des modules
 * Created by Skronak on 21/03/2018.
 *
 */

public class ItemMenuElement extends Table {
    private GameManager gameManager;
    private Label itemNameLabel;
    private String PASSIVE_GOLD_LABEL = "Gold: ";
    private String ACTIVE_GOLD_LABEL = "Damage: ";
    private String PLUS_GOLD_LABEL =" + ";
    private TextButton equipButton;
    private TextButton upgradeButton;
    private Label activeGoldLabel;
    private Label passiveGoldLabel;
    private Label nextActiveGoldLabel;
    private Label nextPassiveGoldLabel;
    private Image skillIcon;
    private ItemEntity itemEntitySource;

    public ItemMenuElement(GameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * Methode d'initialise du module a partir de sa position
     * dans la liste de tous les modules.
     * @param i
     */
    public void initModuleMenuElement(int i) {
        itemEntitySource = AssetManager.INSTANCE.getItemList().get(i);
        int currentLevel = GameInformation.INSTANCE.getUpgradeLevelList().get(i);
        itemNameLabel = new Label("", AssetManager.INSTANCE.getSkin());
        activeGoldLabel = new Label("", AssetManager.INSTANCE.getSkin());
        activeGoldLabel.setFontScale(0.7f);
        passiveGoldLabel = new Label("", AssetManager.INSTANCE.getSkin());
        passiveGoldLabel.setFontScale(0.7f);
        nextActiveGoldLabel = new Label("", AssetManager.INSTANCE.getSkin());
        nextActiveGoldLabel.setFontScale(0.9f);
        nextPassiveGoldLabel= new Label("", AssetManager.INSTANCE.getSkin());
        nextPassiveGoldLabel.setFontScale(0.9f);

        upgradeButton = new TextButton("",AssetManager.INSTANCE.getModuleMenuUpgradeTxtBtnStyle());
        upgradeButton.addListener(new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameManager.playScreen.getHud().showUpgradeMenu();
                return true;
            }});

        if (currentLevel==0) {
            Texture skillTexture = AssetManager.INSTANCE.getDisabledIcon();
            skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            skillIcon = new Image(skillTexture);
        } else {
            Texture skillTexture = AssetManager.INSTANCE.getModuleDrawableUpList().get(i);
            skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            skillIcon = new Image(skillTexture);
        }

        // Liste level actuel du module
        Table moduleLevelGroup = new Table();
        moduleLevelGroup.add(itemNameLabel).top();
        moduleLevelGroup.row();
        moduleLevelGroup.add(activeGoldLabel).left();
        moduleLevelGroup.add(nextActiveGoldLabel);
        moduleLevelGroup.row();
//        moduleLevelGroup.add(passiveGoldLabel).left();
//        moduleLevelGroup.add(nextPassiveGoldLabel);
        moduleLevelGroup.add(upgradeButton).height(40).width(40).right();

        this.add(skillIcon).width(40).height(80).expandX();
        this.add(moduleLevelGroup).width(140).left();
        update();

    }

    public void update() {
        itemNameLabel.setText(itemEntitySource.name+"Lv: "+itemEntitySource.level);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

}
