package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Skronak on 01/02/2017.
 */
public class BitmapFontGenerator {
    private FreeTypeFontGenerator generator;
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

    public BitmapFontGenerator() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/"+Constants.FONT_STYLE+".ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Constants.HUD_TEXT_SIZE;
    }

    public BitmapFont getFont() {
        return generator.generateFont(parameter);
    }

    public void setGenerator(FreeTypeFontGenerator generator) {
        this.generator = generator;
    }

    public void dispose(){
        generator.dispose();
    }
}