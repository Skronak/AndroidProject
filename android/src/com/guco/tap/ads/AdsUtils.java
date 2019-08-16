package com.guco.tap.ads;

import com.google.android.gms.ads.AdRequest;

public class AdsUtils {
    private static final String TEST_DEVICE = "5802FF93CB222F7E80F335A611AE88AC";
    public static String BANNER_ID="";
    public static String INTERSTITIAL_ID="";
    public static String BANNER_ID_TEST="ca-app-pub-3940256099942544/6300978111";
    public static String INTERSTITIAL_ID_TEST="\"ca-app-pub-3940256099942544/1033173712\"";


    public static AdRequest buildRequest(){
        return new AdRequest.Builder()
                .addTestDevice("5802FF93CB222F7E80F335A611AE88AC")
                .build();
    }
}
