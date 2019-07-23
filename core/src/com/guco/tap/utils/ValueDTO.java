package com.guco.tap.utils;

import com.brashmonkey.spriter.Value;

/**
 * Created by Skronak on 18/08/2017.
 * DTO de value et currency pour l'utilisation
 * de LargeMath
 */
public class ValueDTO implements Comparable<ValueDTO> {
    // value
    public float value;

    // currency
    public int currency;

    public ValueDTO(){}

    public ValueDTO(float value, int currency) {
        this.value = value;
        this.currency = currency;
    }

    @Override
    public int compareTo(ValueDTO o) {
        int res = 0;
        if (currency > o.currency) {
            res = 1;
        } else if (currency < o.currency) {
            res = -1;
        } else {
            if (value > o.value) {
                res = 1;
            } else if (value < o.value) {
                res = -1;
            } else {
                res = 0;
            }
        }
        return res;
    }
}
