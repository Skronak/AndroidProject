package com.guco.tap.dto;

import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.SpriterPlayer;

public class SpriterDto {
    public Data data;

    public Drawer drawer;

    public SpriterPlayer spriterPlayer;

    public SpriterDto(Data data, Drawer drawer) {
        this.data = data;
        this.drawer = drawer;
    }
}
