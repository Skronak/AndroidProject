package com.brashmonkey.spriter;

import com.badlogic.gdx.Gdx;
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
        if (playerParent.getAnimation().name.equals("atk")) {
            playerParent.setAnimation("idle_1");
        }
    }

    @Override
    public void animationChanged(Animation oldAnim, Animation newAnim) {

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
