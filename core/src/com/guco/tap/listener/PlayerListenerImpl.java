package com.guco.tap.listener;

import com.badlogic.gdx.Gdx;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.Player;
import com.guco.tap.screen.PlayScreen;

public class PlayerListenerImpl implements Player.PlayerListener {

    Player playerParent;
    PlayScreen playScreen;

    public PlayerListenerImpl(Player playerParent, PlayScreen playScreen){
        this.playerParent = playerParent;
        this.playScreen=playScreen;
    }


    @Override
    public void animationFinished(Animation animation) {
        playerParent.setAnimation("idle");
        playerParent.animationFinished = true;
    }

    @Override
    public void animationChanged(Animation oldAnim, Animation newAnim) {
            playerParent.animationFinished = false;
    }

    @Override
    public void preProcess(Player player) {
    }

    @Override
    public void postProcess(Player player) {
    }

    /**
     * Play hurt animation en enemy on middle of atk animation
     *
     * @param prevKey the previous mainline key
     * @param newKey the new mainline key
     */
    @Override
    public void mainlineKeyChanged(Mainline.Key prevKey, Mainline.Key newKey) {
        if (playerParent.getAnimation().name.equals("atk")&&newKey.id==5) {
            playScreen.enemyActorList.get(0).hurt();
        }
    }
}
