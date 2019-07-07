package com.guco.tap.entity;

public interface UpgradeEffect {
    void apply(ItemEntity itemEntity);
    void unapply(ItemEntity itemEntity);
}
