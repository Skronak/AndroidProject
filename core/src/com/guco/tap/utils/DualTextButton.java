package com.guco.tap.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

/** A button with a child {@link Label} to display text.
 * @author Nathan Sweet */
public class DualTextButton extends Button {
    private Label label;
    private Label subLabel;
    private com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle style;

    public DualTextButton (String text, String subText, Skin skin) {
        this(text, subText, skin.get(com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle.class), skin);
        setSkin(skin);
    }

    public DualTextButton (String text, String subText, Skin skin, String styleName) {
        this(text, subText, skin.get(styleName, com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle.class),skin);
        setSkin(skin);
    }

    public DualTextButton (String text, String subText, com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle style, Skin skin) {
        super();
        setStyle(style);
        this.style = style;
        label = new Label(text, new LabelStyle(style.font, style.fontColor));
        subLabel = new Label(subText, new LabelStyle(style.font, style.fontColor));
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.addActor(label);
        verticalGroup.addActor(subLabel);
        add(verticalGroup).expand().fill();
        setSize(getPrefWidth(), getPrefHeight());
    }

    public void setStyle (ButtonStyle style) {
        if (style == null) throw new NullPointerException("style cannot be null");
        if (!(style instanceof com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle)) throw new IllegalArgumentException("style must be a TextButtonStyle.");
        super.setStyle(style);
        this.style = (com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle)style;
        if (label != null) {
            com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle textButtonStyle = (com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle)style;
            LabelStyle labelStyle = label.getStyle();
            labelStyle.font = textButtonStyle.font;
            labelStyle.fontColor = textButtonStyle.fontColor;
            label.setStyle(labelStyle);
        }
    }

    public com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle getStyle () {
        return style;
    }

    public void draw (Batch batch, float parentAlpha) {
        Color fontColor;
        if (isDisabled() && style.disabledFontColor != null)
            fontColor = style.disabledFontColor;
        else if (isPressed() && style.downFontColor != null)
            fontColor = style.downFontColor;
        else if (isChecked() && style.checkedFontColor != null)
            fontColor = (isOver() && style.checkedOverFontColor != null) ? style.checkedOverFontColor : style.checkedFontColor;
        else if (isOver() && style.overFontColor != null)
            fontColor = style.overFontColor;
        else
            fontColor = style.fontColor;
        if (fontColor != null) label.getStyle().fontColor = fontColor;
        super.draw(batch, parentAlpha);
    }

    public void setLabel (Label label) {
        getLabelCell().setActor(label);
        this.label = label;
    }

    public Label getLabel () {
        return label;
    }

    public Cell<Label> getLabelCell () {
        return getCell(label);
    }

    public void setText (String text) {
        label.setText(text);
    }

    public CharSequence getText () {
        return label.getText();
    }

    public String toString () {
        String name = getName();
        if (name != null) return name;
        String className = getClass().getName();
        int dotIndex = className.lastIndexOf('.');
        if (dotIndex != -1) className = className.substring(dotIndex + 1);
        return (className.indexOf('$') != -1 ? "TextButton " : "") + className + ": " + label.getText();
    }

    /** The style for a text button, see {@link com.badlogic.gdx.scenes.scene2d.ui.TextButton}.
     * @author Nathan Sweet */
    static public class TextButtonStyle extends ButtonStyle {
        public BitmapFont font;
        /** Optional. */
        public Color fontColor, downFontColor, overFontColor, checkedFontColor, checkedOverFontColor, disabledFontColor;

        public TextButtonStyle () {
        }

        public TextButtonStyle (Drawable up, Drawable down, Drawable checked, BitmapFont font) {
            super(up, down, checked);
            this.font = font;
        }

        public TextButtonStyle (com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle style) {
            super(style);
            this.font = style.font;
            if (style.fontColor != null) this.fontColor = new Color(style.fontColor);
            if (style.downFontColor != null) this.downFontColor = new Color(style.downFontColor);
            if (style.overFontColor != null) this.overFontColor = new Color(style.overFontColor);
            if (style.checkedFontColor != null) this.checkedFontColor = new Color(style.checkedFontColor);
            if (style.checkedOverFontColor != null) this.checkedOverFontColor = new Color(style.checkedOverFontColor);
            if (style.disabledFontColor != null) this.disabledFontColor = new Color(style.disabledFontColor);
        }
    }
}

