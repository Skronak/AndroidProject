package com.guco.tap.ad;

public interface AdHandler {

    void showAds(boolean show);

    boolean checkRewardStatus();

    boolean checkConnectivity();

    boolean isAdLoaded();
}
