package com.guco.tap.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.guco.tap.entity.Enemy;
import com.guco.tap.utils.ValueDTO;

public class EnemyActor extends AnimatedBaseActor {
    public int hp;
    public ValueDTO lifePoint;
    public ValueDTO goldReward;
    public String name;

    public EnemyActor(Enemy enemy, int dungeonLevel){
        this.name = enemy.getName();
        this.hp = enemy.getHp();

        this.lifePoint = calculateHP(enemy.getHp(), dungeonLevel);
        this.setSize(enemy.getHeight(),enemy.getWidth());

        loadAnimation(enemy);
    }

    private ValueDTO calculateHP(int baseHp, int area) {
        int currency = 2%area ==0?area/2:(area-1)/2;//augmenter la currency tout les 2 niveaux
        ValueDTO life = new ValueDTO(baseHp*area, currency);
        return life;
    }

    private ValueDTO calculateGoldReward(int dungeonLevel){
        return null;
    }

    private void loadAnimation(Enemy enemy) {
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
