package com.guco.tap.actor;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.guco.tap.manager.RessourceManager;
import com.guco.tap.utils.FlamEffectActor;


public class TorchActor extends Group {
    private FlamEffectActor flamEffectActor;
    private Image torchImage;

    public TorchActor(RessourceManager ressourceManager) {
        flamEffectActor = new FlamEffectActor(150,350);
        torchImage = new Image(ressourceManager.torchTexture);
        torchImage.setSize(20,50);
        torchImage.setPosition(140,290);

        addActor(torchImage);
        addActor(flamEffectActor);
    }

    public void start(){
        flamEffectActor.start();
    }

    public void stop(){
        flamEffectActor.stop();
    }
}
