package com.guco.tap.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.guco.tap.entity.EnemyTemplateEntity;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.ValueDTO;

import java.io.File;

public class SpriterEnemyActor extends SpriterActor {
    private float actionTimer;
    private float actionDelay;
    private float damageDelay;
    private float attackDuration;
    private GameManager gameManager;
    private String name;
    private ValueDTO lifePoint;

    public SpriterEnemyActor(SpriteBatch spriteBatch, GameManager gameManager, String animationPath, float actionDelay, EnemyTemplateEntity enemyTemplateEntity, int dungeonLevel) {
        super(spriteBatch, animationPath+ File.separator+enemyTemplateEntity.getScmlFile());
        this.actionDelay = actionDelay;
        this.gameManager = gameManager;
        this.name = enemyTemplateEntity.getName();
        this.lifePoint = calculateHP(enemyTemplateEntity.getBaseHp(), dungeonLevel);
        this.attackDuration = enemyTemplateEntity.getAttackDuration();
    }

    public void update(float delta) {
        actionTimer += delta;
        if (actionTimer >= actionDelay) {
            actionTimer = 0;
            spriterPlayer.setAnimation("attack");
            isAttacking = true;
        }


        //anime le block cote joueur
        if (isAttacking) {
            damageDelay += delta;

            if (damageDelay >= attackDuration) {
                gameManager.handleEnemyAttack();
                damageDelay = 0;
                isAttacking = false;
            }
        }
    }

    private ValueDTO calculateHP(int baseHp, int level) {
        int currency = 0;
        ValueDTO life = new ValueDTO(baseHp*level, currency);
        return life;
    }
}
