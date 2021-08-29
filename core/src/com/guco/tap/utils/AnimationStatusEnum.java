package com.guco.tap.utils;

public enum AnimationStatusEnum {
    IDLE("idle"),
    HIT("hit"),
    ATTACK("attack"),
    DIE("die"),
    BLOCK_HIT("block_hit"),
    WALK("walk");

    private String name;

    AnimationStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
