package com.guco.tap.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.guco.tap.entity.EnemyTemplateEntity;
import com.guco.tap.utils.ValueDTO;

public class EnemyActor extends AnimatedBaseActor {
    public ValueDTO lifePoint;
    public String name;

    public EnemyActor(EnemyTemplateEntity enemyTemplateEntity, int dungeonLevel){
        this.name = enemyTemplateEntity.getName();

        this.lifePoint = calculateHP(enemyTemplateEntity.getBaseHp(), dungeonLevel);
        this.setSize(enemyTemplateEntity.getHeight(), enemyTemplateEntity.getWidth());

        loadAnimation(enemyTemplateEntity);
    }

    public EnemyActor() {
    }

    private ValueDTO calculateHP(int baseHp, int level) {
        int currency = 0;
        ValueDTO life = new ValueDTO(baseHp*level, currency);
        return life;
    }

     private void loadAnimation(EnemyTemplateEntity enemyTemplateEntity) {
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for (int i = 0; i< enemyTemplateEntity.getFramesIdle().size(); i++){
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/character/"+ enemyTemplateEntity.getFramesIdle().get(i)))));
        }
        Animation idleAnimation = new Animation(0.5f,frames);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        storeAnimation("idle", idleAnimation);

        frames = new Array<TextureRegion>();
        for (int i = 0; i< enemyTemplateEntity.getFramesHurt().size(); i++){
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/character/"+ enemyTemplateEntity.getFramesHurt().get(i)))));
        }
        Animation hurtAnimation = new Animation(0.08f,frames);
        storeAnimation("hurt", hurtAnimation);

        frames = new Array<TextureRegion>();
        for (int i = 0; i< enemyTemplateEntity.getFramesDeath().size(); i++){
            frames.add(new TextureRegion(new Texture(Gdx.files.internal("sprites/character/"+ enemyTemplateEntity.getFramesDeath().get(i)))));
        }
        Animation deathAnimation = new Animation(0.8f,frames);
        storeAnimation("death", deathAnimation);
    }

    public void hurt() {
        this.setActiveAnimation("hurt");
    }

    public void death(){
        this.setActiveAnimation("death");
    }

}
