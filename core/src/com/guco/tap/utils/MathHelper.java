package com.guco.tap.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Skronak on 17/08/2017.
 * Classe utilitaire pour gerer
 * les tres larges montants du jeu
 */
public class MathHelper {

    private DecimalFormat decimalFormat;
    private BigDecimal currencyDividor;

    public MathHelper() {
        decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.ENGLISH);
        decimalFormat.applyPattern("##.##");
        currencyDividor =new BigDecimal(1000);
    }

    public String add(String v1, String v2) {
        String result;
        if (v1.length() - v2.length() < 6) {
            BigInteger res = new BigInteger(v1).add(new BigInteger(v2));
            result = res.toString();
            return result;
        } else {
            return v1;
        }
    }

    public String substract(String v1, String v2){
        String result;
        if (v1.length() - v2.length() > 6) {
            BigInteger res = new BigInteger(v1).subtract(new BigInteger(v2));
            result = res.toString();
            return result;
        } else {
            return v1;
        }
    }

    // Transforme index lettre en lettre
    public String transformCurrencyToLetter(int currency) {
        String currencyString = "";
        int nbLetter = 0;

        if (currency ==0) {
            return currencyString;
        }

        // cas multi lettres
        if (currency > Constants.CURRENCY_SINGLE_MAX_INDEX) {
            nbLetter = currency%Constants.CURRENCY_SINGLE_MAX_INDEX;
            if (nbLetter==0) {
                nbLetter=(currency)/Constants.CURRENCY_SINGLE_MAX_INDEX;
            }

            while (currency > Constants.CURRENCY_SINGLE_MAX_INDEX) {
                currency = currency/Constants.CURRENCY_SINGLE_MAX_INDEX;
                if (currency >= Constants.CURRENCY_SINGLE_MAX_INDEX) {
                    currencyString += String.valueOf(getLetter(Constants.CURRENCY_SINGLE_MAX_INDEX));
                } else {
                    currencyString += String.valueOf(getLetter(currency));
                }
            }
            //    	Calcule derniere lettre
            if (nbLetter >0) {
                currencyString += String.valueOf(getLetter(nbLetter));
            }
        } else {
            currencyString += getLetter(currency);
        }
        return currencyString;
    }

    public char getLetter(int i)
    {
        return (char) (i + 64);
    }

}

