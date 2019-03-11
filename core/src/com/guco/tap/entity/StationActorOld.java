package com.guco.tap.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.guco.tap.Animation.AnimatedActor;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Skronak on 23/07/2017.
 */
//TODO rendre la classe generique (module) et ajouter prise en charge atlas
public class StationActorOld extends Actor {
    private Animation idleAnimation;
    private Array<TextureRegion> frames;
    private float deltatime;
    private int width;
    private int height;
    private List<ModuleElement> moduleToDraw;
    private float animSpeed;
    private TextureRegion currentFrame;
    private AnimatedActor beamActor;
    private GameManager gameManager;

    public StationActorOld(int posX, int posY, int width, int height, float animSpeed, GameManager gameManager) {
        deltatime = 0;
        this.width = width;
        this.height = height;
        this.animSpeed = animSpeed;
        this.setPosition(posX, posY);
        this.gameManager = gameManager;
        moduleToDraw = loadUpgrade();
        beamActor = initBeam();

        //Animation par defaut de la station
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/station/ship"+ GameInformation.INSTANCE.getStationId()+"_0.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/station/ship"+ GameInformation.INSTANCE.getStationId()+"_1.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/station/ship"+ GameInformation.INSTANCE.getStationId()+"_2.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/station/ship"+ GameInformation.INSTANCE.getStationId()+"_3.png"))));
        idleAnimation = new Animation(animSpeed, frames);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    /**
     * Liste des upgrades a afficher
     * @return
     */
    public List<ModuleElement> loadUpgrade() {
        this.moduleToDraw = new ArrayList<ModuleElement>();
        for (int i=0;i<GameInformation.INSTANCE.getUpgradeLevelList().size();i++) {
            // Pour les upgrade 1-8, si lvl i > 0 alors ajoute dans liste a afficher
            if (GameInformation.INSTANCE.getUpgradeLevelList().get(i) > 0) {
                ModuleElement moduleElement = AssetManager.INSTANCE.getModuleElementList().get(i);
                moduleElement.setTextureRegion(new TextureRegion(new Texture(Gdx.files.internal(("sprites/upgrade/"+ moduleElement.getLevel().get(GameInformation.INSTANCE.getUpgradeLevelList().get(i)).getSprite())))));
                moduleToDraw.add(moduleElement);
            }
        }
        return moduleToDraw;
    }


    /**
     * Initialisation du beamActor de la station
     * @return
     */
    private AnimatedActor initBeam() {
        frames = new Array<TextureRegion>();
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/b1.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/b2.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/b3.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/b4.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/b5.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/b6.png"))));
        frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/b7.png"))));
        beamActor = new AnimatedActor(157,50, 28,(int)this.getY()-30, 0.2f, frames, Animation.PlayMode.LOOP);
        beamActor.setVisible(false);
        return beamActor;
    }

    @Override
    public void draw (Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        currentFrame = (TextureRegion) idleAnimation.getKeyFrame(deltatime, true);
        for (int i=0;i<moduleToDraw.size();i++) {
            batch.draw(moduleToDraw.get(i).getTextureRegion(), getX()+ moduleToDraw.get(i).getPosX(), getY() + moduleToDraw.get(i).getPosY(), moduleToDraw.get(i).getWidth(), moduleToDraw.get(i).getHeight());
        }
        batch.draw(currentFrame,getX(),getY(),width,height);
    }

    @Override
    public void act(float deltaTime)
    {
        super.act(deltaTime);
        deltatime += deltaTime;
    }

//*****************************************************
//                  GETTER & SETTER
//****************************************************
    public AnimatedActor getBeamActor() {
        return beamActor;
    }

    public void setBeamActor(AnimatedActor beamActor) {
        this.beamActor = beamActor;
    }

}
