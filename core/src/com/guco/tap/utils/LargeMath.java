package com.guco.tap.utils;

import com.badlogic.gdx.Gdx;
import com.guco.tap.entity.Item;

import java.math.BigDecimal;
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
    private BigDecimal currencyDividor;

    public LargeMath() {
        decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        decimalFormat.applyPattern("##.##");
        currencyDividor =new BigDecimal(1000);
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
        ValueDTO valueDTO;

        if (currencyDifference >= Constants.UNLIMITED_CURRENCY_TRIGGER && firstValue > secValue) {
            Gdx.app.debug("LargeMath", "Non significative value " + newValue + " currencyDif " + currencyDifference);
            return new ValueDTO(newValue, firstCurrency);
        } else if (currencyDifference <= -Constants.UNLIMITED_CURRENCY_TRIGGER && firstValue < secValue) {
            Gdx.app.debug("LargeMath", "Non significative value " + newValue + " currencyDif " + currencyDifference);
            return new ValueDTO(secValue, secCurrency);
        }
        if (currencyDifference == 0) {
            valueDTO = adjustCurrency(firstValue + secValue, firstCurrency);
            return valueDTO;
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
        valueDTO = adjustCurrency(valueRes, maxCurrency);
        return valueDTO;
    }

    public ValueDTO decreaseValue(ValueDTO decreaseValue, ValueDTO originValue) {
        return decreaseValue(decreaseValue.value, decreaseValue.currency, originValue.value, originValue.currency);
    }

        /**
         * Compare two ValueDTO and decrease the first one from the second one
         * @param baseValue
         * @param baseCurrency
         * @param substractValue
         * @param subtractCurrency
         * @return
         */
    public ValueDTO decreaseValue(float baseValue, int baseCurrency, float substractValue, int subtractCurrency) {
        float newValue=baseValue;
        int currencyDifference = baseCurrency - subtractCurrency ;
        int maxCurrency = Math.max(baseCurrency, subtractCurrency);

        if (currencyDifference >= Constants.UNLIMITED_CURRENCY_TRIGGER) {
            Gdx.app.debug("LargeMath", "Non significative value " + newValue + " currencyDif " + currencyDifference);
            return new ValueDTO(newValue, baseCurrency);
        } else if (-currencyDifference >= 1) {
            Gdx.app.debug("LargeMath", "Non significative value " + newValue + " currencyDif " + currencyDifference);
            return new ValueDTO(0, 0);
        } else if (currencyDifference == 0) {
            return new ValueDTO(baseValue - substractValue, baseCurrency);
        }

        float valueRes=0;
        // cas firstValue > secValue
        if (maxCurrency==baseCurrency) {
            valueRes = Math.round( ((baseValue * Math.pow(1000, Double.valueOf(currencyDifference))) - (substractValue)));
            valueRes = Math.round( (valueRes / Math.pow(1000, Double.valueOf(currencyDifference))));
        } else {
            valueRes =Math.round( ((baseValue) - (substractValue * Math.pow(1000, Double.valueOf(currencyDifference)))));
            valueRes = Math.round( (valueRes / Math.pow(1000, Double.valueOf(currencyDifference))));
        }

        return new ValueDTO(valueRes, maxCurrency);
    }

    /**
     * Divise la valeur et augmente la currency pour rester dans les limites du systeme
     * @param value
     * @param currency
     * @return
     */
    public ValueDTO adjustCurrency(BigDecimal value, int currency) {
        while(value.compareTo(currencyDividor) == 1) {
            value=value.divide(currencyDividor);
            currency+=1;
        }
        return new ValueDTO(value.floatValue(), currency);
    }

    public ValueDTO adjustCurrency(float value, int currency) {
        while(value > 1000) {
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
    public String getDisplayValue(ValueDTO valueDTO) {
        ValueDTO valueDto = adjustCurrency(valueDTO.value, valueDTO.currency);
        return (decimalFormat.format(valueDto.value) + printLetter(valueDto.currency));
    }

    /**
     * Renvoie la lettre Majuscule associee a l'index passe en parametre
     * @param i
     * @return
     */
    public char getLetter(int i)
    {
        return (char) (i + 64);
    }

    public ValueDTO calculateCost(float baseCost, float rateCost, int lvl) {
        BigDecimal bigDecimal = new BigDecimal(baseCost*(Math.pow(rateCost, lvl)));
        ValueDTO valueDTO = adjustCurrency(bigDecimal,0);
        return valueDTO;
    }

}

