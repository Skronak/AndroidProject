package com.guco.tap.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.guco.tap.Animation.AnimatedBaseActor;
import com.guco.tap.entity.Enemy;

public class EnemyActor extends AnimatedBaseActor {
    public int hp;
    public String name;

    public EnemyActor(Enemy enemy){
        loadFromModel(enemy);
    }

    public void loadFromModel(Enemy enemy) {
        this.name = enemy.getName();
        this.hp = enemy.getHp();
        this.setSize(enemy.getHeight(),enemy.getWidth());

        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i=0;i<enemy.getFramesIdle().size();i++){
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/character/"+enemy.getFramesIdle().get(i)))));
        }
        Animation idleAnimation = new Animation(0.5f,frames);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        storeAnimation("idle", idleAnimation);

        frames = new Array<TextureRegion>();
        for (int i=0;i<enemy.getFramesHurt().size();i++){
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/character/"+enemy.getFramesHurt().get(i)))));
        }
        Animation hurtAnimation = new Animation(0.08f,frames);
        storeAnimation("hurt", hurtAnimation);

        frames = new Array<TextureRegion>();
        for (int i=0;i<enemy.getFramesDeath().size();i++){
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/character/"+enemy.getFramesDeath().get(i)))));
        }
        Animation deathAnimation = new Animation(0.8f,frames);
        storeAnimation("death", deathAnimation);
    }

    public void hurt(){
        this.setActiveAnimation("hurt");
    }

    public void death(){
        this.setActiveAnimation("death");
    }

}
