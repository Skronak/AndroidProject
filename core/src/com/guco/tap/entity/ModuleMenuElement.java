package com.guco.tap.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.input.BuyUpgradeButtonListener;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;


/**
 * Represente un module dans le menu deroulant des modules
 * Created by Skronak on 21/03/2018.
 *
 */

public class ModuleMenuElement extends Table {
    private GameManager gameManager;
    private Label moduleLevelLabel;
    private String PASSIVE_GOLD_LABEL = "Passive Gold: ";
    private String ACTIVE_GOLD_LABEL = "Active Gold: ";
    private String PLUS_GOLD_LABEL =" + ";
    private TextButton buyButton;
    private Image moduleLevelImage;
    private Label elementTitle;
    private Label activeGoldLabel;
    private Label passiveGoldLabel;
    private Label nextActiveGoldLabel;
    private Label nextPassiveGoldLabel;
    private Image skillIcon;
    private Image goldIcon;
    private ModuleElement moduleElementSource;

    public ModuleMenuElement(GameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * Methode d'initialise du module a partir de sa position
     * dans la liste de tous les modules.
     * @param i
     */
    public void initModuleMenuElement(int i) {
        moduleElementSource = gameManager.assetManager.getModuleElementList().get(i);
        int currentLevel = gameManager.gameInformation.getUpgradeLevelList().get(i);
        ModuleElementLevel moduleLevel = moduleElementSource.getLevel().get(gameManager.gameInformation.getUpgradeLevelList().get(i));

        moduleLevelLabel = new Label("Level "+currentLevel, gameManager.assetManager.getSkin());
        moduleLevelImage = new Image(gameManager.moduleManager.getLevelTextureByLevel(i));
        elementTitle = new Label(moduleElementSource.getTitle(), gameManager.assetManager.getSkin());
        activeGoldLabel = new Label("", gameManager.assetManager.getSkin());
        activeGoldLabel.setFontScale(0.7f);
        passiveGoldLabel = new Label("", gameManager.assetManager.getSkin());
        passiveGoldLabel.setFontScale(0.7f);
        nextActiveGoldLabel = new Label("", gameManager.assetManager.getSkin());
        nextActiveGoldLabel.setFontScale(0.9f);
        nextPassiveGoldLabel= new Label("", gameManager.assetManager.getSkin());
        nextPassiveGoldLabel.setFontScale(0.9f);

        buyButton = new TextButton("",gameManager.assetManager.getModuleMenuBuyTxtBtnStyle());
        buyButton.addListener(new BuyUpgradeButtonListener(gameManager.moduleManager, i));

        if (currentLevel==0) {
            Texture skillTexture = gameManager.assetManager.getDisabledIcon();
            skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            skillIcon = new Image(skillTexture);
        } else {
            Texture skillTexture = gameManager.assetManager.getModuleDrawableUpList().get(i);
            skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            skillIcon = new Image(skillTexture);
        }
        goldIcon = new Image(gameManager.assetManager.getGoldIcon());

        // Liste level actuel du module
        Table moduleLevelGroup = new Table();
        moduleLevelGroup.add(moduleLevelLabel).colspan(2).left().expandX().top().padBottom(10);
        moduleLevelGroup.row();
//        moduleLevelGroup.add(moduleLevelImage).size(moduleElementSource.getWidth(), moduleElementSource .getHeight()).left().colspan(4);
//        moduleLevelGroup.row();
//        moduleLevelGroup.add(goldIcon).size(20,20).left();
        moduleLevelGroup.add(activeGoldLabel).left();
        moduleLevelGroup.add(nextActiveGoldLabel);
        moduleLevelGroup.row();
//        moduleLevelGroup.add(timeIcon).size(20,20).left();
        moduleLevelGroup.add(passiveGoldLabel).left();
        moduleLevelGroup.add(nextPassiveGoldLabel);
        //moduleLevelGroup.add(buyButton).height(30).width(140);

        this.setHeight(30);
        this.add(skillIcon).width(80).height(80).padLeft(10);
        this.add(moduleLevelGroup).width(140);
        this.add(buyButton).height(80).width(70).padRight(10);

        update();
    }

    public void update() {
        ModuleElementLevel moduleLevel = moduleElementSource.getLevel().get(gameManager.gameInformation.getUpgradeLevelList().get(moduleElementSource.getId()));
        ModuleElementLevel moduleNextLevel=null;
        if (gameManager.gameInformation.getUpgradeLevelList().get(moduleElementSource.getId())<moduleElementSource.getLevel().size()-1) {
            moduleNextLevel = moduleElementSource.getLevel().get(gameManager.gameInformation.getUpgradeLevelList().get(moduleElementSource.getId()) + 1);
        } else {
            buyButton.setTouchable(Touchable.disabled);
            buyButton.setColor(Color.GRAY);
        }
        moduleLevelLabel.setText("Level "+gameManager.gameInformation.getUpgradeLevelList().get(moduleElementSource.getId()));
        moduleLevelImage.setDrawable(new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.getUpgradeLvlImageList().get(gameManager.gameInformation.getUpgradeLevelList().get(moduleElementSource.getId())))));
        skillIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.getModuleDrawableUpList().get(moduleElementSource.getId()))));
        skillIcon.setSize(60,60);
        activeGoldLabel.setText(ACTIVE_GOLD_LABEL+PLUS_GOLD_LABEL+gameManager.largeMath.getDisplayValue(moduleLevel.getActGen().getValue(), moduleLevel.getActGen().getCurrency()));
        passiveGoldLabel.setText(PASSIVE_GOLD_LABEL+PLUS_GOLD_LABEL+gameManager.largeMath.getDisplayValue(moduleLevel.getPassGen().getValue(), moduleLevel.getPassGen().getCurrency()));
        nextActiveGoldLabel.setText(PLUS_GOLD_LABEL +(null!=moduleNextLevel?gameManager.largeMath.getDisplayValue(moduleNextLevel.getActGen().getValue(), moduleNextLevel.getActGen().getCurrency()):"max"));
        nextPassiveGoldLabel.setText(PLUS_GOLD_LABEL +(null!=moduleNextLevel?gameManager.largeMath.getDisplayValue(moduleNextLevel.getPassGen().getValue(), moduleNextLevel.getPassGen().getCurrency()):"max"));
        buyButton.setText(gameManager.largeMath.getDisplayValue(moduleLevel.getCost().getValue(), moduleLevel.getCost().getCurrency()));
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************
    public Label getModuleLevelLabel() {
        return moduleLevelLabel;
    }

    public void setModuleLevelLabel(Label moduleLevelLabel) {
        this.moduleLevelLabel = moduleLevelLabel;
    }

    public TextButton getBuyButton() {
        return buyButton;
    }

    public void setBuyButton(TextButton buyButton) {
        this.buyButton = buyButton;
    }

    public Image getModuleLevelImage() {
        return moduleLevelImage;
    }

    public void setModuleLevelImage(Image moduleLevelImage) {
        this.moduleLevelImage = moduleLevelImage;
    }

    public Label getElementTitle() {
        return elementTitle;
    }

    public void setElementTitle(Label elementTitle) {
        this.elementTitle = elementTitle;
    }

    public Label getActiveGoldLabel() {
        return activeGoldLabel;
    }

    public void setActiveGoldLabel(Label activeGoldLabel) {
        this.activeGoldLabel = activeGoldLabel;
    }

    public Label getPassiveGoldLabel() {
        return passiveGoldLabel;
    }

    public void setPassiveGoldLabel(Label passiveGoldLabel) {
        this.passiveGoldLabel = passiveGoldLabel;
    }

    public Image getSkillIcon() {
        return skillIcon;
    }

    public Image getGoldIcon() {
        return goldIcon;
    }

    public Label getNextActiveGoldLabel() {
        return nextActiveGoldLabel;
    }

    public void setNextActiveGoldLabel(Label nextActiveGoldLabel) {
        this.nextActiveGoldLabel = nextActiveGoldLabel;
    }

    public Label getNextPassiveGoldLabel() {
        return nextPassiveGoldLabel;
    }

    public void setNextPassiveGoldLabel(Label nextPassiveGoldLabel) {
        this.nextPassiveGoldLabel = nextPassiveGoldLabel;
    }
}
