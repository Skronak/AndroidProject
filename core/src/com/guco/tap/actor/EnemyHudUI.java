package com.guco.tap.actor;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.Constants;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

/**
 * TODO a revoir pour prendre en compte
 * ValueDTO previousLife? 
 */
public class EnemyHudUI extends Group {
    private Image redImage;
    private Image orangeImage;
    private Image greyImage;
    private Image difficulty;
    private Label nameLabel;
    private Label healthLabel;
    private ValueDTO maxHpValue;
    private int maxHpCurrency;
    private float BAR_WIDTH=170;
    private float BAR_HEIGHT=15;
    private float percent;
    private SpriterEnemyActor targetActor;
    private LargeMath largeMath;

    public EnemyHudUI(GameManager gameManager) {
        nameLabel = new Label("default", gameManager.assetsManager.getSkin());
        healthLabel = new Label("", gameManager.assetsManager.getSkin());
        largeMath = gameManager.largeMath;
        redImage = new Image(gameManager.assetsManager.redTexture);
        orangeImage = new Image(gameManager.assetsManager.orangeTexture);
        greyImage = new Image(gameManager.assetsManager.greyTexture);
        difficulty = new Image(gameManager.assetsManager.diffTexture3);
        nameLabel.setPosition(0,BAR_HEIGHT);
        orangeImage.setSize(BAR_WIDTH,BAR_HEIGHT);
        redImage.setSize(BAR_WIDTH,BAR_HEIGHT);
        greyImage.setSize(BAR_WIDTH+6, redImage.getHeight()+6);
        greyImage.setPosition(-3, -3);
        difficulty.setSize(20,20);
        difficulty.setPosition(orangeImage.getX()+orangeImage.getWidth()+10, orangeImage.getY());
        nameLabel.setPosition(greyImage.getWidth()/2-nameLabel.getWidth()/2,BAR_HEIGHT);
        healthLabel.setPosition(greyImage.getWidth()/2-nameLabel.getWidth()/2,BAR_HEIGHT/2);
        this.addActor(nameLabel);
        this.addActor(greyImage);
        this.addActor(orangeImage);
        this.addActor(redImage);
        this.addActor(healthLabel);
        this.addActor(difficulty);

        this.setPosition(Constants.V_WIDTH/2-BAR_WIDTH/2, 450);
    }

    public void init(SpriterEnemyActor sourceActor) {
        this.targetActor = sourceActor;
        this.maxHpValue = sourceActor.lifePoint;
        redImage.setWidth(BAR_WIDTH);
        nameLabel.setText(targetActor.name);

        updateDamagelabel();
    }

    public void updateLifeBar(ValueDTO damage) {
        orangeImage.clear();
        orangeImage.getColor().a=1f;
        orangeImage.setSize(redImage.getWidth(),redImage.getHeight());

        percent = ((damage.value * BAR_WIDTH)/ maxHpValue.value);
        if (targetActor.lifePoint.value<=0|| percent <0 || percent>redImage.getWidth()) {
            percent = redImage.getWidth();
        }
        redImage.setWidth(redImage.getWidth()-percent);
        orangeImage.addAction(Actions.fadeOut(1f));

        updateDamagelabel();
    }

    private void updateDamagelabel(){
        if(targetActor.lifePoint.value<=0){
            targetActor.lifePoint.value=0;
        }
        String damageLabel = largeMath.getDisplayValue(targetActor.lifePoint) +"/"+largeMath.getDisplayValue(maxHpValue);
        healthLabel.setText(damageLabel);
    }
}
