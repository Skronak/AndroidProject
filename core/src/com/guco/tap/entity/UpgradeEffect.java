package com.guco.tap.entity;

public interface UpgradeEffect {
    void apply(Item item);
    void unapply(Item item);
}
