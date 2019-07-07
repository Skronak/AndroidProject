package com.guco.tap.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.guco.tap.entity.GameInformation;
import com.guco.tap.input.AchievementButtonListener;
import com.guco.tap.input.ClaimAchievementButtonListener;
import com.guco.tap.manager.AssetManager;
import com.guco.tap.manager.GameManager;

/**
 * Created by Skronak on 11/02/2017.
 */
public class AchievementMenu extends AbstractMenu {
    public Label titleLabel;
    public Label descriptionLabel;
    public Label skillPointLabel;
    public TextButton claimButton;
    public ClaimAchievementButtonListener claimAchievementButtonListener;

    private int max_element_row=3;
    private Table achievementTable;

    public AchievementMenu(GameManager gameManager) {
        super(gameManager);
        customizeMenuTable();
    }

    public void customizeMenuTable() {
        titleLabel = new Label(gameManager.achievementManager.achievementElementList.get(0).title,skin);
        titleLabel.setColor(Color.TEAL);
        descriptionLabel = new Label(gameManager.achievementManager.achievementElementList.get(0).description,skin);
        descriptionLabel.setWrap(true);
        descriptionLabel.setScale(0.8f);
        skillPointLabel = new Label(String.valueOf(gameManager.achievementManager.achievementElementList.get(0).skillPoint)+"SP",skin);
        claimButton = new TextButton("claim",gameManager.assetManager.getModuleMenuBuyTxtBtnStyle());
        claimAchievementButtonListener = new ClaimAchievementButtonListener(gameManager, this);
        claimButton.addListener(claimAchievementButtonListener);

        VerticalGroup scrollContainerVG = new VerticalGroup();
        scrollContainerVG.space(5f);
        ScrollPane.ScrollPaneStyle paneStyle = new ScrollPane.ScrollPaneStyle();
        paneStyle.hScroll = paneStyle.hScrollKnob = paneStyle.vScroll = paneStyle.vScrollKnob;
        paneStyle.vScrollKnob = new TextureRegionDrawable(new TextureRegion(gameManager.assetManager.getScrollTexture(), 10, 50));
        ScrollPane pane = new ScrollPane(scrollContainerVG, paneStyle);
        pane.setScrollingDisabled(true, false);
        achievementTable = new Table();
        int y=0; // count nb element per line
        for (int i = 0; i<gameManager.achievementManager.achievementElementList.size(); i++){
            Image achievElement = new Image(gameManager.assetManager.achievementTexture);
            achievElement.addListener(new AchievementButtonListener(this,gameManager.achievementManager.achievementElementList.get(i)));
            achievementTable.add(achievElement).width(50).height(50).pad(10);
            if (y<max_element_row) {
                y++;
            } else {
                y=0;
                achievementTable.row();
            }
        }

        scrollContainerVG.addActor(achievementTable);
        Table descriptionTable = new Table();
        descriptionTable.add(titleLabel).expand().fill();
        descriptionTable.row();
        descriptionTable.add(descriptionLabel).fillX();
        descriptionTable.add(claimButton).width(70).height(70);
        NinePatch patch = new NinePatch(gameManager.assetManager.grey9Texture,
                6,6, 6, 6);
        NinePatchDrawable background = new NinePatchDrawable(patch);
        descriptionTable.setBackground(background);

        parentTable.add(new Label("ACHIEVEMENT", skin)).center().padTop(10).padBottom(25);
        parentTable.row();
        parentTable.add(pane).grow().top();
        parentTable.row();
        parentTable.add(descriptionTable).fillX();
    }

    public void animateClaim(){
        claimButton.addAction(Actions.parallel(Actions.moveBy(100,1f), Actions.fadeOut(1f)));
    }
    @Override
    public void update() {
    }

    @Override
    public void show(){
        for (int i = 0; i<gameManager.achievementManager.achievementElementList.size(); i++) {
            if (gameManager.achievementManager.achievementElementList.get(i).isAchieved || gameManager.achievementManager.achievementElementList.get(i).condition.isAchieved()) {
                ((Image) achievementTable.getCells().get(i).getActor()).setDrawable(new TextureRegionDrawable(gameManager.assetManager.achievementTexture));
                achievementTable.getCells().get(i).getActor().setOrigin(achievementTable.getCells().get(i).getActor().getWidth()/2,achievementTable.getCells().get(i).getActor().getHeight()/2);

                // Animate elements to claim
                if (!gameManager.achievementManager.achievementElementList.get(i).isClaimed) {
                    gameManager.gameInformation.achievList.set(i,1);
                    if (gameManager.achievementManager.achievementElementList.get(i).isNew) {
                        achievementTable.getCells().get(i).getActor().addAction(
                                Actions.sequence(Actions.parallel(
                                        Actions.scaleBy(0.2f, 0.2f, 0.5f),
                                        Actions.repeat(2,
                                                Actions.sequence(Actions.rotateBy(-20, 0.2f),
                                                        Actions.rotateBy(20, 0.2f))
                                        )),
                                        Actions.sequence(Actions.scaleBy(-0.2f, -0.2f, 0.2f))));
                        gameManager.achievementManager.achievementElementList.get(i).isNew=false;
                    }
                } else {
                    gameManager.gameInformation.achievList.set(i,2);
                }
            }
        }
        super.show();
    }
}
