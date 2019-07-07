package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.guco.tap.entity.GameInformation;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Skronak on 17/08/2017.
 * Classe utilitaire pour gerer
 * les tres larges montants du jeu
 */
public class LargeMath {

    private DecimalFormat decimalFormat;
    private GameInformation gameInformation;

    public LargeMath(GameInformation gameInformation) {
        this.gameInformation = gameInformation;
        decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        decimalFormat.applyPattern("##.##");
    }

    // Transforme index lettre en lettre
    public String printLetter(int currency) {
        String currencyString = "";
        int currencyLast = 0;

        if (currency ==0) {
            return currencyString;
        }

        // cas multi lettres
        if (currency > Constants.CURRENCY_SINGLE_MAX_INDEX) {
            currencyLast = currency%Constants.CURRENCY_SINGLE_MAX_INDEX;

            while (currency > Constants.CURRENCY_SINGLE_MAX_INDEX) {
                currency = (int) currency/Constants.CURRENCY_SINGLE_MAX_INDEX;
                if (currency >= Constants.CURRENCY_SINGLE_MAX_INDEX) {
                    currencyString += String.valueOf(getLetter(Constants.CURRENCY_SINGLE_MAX_INDEX));
                } else {
                    currencyString += String.valueOf(getLetter(currency));
                }
            }
//    	Calcule derniere lettre
            if (currencyLast >0) {
                currencyString += String.valueOf(getLetter(currencyLast));
            }
        } else {
            currencyString += getLetter(currency);
        }
        return currencyString;
    }

    /**
     * @param firstValueDTO
     * @param secondValueDTO
     * @return
     */
    public ValueDTO increaseValue(ValueDTO firstValueDTO, ValueDTO secondValueDTO) {
        ValueDTO newValueDTO = increaseValue(firstValueDTO.value, firstValueDTO.currency, secondValueDTO.value, secondValueDTO.currency);
        return newValueDTO;
    }

    /**
     * @param firstValue
     * @param firstCurrency
     * @param secValue
     * @param secCurrency
     * @return
     */
    public ValueDTO increaseValue(Float  firstValue, int firstCurrency, Float secValue, int secCurrency) {
        float newValue=firstValue;
        int currencyDifference = firstCurrency - secCurrency ;
        int maxCurrency = Math.max(firstCurrency, secCurrency);

        if (currencyDifference >= Constants.UNLIMITED_CURRENCY_TRIGGER && firstValue > secValue) {
            Gdx.app.debug("LargeMath", "Non significative value " + newValue + " currencyDif " + currencyDifference);
            return new ValueDTO(newValue, firstCurrency);
        } else if (currencyDifference <= -Constants.UNLIMITED_CURRENCY_TRIGGER && firstValue < secValue) {
            Gdx.app.debug("LargeMath", "Non significative value " + newValue + " currencyDif " + currencyDifference);
            return new ValueDTO(secValue, secCurrency);
        }
        if (currencyDifference == 0) {
            return new ValueDTO(firstValue + secValue, firstCurrency);
        }

        float valueRes=0;
        // cas firstValue > secValue
        if (maxCurrency==firstCurrency) {
            valueRes=(float) ((firstValue * Math.pow(1000, Double.valueOf(currencyDifference))) + (secValue));
            valueRes = (float) (valueRes / Math.pow(1000, Double.valueOf(currencyDifference)));
        } else {
            valueRes=(float) ((firstValue) + (secValue * Math.pow(1000, Double.valueOf(currencyDifference))));
            valueRes = (float) (valueRes / Math.pow(1000, Double.valueOf(currencyDifference)));
        }

        return new ValueDTO(valueRes, maxCurrency);
    }

    /**
     * Compare two ValueDTO and decrease the first one from the second one
     * @param firstValue
     * @param firstCurrency
     * @param secValue
     * @param secCurrency
     * @return
     */
    public ValueDTO decreaseValue(float firstValue, int firstCurrency, float secValue, int secCurrency) {
        float newValue=firstValue;
        int currencyDifference = firstCurrency - secCurrency ;
        int maxCurrency = Math.max(firstCurrency, secCurrency);

        if (currencyDifference >= Constants.UNLIMITED_CURRENCY_TRIGGER) {
            Gdx.app.debug("LargeMath", "Non significative value " + newValue + " currencyDif " + currencyDifference);
            return new ValueDTO(newValue, firstCurrency);
        } else if (currencyDifference <= -Constants.UNLIMITED_CURRENCY_TRIGGER) {
            Gdx.app.debug("LargeMath", "Non significative value " + newValue + " currencyDif " + currencyDifference);
            return new ValueDTO(secValue, secCurrency);
        }
        if (currencyDifference == 0) {
            return new ValueDTO(firstValue - secValue, firstCurrency);
        }

        float valueRes=0;
        // cas firstValue > secValue
        if (maxCurrency==firstCurrency) {
            valueRes =(float) ((firstValue * Math.pow(1000, Double.valueOf(currencyDifference))) - (secValue));
            valueRes = (float) (valueRes / Math.pow(1000, Double.valueOf(currencyDifference)));
        } else {
            valueRes =(float) ((firstValue) - (secValue * Math.pow(1000, Double.valueOf(currencyDifference))));
            valueRes = (float) (valueRes / Math.pow(1000, Double.valueOf(currencyDifference)));
        }

        return new ValueDTO(valueRes, maxCurrency);
    }
    /**
     * Format les valeurs de gameInformation pour
     * la sauvegarde
     * Similaire a adjustCurrency
     */
    public void formatGameInformation(){
        float value= gameInformation.currentGold;
        int currency=gameInformation.currentCurrency;
        while(value>=1000) {
            value=value/1000;
            currency+=1;
        }
        gameInformation.currentGold=value;
        gameInformation.currentCurrency=currency;
        //TODO formater pour que virgule ne passe pas la limite du systeme
    }

    /**
     * Divise la valeur et augmente la currency pour rester dans les limites du systeme
     * @param value
     * @param currency
     * @return
     */
    public ValueDTO adjustCurrency(Float value, int currency) {
        while(value>=1000) {
            value=value/1000;
            currency+=1;
        }
        return new ValueDTO(value, currency);
    }

    // Retourne la valeur afficher
    public String getDisplayValue(Float value, int currency) {
        ValueDTO valueDto = adjustCurrency(value, currency);
        return (decimalFormat.format(valueDto.value) + printLetter(valueDto.currency));
    }

    /// TODO : PERF TO ANALYSE REAL PLAYTIME: perf2 better than perf& atm///
    //public String getDisplayValue(Float value, int currency) {
    //    testPerf1(value, currency);
    //    testPerf2(value, currency);
    //    return "A";
    //}

    //public void testPerf1(Float value, int currency){
    //    long startTime = System.nanoTime();
    //    ValueDTO valueDto = adjustCurrency(value, currency);
    //    String r = (decimalFormat.format(valueDto.value) + printLetter(valueDto.currency));
    //    long endTime = System.nanoTime();
    //    long duration = (endTime - startTime);
    //    Gdx.app.log("Test1",String.valueOf(duration));
    //}

    //public void testPerf2(Float value, int currency){
    //    long startTime = System.nanoTime();
    //    ValueDTO valueDto = adjustCurrency(value, currency);
    //    if(value==valueDto.currency) {
    //        String r = (decimalFormat.format(valueDto.value) + getLetter(valueDto.currency));
    //    } else {
    //        String r = (decimalFormat.format(valueDto.value) + printLetter(valueDto.currency));
    //    }
    //    long endTime = System.nanoTime();
    //    long duration = (endTime - startTime);
    //    Gdx.app.log("Test2:",String.valueOf(duration));
    //}

    public String getDisplayValue(ValueDTO valueDTO) {
        ValueDTO valueDto = adjustCurrency(valueDTO.value, valueDTO.currency);
        return (decimalFormat.format(valueDto.value) + printLetter(valueDto.currency));
    }

    //    formatgameInformation();

    /**
     * Renvoie la lettre Majuscule associee a l'index passe en parametre
     * @param i
     * @return
     */
    public char getLetter(int i)
    {
        return (char) (i + 64);
    }
}

