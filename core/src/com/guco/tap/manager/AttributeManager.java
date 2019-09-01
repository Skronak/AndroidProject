package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.guco.tap.entity.AttributeElement;
import com.guco.tap.entity.AttributeElementLevel;
import com.guco.tap.menu.characterAttribute.CharacterAttributeMenu;
import com.guco.tap.menu.characterAttribute.element.CharacterAttributeElement;
import com.guco.tap.utils.ValueDTO;

import java.util.List;

/**
 * Created by Skronak on 14/08/2017.
 * Classe gerant les upgrades et l'ecran d'upgrade
 */
public class AttributeManager {

    private GameManager gameManager;
    private CharacterAttributeMenu characterAttributeMenu;
    private List<AttributeElement> attributeEntityList;
    private AssetsManager assetsManager;

    public AttributeManager(GameManager gameManager) {
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");
        this.gameManager = gameManager;
        this.attributeEntityList = gameManager.assetsManager.getAttributeElementList();
    }

    public void initialize(CharacterAttributeMenu characterAttributeMenu) {
        this.characterAttributeMenu = characterAttributeMenu;
        evaluateAttributeGeneration();
    }

    /**
     * Calculate incomes according to unlocked upgrade
     * du niveau des updates
     */
    public void evaluateAttributeGeneration(){
        float passGoldGen = 0;
        int passCurrGen = 0;
        float actGoldGen = 0;
        int actCurrGen = 0;
        float goldGenToAdd = 0;
        int currGenToAdd = 0;
        // Total a additionner
        ValueDTO passGenSum = new ValueDTO(0,0);
        ValueDTO actGenSum = new ValueDTO(0,0);
        int upgradeLevel=0;

        // Parcours la liste upgrades du joueur
        for (int i = 0; i<gameManager.gameInformation.attributeLevel.size(); i++) {
            if (gameManager.gameInformation.attributeLevel.get(i) > 0) {
                upgradeLevel = gameManager.gameInformation.attributeLevel.get(i);

                //Passive generation
                passGoldGen = passGenSum.value;
                passCurrGen = passGenSum.currency;

                goldGenToAdd = attributeEntityList.get(i).getLevel().get(upgradeLevel).getPassGen().value;
                currGenToAdd = attributeEntityList.get(i).getLevel().get(upgradeLevel).getPassGen().currency;
                passGenSum = gameManager.largeMath.increaseValue(passGoldGen ,passCurrGen ,goldGenToAdd,currGenToAdd);

                //Active generation
                actGoldGen = actGenSum.value;
                actCurrGen = actGenSum.currency;
                goldGenToAdd = attributeEntityList.get(i).getLevel().get(upgradeLevel).getActGen().value;
                currGenToAdd = attributeEntityList.get(i).getLevel().get(upgradeLevel).getActGen().currency;
                actGenSum = gameManager.largeMath.increaseValue(actGoldGen,actCurrGen ,goldGenToAdd,currGenToAdd);

            }
        }
        gameManager.gameInformation.passivGoldValue =passGenSum.value;
        gameManager.gameInformation.passivGoldCurrency =passGenSum.currency;
        gameManager.gameInformation.tapDamageValue =actGenSum.value;
        gameManager.gameInformation.tapDamageCurrency =actGenSum.currency;
    }

    /**
     * Compare current gold with a module cost
     *
     * @param
     * @return true if current gold > module cost
     * @return false if current gold < module cost
     */
    public boolean isAvailableUpgrade (int idSelect) {
        int currentlevel = gameManager.gameInformation.attributeLevel.get(idSelect);
        AttributeElementLevel attributeLevelCurrent = attributeEntityList.get(idSelect).getLevel().get(currentlevel);

        if ( (attributeEntityList.get(idSelect).getLevel().size() > currentlevel + 1)
                && ( (gameManager.gameInformation.currentGoldCurrency > attributeLevelCurrent.getCost().currency
                    || (gameManager.gameInformation.currentGoldCurrency == attributeLevelCurrent.getCost().currency
                        && (gameManager.gameInformation.currentGoldValue >= attributeLevelCurrent.getCost().value)
                        ))))
        {
            return true;
        } else {
            return false;
        }
    }

    public void getModuleElementById(){
    }

    /**
     * Return level texture depending on module element level
      * @param level
     * @return
     */
    public Texture getLevelTextureByLevel(int level) {
        Texture levelTexture = gameManager.assetsManager.getUpgradeLvlImageList().get(gameManager.gameInformation.attributeLevel.get(level));
        if (null==levelTexture) {
            levelTexture = gameManager.assetsManager.getUpgradeLvlImageList().get(0);
        }
        return levelTexture;
    }

    public void getModuleElementByLevel(){

    }

    /**
     * Increase a module level
     * @param idSelect
     */
    public void increaseAttributeLevel(int idSelect) {
        // Calcul et Affichage de la soustraction
        ValueDTO decreaseValue = attributeEntityList.get(idSelect).getLevel().get(gameManager.gameInformation.attributeLevel.get(idSelect)).getCost();
        gameManager.playScreen.getHud().animateDecreaseGold(decreaseValue);

        // Mise a jour du montant des golds du joueur
        ValueDTO gameInformationValue = gameManager.largeMath.decreaseValue(gameManager.gameInformation.currentGoldValue,gameManager.gameInformation.currentGoldCurrency,decreaseValue.value, decreaseValue.currency);
        gameManager.gameInformation.currentGoldValue =gameInformationValue .value;
        gameManager.gameInformation.currentGoldCurrency =gameInformationValue .currency;

        // Met a jour le gameInformation
        gameManager.gameInformation.attributeLevel.set(idSelect, gameManager.gameInformation.attributeLevel.get(idSelect) + 1);
        this.evaluateAttributeGeneration();

        // Ajoute l'element dans la station
        if (gameManager.gameInformation.attributeLevel.get(idSelect)==1){
            gameManager.newModuleIdList.add(idSelect);
        }
     }

     public void animateNewModule() {

     }

    /**
     * Update upgrade information from the menu
     * @param id
     */
    public void updateAttributeMenuInformation(int id) {
        CharacterAttributeElement characterAttributeElement = (CharacterAttributeElement) characterAttributeMenu.getScrollContainerVG().getChildren().get(id);
        characterAttributeElement.update();
        animateLabel(characterAttributeElement.getActiveGoldLabel());
        animateLabel(characterAttributeElement.getPassiveGoldLabel());
    }

    public void animateLabel(Label label) {
        label.clearActions();
        label.addAction(Actions.sequence(Actions.color(Color.BLUE,0.5f), Actions.color(Color.WHITE)));
    }

//*****************************************************
//                  GETTER & SETTER
// ****************************************************
    public GameManager getGameManager() {
        return gameManager;
    }

    public void gameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}
