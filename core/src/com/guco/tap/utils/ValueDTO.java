package com.guco.tap.utils;

/**
 * Created by Skronak on 18/08/2017.
 * DTO de value et currency pour l'utilisation
 * de LargeMath
 */
public class ValueDTO {
    // value
    private float value;

    // currency
    private int currency;

    public ValueDTO(){}

    public ValueDTO(float value, int currency) {
        this.value = value;
        this.currency = currency;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

}
