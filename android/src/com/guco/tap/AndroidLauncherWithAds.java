package com.guco.tap;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.guco.tap.ad.AdHandler;
import com.guco.tap.ads.AdsUtils;
import com.guco.tap.game.TapDungeonGame;

public class AndroidLauncherWithAds extends AndroidApplication implements AdHandler {
	private final String TAG = "AndroidLauncher";
	private RewardedAd rewardedAd;
	private final int HIDE_ADS=0;
	private final int SHOW_ADS=1;
	private RewardedAdCallback rewardedAdCallback;
	RewardedAdLoadCallback adLoadCallback;
	private boolean rewardEarned;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initAds();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch ((msg.what)) {
				case SHOW_ADS:
					showAds();
			}
		}
	};

	private void showAds() {
		rewardedAd.show(this, rewardedAdCallback);
	}

	private void initAds() {
		adLoadCallback = new RewardedAdLoadCallback() {
			@Override
			public void onRewardedAdLoaded() {
				Gdx.app.debug("AndroidLauncher","RewardedApp loaded");
			}
			@Override
			public void onRewardedAdFailedToLoad(int errorCode) {
				Gdx.app.debug("AndroidLauncher","RewardedApp failed to load");
			}
		};

		rewardedAd = new RewardedAd(this, AdsUtils.REWARDED_VIDEO_ID_TEST);
		rewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
		rewardedAdCallback = new RewardedAdCallback() {
			@Override
			public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
				rewardEarned=true;
			}
		};

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new TapDungeonGame(true, this), config);
	}

	public boolean isNetworkConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo !=null &&  networkInfo.isConnected();
	}

	@Override
	public void showAds(boolean show) {
		if (isNetworkConnected()) {
			handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
		}
	}

	@Override
	public boolean checkRewardStatus() {
		return rewardEarned;
	}

	@Override
	public boolean checkConnectivity() {
		return isNetworkConnected();
	}

	@Override
	public boolean isAdLoaded() {
		return rewardedAd.isLoaded();
	}
}
