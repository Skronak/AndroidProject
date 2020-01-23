package com.guco.tap.desktop;

import com.guco.tap.ad.AdController;

public class DesktopAdController implements AdController {

    @Override
    public void showRewardedAd() {
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public boolean isRewardEarned() {
        return false;
    }

    @Override
    public void claimReward() {
    }
}
