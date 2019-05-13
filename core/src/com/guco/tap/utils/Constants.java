package com.guco.tap.utils;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by Skronak on 29/01/2017.
 */
public class Constants {

    public static final String CURRENT_VERSION = "build pre-alpha v1.0.1";

    public static final String APP_NAME = "TapTapDungeon";
    // Taille virtuelle verticale de l'application
    public static final int V_WIDTH = 320; //320

    // Taille virtuelle horizontale de l'application
    public static final int V_HEIGHT = 570; //570

    public static final int CAMERA_MAX_Y=295;

    public static final int CAMERA_MIN_Y=-600;

    // Taux generation gold offline
    public static float RATE_GENGOLD_OFFLINE = 0.5f;

    // Delay pour generation coffre rattrapage gold offline
    public static int DELAY_GENGOLD_LAST_CO = 300;

    // Delay pour declenchement save automatique
    public static int DELAY_AUTOSAVE = 5;

    // Taille du texte du HUD
    public static int HUD_TEXT_SIZE = 15;

    // Style du font utilise
    public static String FONT_STYLE = "stocky";

    // chance d'avoir un crit (1/10)
    public static int CRITICAL_CHANCE = 20;

    public static Color CRITICAL_LABEL_COLOR = Color.ROYAL;

    public static Color NORMAL_LABEL_COLOR = Color.WHITE;

    public static float UPDATE_MENU_PAD_EXTERNAL_HEIGHT = 40;

    public static float UPDATE_MENU_PAD_EXTERNAL_WIDTH = 20;

    public static int UPDATE_MENU_PAD_INTERNAL = 5;

    public static int PLAYSCREEN_MENU_BUTTON_HEIGHT = 52;

    // Nombre d'heure minimum pour toucher la recompense de repos
    public static int DELAY_HOURS_REWARD = 1;

    // Delai avant de generer de l'or automatiquement
    public static int DELAY_GENGOLD_PASSIV = 2;

    // Difference de currency pour passer le cout en free
    public static int UNLIMITED_CURRENCY_TRIGGER = 9;

    // Index de la lettre maximale atteignable
    public static int CURRENCY_SINGLE_MAX_INDEX= 14;

    // nombre de digit qu'on conserve des gold du joueur
    public static int GOLD_PLAYER_SIZE_STORAGE = 9;

    public static int STATION_ANIMATION_MAX_ALTITUDE = 370;//408;

    public static int STATION_ANIMATION_MIN_ALTITUDE = 363;//402;

    public static int DELAY_WEATHER_CHANGE = 15;

    public static int MAX_FPS = 60;

    public static int HEAD_ID=0;
    public static int BODY_ID=1;
    public static int WEAPON_ID=2;
    public static int MAX_CHARACTERMAP=4;
}
