package com.guco.tap.entity;

import com.badlogic.gdx.Gdx;
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

import static com.guco.tap.manager.AssetManager.INSTANCE;

/**
 * Represente un module dans le menu deroulant des modules
 * Created by Skronak on 21/03/2018.
 *
 */

public class SkillMenuElement extends Table {
    private GameManager gameManager;
    public TextButton skill1Button;
    public TextButton skill2Button;
    public TextButton skill3Button;
    private Image skillIcon;
    private ModuleElement moduleElementSource;

    public SkillMenuElement(GameManager gameManager){
        this.gameManager = gameManager;
    }

    /**
     * Methode d'initialise du module a partir de sa position
     * dans la liste de tous les modules.
     * @param i
     */
    public void initModuleMenuElement(int i) {
        moduleElementSource = AssetManager.INSTANCE.getModuleElementList().get(i);
        int currentLevel = GameInformation.INSTANCE.getUpgradeLevelList().get(i);

        skill1Button = new TextButton("",AssetManager.INSTANCE.getModuleMenuBuyTxtBtnStyle());
        skill1Button.addListener(new BuyUpgradeButtonListener(gameManager.moduleManager, i));

        skill2Button = new TextButton("",AssetManager.INSTANCE.getModuleMenuBuyTxtBtnStyle());
        skill2Button.addListener(new BuyUpgradeButtonListener(gameManager.moduleManager, i));

        skill3Button = new TextButton("",AssetManager.INSTANCE.getModuleMenuBuyTxtBtnStyle());
        skill3Button.addListener(new BuyUpgradeButtonListener(gameManager.moduleManager, i));

        if (currentLevel==0) {
//            Texture skillTexture = AssetManager.INSTANCE.getDisabledIcon();
            Texture skillTexture = new Texture(Gdx.files.internal("icons/skillLevel.png"));
            skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            skillIcon = new Image(skillTexture);
        } else {
//            Texture skillTexture = AssetManager.INSTANCE.getModuleDrawableUpList().get(i);
            Texture skillTexture = new Texture(Gdx.files.internal("icons/skillLevel.png"));
            skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            skillIcon = new Image(skillTexture);
        }

        this.add(skillIcon).height(45).width(80).padLeft(5).padRight(10);
        this.add(skill1Button).height(45).width(45).padRight(10).padLeft(10);
        this.add(skill2Button).height(45).width(45).padRight(10);
        this.add(skill3Button).height(45).width(45).padRight(10);
        update();
        this.debug();
    }

    public void update() {
        //skillIcon.setDrawable(new TextureRegionDrawable(new TextureRegion(INSTANCE.getModuleDrawableUpList().get(moduleElementSource.getId()))));
        //skillIcon.setSize(60,60);
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************
}
