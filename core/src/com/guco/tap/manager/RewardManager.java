package com.guco.tap.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.guco.tap.Animation.AnimatedBaseActor;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.screen.PlayScreen;
import com.guco.tap.utils.Constants;

import static com.badlogic.gdx.Gdx.files;

/**
 * RewardManager
 */
public class RewardManager {
    PlayScreen playScreen;
    AnimatedBaseActor restRewardActor;
    boolean rewardToCollect;
    GameManager gameManager;

    public RewardManager(PlayScreen playScreen, GameManager gameManager){
        Gdx.app.debug(this.getClass().getSimpleName(), "Instanciate");

        this.playScreen = playScreen;
        this.gameManager = gameManager;
    }

    public void calculateRestReward() {
        long diff = System.currentTimeMillis() - gameManager.gameInformation.lastLogin;
        float hours = (diff / (1000 * 60 * 60));

        if (hours >= Constants.DELAY_HOURS_REWARD) {
            // Calculer reward afk
        }
        rewardToCollect=true;
    }

    public AnimatedBaseActor getRestRewardActor(){
        Array<TextureRegion> frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture(files.internal("sprites/reward/chest1.png"))));
        frames.add(new TextureRegion(new Texture(files.internal("sprites/reward/chest1.png"))));
        frames.add(new TextureRegion(new Texture(files.internal("sprites/reward/chest1.png"))));
        Animation openAnimation = new Animation(2f, frames);

        AnimatedBaseActor restRewardActor= new AnimatedBaseActor();
        restRewardActor.storeAnimation("idle",openAnimation);
        // add idle animation with chest shaking each x sec "comme un reveil"
        restRewardActor.setVisible(true);
        return restRewardActor;
    }
}
