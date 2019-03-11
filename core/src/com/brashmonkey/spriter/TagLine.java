package com.brashmonkey.spriter;

public class TagLine {
    public Key[] keys;
    private int keyPointer=0;

    public TagLine(int keys){
        this.keys = new Key[keys];
    }

    public void addKey(Key key){
       this.keys[keyPointer++] = key;
    }
}
