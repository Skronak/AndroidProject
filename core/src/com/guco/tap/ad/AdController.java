package com.guco.tap.ad;

public interface AdController {

    void showRewardedAd();

    boolean isNetworkConnected();

    boolean isRewardEarned();

    void claimReward();
}
