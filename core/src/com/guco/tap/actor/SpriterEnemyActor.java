package com.guco.tap.actor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.brashmonkey.spriter.Point;
import com.guco.tap.entity.EnemyTemplateEntity;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.AnimationStatusEnum;
import com.guco.tap.utils.ValueDTO;

import java.io.File;

public class SpriterEnemyActor extends SpriterActor {
    private float actionTimer;
    private float actionDelay;
    private float damageDelay;
    private float attackDuration;
    private GameManager gameManager;
    public String name;
    public ValueDTO lifePoint;
    public boolean active;

    public SpriterEnemyActor(SpriteBatch spriteBatch, GameManager gameManager, String animationPath, float actionDelay, EnemyTemplateEntity enemyTemplateEntity, int dungeonLevel) {
        super(spriteBatch, animationPath+ File.separator+enemyTemplateEntity.getScmlFile());
        this.actionDelay = actionDelay;
        this.gameManager = gameManager;
        this.name = enemyTemplateEntity.getName();
        this.lifePoint = calculateHP(enemyTemplateEntity.getBaseHp(), dungeonLevel);
        this.attackDuration = enemyTemplateEntity.getAttackDuration();
        this.active = enemyTemplateEntity.isCanAttack();
    }

    public void update(float delta) {
        actionTimer += delta;
        if(active) {
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
    }

    public Point getPosition() {
        return spriterPlayer.getPosition();
    }

    private ValueDTO calculateHP(int baseHp, int level) {
        int currency = 0;
        ValueDTO life = new ValueDTO(baseHp*level, currency);
        return life;
    }

    public void die() {
        setAnimation(AnimationStatusEnum.DIE);
        this.addAction(Actions.sequence(Actions.delay(1), Actions.removeActor()));
    }

    public void renderBlack() {
        drawer.blackRender = true;
    }

    public void renderNormal() {
        drawer.blackRender = false;
    }
}
