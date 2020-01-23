package com.guco.tap.ads;

import com.google.android.gms.ads.AdRequest;

public class AdsUtils {
    public static final String TEST_DEVICE = "5802FF93CB222F7E80F335A611AE88AC";
    public static String BANNER_ID="";
    public static String INTERSTITIAL_ID="";
    public static String BANNER_ID_TEST="ca-app-pub-3940256099942544/6300978111";
    public static String INTERSTITIAL_ID_TEST="ca-app-pub-3940256099942544/1033173712";
    public static String INTERSTITIAL_VIDEO_ID_TEST="ca-app-pub-3940256099942544/8691691433";
    public static String REWARDED_VIDEO_ID_TEST = "ca-app-pub-3940256099942544/5224354917";

    public static AdRequest buildRequest(){
        return new AdRequest.Builder()
                .addTestDevice(AdsUtils.TEST_DEVICE)
                .build();
    }
}
