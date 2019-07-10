package com.guco.tap.object;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.guco.tap.actor.EnemyActor;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.Constants;

/**
 *
 */
public class EnemyInformation extends Group {
    Image redImage;
    Image orangeImage;
    Image greyImage;
    Image crossImage;
    Image difficulty;
    Label nameLabel;
    Label healthLabel;
    int maxHp;
    float BAR_WIDTH=170;
    float BAR_HEIGHT=15;
    float percent;
    EnemyActor targetActor;

    public EnemyInformation(GameManager gameManager) {
        nameLabel = new Label("default", gameManager.ressourceManager.getSkin());
        healthLabel = new Label("", gameManager.ressourceManager.getSkin());

        redImage = new Image(gameManager.ressourceManager.redTexture);
        orangeImage = new Image(gameManager.ressourceManager.orangeTexture);
        greyImage = new Image(gameManager.ressourceManager.greyTexture);
        //crossImage = new Image(gameManager.ressourceManager.crossTexture);
        difficulty = new Image(gameManager.ressourceManager.diffTexture3);
        nameLabel.setPosition(0,BAR_HEIGHT);
        //crossImage.setSize(10, BAR_HEIGHT);
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
        //this.addActor(crossImage);
        this.addActor(redImage);
        this.addActor(healthLabel);
        this.addActor(difficulty);

        this.setPosition(Constants.V_WIDTH/2-BAR_WIDTH/2, 470);
    }

    public void reinitialise(EnemyActor enemyActor) {
        this.targetActor = enemyActor;
        this.maxHp = enemyActor.hp;
        redImage.setWidth(BAR_WIDTH);
        nameLabel.setText(targetActor.name);
        healthLabel.setText(String.valueOf(maxHp+"/"+maxHp));
    }

    public void decrease(float value){
        orangeImage.clear();
        orangeImage.getColor().a=1f;
        orangeImage.setSize(redImage.getWidth(),redImage.getHeight());
        percent = ((value * BAR_WIDTH)/ maxHp);
        if (percent <0 || percent>redImage.getWidth()) percent=redImage.getWidth();
        redImage.setWidth(redImage.getWidth()-percent);
        orangeImage.addAction(Actions.fadeOut(1f));
        healthLabel.setText(String.valueOf(targetActor.hp+"/"+maxHp));
        //crossImage.setPosition(redImage.getX()+redImage.getWidth(),redImage.getY());
    }
}
