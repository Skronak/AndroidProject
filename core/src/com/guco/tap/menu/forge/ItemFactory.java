package com.guco.tap.menu.forge;

import com.guco.tap.entity.GameInformation;
import com.guco.tap.entity.Item;
import com.guco.tap.entity.Skin;
import com.guco.tap.manager.GameManager;
import com.guco.tap.utils.LargeMath;
import com.guco.tap.utils.ValueDTO;

import java.util.Random;

public class ItemFactory {

    private final double RARITY_COMMON_RATE=0.75;
    private final double RARITY_UNCOMMON_RATE=0.2;
    private final double RARITY_RARE_RATE=0.1;
    private final double RARITY_UNIQ_RATE=0.05;
    private String[] NAME_PREFIX = {"Saber of ", "Sword of ", "The secret of ", "Silver", "Pain and ","Master of "};
    private String[] NAME = {"Pain", "Silence", "the dead", "sorrow", "the elder", "Storms", "Doom"};

    private LargeMath largeMath;
    private GameInformation gameInformation;

    private int baseDamage = 100;
    private int area = 5;
    private int forgeLvl=10;

    // AUTO FROM DATAMANAGER
    private String[] hiltSprites_A = {"hilt0","hilt1", "hilt2"};
    private String[] hiltSprites_B = {"hilt3","hilt4", "hilt5"};
    private String[] hiltSprites_C = {"hilt6","hilt7", "hilt8"};
    private String[] hiltSprites_D = {"hilt6","hilt7", "hilt8"};
    private String[] guardSprites_A = {"guard0","guard1", "guard2"};
    private String[] guardSprites_B = {"guard3","guard4", "guard5"};
    private String[] guardSprites_C = {"guard6","guard7", "guard8"};
    private String[] guardSprites_D = {"guard6","guard7", "guard8"};
    private String[] bladeSprites_A = {"blade0","blade1", "blade2"};
    private String[] bladeSprites_B = {"blade3","blade4", "blade5"};
    private String[] bladeSprites_C = {"blade6","blade7", "blade8"};
    private String[] bladeSprites_D = {"blade6","blade7", "blade8"};

    public ItemFactory(GameManager gameManager) {
        this.largeMath = gameManager.largeMath;
        this.gameInformation = gameManager.gameInformation;
    }

    public Item rollItem() {
        int itemGrade = rollRarity();
        ValueDTO stat = rollStat(itemGrade);
        ValueDTO cost = rollCost();
        Skin skin = rollSkin(itemGrade);
        String name = rollName();

        Item item = new Item();
        item.grade = itemGrade;
        item.damageRate=1.5f; //
        item.level=1;
        item.name=rollName();
        item.damageValue=stat.value;
        item.damageCurrency=stat.currency;
        item.baseCostValue=(int)cost.value;
        item.baseCostCurrency=cost.currency;
        item.skin = skin;

        return item;
    }

    public int rollRarity() {
        double gradeRoll = Math.random();

        int grade = 0;
        if (gradeRoll < RARITY_UNIQ_RATE) {
            grade=3;
        } else if (gradeRoll < RARITY_RARE_RATE) {
            grade = 2;
        } else if (gradeRoll < RARITY_UNCOMMON_RATE) {
            grade = 1;
        }
        return grade;
    }

    public ValueDTO rollStat(int grade) {
        float damageValue = baseDamage + (baseDamage*(1+(forgeLvl/10)*grade));
        damageValue = damageValue * (1+(grade*0.5f));

        int damageCurrency = area;
        ValueDTO damageVal = largeMath.adjustCurrency(damageValue, damageCurrency);
        return damageVal;
    }

    public ValueDTO rollCost() {
        int baseCost = 100;
        int area = 5;
        int forgeLvl=10;

        float costValue = baseCost - (baseCost*(1+(forgeLvl/100)));
        int costCurrency = area;

        return new ValueDTO(costValue, costCurrency);
    }

    public Skin rollSkin(int grade) {
        Skin skin = new Skin();
        switch(grade){
            case 0:
                skin.hiltMap=getRandom(hiltSprites_A);
                skin.guardMap=getRandom(guardSprites_A);
                skin.bladeMap=getRandom(bladeSprites_A);
                break;
            case 1:
                skin.hiltMap=getRandom(hiltSprites_B);
                skin.guardMap=getRandom(guardSprites_B);
                skin.bladeMap=getRandom(bladeSprites_B);
                break;
            case 2:
                skin.hiltMap=getRandom(hiltSprites_C);
                skin.guardMap=getRandom(guardSprites_C);
                skin.bladeMap=getRandom(bladeSprites_C);
                break;
            case 3:
                skin.hiltMap=getRandom(hiltSprites_D);
                skin.guardMap=getRandom(guardSprites_D);
                skin.bladeMap=getRandom(bladeSprites_D);
                break;
            default:
                skin.hiltMap=hiltSprites_A[0];
                skin.guardMap=guardSprites_A[0];
                skin.bladeMap=bladeSprites_A[0];
                break;
        }
        return skin;
    }

    private String getRandom(String[] array){
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    private String rollName() {
        return getRandom(NAME_PREFIX) + getRandom(NAME);
    }


}
