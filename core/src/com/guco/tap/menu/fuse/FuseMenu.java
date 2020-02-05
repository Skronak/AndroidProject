package com.guco.tap.menu.fuse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.guco.tap.input.GemSlotListener;
import com.guco.tap.manager.GameManager;
import com.guco.tap.menu.AbstractMenu;

public class FuseMenu extends AbstractMenu {
    private Label weaponCurrentLevelLabel;
    private Label weaponLevelLabel;
    private Label damageLabel;
    private Label criticalLabel;
    private Label manaLabel;
    private ScrollPane inventoryPane;
    public InventoryPanel inventoryPanel;
    private Image[] slotedImage;
    private int selectedSlot;

    public FuseMenu(GameManager gameManager) {
        super(gameManager);
        weaponCurrentLevelLabel = new Label("" + gameManager.gameInformation.currentWeapon.lvl, skin);
        weaponLevelLabel = new Label(""+gameManager.gameInformation.currentWeapon.lvl, skin);
        damageLabel = new Label(""+gameManager.gameInformation.currentWeapon.damage_value, skin);
        criticalLabel = new Label("", skin);
        manaLabel = new Label("", skin);
        customizeMenuTable();
    }

    public void customizeMenuTable() {
        addMenuHeader("FUSE MENU", 2);
        Table contentTable = new Table();

        Image currentWeaponImage = new Image(new Texture(Gdx.files.internal("sprites/icon/icon_sword_3.png")));
//        currentWeaponImage.getDrawable().setMinHeight(70);
//        currentWeaponImage.getDrawable().setMinWidth(70);

        Table table = new Table();
        table.add(currentWeaponImage).size(90,90);

        Label.LabelStyle labelStyle = new Label.LabelStyle( gameManager.assetsManager.getFont(), Color.WHITE );
        weaponCurrentLevelLabel = new Label("Lv " + gameManager.gameInformation.currentWeapon.lvl, labelStyle);
        weaponCurrentLevelLabel.setBounds(-10, -60, currentWeaponImage.getWidth(), currentWeaponImage.getHeight()-weaponCurrentLevelLabel.getHeight());
        weaponCurrentLevelLabel.setAlignment(Align.topRight);
        table.addActor(weaponCurrentLevelLabel);
        contentTable.add(table).colspan(3);
        contentTable.row().padTop(15);

        Image fuseSlotImage1 = new Image(new Texture(Gdx.files.internal("sprites/icon/empty_slot.png")));
        fuseSlotImage1.addListener(new GemSlotListener(this));
        Image fuseSlotImage2 = new Image(new Texture(Gdx.files.internal("sprites/icon/empty_slot.png")));
        fuseSlotImage2.addListener(new GemSlotListener(this));
        Image fuseSlotImage3 = new Image(new Texture(Gdx.files.internal("sprites/icon/empty_slot.png")));
        fuseSlotImage3.addListener(new GemSlotListener(this));

        contentTable.add(fuseSlotImage1).size(70,70).padRight(20);
        contentTable.add(fuseSlotImage2).size(70,70).padRight(20);
        contentTable.add(fuseSlotImage3).size(70,70);
        contentTable.row();
        contentTable.add(weaponLevelLabel).left().padTop(5).expandX();
        contentTable.row();
        contentTable.add(damageLabel).left();
        contentTable.row();
        contentTable.add(criticalLabel).left();
        contentTable.row();
        contentTable.add(manaLabel).left();
        contentTable.row();
        TextButton textButton = new TextButton("FUSE",skin);
        contentTable.row();
        contentTable.add(textButton).bottom().colspan(3);

        parentTable.add(contentTable).expandX();
        parentTable.row();
        inventoryPanel = new InventoryPanel(gameManager);
        inventoryPane = inventoryPanel.pane;
        inventoryPane.setSize(parentTable.getWidth(),200);
        inventoryPane.setPosition(inventoryPane.getX(),-parentTable.getHeight());
        parentTable.addActor(inventoryPane);

        //debug ImageText
//        Image image = new Image(new Texture(Gdx.files.internal("sprites/icon/empty_slot.png")));
//        ImageText imageText = new ImageText(image,null,"1/20",skin);
//        contentTable.add(imageText).size(50,50);

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
