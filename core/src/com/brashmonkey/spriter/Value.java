package com.brashmonkey.spriter;

public class Value {
    Object value;

    public int getInt() {
        return (Integer)value;
    }

    public long getLong() {
        return (Long)value;
    }

    public String getString() {
        return (String)value;
    }
}
