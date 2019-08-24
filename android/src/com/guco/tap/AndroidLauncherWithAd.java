package com.guco.tap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.guco.tap.ads.AdController;
import com.guco.tap.ads.AdsUtils;
import com.guco.tap.game.TapDungeonGame;

public class AndroidLauncherWithAd extends AndroidApplication implements AdController {
	private final String TAG = "AndroidLauncher";
	private AdView bannerView;
	private InterstitialAd interstitialAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 *         <activity android:name="com.google.android.gms.ads.AdActivity"
		 *             android:configChanges="keyboard|keyboardHidden|navigation|orientation|screenSize|screenLayout"
		 *             android:theme="@android:style/Theme.Translucent" />
		 */
		initAds();
		initUi();

	}

	private void initAds() {
		bannerView = new AdView(this);
		bannerView.setAdSize(AdSize.SMART_BANNER);
		bannerView.setAdUnitId(AdsUtils.BANNER_ID_TEST);
		bannerView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				int visiblity = bannerView.getVisibility();
				bannerView.setVisibility(AdView.GONE);
				bannerView.setVisibility(visiblity);
				Log.i(TAG, "Ad Loaded...");
			}
		});
		interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(AdsUtils.INTERSTITIAL_ID_TEST);
		interstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				//loadInterstitial();
			}
		});

		//loadInterstitial();

	}

	private void initUi(){
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		View gameView = initializeForView(new TapDungeonGame(true), config);

		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT
		);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

		RelativeLayout.LayoutParams gameParams = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.MATCH_PARENT
		);

		layout.addView(gameView,gameParams);
		layout.addView(bannerView, adParams);

		setContentView(layout);
	}

	@Override
	protected void onResume(){
		super.onResume();
		bannerView.resume();
	}

	@Override
	protected void onPause(){
		super.onPause();
		bannerView.pause();
	}

	@Override
	protected void onDestroy(){
		bannerView.destroy();
		super.onDestroy();
	}

	@Override
	public void showBanner() {
		bannerView.loadAd(AdsUtils.buildRequest());
	}

	@Override
	public void showInterstitial() {

	}

	@Override
	public boolean isNetworkConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo !=null &&  networkInfo.isConnected();
	}
}
