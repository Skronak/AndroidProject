package com.guco.tap.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.guco.tap.input.SkillSelectButtonListener;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.ItemUpgradeMenu;

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
    private ItemUpgradeMenu itemUpgradeMenu;
    private ItemEntity itemSource;

    public SkillMenuElement(GameManager gameManager, ItemUpgradeMenu itemUpgradeMenu, ItemEntity itemEntity){
        this.gameManager = gameManager;
        this.itemUpgradeMenu = itemUpgradeMenu;
        this.itemSource = itemEntity;
    }

    /**
     * Methode d'initialise du module a partir de sa position
     * dans la liste de tous les modules.
     * @param i
     */
    public void initSkillMenuElement(int i) {
        moduleElementSource = gameManager.assetManager.getModuleElementList().get(i);
        int currentLevel = gameManager.gameInformation.moduleLevelList.get(i);

        skill1Button = new TextButton("",gameManager.assetManager.getModuleMenuBuyTxtBtnStyle());
        skill1Button.addListener(new SkillSelectButtonListener(this, itemUpgradeMenu, itemSource, itemSource.upgrades.firstTier.get(i)));

       /* skill2Button = new TextButton("",gameManager.assetManager.getModuleMenuBuyTxtBtnStyle());
        skill2Button.addListener(new SkillSelectButtonListener(this, itemUpgradeMenu, itemSource, itemSource.upgrades.get(i)));

        skill3Button = new TextButton("",gameManager.assetManager.getModuleMenuBuyTxtBtnStyle());
        skill3Button.addListener(new SkillSelectButtonListener(this, itemUpgradeMenu, itemSource, itemSource.upgrades.get(i)));
*/
        Texture skillTexture = new Texture(Gdx.files.internal("sprites/icon/skillLevel"+i+".png"));
        skillTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        skillIcon = new Image(skillTexture);

        this.add(skillIcon).height(45).width(80).padLeft(5).padRight(10);
        this.add(skill1Button).height(45).width(45).padRight(10).padLeft(10);
        //this.add(skill2Button).height(45).width(45).padRight(10);
        //this.add(skill3Button).height(45).width(45).padRight(10);
        update();
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
