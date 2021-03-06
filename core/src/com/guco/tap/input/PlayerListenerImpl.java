package com.guco.tap.input;

import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.SpriterPlayer;
import com.guco.tap.manager.GameManager;

public class PlayerListenerImpl implements SpriterPlayer.PlayerListener {

    SpriterPlayer spriterPlayerParent;
    GameManager gameManager;

    public PlayerListenerImpl(SpriterPlayer spriterPlayerParent, GameManager gameManager){
        this.spriterPlayerParent = spriterPlayerParent;
        this.gameManager = gameManager;
    }


    @Override
    public void animationFinished(Animation animation) {
        spriterPlayerParent.setAnimation("idle");
        spriterPlayerParent.animationFinished = true;
    }

    @Override
    public void animationChanged(Animation oldAnim, Animation newAnim) {
            spriterPlayerParent.animationFinished = false;
    }

    @Override
    public void preProcess(SpriterPlayer spriterPlayer) {
    }

    @Override
    public void postProcess(SpriterPlayer spriterPlayer) {
    }

    /**
     * Play hurt animation en enemy on middle of atk animation
     *
     * @param prevKey the previous mainline key
     * @param newKey the new mainline key
     */
    @Override
    public void mainlineKeyChanged(Mainline.Key prevKey, Mainline.Key newKey) {
        if (spriterPlayerParent.getAnimation().name.equals("atk")&&newKey.id==5) {
            gameManager.currentEnemyActor.hurt();
        }
    }
}
