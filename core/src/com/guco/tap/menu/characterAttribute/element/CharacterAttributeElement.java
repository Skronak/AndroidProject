package com.guco.tap.menu.characterAttribute.element;

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
import com.guco.tap.entity.AttributeElement;
import com.guco.tap.entity.AttributeElementLevel;
import com.guco.tap.input.BuyUpgradeButtonListener;
import com.guco.tap.manager.GameManager;


/**
 * Represente un module dans le menu deroulant des modules
 * Created by Skronak on 21/03/2018.
 *
 */

public class CharacterAttributeElement extends Table {
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
    private AttributeElement attributeElementSource;

    public CharacterAttributeElement(GameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * Methode d'initialise du module a partir de sa position
     * dans la liste de tous les modules.
     * @param i
     */
    public void initModuleMenuElement(int i) {
        attributeElementSource = gameManager.assetsManager.getAttributeElementList().get(i);
        int currentLevel = gameManager.gameInformation.attributeLevel.get(i);
//        AttributeElementLevel moduleLevel = attributeElementSource.getLevel().get(gameManager.gameInformation.attributeLevel.get(i));

        moduleLevelLabel = new Label("Level "+currentLevel, gameManager.assetsManager.getSkin());
        //moduleLevelImage = new Image(gameManager.attributeManager.getLevelTextureByLevel(i));
        elementTitle = new Label(attributeElementSource.getTitle(), gameManager.assetsManager.getSkin());
        activeGoldLabel = new Label("", gameManager.assetsManager.getSkin());
        activeGoldLabel.setFontScale(0.7f);
        passiveGoldLabel = new Label("", gameManager.assetsManager.getSkin());
        passiveGoldLabel.setFontScale(0.7f);
        nextActiveGoldLabel = new Label("", gameManager.assetsManager.getSkin());
        nextActiveGoldLabel.setFontScale(0.9f);
        nextPassiveGoldLabel= new Label("", gameManager.assetsManager.getSkin());
        nextPassiveGoldLabel.setFontScale(0.9f);

        buyButton = new TextButton("",gameManager.assetsManager.getModuleMenuBuyTxtBtnStyle());
        buyButton.addListener(new BuyUpgradeButtonListener(gameManager.attributeManager, i));

        if (currentLevel==0) {
            Texture skillTexture = gameManager.assetsManager.getDisabledIcon();
            skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            skillIcon = new Image(skillTexture);
        } else {
            Texture skillTexture = gameManager.assetsManager.getModuleDrawableUpList().get(i);
            skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            skillIcon = new Image(skillTexture);
        }
        goldIcon = new Image(gameManager.assetsManager.getGoldIcon());

        // Liste level actuel du module
        Table moduleLevelGroup = new Table();
        moduleLevelGroup.add(moduleLevelLabel).colspan(2).left().expandX().top().padBottom(10);
        moduleLevelGroup.row();
        moduleLevelGroup.add(activeGoldLabel).left();
        moduleLevelGroup.add(nextActiveGoldLabel);
        moduleLevelGroup.row();
        moduleLevelGroup.add(passiveGoldLabel).left();
        moduleLevelGroup.add(nextPassiveGoldLabel);

        this.setHeight(30);
        this.add(skillIcon).width(80).height(80).padLeft(10);
        this.add(moduleLevelGroup).width(140);
        this.add(buyButton).height(80).width(70).padRight(10);

        update();
    }

    public void update() {
        int attributeLevelId = gameManager.gameInformation.attributeLevel.get(attributeElementSource.getId());
        AttributeElementLevel moduleLevel = attributeElementSource.getLevel().get(attributeLevelId);
        AttributeElementLevel moduleNextLevel=null;
        if (gameManager.gameInformation.attributeLevel.get(attributeElementSource.getId())< attributeElementSource.getLevel().size()-1) {
            moduleNextLevel = attributeElementSource.getLevel().get(gameManager.gameInformation.attributeLevel.get(attributeElementSource.getId()) + 1);
        } else {
            buyButton.setTouchable(Touchable.disabled);
            buyButton.setColor(Color.GRAY);
        }
        moduleLevelLabel.setText("Level "+gameManager.gameInformation.attributeLevel.get(attributeElementSource.getId()));
        //moduleLevelImage.setDrawable(new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.getUpgradeLvlImageList().get(gameManager.gameInformation.attributeLevel.get(attributeElementSource.getId())))));
        skillIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(gameManager.assetsManager.getModuleDrawableUpList().get(attributeElementSource.getId()))));
        skillIcon.setSize(60,60);
        activeGoldLabel.setText(ACTIVE_GOLD_LABEL+PLUS_GOLD_LABEL+gameManager.largeMath.getDisplayValue(moduleLevel.getActGen().value, moduleLevel.getActGen().currency));
        passiveGoldLabel.setText(PASSIVE_GOLD_LABEL+PLUS_GOLD_LABEL+gameManager.largeMath.getDisplayValue(moduleLevel.getPassGen().value, moduleLevel.getPassGen().currency));
        nextActiveGoldLabel.setText(PLUS_GOLD_LABEL +(null!=moduleNextLevel?gameManager.largeMath.getDisplayValue(moduleNextLevel.getActGen().value, moduleNextLevel.getActGen().currency):"max"));
        nextPassiveGoldLabel.setText(PLUS_GOLD_LABEL +(null!=moduleNextLevel?gameManager.largeMath.getDisplayValue(moduleNextLevel.getPassGen().value, moduleNextLevel.getPassGen().currency):"max"));
        buyButton.setText(gameManager.largeMath.getDisplayValue(moduleLevel.getCost().value, moduleLevel.getCost().currency));
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
