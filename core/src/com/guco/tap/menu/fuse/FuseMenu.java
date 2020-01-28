package com.guco.tap.menu.fuse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;

public class FuseMenu extends AbstractMenu {
    private Label weaponCurrentLevelLabel;
    private Label weaponLevelLabel;
    private Label damageLabel;
    private Label criticalLabel;
    private Label manaLabel;
    private InventoryPanel inventoryPanel;

    public FuseMenu(GameManager gameManager) {
        super(gameManager);
        weaponCurrentLevelLabel = new Label("" + gameManager.gameInformation.currentWeapon.lvl, skin);
        weaponLevelLabel = new Label(""+gameManager.gameInformation.currentWeapon.lvl, skin);
        damageLabel = new Label(""+gameManager.gameInformation.currentWeapon.damage_value, skin);
        criticalLabel = new Label("", skin);
        manaLabel = new Label("", skin);
        inventoryPanel = new InventoryPanel();
        customizeMenuTable();
    }

    public void customizeMenuTable() {
        addMenuHeader("FUSE MENU", 2);
        Table contentTable = new Table();

        Image currentWeaponImage = new Image(new Texture(Gdx.files.local("sprites/icon/icon_sword_3.png")));
        Label.LabelStyle labelStyle = new Label.LabelStyle( gameManager.assetsManager.getFont(), Color.WHITE );
        weaponCurrentLevelLabel = new Label("Lv " + gameManager.gameInformation.currentWeapon.lvl, labelStyle);
        weaponCurrentLevelLabel.setBounds(currentWeaponImage.getX()-5, currentWeaponImage.getY()-10, currentWeaponImage.getWidth(), currentWeaponImage.getHeight()-weaponCurrentLevelLabel.getHeight());
        weaponCurrentLevelLabel.setAlignment(Align.topRight);
        Table table = new Table();
        table.add(currentWeaponImage);
        table.addActor(weaponCurrentLevelLabel);
        contentTable.add(table).colspan(3);
        contentTable.row().padTop(30);

        ClickListener emptySlotListener = new ClickListener(){
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.debug("","");
                return true;
            }};
        Image emptySlotImage1 = new Image(new Texture(Gdx.files.local("sprites/icon/empty_slot.png")));
        Image emptySlotImage2 = new Image(new Texture(Gdx.files.local("sprites/icon/empty_slot.png")));
        Image emptySlotImage3 = new Image(new Texture(Gdx.files.local("sprites/icon/empty_slot.png")));
        emptySlotImage1.addListener(emptySlotListener);

        contentTable.add(emptySlotImage1).size(70,70).padRight(20);
        contentTable.add(emptySlotImage2).size(70,70).padRight(20);
        contentTable.add(emptySlotImage3).size(70,70);
        contentTable.row();
        contentTable.add(weaponLevelLabel).left().padTop(25).expandX();
        contentTable.row();
        contentTable.add(damageLabel).left();
        contentTable.row();
        contentTable.add(criticalLabel).left();
        contentTable.row();
        contentTable.add(manaLabel).left();
        contentTable.row();
        TextButton textButton = new TextButton("FUSE",skin);
        contentTable.add(textButton).padTop(25).colspan(3);

        parentTable.add(contentTable).expandX();
        parentTable.add(inventoryPanel.mainTable);
    }

    @Override
    public void update(){
        update(0,0,0,0);
    }

    public void update(int newLvl, int newDamage, int newCritical, int newMana) {
        String lvl = "Level: " +gameManager.gameInformation.currentWeapon.lvl;
        String nextValue = " > ";
        if (newLvl==0){
            weaponLevelLabel.setText(lvl);
        } else {
            weaponLevelLabel.setText(lvl + nextValue +newLvl);
        }
        String dmg = "Damage: " +gameManager.gameInformation.currentWeapon.damage_value;
        if (newDamage == 0){
            damageLabel.setText(dmg);
        } else {
            damageLabel.setText(dmg + nextValue +newDamage);
        }
        String critical = "Critical: " +gameManager.gameInformation.currentWeapon.critical_currency;
        if (newDamage == 0){
            criticalLabel.setText(critical);
        } else {
            criticalLabel.setText(critical + nextValue +newDamage);
        }
        String mana = "Mana: ";
        if (newMana == 0){
            manaLabel.setText(mana);
        } else {
            damageLabel.setText(mana+ nextValue +mana);
        }
    }
}
