package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.ModuleElement;
import com.guco.tap.entity.ModuleElementLevel;
import com.guco.tap.menu.ModuleMenu;
import com.guco.tap.entity.ModuleMenuElement;
import com.guco.tap.utils.ValueDTO;

import java.util.List;

/**
 * Created by Skronak on 14/08/2017.
 * Classe gerant les upgrades et l'ecran d'upgrade
 */
public class ModuleManager {

    private GameManager gameManager;
    private ModuleMenu moduleMenu;
    private List<ModuleElement> moduleEntityList;
    

    public ModuleManager(GameManager gameManager) {
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");

        this.moduleMenu = moduleMenu;
        this.gameManager = gameManager;
        this.moduleEntityList = gameManager.assetManager.getModuleElementList();
    }

    public void initialize(ModuleMenu moduleMenu){
        this.moduleMenu=moduleMenu;
        evaluateModuleGeneration();
    }

    /**
     * Calculate incomes according to unlocked upgrade
     * du niveau des updates
     */
    public void evaluateModuleGeneration(){
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
        for (int i=0;i<gameManager.gameInformation.getUpgradeLevelList().size();i++) {
            if (gameManager.gameInformation.getUpgradeLevelList().get(i) > 0) {
                upgradeLevel = gameManager.gameInformation.getUpgradeLevelList().get(i);

                //Passive generation
                passGoldGen = passGenSum.getValue();
                passCurrGen = passGenSum.getCurrency();

                goldGenToAdd = moduleEntityList.get(i).getLevel().get(upgradeLevel).getPassGen().getValue();
                currGenToAdd = moduleEntityList.get(i).getLevel().get(upgradeLevel).getPassGen().getCurrency();
                passGenSum = gameManager.largeMath.increaseValue(passGoldGen ,passCurrGen ,goldGenToAdd,currGenToAdd);

                //Active generation
                actGoldGen = actGenSum.getValue();
                actCurrGen = actGenSum.getCurrency();
                goldGenToAdd = moduleEntityList.get(i).getLevel().get(upgradeLevel).getActGen().getValue();
                currGenToAdd = moduleEntityList.get(i).getLevel().get(upgradeLevel).getActGen().getCurrency();
                actGenSum = gameManager.largeMath.increaseValue(actGoldGen,actCurrGen ,goldGenToAdd,currGenToAdd);

            }
        }
        gameManager.gameInformation.setGenGoldPassive(passGenSum.getValue());
        gameManager.gameInformation.setGenCurrencyPassive(passGenSum.getCurrency());
        gameManager.gameInformation.setGenGoldActive(actGenSum.getValue());
        gameManager.gameInformation.setGenCurrencyActive(actGenSum.getCurrency());
    }

    /**
     * Compare current gold with a module cost
     *
     * @param
     * @return true if current gold > module cost
     * @return false if current gold < module cost
     */
    public boolean isAvailableUpgrade (int idSelect) {
        int currentlevel = gameManager.gameInformation.getUpgradeLevelList().get(idSelect);
        ModuleElementLevel moduleLevelCurrent = moduleEntityList.get(idSelect).getLevel().get(currentlevel);

        if ( (moduleEntityList.get(idSelect).getLevel().size() > currentlevel + 1)
                && ( (gameManager.gameInformation.getCurrency() > moduleLevelCurrent.getCost().getCurrency()
                    || (gameManager.gameInformation.getCurrency() == moduleLevelCurrent.getCost().getCurrency()
                        && (gameManager.gameInformation.getCurrentGold() >= moduleLevelCurrent.getCost().getValue())
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
        Texture levelTexture = gameManager.assetManager.getUpgradeLvlImageList().get(gameManager.gameInformation.getUpgradeLevelList().get(level));
        if (null==levelTexture) {
            levelTexture = gameManager.assetManager.getUpgradeLvlImageList().get(0);
        }
        return levelTexture;
    }

    public void getModuleElementByLevel(){

    }

    /**
     * Increase a module level
     * @param idSelect
     */
    public void increaseModuleLevel(int idSelect) {
        // Calcul et Affichage de la soustraction
        ValueDTO decreaseValue = moduleEntityList.get(idSelect).getLevel().get(gameManager.gameInformation.getUpgradeLevelList().get(idSelect)).getCost();
        gameManager.playScreen.getHud().animateDecreaseGold(decreaseValue);

        // Mise a jour du montant des golds du joueur
        ValueDTO gameInformationValue = gameManager.largeMath.decreaseValue(gameManager.gameInformation.getCurrentGold(),gameManager.gameInformation.getCurrency(),decreaseValue.getValue(), decreaseValue.getCurrency());
        gameManager.gameInformation.setCurrentGold(gameInformationValue .getValue());
        gameManager.gameInformation.setCurrency(gameInformationValue .getCurrency());

        // Met a jour le gameInformation
        gameManager.gameInformation.getUpgradeLevelList().set(idSelect, gameManager.gameInformation.getUpgradeLevelList().get(idSelect) + 1);
        this.evaluateModuleGeneration();

        // Ajoute l'element dans la station
        if (gameManager.gameInformation.getUpgradeLevelList().get(idSelect)==1){
            gameManager.newModuleIdList.add(idSelect);
        }
     }

     public void animateNewModule() {

     }

    /**
     * Update upgrade information from the menu
     * @param id
     */
    public void updateModuleMenuInformation(int id) {
        ModuleMenuElement moduleMenuElement = (ModuleMenuElement) moduleMenu.getScrollContainerVG().getChildren().get(id);
        moduleMenuElement.update();
        animateLabel(moduleMenuElement.getActiveGoldLabel());
        animateLabel(moduleMenuElement.getPassiveGoldLabel());
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

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}
