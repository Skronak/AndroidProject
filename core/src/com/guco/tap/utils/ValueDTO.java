package com.guco.tap.utils;

/**
 * Created by Skronak on 18/08/2017.
 * DTO de value et currency pour l'utilisation
 * de LargeMath
 */
public class ValueDTO {
    // value
    public float value;

    // currency
    public int currency;

    public ValueDTO(){}

    public ValueDTO(float value, int currency) {
        this.value = value;
        this.currency = currency;
    }
}
